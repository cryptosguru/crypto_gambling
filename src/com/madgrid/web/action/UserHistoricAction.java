package com.madgrid.web.action;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

public class UserHistoricAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(UserHistoricAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserSession userSession = super.checkUserSession(request);
		UserHistoricDAO userHistoricDAO = new UserHistoricDAO();
		
		logger.info( "UserHistoricAction called");
		
		if( userSession == null){
			logger.warn( "UserHistoricAction called without userSession");
			return null;
		}
		
		Criteria criteria = new Criteria();
		criteria.addEqualTo("userId", userSession.getUser().getId());
		List<UserHistoric> fullUserHistoricList = userHistoricDAO.getUserHistoricListByCriteriaAndRange(criteria, "created", 0, 100);
		int count = userHistoricDAO.getUserHistoricCountByCriteria(criteria);
		
		Collections.sort( fullUserHistoricList, new Comparator<UserHistoric>() {
			public int compare( UserHistoric g1, UserHistoric g2) {
				if( g1.getCreated().before(g2.getCreated())) return 1;
	            if( g1.getCreated().after(g2.getCreated())) return -1;
	            return g2.getType().compareTo(g1.getType());
	        }
		});
		 
		request.setAttribute( "fullUserHistoricList", fullUserHistoricList);
		
		String lastDate = null;
		
		if( fullUserHistoricList.size() >= 1){
			lastDate = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss").format( fullUserHistoricList.get( fullUserHistoricList.size() - 1).getCreated());
		}
		
		request.setAttribute( "lastDate", lastDate);
		request.setAttribute( "isLastPage", count <= fullUserHistoricList.size());
		
		return mapping.findForward("ok");
	}
}		
