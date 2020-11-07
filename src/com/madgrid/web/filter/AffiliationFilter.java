package com.madgrid.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ojb.broker.query.Criteria;

import com.madgrid.dao.AffiliationUserDAO;
import com.madgrid.model.AffiliationUser;



public class AffiliationFilter implements Filter {


    
    public void init(FilterConfig config) throws ServletException 
    { 
    }

    public void destroy() 
    {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
    	String ip = request.getRemoteAddr();
    	boolean error = false;
    	
    	if( !ip.equals("91.213.8.84") && !ip.equals("37.157.192.208") && !ip.equals("188.138.1.229") && !ip.equals("174.228.194.12") && !ip.equals("46.173.164.190") && !ip.equals("63.224.22.184") && !ip.equals("89.109.90.105") && !ip.equals("175.156.71.197") && !ip.equals("80.26.159.249")){
    		
	        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
	        	
	            HttpServletRequest httpRequest = (HttpServletRequest)request;
	            HttpSession session = httpRequest.getSession();
	            
	            String affiliate=null;
	            try{
	            	affiliate=(String)request.getParameter( "affiliate");
	            }catch (Exception e){
	            	error = true;
	            }
	            
	            if (affiliate != null && error == false){
	            	AffiliationUserDAO affiliationUserDAO = new AffiliationUserDAO();
	            	Criteria criteria = new Criteria();
	    			criteria.addEqualTo("login", affiliate);
	    			
	    			try{
		    			AffiliationUser affiliationUser = affiliationUserDAO.getAffiliationUserByCriteria(criteria);
						
						if( affiliationUser != null){
		            		session.setAttribute( "affiliate", affiliationUser.getLogin());
						}
	    			}catch (Exception e){}
	            }
	            
	        }
    	}

    	if( error == false && request != null && response != null){
    		chain.doFilter(request, response);
    	}
    }
 

}
