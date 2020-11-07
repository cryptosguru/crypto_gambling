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

public class MarkAsFraudulentAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(MarkAsFraudulentAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String login = request.getParameter("user");
		String password = request.getParameter("password");
		
		
		UserDAO userDAO = new UserDAO();
		Criteria criteria = new Criteria();
		criteria.addEqualTo("login", login);
		criteria.addEqualTo("password", password);
	
		
		User user = userDAO.getUserByCriteria(criteria);
		
		logger.info( "ValidateEmailAction called");
		
		if( user != null){
			user.setIsFraudulent(true);
			userDAO.setUser(user);
		}
		return null;

	}
	
}		
