package com.madgrid.web.action;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.UserDAO;
import com.madgrid.model.User;
import com.madgrid.web.util.CookieManager;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;

public class PasswordResetAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(PasswordResetAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String login = request.getParameter("login");
		String email = request.getParameter("email");
		String code = request.getParameter("code");
		
		UserDAO userDAO = new UserDAO();
		Criteria criteria = new Criteria();
		criteria.addEqualTo("login", login);
		criteria.addEqualTo("email", email);
		criteria.addEqualTo("codeForPasswordRestart", code);
		
		User user = userDAO.getUserByCriteria(criteria);
		
		logger.info( "PasswordResetAction called with " + login + " " + email + " " + code);
		
		Date today = Utils.today();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime( today);
		calendar.add(GregorianCalendar.HOUR, 24);
		
		if( user != null && user.getCodeForPasswordRestartCreated().getTime() <= calendar.getTimeInMillis()){
			UserSession userSession = new UserSession();
			
			userSession.setLoggedIn(Utils.today());
			userSession.setUser( user);
			
			request.getSession().setAttribute( "userSession", userSession);
			CookieManager.setUserCookie(user, response);
			
			return mapping.findForward( "ok");
		} else{
			logger.warn( "PasswordResetAction there is no user " + login + " " + email + " " + code);
			return mapping.findForward( "ko");
		}
		
		
	}
	
}		
