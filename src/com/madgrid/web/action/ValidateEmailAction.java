package com.madgrid.web.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.PromotionDAO;
import com.madgrid.dao.UserDAO;
import com.madgrid.dao.UserHistoricDAO;
import com.madgrid.model.Promotion;
import com.madgrid.model.User;
import com.madgrid.model.UserHistoric;
import com.madgrid.web.util.CookieManager;
import com.madgrid.web.util.Mail;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.mail.AffiliatedUserMailObject;

public class ValidateEmailAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(ValidateEmailAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String login = request.getParameter("user");
		String email = request.getParameter("email");
		String code = request.getParameter("code");
		
		UserDAO userDAO = new UserDAO();
		Criteria criteria = new Criteria();
		criteria.addEqualTo("login", login);
		criteria.addEqualTo("email", email);
		criteria.addEqualTo("validated", false);
		criteria.addEqualTo("validationCode", code);
		
		User user = userDAO.getUserByCriteria(criteria);
		
		logger.info( "ValidateEmailAction called");
		
		if( user != null){
			user.setValidated(true);
			user.setValidationCode(null);
			
			PromotionDAO promotionDAO = new PromotionDAO();
			List<Promotion> promotionList = promotionDAO.getPromotionListByCriteria(new Criteria(), null);
			Date today = Utils.today();
			int promotionCredits = 0;
			for( Promotion promotion:promotionList){
				if( today.after( promotion.getStartDate()) && today.before( promotion.getEndDate())){
					if( promotion.getType() == Promotion.PROMOTION_TYPE_1_IN_REGISTER){
						promotionCredits = 1;
					} else if( promotion.getType() == Promotion.PROMOTION_TYPE_2_IN_REGISTER){
						promotionCredits = 2;
					} else if( promotion.getType() == Promotion.PROMOTION_TYPE_5_IN_REGISTER){
						promotionCredits = 5;
					} else if( promotion.getType() == Promotion.PROMOTION_TYPE_10_IN_REGISTER){
						promotionCredits = 10;
					} else if( promotion.getType() == Promotion.PROMOTION_TYPE_20_IN_REGISTER){
						promotionCredits = 20;
					} 
				}
			}
			
			if( user.getCompanyAffiliationUser() != null && user.getCompanyAffiliationUser().getLogin().equals("DINEROCONSTANTE")){
				promotionCredits = 1;
			}
			
			user.setCredits(user.getCredits() + promotionCredits);
			userDAO.setUser(user);
			
			if( promotionCredits != 0){
				UserHistoricDAO userHistoricDAO = new UserHistoricDAO();
				UserHistoric userHistoric = new UserHistoric();
				userHistoric.setCreated( Utils.today());
				userHistoric.setUser(user);
				userHistoric.setUserId(user.getId());
				userHistoric.setType( UserHistoric.GET_CREDITS_BY_PROMO);
				userHistoric.setValue1(new Double(promotionCredits));
				userHistoricDAO.setUserHistoric(userHistoric);
			}
			
			//Gestion de usuarios afiliados
			//DESACTIVADA
			
			
			if( user.getRecomendedUser() != null && !user.getRecomendedUser().equals("")){
				criteria = new Criteria();
				criteria.addEqualTo("login", user.getRecomendedUser());
				User recomendedUser = userDAO.getUserByCriteria(criteria);
				if( recomendedUser != null && recomendedUser.getValidated() && !recomendedUser.getIsFraudulent() && recomendedUser.getAffiliatedUsers() < 5 && !user.getIsFraudulent()){
					//recomendedUser.setCredits(recomendedUser.getCredits() + 1);
					recomendedUser.setAffiliatedUsers(recomendedUser.getAffiliatedUsers() + 1);
					
					userDAO.setUser(recomendedUser);
					
					/*userHistoricDAO = new UserHistoricDAO();
					UserHistoric recomendedUserHistoric = new UserHistoric();
					recomendedUserHistoric.setCreated( Utils.today());
					recomendedUserHistoric.setUser(recomendedUser);
					recomendedUserHistoric.setUserId(recomendedUser.getId());
					recomendedUserHistoric.setType( UserHistoric.GET_CREDIT_BY_AFFILIATE);
					recomendedUserHistoric.setValue1(1d);
					userHistoricDAO.setUserHistoric(recomendedUserHistoric);
					
					AffiliatedUserMailObject affiliatedUserMailObject = new AffiliatedUserMailObject( recomendedUser.getLogin(), recomendedUser.getEmail(), user.getRecomendedUser(), recomendedUser.getAffiliatedUsers(), Utils.getBaseUrl());
					
					Mail mail = new Mail( user.getEmail(), "You've won a credit thanks a new affiliated user in Instantri.ch.", affiliatedUserMailObject);
					mail.start();*/
				}
			}
			
			
			UserSession userSession = new UserSession();
			
			userSession.setLoggedIn(Utils.today());
			userSession.setUser( user);
			
			request.getSession().setAttribute( "userSession", userSession);
			CookieManager.setUserCookie(user, response);
			
			request.getSession().setAttribute("validationCorrect", "ok");
			
			
			return mapping.findForward( "ok");
		} else{
			logger.warn( "ValidateEmailAction there is no user " + login + " " + email + " " + code);
			return mapping.findForward( "ko");
		}
		
		
	}
	
}		
