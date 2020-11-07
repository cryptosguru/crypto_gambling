package com.madgrid.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.UserDAO;

import com.madgrid.model.User;

public class UnsubscribeAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(UnsubscribeAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String login = request.getParameter("user");
		String email = request.getParameter("email");
		
		UserDAO userDAO = new UserDAO();
		Criteria criteria = new Criteria();
		criteria.addEqualTo("login", login);
		criteria.addEqualTo("email", email);
		
		User user = userDAO.getUserByCriteria(criteria);
		
		logger.info( "UnsubscribeAction called");
		
		if( user != null){
			user.setIsSubscribed(false);
			userDAO.setUser(user);
			
			request.setAttribute("email", user.getEmail());
			
			return mapping.findForward( "ok");
		} else{
			logger.warn( "UnsubscribeAction there is no user " + login + " " + email );
			return mapping.findForward( "ko");
		}
		
		
	}
	
}		
