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
			$(".config_options").hide();
			$(".config_btn").click(function () {
				$(".config_options").slideToggle("slow");
			});
		});
		

		
	</tiles:put>
	<tiles:put name="onload" type="string">
	</tiles:put>
	<tiles:put name="body" type="string">
		<div id="pagina" style="height:800px;">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/message"><bean:message key="message.breadcrumb"/></a></li>
	        </ul>
	        <h2 class="tituloProducto"><bean:message key="message.title"/></h2>
			<html:form action="/message/save">
	            <div class="producto noBorderBottom">
	                <p><bean:message key="message.text1"/></p>
	                <fieldset>
	                    <p>
	                    	<label for="tipomensaje"><bean:message key="message.type"/></label>
	                    	<html:select property="messageType">
								<html:option value=""><bean:message key="message.choose"/></html:option>
								<html:option value="1"><bean:message key="message.help"/></html:option>
								<html:option value="2"><bean:message key="message.suggestion"/></html:option>
								<html:option value="5"><bean:message key="message.affiliationrequest"/></html:option>
								<html:option value="4"><bean:message key="message.other"/></html:option>
							</html:select>
	                    	<strong class="dato_ko" >
	                    		<html:errors property="messageType"/>
	                    	</strong>
	                    </p>
	                    <p>
	                    	<label for="subject"><bean:message key="message.email"/></label>
	                    	 <%
	                    	 UserSession userSession = (UserSession)session.getAttribute( "userSession");
	                    	 if (userSession == null){ %>
	                    		<html:text property="email"/>
	                    	<%}else{ %>
	                    		
	                    		<input type="text" name="email" disabled="disabled" value="<c:out value="${userSession.user.email}"/>" style="background-color:#EFEFEF;">
	                    	<%} %>
	                    	<strong class="dato_ko">
	                    		<html:errors property="email"/>
	                    	</strong>
	                    </p>
	                    <p>
	                    	<label for="subject"><bean:message key="message.subject"/></label>
	                    	<html:text property="subject"/>
	                    	<strong class="dato_ko">
	                    		<html:errors property="subject"/>
	                    	</strong>
	                    </p>
	                    <p>
	                    	<label for="subject"><bean:message key="message.text"/></label>
	                    	<html:textarea property="text" cols="40" rows="7"/>
	                    	<strong class="dato_ko">
	                    		<html:errors property="text"/>
	                    	</strong>
	                    </p>
	                </fieldset>
	            </div>
	            <br class="clear" />
	       		<div class="botonera">
	       			<html:submit styleClass="boton"><bean:message key="message.send"/></html:submit>
	       		</div>
	            
	        </html:form>
	        <br class="clear" />
	    </div>
	</tiles:put>
</tiles:insert>

