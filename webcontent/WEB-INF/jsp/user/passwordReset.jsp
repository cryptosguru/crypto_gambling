<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


<tiles:insert template='/WEB-INF/jsp/mainTile.jsp'>
	<tiles:put name='script' type='string'>
		
		$(document).ready(function(){
			$(".config_options").hide();
			$(".config_btn").click(function () {
				$(".config_options").slideToggle("slow");
			});
		});
		
	
		
		function validatePassword(value){
			if( value != null && value != ""){
				$.post("/do/validate", { property: 'password', value: value, isEdit:'true', id:'<c:out value="${userSession.user.id}"/>'}, function(data){
					$("#passwordId").html(data);
				});
			}
		}
		
		function validatePassword2(value2,value){
			if( value != null && value != ""){
				$.post("/do/validate", { property: 'password2', value: value, value2: value2, isEdit:'true', id:'<c:out value="${userSession.user.id}"/>'}, function(data){
					$("#password2Id").html(data);
				});
			}
		}
		
	</tiles:put>
	<tiles:put name="onload" type="string">
	</tiles:put>
	<tiles:put name="body" type="string">
		<div id="pagina">
	       <h2 class="tituloProducto"><bean:message key="user.title"/></h2>
	       <html:form action="/passwordResetSave" >
	            <div class="producto noBorderBottom">
	                <p><strong>Please, set your new password.</strong></p>
	              <p class="padleft"><bean:message key="user.text3"/></p>
	                
	                <fieldset>
	                    <p>
	                    	<label for="newpassword">New password</label>
	                   	  	<input type="password" name="password" onkeyup="validatePassword(this.value)" value=""  autocomplete="off" id="password"/>
	                   	  	<strong id="passwordId">
	                    		<logic:messagesPresent>
									<logic:messagesPresent property="password">
										<html:errors property="password"/>
									</logic:messagesPresent>
									<logic:messagesNotPresent property="password">
										<span style='color:green'><bean:message key="correct"/></span>
									</logic:messagesNotPresent>				
								</logic:messagesPresent>
	                    	</strong>
	                    </p>
	                    <p>
	                    	<label for="nacimiento"><bean:message key="user.repeatnewpassword"/></label>
	                   	  	<input type="password" name="password2" value="" onkeyup="validatePassword2(this.value, document.getElementById('password').value)" id="password2" autocomplete="off"/>
	                   	  	<strong id="password2Id">
	                    		<logic:messagesPresent>
									<logic:messagesPresent property="password2">
										<html:errors property="password2"/>
									</logic:messagesPresent>
									<logic:messagesNotPresent property="password2">
										<span style='color:green'><bean:message key="correct"/></span>
									</logic:messagesNotPresent>				
								</logic:messagesPresent>
	                    	</strong>
	                    </p>
	                </fieldset>
	            </div>
	            <br class="clear" />
	       		<div class="botonera">
	       			<html:submit styleClass="boton"><bean:message key="user.save"/></html:submit>
	       		</div>
	            
	        </html:form>
	        <br class="clear" />
	    </div>
	</tiles:put>
</tiles:insert>

