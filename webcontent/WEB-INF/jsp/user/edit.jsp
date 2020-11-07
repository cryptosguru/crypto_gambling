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
			
			if($("#autoPay").is(':checked')) {  
				$("#bitcoinAddress").removeAttr("disabled"); 
				$("#bitcoinAddress").css('background-color', '#FFFFFF');
				$("#bitcoinAddress").css('color', '#666666');
			} else{
				$("#bitcoinAddress").attr("disabled", "disabled"); 
				$("#bitcoinAddress").css('background-color', '#EEEEEE');
				$("#bitcoinAddress").css('color', '#AAAAAA');
			}
			
			reloadCluetip();
		});
		
		
		function reloadCluetip(){
				$('.showDetails').cluetip({
					cluetipClass: 'detalleServicio',
					positionBy: 'mouse',
					dropShadow: true,
					topOffset: 0,
					leftOffset: 10,
					width:450,
					sticky: false,
					activation: 'hover',
					mouseOutClose: true, 
					local: true,
					hideLocal:true,
					showTitle: false,
					closePosition: 'top',
					closeText: '<img src="/img/btnCloseHome.gif" alt="Cerrar" title="Cerrar"></img>',
					arrows: false,
				});
			}
			
		function clickAutoPay(){
			if($("#autoPay").is(':checked')) {  
				$("#bitcoinAddress").removeAttr("disabled"); 
				$("#bitcoinAddress").css('background-color', '#FFFFFF');
				$("#bitcoinAddress").css('color', '#666666');
			} else{
				$("#bitcoinAddress").attr("disabled", "disabled"); 
				$("#bitcoinAddress").css('background-color', '#EEEEEE');
				$("#bitcoinAddress").css('color', '#AAAAAA');
			}
		}
		
		function validateOldPassword(value){
			if( value != null && value != ""){
				$.post("/do/validate", { property: 'oldPassword', value: value, value2: '<c:out value="${userSession.user.id}"/>', isEdit:'true', id:'<c:out value="${userSession.user.id}"/>'}, function(data){
					$("#oldPasswordId").html(data);
				});
			}
		}
		
		function validatePassword(value){
			if( value != null && value != ""){
				$.post("/do/validate", { property: 'password', value: value, isEdit:'true', id:'<c:out value="${userSession.user.id}"/>'}, function(data){
					$("#passwordId").html(data);
				});
			}
		}
		
		function validateBitcoinAddress( value){
			var autoPay = document.getElementById("autoPay");
			if( autoPay != null && autoPay.checked == true){
				if( value != null && value != ""){
					$.post("/do/validate", { property: 'bitcoinAddress', value: value, isEdit:'true', id:'<c:out value="${userSession.user.id}"/>'}, function(data){
						$("#bitcoinAddressId").html(data);
					});
				}
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
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/do/user/edit"><bean:message key="user.breadcrumb"/></a></li>
	        </ul>
	        <h2 class="tituloProducto"><bean:message key="user.title"/></h2>
	       <html:form action="/user/save" >
	            <div class="producto noBorderBottom">
	                <p><strong><bean:message key="user.text1"/></strong></p>
	                <p><bean:message key="user.text2"/></p>
	                <fieldset>
	                    <p>
	                    	<label for="nombreusuario" style="width:270px;"><bean:message key="user.username"/></label>
	                    	<html:text property="login"  disabled="true" style="background-color:#DDD;"/>
	                    	<html:hidden property="login"/>
	                    </p>
	                    
	                   
	                    <p>
	                    	<label for="email" style="width:270px;" ><bean:message key="user.email"/></label>
	                    	<html:text property="email" disabled="true"  style="background-color:#DDD;"  />
	                    	<html:hidden property="email"/>
	                    </p>
	                   
	                </fieldset>
	                <fieldset style="border-bottom: none;">
	                    <p>
	                    	<label for="requestPrizeSubscribed" style="width:270px;">Receive 'prize requested' emails</label>
	                    	<html:checkbox property="requestPrizeSubscribed" style="width:27px;"/>
	                    	<input type="hidden" name="requestPrizeSubscribed" value="false"/>
	                    </p>
	               </fieldset>
	               <fieldset>
	                     <p>
	                    	<label for="bitcoinSentSubscribed" style="width:270px;">Receive 'prize sent' emails</label>
	                    	<html:checkbox property="bitcoinSentSubscribed" style="width:27px;"/>
	                    	<input type="hidden" name="bitcoinSentSubscribed" value="false"/>
	                    </p>
	               </fieldset>
	               
	               <fieldset>
	                     <p>
	                    	<label for="autoPay" style="width:270px;">Autoclaim BTC prizes</label>
	                    	<html:checkbox property="autoPay" styleId="autoPay" style="width:27px;" onclick="clickAutoPay();"/>
	                    	<a href="#" class="showDetails" rel=".info_002" >
   								<img src="/img/icon_help.png" alt="What is this" style="padding-bottom:5px;"/>
   							</a>
	                    	<input type="hidden" name="autoPay" value="false"/>
	                    </p>
	               
	                   <p>
	                    	<label for="bitcoinAddress" style="width:270px;">Bitcoin wallet address</label>
	                   	  	<html:text property="bitcoinAddress" onblur="validateBitcoinAddress(this.value)" styleId="bitcoinAddress" style="width:290px;"/>
	                   	  	<strong id="bitcoinAddressId">
	                    		<logic:messagesPresent>
	                    			<br/>
									<logic:messagesPresent property="bitcoinAddress">
										<html:errors property="bitcoinAddress"/>
									</logic:messagesPresent>
									<logic:messagesNotPresent property="bitcoinAddress">
										<span style='color:green'><bean:message key="correct"/></span>
									</logic:messagesNotPresent>				
								</logic:messagesPresent>
	                    	</strong>
	                    </p>
	                </fieldset>
	                
	                  <div class="info_002" style="display:none;">
						<div class="infoTitle">What is '<strong>Autoclaim BTC prizes</strong>'?</div>
						<ul class="infoUl" style="width:400px;line-height:10px;">
							<li style="text-align:justify">In Instantri.ch when you win a bitcoin prize it is no automatically sent to your wallet address.</li>
							<li style="text-align:justify">Instead, you have to claim your prize telling us the bitcoin address you want us to send the prize.</li>
							<li style="text-align:justify">If you want the system to perform that action for you just check this checkbox and type your bitcoin wallet address below.</li>
						</ul>
					</div>
	               
	               
	                
	                <fieldset>
	                	<p><bean:message key="user.text3"/></p>
	                   <p>
	                    	<label for="nacimiento" style="width:270px;"><bean:message key="user.oldpassword"/></label>
	                   	  	<input type="password" name="oldPassword" onblur="validateOldPassword(this.value)" value="" id="oldPassword" autocomplete="off"/>
	                   	  	<strong id="oldPasswordId">
	                    		<logic:messagesPresent>
									<logic:messagesPresent property="oldPassword">
										<html:errors property="oldPassword"/>
									</logic:messagesPresent>
									<logic:messagesNotPresent property="oldPassword">
										<span style='color:green'><bean:message key="correct"/></span>
									</logic:messagesNotPresent>				
								</logic:messagesPresent>
	                    	</strong>
	                    </p>
	                    <p>
	                    	<label for="nacimiento" style="width:270px;"><bean:message key="user.newpassword"/></label>
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
	                    	<label for="nacimiento" style="width:270px;"><bean:message key="user.repeatnewpassword"/></label>
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

