<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>



<%@page import="org.apache.struts.Globals"%>
<tiles:insert template='/WEB-INF/jsp/mainTile.jsp'>
	<tiles:put name='script' type='string'>
		$(document).ready(function(){
			$(".config_options").hide();
			$(".config_btn").click(function () {
				$(".config_options").slideToggle("slow");
			});
			
		});
		
		
	</tiles:put>
	<tiles:put name="onload" type="string">
	</tiles:put>
	<tiles:put name="body" type="string">
		<div id="pagina">
			<div style="height:800px;display:table;padding:30px 160px 30px 60px;">
		
			<img src="/img/500.png" style="padding:40px 0 0 55px;"/>
			<span style="padding-left:550px;">Art by <a href="http://sinergiasincontrol.blogspot.com" target="_blank" style="font-weight:bold;">Sinergia Sin Control</a></span> 
		
	    	<!--
	        <% Exception ex = (Exception) request.getAttribute("javax.servlet.error.exception");
	        if ( ex != null ){
	            java.io.PrintWriter pw = new java.io.PrintWriter(out);
	            ex.printStackTrace(pw);
	            ex.printStackTrace();
	            
	            if ( ex.getCause() != null ){
	                ex.getCause().printStackTrace(pw);
	                ex.getCause().printStackTrace();
	            }
	            if ( ex instanceof ServletException){
	                ServletException se = (ServletException) ex;
	                if ( se.getRootCause() != null ){
	                    se.getRootCause().printStackTrace(pw);
	                    se.getRootCause().printStackTrace();
	                }
	            }
	        }
	        ex = (Exception) request.getAttribute(Globals.EXCEPTION_KEY);
	        if ( ex != null ) {
	            out.println("Nested struts exception: ");
	            ex.printStackTrace(new java.io.PrintWriter(out));
	            ex.printStackTrace();
	        }
	        
	        %>
	        -->
	    	
	    	</div>
	    </div>
	</tiles:put>
</tiles:insert>


