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

import com.madgrid.dao.PromoCodeDAO;
import com.madgrid.dao.PromotionDAO;
import com.madgrid.dao.UserDAO;
import com.madgrid.model.PromoCode;
import com.madgrid.model.Promotion;
import com.madgrid.model.User;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;

public class CreditsValidateAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(CreditsValidateAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserSession userSession = super.checkUserSession(request);
		PromoCodeDAO promoCodeDAO = new PromoCodeDAO();
		Date today = Utils.today(); 
		
		logger.info( "CreditsValidateAction called");
		
		if( userSession == null){
			logger.warn( "CreditsValidateAction without userSession");
			return null;
		}
		
		String code = request.getParameter("code");
		
		logger.info( "CreditsValidateAction code=" + code);
		
		Criteria criteria = new Criteria();
		criteria.addEqualTo("code", code.toLowerCase());
		PromoCode promoCode = promoCodeDAO.getPromoCodeByCriteria(criteria);
		
		response.addHeader("pragma", "no-cache");
		response.addHeader("cache-control", "no-cache");
		response.addHeader("expires", "0");
		
		if( promoCode != null && promoCode.getCount()>= 1 && (promoCode.getType() >=5 && promoCode.getType() <= 9)){
			String text = "";
			
			logger.info( "CreditsValidateAction promoCode found " + promoCode.getId());
			criteria = new Criteria();
			PromotionDAO promotionDAO = new PromotionDAO();
			
			List<Promotion> promotionList = promotionDAO.getPromotionListByCriteria(criteria, null);
			
			for( Promotion promotion:promotionList){
				if( today.after( promotion.getStartDate()) && today.before( promotion.getEndDate())){
					if( promotion.getType() == Promotion.PROMOTION_TYPE_2x1 ){
						response.getWriter().print("0#You already have a 2x1 promotion if you buy some credits");
						return null;
					}
					
					if( promotion.getType() == Promotion.PROMOTION_TYPE_30_PERCENT_IN_BUY ){
						response.getWriter().print("0#You already have a 30% more credits promotion if you buy some credits");
						return null;
					}
					
					if( promotion.getType() == Promotion.PROMOTION_TYPE_20_PERCENT_IN_BUY ){
						response.getWriter().print("0#You already have a 20% more credits promotion if you buy some credits");
						return null;
					}
					
					if( promotion.getType() == Promotion.PROMOTION_TYPE_10_PERCENT_IN_BUY ){
						response.getWriter().print("0#You already have a 10% more credits promotion if you buy some credits");
						return null;
					}
					
					if( promotion.getType() == Promotion.PROMOTION_TYPE_10_IN_BUY ){
						response.getWriter().print("0#You already have a promotion if you buy some credits. You will get 10 credits for free.");
						return null;
					}
					
					if( promotion.getType() == Promotion.PROMOTION_TYPE_5_IN_BUY ){
						response.getWriter().print("0#You already have a promotion if you buy some credits. You will get 5 credits for free.");
						return null;
					}
					
					if( promotion.getType() == Promotion.PROMOTION_TYPE_1_IN_BUY ){
						response.getWriter().print("0#You already have a promotion if you buy some credits. You will get 1 credit for free.");
						return null;
					}
				}
			}
			
			
			switch( promoCode.getType()){
				case 5: text = "2x1";break;
				case 6: text = "50% more credits";break;
				case 7: text = "20% more credits";break;
				case 8: text = "10% more credits";break;
				case 9: text = "10 free credits";break;
			}
			
			
			response.getWriter().print(promoCode.getType() + "#" + text);
			
			UserDAO userDAO = new UserDAO();
			request.getSession().setAttribute("promoCode", promoCode);
			User user = userDAO.getUserById( userSession.getUser().getId());
			user.setBuyCreditsPromoCode(promoCode.getId().toString());
			
			switch (promoCode.getType()){
				case 5:user.setBuyCreditsPromoCodeText("2x1");break;
				case 6:user.setBuyCreditsPromoCodeText("50% more credits");break;
				case 7:user.setBuyCreditsPromoCodeText("20% more credits");break;
				case 8:user.setBuyCreditsPromoCodeText("10% more credits");break;
				case 9:user.setBuyCreditsPromoCodeText("10 free credits");break;
			}

			
			logger.info( "Promotion " + promoCode.getId() + " temoprary added to user " + user.getLogin() + "(" + user.getId() + ")");
			
			userDAO.setUser(user);
			return null;
		}
		

		response.getWriter().print("0#Promo code not valid");
		return null;
		
	}
}		
