package com.madgrid.web.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.web.util.Utils;

public class LogAction extends AbstractAction {

	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String value = (String)request.getParameter("value");

		String ip = request.getRemoteAddr();
		Date now = Utils.today();
		
		String place = "";
		if( ip.equals("80.26.159.249") || ip.equals("80.26.159.251")){
			place =" (oficina)";
		}
		if( ip.equals("77.228.91.164")){
			place =" (casa)";
		}
		if( ip.equals("81.39.153.200")){
			place =" (casa 2)";
		}

		if( place.equals("")){
			System.out.println("--Date= " + now.toGMTString() + "  IP: " + ip + "  URL: " + value );
		}
		
		return null;
		
	}

}		
