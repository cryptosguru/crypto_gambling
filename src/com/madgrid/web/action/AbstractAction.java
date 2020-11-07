package com.madgrid.web.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.Action;

import com.madgrid.dao.ParameterDAO;
import com.madgrid.model.Parameter;
import com.madgrid.model.User;
import com.madgrid.web.util.CookieManager;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;

public abstract class AbstractAction extends Action {

	public UserSession checkUserSession( HttpServletRequest request) throws Exception 
	{
		UserSession userSession = (UserSession)request.getSession().getAttribute( "userSession");
		if( userSession == null){
			User user = CookieManager.getUserCookie( request);
			if( user != null){
				userSession = new UserSession();
				userSession.setLoggedIn( Utils.today());
				userSession.setUser( user);
				request.getSession().setAttribute( "userSession", userSession);
				return userSession;
			}
		} else{
			return userSession;
		}
		
		
		return null;
	}
	
	
	public String getBaseUrl() throws Exception
	{
		ParameterDAO parameterDAO = new ParameterDAO();
		Criteria criteria = new Criteria();
		criteria.addEqualTo("name", "baseUrl");
		Parameter parameter = parameterDAO.getParameterByCriteria( criteria);
		if( parameter != null){
			return parameter.getValue();
		}
		return null;
	}
}		
