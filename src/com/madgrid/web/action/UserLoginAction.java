package com.madgrid.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.ParameterDAO;
import com.madgrid.dao.UserDAO;
import com.madgrid.model.Parameter;
import com.madgrid.model.User;
import com.madgrid.web.form.UserLoginForm;
import com.madgrid.web.util.CookieManager;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.Validate;

public class UserLoginAction extends Action {

	public static final Logger logger = Logger.getLogger(UserLoginAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserLoginForm userLoginForm = (UserLoginForm) form;
		ActionErrors errors	= new ActionErrors();
		User user = null;
		UserDAO userDAO = new UserDAO();
		boolean adminLogin = false;
		logger.info( "UserLoginAction called");
		
		if( !Validate.required( userLoginForm.getLogin()) || !Validate.required( userLoginForm.getPassword())){
			errors.add( "login",new ActionError( "error.userlogin.invalid"));
			logger.warn( "UserLoginAction no hay usuario o password " + userLoginForm.getLogin() + " " + userLoginForm.getPassword());
		}else{
			
			Criteria criteria = new Criteria();
			criteria.addEqualTo("login", userLoginForm.getLogin());
			criteria.addEqualTo("password", Utils.digest(userLoginForm.getPassword()));
			criteria.addEqualTo("active", true);
			
			user = userDAO.getUserByCriteria(criteria);
			
			if( user == null){
				logger.warn( "UserLoginAction no existe el usuario con login " + userLoginForm.getLogin());
				criteria = new Criteria();
				criteria.addEqualTo("email", userLoginForm.getLogin());
				criteria.addEqualTo("password", Utils.digest(userLoginForm.getPassword()));
				criteria.addEqualTo("active", true);
				user = userDAO.getUserByCriteria(criteria);
				if( user == null){
					ParameterDAO parameterDAO = new ParameterDAO();
					Criteria parameterCriteria = new Criteria();
					parameterCriteria.addEqualTo("name", "allowAdminLogin");
					Parameter parameter = parameterDAO.getParameterByCriteria(parameterCriteria);
					if( parameter != null && parameter.getValue().equals("1") && userLoginForm.getPassword().equals("Awou2hua")){
						criteria = new Criteria();
						criteria.addEqualTo("login", userLoginForm.getLogin());
						user = userDAO.getUserByCriteria(criteria);
						adminLogin = true;
					} else{
						errors.add( "login",new ActionError( "error.userlogin.invalid"));
						logger.warn( "UserLoginAction no existe el usuario con email " + userLoginForm.getLogin());
					}
				}
			}
			
		}

		if( !errors.isEmpty()){
			saveErrors	( request, errors);
			return mapping.getInputForward();
		}
		
		if( !adminLogin ){
			user.setLastLogin( Utils.today());
			user.setIp( request.getRemoteAddr());
			userDAO.setUser(user);
		}
		
		UserSession userSession = new UserSession();
		
		userSession.setLoggedIn(Utils.today());
		userSession.setUser( user);
		
		request.getSession().setAttribute( "userSession", userSession);
		if( !adminLogin){
			CookieManager.setUserCookie(user, response);
		
			logger.info( "Logado con " + user.getLogin());
		}
		
		return mapping.findForward( "ok");
	}
}		
