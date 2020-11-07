package com.madgrid.web.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.madgrid.dao.GridDAO;
import com.madgrid.dao.PromoCodeDAO;
import com.madgrid.dao.PromotionDAO;
import com.madgrid.dao.UserDAO;
import com.madgrid.model.AffiliationActivity;
import com.madgrid.model.AffiliationUser;
import com.madgrid.model.Grid;
import com.madgrid.model.PromoCode;
import com.madgrid.model.Promotion;
import com.madgrid.model.User;
import com.madgrid.web.form.UserRegisterForm;
import com.madgrid.web.util.CookieManager;
import com.madgrid.web.util.Mail;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.Validate;
import com.madgrid.web.util.dwr.DwrTools;
import com.madgrid.web.util.mail.CompanyAffiliatedUserMailObject;
import com.madgrid.web.util.mail.UserRegisterForAdminMailObject;
import com.madgrid.web.util.mail.UserRegisterMailObject;
import com.madgrid.web.util.mail.UserRegisterNotValidatedMailObject;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class UserRegisterAction extends AbstractAction {
	
	public static final Logger logger = Logger.getLogger(UserRegisterAction.class);

	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserRegisterForm userRegisterForm = (UserRegisterForm) form;
		ActionErrors errors	= new ActionErrors();
		boolean error = false;
		logger.info( "UserRegisterAction called");
		
		UserSession userSession = super.checkUserSession(request);
		
		if( userSession != null){
			logger.warn( "UserRegisterAction called with userSession");
			return null;
		}
		
		
		if( !Validate.required( userRegisterForm.getLogin())){
			errors.add( "login2",new ActionError( "error.login.required"));
		}else{
			if( !Validate.loginShort( userRegisterForm.getLogin())){
				errors.add( "login2",new ActionError( "error.login.short"));
			} else{
				if( !Validate.loginLong( userRegisterForm.getLogin())){
					errors.add( "login2",new ActionError( "error.login.long"));
				}else{
					if( !Validate.loginCharacters( userRegisterForm.getLogin())){
						errors.add( "login2",new ActionError( "error.login.characters"));
					}else{
						UserDAO userDAO = new UserDAO();
						Criteria criteria = new Criteria();
						criteria.addEqualTo( "login", userRegisterForm.getLogin());
						if (userDAO.getUserByCriteria(criteria) != null){
							errors.add( "login2",new ActionError( "error.login.used"));
						} else{
							AffiliationUserDAO affiliationUserDAO = new AffiliationUserDAO();
							AffiliationUser affiliationUser = affiliationUserDAO.getAffiliationUserByCriteria(criteria);
							if( affiliationUser != null){
								errors.add( "login2",new ActionError( "error.login.used"));
							}
						}
					}
				}
			}
		}
			
		if( !Validate.required( userRegisterForm.getPassword())){
			errors.add( "password",new ActionError( "error.password.required"));
		} else{
			if( !Validate.passwordLong( userRegisterForm.getPassword())){
				errors.add( "password",new ActionError( "error.password.long"));
			} else{
				if( !Validate.passwordShort( userRegisterForm.getPassword())){
					errors.add( "password",new ActionError( "error.password.short"));
				}
			}
		}
		
		if( !Validate.required( userRegisterForm.getPassword2())){
			errors.add( "password2",new ActionError( "error.password2.required"));
		} else{
			if( !userRegisterForm.getPassword().equals( userRegisterForm.getPassword2())){
				errors.add( "password2",new ActionError( "error.password2.equals"));
			}
		}
		
		
		
		if( !Validate.required( userRegisterForm.getEmail())){
			errors.add( "email", new ActionError( "error.email.required"));
		} else{
			if( !Validate.emailShort( userRegisterForm.getEmail())){
				errors.add( "email", new ActionError( "error.email.short"));
			} else{
				if( !Validate.emailLong( userRegisterForm.getEmail())){
					errors.add( "email", new ActionError( "error.email.long"));
				} else{
					if( !Validate.emailValid( userRegisterForm.getEmail())){
						errors.add( "email", new ActionError( "error.email.valid"));
					} else{
						UserDAO userDAO = new UserDAO();
						Criteria criteria = new Criteria();
						criteria.addEqualTo( "email", userRegisterForm.getEmail());
						if (!userRegisterForm.getEmail().equals("amandris@hotmail.com") && userDAO.getUserByCriteria(criteria) != null){
							errors.add( "email", new ActionError( "error.email.used"));
						} else{
							criteria = new Criteria();
							criteria.addEqualTo( "canonicalEmail", userRegisterForm.getEmail().replaceAll("\\.", ""));
							if (!userRegisterForm.getEmail().equals("amandris@hotmail.com") && userDAO.getUserByCriteria(criteria) != null){
								errors.add( "email", new ActionError( "error.email.used"));
							}
							
						}
					}
						
				}
			}
		}
		
		if( userRegisterForm.getAutoPay()){
			if( !Validate.required( userRegisterForm.getBitcoinAddress())){
				errors.add( "bitcoinAddress", new ActionError( "error.bitcoinaddress.required"));
			} else{
				if( !Validate.bitcoinAddressShort( userRegisterForm.getBitcoinAddress())){
					errors.add( "bitcoinAddress", new ActionError( "error.bitcoinaddress.short"));
				} else{
					if( !Validate.bitcoinAddressLong( userRegisterForm.getBitcoinAddress())){
						errors.add( "bitcoinAddress", new ActionError( "error.bitcoinaddress.long"));
					} 
				}
			}
		}
		
		
		String remoteAddr = request.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LekMPASAAAAAJ_znmNTHFZxmIHx6Ofx8rtm8Sf3");

        String challenge = request.getParameter("recaptcha_challenge_field");
        String uresponse = request.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = null;
        try{
        	reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);
        }catch (Exception e){
        	error = true;
        }

        if (reCaptchaResponse != null && error == false && !reCaptchaResponse.isValid()) {
        	errors.add( "captcha", new ActionError( "error.captcha.valid"));
        }
		
        if( error == true){
        	return null;
        }
		if( !errors.isEmpty()){
			saveErrors	( request, errors);
			return mapping.getInputForward();
		}
		
		User user = new User();
		UserDAO userDAO = new UserDAO();
		PromotionDAO promotionDAO = new PromotionDAO();
		PromoCodeDAO promoCodeDAO = new PromoCodeDAO();
		
		user.setActive( true);
		user.setCreated( Utils.today());
		user.setEmail( userRegisterForm.getEmail());
		user.setCanonicalEmail( userRegisterForm.getEmail().replaceAll("\\.", ""));
		
		user.setLogin( userRegisterForm.getLogin());
		user.setModified( Utils.today());
		user.setPassword( Utils.digest(userRegisterForm.getPassword()));
		user.setPromoCode( userRegisterForm.getPromoCode());
		user.setAffiliatedUsers( 0);
		user.setCredits( 0);
		user.setIsBeginner( true);
		user.setIp( request.getRemoteAddr());
		user.setLastLogin( Utils.today());
		user.setIsSubscribed(true);
		user.setRequestPrizeSubscribed(true);
		user.setBitcoinSentSubscribed(true);
		user.setAutoPay( userRegisterForm.getAutoPay());
		if( userRegisterForm.getAutoPay()){
			user.setBitcoinAddress(userRegisterForm.getBitcoinAddress());
		} else{
			user.setBitcoinAddress(null);
		}
		
		boolean isFraudulent = false;
		for( String email:DwrTools.fraudulentEmails){
			if( userRegisterForm.getEmail().toLowerCase().contains(email)){
				isFraudulent = true;
				break;
			}
		}
		if( user.getPassword().equals("c30e01f0d1949406ffbe278c01704571707380a6c5aa07fb0f0286cd16f6e3b2") || user.getPassword().equals("9372a0ee98d9f9cda842617dac76796ea8c2df1b2a0a58c26f6bd637d00cceba") || request.getRemoteAddr().equals("121.54.54.224")){
			user.setIsFraudulent(true);
		} else{
			if(user.getEmail().replaceAll("\\.", "").contains( "pateloy123") || user.getEmail().replaceAll("\\.", "").contains( "markoppa12") ){
				user.setIsFraudulent(true);
			} else{
				user.setIsFraudulent(false);
			}
		}
		
		if( !Utils.nullOrBlank(userRegisterForm.getRecomendedUser())){
			Criteria criteria = new Criteria();
			criteria.addEqualTo("login", userRegisterForm.getRecomendedUser());
			AffiliationUserDAO affiliationUserDAO = new AffiliationUserDAO();
			AffiliationUser affiliationUser = affiliationUserDAO.getAffiliationUserByCriteria(criteria);
			
			if( affiliationUser != null){
				user.setCompanyAffiliationUser(affiliationUser);
				user.setCompanyAffiliationUserId(affiliationUser.getId());
				
				AffiliationActivityDAO affiliationActivityDAO = new AffiliationActivityDAO();
				AffiliationActivity affiliationActivity = new AffiliationActivity();
				affiliationActivity.setAffiliationUser(affiliationUser);
				affiliationActivity.setAffiliationUserId(affiliationUser.getId());
				affiliationActivity.setAssignedBitcoins(0d);
				affiliationActivity.setCreated(Utils.today());
				affiliationActivity.setTotalBitcoins(0d);
				affiliationActivity.setType( AffiliationActivity.AFFILIATION_ACTIVITY_USER_REGISTRATION);
				affiliationActivity.setUser(user);
				affiliationActivity.setUserId(user.getId());
				affiliationActivity.setAlreadyPayed(false);
				
				affiliationActivityDAO.setAffiliationActivity(affiliationActivity);
				
				if( affiliationUser.getSendEmailAlerts()){
					CompanyAffiliatedUserMailObject companyAffiliatedUserMailObject = new CompanyAffiliatedUserMailObject( affiliationUser.getLogin(), affiliationUser.getEmail(), user.getLogin(), Utils.getBaseUrl());
				
					Mail mail = new Mail( affiliationUser.getEmail(), "Good news, you have a new affiliated user on Instantri.ch", companyAffiliatedUserMailObject);
					mail.start();
				}
			} else{ 
				User recomendedUser = userDAO.getUserByCriteria(criteria);
				
				if( recomendedUser != null){
					user.setRecomendedUser(recomendedUser.getLogin());
				}
			} 
		}
		
		user.setStatisticsBoughtBoxes(0);
		user.setStatisticsPlayedGames(0);
		user.setStatisticsUsedCredits(0);
		user.setStatisticsWonGames(0);
		
		Criteria criteria = new Criteria();
		criteria.addEqualTo( "code", userRegisterForm.getPromoCode());
		criteria.addGreaterOrEqualThan( "count", 1);
		PromoCode promoCode = promoCodeDAO.getPromoCodeByCriteria(criteria);
		
		if( promoCode != null){
			user.setPromoCode( userRegisterForm.getPromoCode());
			promoCode.setCount( promoCode.getCount() - 1);
			promoCodeDAO.setPromoCode(promoCode);

			if( promoCode.getType() == PromoCode.PROMOCODE_TYPE_5_IN_REGISTER){
				user.setCredits( user.getCredits() + 5);
			} else if( promoCode.getType() == PromoCode.PROMOCODE_TYPE_10_IN_REGISTER){
				user.setCredits( user.getCredits() + 10);
			} else if( promoCode.getType() == PromoCode.PROMOCODE_TYPE_15_IN_REGISTER){
				user.setCredits( user.getCredits() + 15);
			} else if( promoCode.getType() == PromoCode.PROMOCODE_TYPE_20_IN_REGISTER){
				user.setCredits( user.getCredits() + 20);
			}
		}
		
		
		user.setValidationCode(generateCode());
		user.setValidated(false);
		
		userDAO.setUser( user);
		
		logger.info( "UserRegisterAction user registered " + user.getLogin());
		
		Criteria ipCriteria = new Criteria();
		ipCriteria.addEqualTo("ip", user.getIp());
		List<User> sameIpUserList = userDAO.getUserListByCriteria(ipCriteria);
		
		Criteria passwordCriteria = new Criteria();
		passwordCriteria.addEqualTo("password", user.getPassword());
		List<User> samePasswordUserList = userDAO.getUserListByCriteria(passwordCriteria);
		
		List<User> sameBitcoinAddressUserList = new ArrayList<User>();
		
		if( !Utils.nullOrBlank( user.getBitcoinAddress())){
			Criteria bitcoinAddressCriteria = new Criteria();
			bitcoinAddressCriteria.addEqualTo("bitcoinAddress", user.getBitcoinAddress());
			sameBitcoinAddressUserList = userDAO.getUserListByCriteria(bitcoinAddressCriteria);
		}
		
		String rewardUser = null;
		String affiliationUser = null;
		
		if( user.getCompanyAffiliationUser() != null){
			affiliationUser = user.getCompanyAffiliationUser().getLogin();
		} else if(user.getRecomendedUser() != null){
			rewardUser = user.getRecomendedUser();
		}
		
		
		//Comprobar si hay promoción de créditos de regalo al registrarse
		List<Promotion> promotionList = promotionDAO.getPromotionListByCriteria(new Criteria(), null);
		Date today = Utils.today();
		Integer promotionCredits = null;
		for( Promotion promotion:promotionList){
			if( today.after( promotion.getStartDate()) && today.before( promotion.getEndDate())){
				if( promotion.getType() == Promotion.PROMOTION_TYPE_1_IN_REGISTER){
					promotionCredits = new Integer(1);
				} else if( promotion.getType() == Promotion.PROMOTION_TYPE_2_IN_REGISTER){
					promotionCredits = new Integer(2);
				} else if( promotion.getType() == Promotion.PROMOTION_TYPE_5_IN_REGISTER){
					promotionCredits = new Integer(5);
				} else if( promotion.getType() == Promotion.PROMOTION_TYPE_10_IN_REGISTER){
					promotionCredits = new Integer(10);
				} else if( promotion.getType() == Promotion.PROMOTION_TYPE_20_IN_REGISTER){
					promotionCredits = new Integer(20);
				} 
			}
		}
		
		//Dar 1 crédito a los de dinero constante
		
		if( user.getCompanyAffiliationUser() != null && user.getCompanyAffiliationUser().getLogin().equals("DINEROCONSTANTE")){
			promotionCredits = new Integer(1);
		}
		
		
		UserRegisterForAdminMailObject userRegisterForAdminMailObject = new UserRegisterForAdminMailObject( user.getLogin(), user.getEmail(), user.getPassword(),user.getIp(), sameIpUserList, samePasswordUserList, sameBitcoinAddressUserList, isFraudulent, rewardUser, affiliationUser, Utils.getBaseUrl());
		
		Mail adminMail = new Mail( "amandris@hotmail.com", "Gus, se acaba de registrar un usuario.", userRegisterForAdminMailObject);
		adminMail.start();
		
		GridDAO gridDAO = new GridDAO();
		Criteria criteria2 = new Criteria();
		criteria2.addEqualTo( "finished", false);
		criteria2.addEqualTo( "ongoing", true);
		List<Grid> gridList = gridDAO.getGridListByCriteriaAndRange(criteria2, "startDate", 0, 6);
		
		Collections.sort( gridList, new Comparator<Grid>() {
			public int compare( Grid g1, Grid g2) {
				if( g1.getStartDate().before(g2.getStartDate())) return -1;
				if( g1.getStartDate().after(g2.getStartDate())) return 1;
				return 0;
			}
		});
		
		if( isFraudulent){
			UserRegisterNotValidatedMailObject userRegisterNotValidatedMailObject = new UserRegisterNotValidatedMailObject( user.getLogin(), user.getEmail(), gridList, Utils.getBaseUrl());
			
			Mail mail = new Mail( user.getEmail(), "Welcome to Instantri.ch", userRegisterNotValidatedMailObject);
			mail.start();
		} else{
			
			UserRegisterMailObject userRegisterMailObject = new UserRegisterMailObject( user.getLogin(), user.getEmail(), user.getValidationCode(), gridList, promotionCredits, Utils.getBaseUrl());
			
			Mail mail = new Mail( user.getEmail(), "Welcome to Instantri.ch", userRegisterMailObject);
			mail.start();
		}
		
		
			
		userSession = new UserSession();
		
		userSession.setLoggedIn(Utils.today());
		userSession.setUser( user);
		
		request.getSession().setAttribute( "userSession", userSession);
		CookieManager.setUserCookie(user, response);
		
		
		return mapping.findForward( "ok");
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
