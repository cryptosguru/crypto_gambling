
package com.madgrid.web.util;

import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.madgrid.dao.UserDAO;
import com.madgrid.model.User;


public class CookieManager {

    public static void setUserCookie(User user, HttpServletResponse response) 
    {
    	String stringToDigest = user.getId()+"gc677";
		String token =  Utils.digest( stringToDigest);	
    	
        Cookie userCookie = new Cookie("instantrich", user.getId() + "|" + token);
        userCookie.setMaxAge(2592000);
        userCookie.setPath("/");

        response.addCookie(userCookie);
    }
    
    public static User getUserCookie(HttpServletRequest request) throws Exception
    {
    	String value = "";
    	
    	Cookie cookie = getCookie( request, "instantrich");
    	if( cookie != null){
    		value = (cookie.getValue());
    	}
    	
    	StringTokenizer tokens = new StringTokenizer( value, "|");
    	String id = null;
    	String digestString = null;
    	if( tokens.hasMoreTokens()){
    		id = tokens.nextToken();
    	}
    	if( tokens.hasMoreTokens()){
    		digestString = tokens.nextToken();
    	}
    	
    	if( id != null){
    		Integer idInteger = Integer.parseInt( id);
	    	UserDAO userDAO = new UserDAO();
	    	User user = userDAO.getUserById( idInteger);
	    	if( user != null && digestString != null){
	    		if( user.getActive() && Utils.digest( user.getId()+"gc677").equals( digestString)){
	    			
	    			user.setLastLogin(Utils.today());
	    			user.setIp( request.getRemoteAddr());
	    			userDAO.setUser(user);
	    			
	    			return user;
	    		} else{
	    			return null;
	    		}
	    	}
    	}
    	
    	return null;
    	
    	
    }

    public static Cookie getCookie(HttpServletRequest request, String cookieName) 
    {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName))
                   return cookie;
            }
        }
        return null;
    }
}
