package com.madgrid.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.UserDAO;
import com.madgrid.model.User;
import com.madgrid.web.form.UserEditForm;
import com.madgrid.web.util.UserSession;

public class UserEditAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(UserEditAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserSession userSession = super.checkUserSession(request);
		UserDAO userDAO = new UserDAO();
		
		logger.info( "UserEditAction called");
		
		if( userSession == null){
			logger.warn( "UserEditAction called without userSession");
			return null;
		}
		
		User user = userDAO.getUserById(userSession.getUser().getId());
		UserEditForm userEditForm = new UserEditForm();
		
		if( user != null){
			userEditForm.setId(user.getId());
			userEditForm.setEmail( user.getEmail());
			userEditForm.setId(user.getId());
			userEditForm.setLogin(user.getLogin());
			userEditForm.setBitcoinSentSubscribed( user.getBitcoinSentSubscribed());
			userEditForm.setRequestPrizeSubscribed( user.getRequestPrizeSubscribed());
			userEditForm.setAutoPay( user.getAutoPay());
			userEditForm.setBitcoinAddress( user.getBitcoinAddress());
		}
		
		request.setAttribute( "userEditForm", userEditForm);
		
		return mapping.findForward("ok");
	}
}		
