package com.madgrid.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.AffiliationUserDAO;
import com.madgrid.dao.PromoCodeDAO;
import com.madgrid.dao.UserDAO;
import com.madgrid.model.AffiliationUser;
import com.madgrid.model.PromoCode;
import com.madgrid.web.util.PasswordCheck;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.Validate;

public class ValidateAction extends AbstractAction {

	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String property = (String)request.getParameter("property");
		String value = (String)request.getParameter("value");
		String isEdit = (String)request.getParameter("isEdit");
		String id = (String)request.getParameter("id");

		response.addHeader("pragma", "no-cache");
		response.addHeader("cache-control", "no-cache");
		response.addHeader("expires", "0");
		String tooLong = "Too long";
		String tooShort = "Too short";
		String correct = "Correct";
		String incorrect = "Incorrect";
		String required = "Required";
		
		
		if( property != null && property.equals( "login")){
			if( !Validate.loginLong( value)){
				response.getWriter().print("<span style='color:red'>"+tooLong+"</span>");
				return null;
			}
			if( !Validate.loginShort( value)){
				response.getWriter().print("<span style='color:red'>"+tooShort+"</span>");
				return null;
			}
			if( !Validate.loginCharacters( value)){
				response.getWriter().print("<span style='color:red'>Incorrect characters</span>");
				return null;
			}
			UserDAO userDAO = new UserDAO();
			Criteria criteria = new Criteria();
			criteria.addEqualTo( "login", value);
			if( !Utils.nullOrBlank(isEdit) && isEdit.equalsIgnoreCase("true") && !Utils.nullOrBlank(id)){
				criteria.addNotEqualTo( "id", id);
			}
			if (userDAO.getUserByCriteria(criteria) != null){
				response.getWriter().print("<span style='color:red'>User already exists</span>");
				return null;
			}
			
			AffiliationUserDAO affiliationUserDAO = new AffiliationUserDAO();
			criteria = new Criteria();
			criteria.addEqualTo( "login", value);
			AffiliationUser affiliationUser = affiliationUserDAO.getAffiliationUserByCriteria(criteria);
			if( affiliationUser != null){
				response.getWriter().print("<span style='color:red'>User already exists</span>");
				return null;
			}
			response.getWriter().print("<span style='color:green'>"+correct+"</span>");
			return null;
		}
		
		if( property != null && property.equals( "oldPassword")){
			String value2 = (String)request.getParameter("value2");
			UserDAO userDAO = new UserDAO();
			Criteria criteria = new Criteria();
			criteria.addEqualTo( "password", Utils.digest(value));
			criteria.addEqualTo( "id", value2);
			if (userDAO.getUserByCriteria(criteria) != null){
				response.getWriter().print("<span style='color:green'>"+correct+"</span>");
				return null;
			}
			
			response.getWriter().print("<span style='color:red'>"+incorrect+"</span>");
			return null;
		}
		
		if( property != null && property.equals( "password")){
			if( !Validate.passwordLong( value)){
				response.getWriter().print("<span style='color:red'>"+tooLong+"</span>");
				return null;
			}
			if( !Validate.passwordShort( value)){
				response.getWriter().print("<span style='color:red'>"+tooShort+"</span>");
				return null;
			}
		
			int intScore = PasswordCheck.CheckPasswordStrength( value);
			String strength = "";
			if (intScore < 16) {
				strength = "<span style='color:orangeRed;'>Very insecure</span>";
			} else if (intScore > 15 && intScore < 25) {
				strength = "<span style='color:orange;'>Insecure</span>";
			} else if (intScore > 24 && intScore < 35) {
				strength = "<span style='color:green;'>Average security</span>";
			} else if (intScore > 34 && intScore < 45) {
				strength = "<span style='color:green;'>Secure</span>";
			} else {
				strength = "<span style='color:darkgreen;'>Very secure</span>";
				}
			
			response.getWriter().print(strength);
			return null;
		}
		
		if( property != null && property.equals( "password2")){
			String value2 = (String)request.getParameter("value2");
			if( !value.equals( value2)){
				response.getWriter().print("<span style='color:red'>Passwords do not match</span>");
				return null;
			}
			response.getWriter().print("<span style='color:green'>"+correct+"</span>");
			return null;
		}
		
	
		
		
		if( property != null && property.equals( "email")){
			if( !Validate.emailShort( value)){
				response.getWriter().print("<span style='color:red'>"+tooShort+"</span>");
				return null;
			}
			if( !Validate.emailLong( value)){
				response.getWriter().print("<span style='color:red'>"+tooLong+"</span>");
				return null;
			}
			if( !Validate.emailValid( value)){
				response.getWriter().print("<span style='color:red'>"+incorrect+"</span>");
				return null;
			}
			
			UserDAO userDAO = new UserDAO();
			Criteria criteria = new Criteria();
			criteria.addEqualTo( "email", value);
			if( !Utils.nullOrBlank(isEdit) && isEdit.equalsIgnoreCase("true") && !Utils.nullOrBlank(id)){
				criteria.addNotEqualTo( "id", id);
			}
			if (userDAO.getUserByCriteria(criteria) != null){
				response.getWriter().print("<span style='color:red'>Email already exists</span>");
				return null;
			}
			
			criteria = new Criteria();
			criteria.addEqualTo( "canonicalEmail", value.replace("\\.", ""));
			if( !Utils.nullOrBlank(isEdit) && isEdit.equalsIgnoreCase("true") && !Utils.nullOrBlank(id)){
				criteria.addNotEqualTo( "id", id);
			}
			if (userDAO.getUserByCriteria(criteria) != null){
				response.getWriter().print("<span style='color:red'>Email already exists</span>");
				return null;
			}
			
			response.getWriter().print("<span style='color:green'>"+correct+"</span>");
			return null;
		}
		
		if( property != null && property.equals( "bitcoinAddress")){
			if( !Validate.required( value)){
				response.getWriter().print("<span style='color:red'>"+required+"</span>");
				return null;
			}
			
			if( !Validate.bitcoinAddressShort( value)){
				response.getWriter().print("<span style='color:red'>"+tooShort+"</span>");
				return null;
			}
			if( !Validate.bitcoinAddressLong( value)){
				response.getWriter().print("<span style='color:red'>"+tooLong+"</span>");
				return null;
			}
			
			response.getWriter().print("<span style='color:green'>"+correct+"</span>");
			return null;
		}
		
		
		
		if( property != null && property.equals( "promoCode")){
			PromoCodeDAO promoCodeDAO = new PromoCodeDAO();
			Criteria criteria = new Criteria();
			criteria.addEqualTo("code", value);
			criteria.addGreaterOrEqualThan("count", 1);
			
			PromoCode promoCode = promoCodeDAO.getPromoCodeByCriteria(criteria);
			
			if( promoCode == null){
				response.getWriter().print("<span style='color:red'>"+incorrect+"</span>");
				return null;
			} 
			String promoCodeDescription = "";
			
			switch( promoCode.getType()){
				case 1:promoCodeDescription="5 free credits when signing up";break;
				case 2:promoCodeDescription="10 free credits when signing up";break;
				case 3:promoCodeDescription="15 free credits when signing up";break;
				case 4:promoCodeDescription="20 free credits when signing up";break;
				default: response.getWriter().print("<span style='color:red'>"+incorrect+"</span>");return null;
			}
			
			response.getWriter().print("<span style='color:green'>"+promoCodeDescription+"</span>");
			return null;
		}
		
		
		return null;
		
	}

}		
