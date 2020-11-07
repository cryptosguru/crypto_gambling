package com.madgrid.web.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.WinDAO;
import com.madgrid.model.Win;
import com.madgrid.web.util.UserSession;

public class WinListAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(WinListAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		UserSession userSession = super.checkUserSession(request);
		WinDAO winDAO = new WinDAO();
		
		
		logger.info( "WinListAction called");
		
		if( userSession == null){
			logger.warn( "WinListAction called withou userSession");
			return mapping.findForward("session");
		}
		Criteria criteria = new Criteria();
		criteria.addEqualTo("userId", userSession.getUser().getId());
		List<Win> winList = winDAO.getWinListByCriteria(criteria, "created");
		
		request.setAttribute( "winList", winList);
		
		
		return mapping.findForward("ok");
	}
}		
