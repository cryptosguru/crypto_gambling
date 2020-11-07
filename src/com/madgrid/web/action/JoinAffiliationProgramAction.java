package com.madgrid.web.action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ojb.broker.query.Criteria;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.AffiliationUserDAO;
import com.madgrid.dao.UserDAO;

import com.madgrid.model.AffiliationUser;
import com.madgrid.model.User;
import com.madgrid.web.util.Mail;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.mail.AffiliationUserRequestMailObject;

public class JoinAffiliationProgramAction extends AbstractAction {

	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserSession userSession = super.checkUserSession(request);
		
		if( userSession == null ){
			request.setAttribute("affiliationResultText", "We cannot create you affiliation user. You are not an Instantri.ch user yet.");
			request.setAttribute("affiliationResult", "Error");
			return mapping.findForward("ok");
		}
		
		
		
		AffiliationUserDAO affiliationUserDAO = new AffiliationUserDAO();
		UserDAO userDAO = new UserDAO();
		
		User user = userDAO.getUserById( userSession.getUser().getId());
		
		if( user == null || user.getValidated() == false){
			request.setAttribute("affiliationResultText", "We cannot create you affiliation user. Your email account was not validated.");
			request.setAttribute("affiliationResult", "Error");
			return mapping.findForward("ok");
		}
		
		String login = userSession.getUser().getLogin();
		String email = userSession.getUser().getEmail();
		
		if( !Utils.nullOrBlank(login) && !Utils.nullOrBlank( email)){
			
			Criteria criteria = new Criteria();
			criteria.addEqualTo("email", email);
			
			if( affiliationUserDAO.getAffiliationUserByCriteria(criteria) != null){
				request.setAttribute("affiliationResultText", "We cannot create your affiliation user. You already are in our affilition program.");
				request.setAttribute("affiliationResult", "Error");
				
				return mapping.findForward("ok");
			}
			
			criteria = new Criteria();
			criteria.addEqualTo("login", login);
			
			if( affiliationUserDAO.getAffiliationUserByCriteria(criteria) != null){
				request.setAttribute("affiliationResultText", "We cannot create your affiliation user. You already are in our affilition program.");
				request.setAttribute("affiliationResult", "Error");
				return mapping.findForward("ok");
			}
			
			
			try{
				AffiliationUser affiliationUser = new AffiliationUser();
				affiliationUser.setAskedForTransfer( false);
				affiliationUser.setBitcoinAddress("Not assigned");
				affiliationUser.setCreated(Utils.today());
				affiliationUser.setLastLogin(Utils.today());
				affiliationUser.setEmail(email);
				affiliationUser.setLogin(login);
				affiliationUser.setModified( Utils.today());
				affiliationUser.setName( login);
				affiliationUser.setPassword(user.getPassword());
				affiliationUser.setPercentage(15d);
				affiliationUser.setCodeForPasswordRestart(null);
				affiliationUser.setCodeForPasswordRestartCreated(null);
				affiliationUser.setCreditAlreadyClaimed(false);
				affiliationUser.setPaymentRequestValidated( false);
				affiliationUser.setSendEmailAlerts(true);
				affiliationUser.setUrl( "Not assigned");
				
				affiliationUserDAO.setAffiliationUser(affiliationUser);
				
				AffiliationUserRequestMailObject affiliationuserRequestMailObject = new AffiliationUserRequestMailObject(login, Utils.getBaseUrl());
				
				Mail mail = new Mail( email, "You've joined Instantri.ch affiliation program", affiliationuserRequestMailObject);
				mail.start();
				
				request.setAttribute("affiliationResultText", "Your affiliation user was successfully created. Soon you'll receive an email with the instructions to access your affiliation control panel.");
				request.setAttribute("affiliationResult", "Congratulations");
				return mapping.findForward("ok");

			}catch (Exception e){
				request.setAttribute("affiliationResultText", "We cannot create your affiliation user. There was an error.");
				request.setAttribute("affiliationResult", "Error");
				
				return mapping.findForward("ok");
			}
		}
		
		request.setAttribute("affiliationResultText", "We cannot create your affiliation user. There was an error.");
		request.setAttribute("affiliationResult", "Error");
		
		return mapping.findForward("ok");
		
		
	}

}		
