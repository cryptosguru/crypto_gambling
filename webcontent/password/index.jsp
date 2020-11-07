<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>



<%@page import="com.madgrid.web.util.UserSession"%>
<tiles:insert template='/WEB-INF/jsp/mainTile.jsp'>
	<tiles:put name='script' type='string'>
		$(document).ready(function(){
		
			<%
			UserSession userSession = (UserSession)session.getAttribute( "userSession");
			if( userSession != null){
			%>
				document.location.href = "/error/500.jsp";
			<%
			}
			%>
		
			$(".config_options").hide();
			$(".config_btn").click(function () {
				$(".config_options").slideToggle("slow");
			});
		});
		
	</tiles:put>
	<tiles:put name="onload" type="string">
	</tiles:put>
	<tiles:put name="body" type="string">
		<%
		UserSession userSession = (UserSession)session.getAttribute( "userSession");
		if( userSession == null){%>
		<div id="pagina" style="height:800px;">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/password"><bean:message key="password.breadcrumb"/></a></li>
	        </ul>
	        <h2 class="tituloProducto"><bean:message key="password.title"/></h2>
	        <html:form action="/password/save">
	            <div class="producto noBorderBottom">
	                <p>Type the email address which you signed up with and we'll send you an email with a link to get a new password.</p> 
	               
	                <fieldset>
	                    <p>
	                    	<label for="nombreusuario"><bean:message key="password.email"/></label>
	                    	<html:text property="email"/>
	                    	<strong class="dato_ko" >
	                    		<html:errors property="email"/>
	                    	</strong>
	                    </p>
	                <fieldset>
	            </div>
	            <br class="clear" />
	       		<div class="botonera">
	       			<html:submit styleClass="boton"><bean:message key="password.send"/></html:submit>
	       		</div>
	        </html:form>
	        <br class="clear" />
	    </div>
	    <%} %>
	</tiles:put>
</tiles:insert>
