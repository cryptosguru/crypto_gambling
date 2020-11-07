package com.madgrid.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.UserDAO;
import com.madgrid.model.User;
import com.madgrid.web.form.PasswordForm;
import com.madgrid.web.util.Mail;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.mail.NewPasswordMailObject;

public class PasswordAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(PasswordAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		logger.info( "PasswordAction called");
		PasswordForm passwordForm = (PasswordForm) form;
		UserDAO userDAO = new UserDAO();
		UserSession userSession = super.checkUserSession(request);
		
		if( userSession != null){
			logger.warn( "PasswordAction called without userSession");
			return null;
		}

		Criteria criteria = new Criteria();
		criteria.addEqualTo("email", passwordForm.getEmail());
		User user = userDAO.getUserByCriteria(criteria);
		
		if( user != null){
			String code = generateCode();
			
			user.setCodeForPasswordRestart(code);
			user.setCodeForPasswordRestartCreated(Utils.today());
			
			userDAO.setUser(user);
			
			NewPasswordMailObject newPasswordMailObject = new NewPasswordMailObject( user.getLogin(), code, user.getEmail(), Utils.getBaseUrl());
		
			Mail mail = new Mail( user.getEmail(), "You have resquested a password reseting on Instantri.ch.", newPasswordMailObject);
			mail.start();
			
			logger.info( "PasswordAction restart password for user " + user.getLogin() + " (" + user.getId() + ")");
			
		} else{
			ActionErrors errors	= new ActionErrors();
			errors.add( "email",new ActionError( "error.password.notFound"));
			saveErrors	( request, errors);
			logger.warn( "PasswordAction user with email " + passwordForm.getEmail() + " not found");
			return mapping.getInputForward();
		}
		
		
		return mapping.findForward( "ok");
	}
	
	private String generateCode() 
	{
		String 	chars 	= "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String 	code	= "";
		long 	size	= 32;
		
		while( code.length() < size){
	      code = code + chars.charAt( ( int) Math.round( Math.random() * ( chars.length() - 1)));
		}
		
		return code;
	}
}		
