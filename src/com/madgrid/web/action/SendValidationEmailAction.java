package com.madgrid.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.AffiliationActivityDAO;
import com.madgrid.dao.AffiliationUserDAO;
import com.madgrid.dao.PromoCodeDAO;
import com.madgrid.dao.PromotionDAO;
import com.madgrid.dao.UserDAO;
import com.madgrid.dao.WinDAO;
import com.madgrid.model.AffiliationActivity;
import com.madgrid.model.AffiliationUser;
import com.madgrid.model.PromoCode;
import com.madgrid.model.Promotion;
import com.madgrid.model.User;
import com.madgrid.model.Win;
import com.madgrid.web.form.UserRegisterForm;
import com.madgrid.web.util.CookieManager;
import com.madgrid.web.util.Mail;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.Validate;
import com.madgrid.web.util.dwr.DwrTools;
import com.madgrid.web.util.mail.CompanyAffiliatedUserMailObject;
import com.madgrid.web.util.mail.SendValidationMailObject;
import com.madgrid.web.util.mail.UserRegisterForAdminMailObject;
import com.madgrid.web.util.mail.UserRegisterMailObject;
import com.madgrid.web.util.mail.UserRegisterNotValidatedMailObject;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class SendValidationEmailAction extends AbstractAction {
	
	public static final Logger logger = Logger.getLogger(SendValidationEmailAction.class);

	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserSession userSession = super.checkUserSession(request);
		
		if( userSession == null){
			return null;
		}
		
		UserDAO userDAO = new UserDAO();
		User user = userDAO.getUserById(userSession.getUser().getId());
		
		if( user != null && user.getIsFraudulent()){
			response.getWriter().print("<span style='color:red'>Validation email not sent. Your user is marked as fraudulent.</span>");
			return null;
		}
		
		if( user != null && user.getValidated() == false){
			boolean isFraudulent = false;
			for( String email:DwrTools.fraudulentEmails){
				if( user.getEmail().toLowerCase().contains(email)){
					isFraudulent = true;
					break;
				}
			}
			
			if( isFraudulent == false){
				user.setValidationCode(generateCode());
				user.setValidated(false);
				
				userDAO.setUser( user);
				
				synchronized (this) {
					SendValidationMailObject sendValidationMailObject = new SendValidationMailObject( user.getLogin(), user.getEmail(), user.getValidationCode(), Utils.getBaseUrl());
					
					Mail mail = new Mail( user.getEmail(), "Click on this link to validate your Instantri.ch account.", sendValidationMailObject);
					mail.start();
					
					Thread.sleep(2500);
					
					response.getWriter().print("<span style='color:green'>Validation email sent</span>");
					return null;
				}
				
			} else{
				response.getWriter().print("<span style='color:red'>Validation email not sent. You have a disposable email address.</span>");
				return null;
			}
		}
		
		
		response.getWriter().print("<span style='color:red'>Validation email not sent. There was a problem with your user.</span>");
		return null;
	}
	
	private String generateCode() 
	{
		String 	chars 	= "23456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ";
		String 	code	= "";
		long 	size	= 32;
		
		while( code.length() < size){
	      code = code + chars.charAt( ( int) Math.round( Math.random() * ( chars.length() - 1)));
		}
		
		return code;
	}
	
}		
