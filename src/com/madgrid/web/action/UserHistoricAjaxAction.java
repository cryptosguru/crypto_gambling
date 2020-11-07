package com.madgrid.web.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.UserHistoricDAO;
import com.madgrid.model.UserHistoric;
import com.madgrid.web.util.UserSession;

public class UserHistoricAjaxAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(UserHistoricAjaxAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserSession userSession = super.checkUserSession(request);
		UserHistoricDAO userHistoricDAO = new UserHistoricDAO();
		
		logger.info( "UserHistoricAjaxAction called");
		
		if( userSession == null){
			logger.warn( "UserHistoricAjaxAction called withou userSession");
			return null;
		}

		List<UserHistoric> fullUserHistoricList = (List<UserHistoric>)request.getSession().getAttribute( "fullUserHistoricList");
		
		if( fullUserHistoricList == null){
			Criteria criteria = new Criteria();
			criteria.addEqualTo("userId", userSession.getUser().getId());
			fullUserHistoricList = userHistoricDAO.getUserHistoricListByCriteria(criteria, "created");
			
			Collections.sort( fullUserHistoricList, new Comparator<UserHistoric>() {
				public int compare( UserHistoric g1, UserHistoric g2) {
					if( g1.getCreated().before(g2.getCreated())) return 1;
		            if( g1.getCreated().after(g2.getCreated())) return -1;
		            return g2.getType().compareTo(g1.getType());
		        }
			});
		}

		String start = request.getParameter("start");
		String end = request.getParameter("end");
		
		GregorianCalendar startCalendar = new GregorianCalendar();
		GregorianCalendar endCalendar = new GregorianCalendar();
		
		logger.info( "UserHistoricAjaxAction start " + start);
		logger.info( "UserHistoricAjaxAction end " + end);
		
		try{
			startCalendar.setTimeInMillis(Long.parseLong( start));
			endCalendar.setTimeInMillis(Long.parseLong( end));
	
			startCalendar.set( GregorianCalendar.HOUR, 0);
			startCalendar.set( GregorianCalendar.MINUTE, 0);
			startCalendar.set( GregorianCalendar.SECOND, 0);
			startCalendar.set( GregorianCalendar.MILLISECOND, 0);
			endCalendar.set( GregorianCalendar.HOUR, 23);
			endCalendar.set( GregorianCalendar.MINUTE, 59);
			endCalendar.set( GregorianCalendar.SECOND, 59);
			endCalendar.set( GregorianCalendar.MILLISECOND, 999);
			
			List<UserHistoric> userHistoricList = new ArrayList<UserHistoric>();
			
			for( UserHistoric userHistoric: fullUserHistoricList){
				if( userHistoric.getCreated().getTime()>=startCalendar.getTimeInMillis() && userHistoric.getCreated().getTime()<=endCalendar.getTimeInMillis()){
					userHistoricList.add( userHistoric);
				}
			}
			
			request.setAttribute( "userHistoricList", userHistoricList);
		} catch (NumberFormatException e){
			logger.error( "UserHistoricAjaxAction parse error", e);
		}
		
		return mapping.findForward("ok");
	}
}		
