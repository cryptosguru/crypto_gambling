package com.madgrid.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.UserDAO;
import com.madgrid.model.User;
import com.madgrid.web.form.PasswordResetForm;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.Validate;

public class PasswordResetSaveAction extends AbstractAction {

	
	public static final Logger logger = Logger.getLogger(PasswordResetSaveAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserSession userSession = super.checkUserSession(request);
		UserDAO userDAO = new UserDAO();
		
		logger.info( "PasswordResetSaveAction called");
		
		if( userSession == null){
			logger.warn( "PasswordResetSaveAction called without userSession");
			return null;
		}
		
		PasswordResetForm passwordResetForm = (PasswordResetForm) form;
		
		ActionErrors errors	= new ActionErrors();
		
		if( !Utils.nullOrBlank( passwordResetForm.getPassword())){
			if( !Validate.passwordLong( passwordResetForm.getPassword())){
				errors.add( "password",new ActionError( "error.password.long"));
			} else{
				if( !Validate.passwordShort( passwordResetForm.getPassword())){
					errors.add( "password",new ActionError( "error.password.short"));
				}
			}
			
			if( !Validate.required( passwordResetForm.getPassword2())){
				errors.add( "password2",new ActionError( "error.password2.required"));
			} else{
				if( !passwordResetForm.getPassword().equals( passwordResetForm.getPassword2())){
					errors.add( "password2",new ActionError( "error.password2.equals"));
				}
			}
		}
		
		if( !errors.isEmpty()){
			saveErrors	( request, errors);
			return mapping.getInputForward();
		}
		
		User user = userDAO.getUserById( userSession.getUser().getId());
		
		user.setModified( Utils.today());
		user.setCodeForPasswordRestart(null);
		user.setCodeForPasswordRestartCreated(null);
		
		if( !Utils.nullOrBlank(passwordResetForm.getPassword())){
			user.setPassword( Utils.digest(passwordResetForm.getPassword()));
		}
		
		logger.info( "PasswordResetSaveAction updated user " + user.getLogin());
		
		userDAO.setUser( user);
		
		return mapping.findForward("ok");
	}
	
	
}		
