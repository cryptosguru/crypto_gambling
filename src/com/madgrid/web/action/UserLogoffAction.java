package com.madgrid.web.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.web.util.CookieManager;

public class UserLogoffAction extends Action {

	public static final Logger logger = Logger.getLogger(UserLogoffAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		request.getSession().removeAttribute( "userSession");
		request.getSession().invalidate();
		
		logger.info( "UserLogoffAction called");
		
		Cookie cookie = CookieManager.getCookie(request, "instantrich");
		
		if( cookie != null){
			cookie.setMaxAge(0);
			cookie.setPath("/");
			cookie.setValue( "");
		    response.addCookie( cookie);
		    
		}
		
		Thread.sleep( 100);
		
		return mapping.findForward( "ok");
	}
}		
