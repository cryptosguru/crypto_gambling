<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.apache.ojb.broker.query.Criteria"%>
<%@page import="com.madgrid.web.util.Utils"%>
<%@page import="java.util.Date"%>
<%@page import="com.madgrid.model.Promotion"%>
<%@page import="java.util.List"%>
<%@page import="com.madgrid.dao.PromotionDAO"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaImpl" %>



<%@page import="com.madgrid.web.util.UserSession"%>
<tiles:insert template='/WEB-INF/jsp/mainTile.jsp'>
	<tiles:put name='script' type='string'>
		$(document).ready(function(){
		
			<%
			UserSession userSession = (UserSession)session.getAttribute( "userSession");
			if( userSession != null){
			%>
				document.location.href = "/";
			<%
			}
			%>
		
			$(".config_options").hide();
			$(".config_btn").click(function () {
				$(".config_options").slideToggle("slow");
			});
			
			if($("#autoPay").is(':checked')) {  
				$("#bitcoinAddressP").show();
			} else{
				$("#bitcoinAddressP").hide();
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
		
		function validateLogin(value){
			$.post("/do/validate", { property: 'login', value: value}, function(data){
					$("#loginId").html(data);
			});
		}
		
		function validatePassword(value){
			if( value != null && value != ""){
				$.post("/do/validate", { property: 'password', value: value}, function(data){
					$("#passwordId").html(data);
				});
			}
		}
		
		function validatePassword2(value2,value){
			if( value != null && value != ""){
				$.post("/do/validate", { property: 'password2', value: value, value2: value2}, function(data){
					$("#password2Id").html(data);
				});
			}
		}
		
		
		
		function validateEmail(value){
			if( value != null && value != ""){
				$.post("/do/validate", { property: 'email', value: value}, function(data){
					$("#emailId").html(data);
				});
			}
		}
		
		
		function validateBitcoinAddress(value){
			if( value != null && value != ""){
				$.post("/do/validate", { property: 'bitcoinAddress', value: value}, function(data){
					$("#bitcoinAddressId").html(data);
				});
			}
		}
		
		
		function validatePromoCode(value){
			if( value != null && value != ""){
				$.post("/do/validate", { property: 'promoCode', value: value}, function(data){
					$("#promoCodeId").html(data);
				});
			}
		}
		
		function clickAutoPay(){
			if($("#autoPay").is(':checked')) {  
				$("#bitcoinAddressP").show();
			} else{
				$("#bitcoinAddressP").hide();
			}
		}
		
		function showAlert(){
			if(!document.getElementById('privacyCheckBoxId').checked){
				var options =	{ 
					themePath : '/jquerybubblepopup-theme/',
					mouseOver: 'hide',
					innerHtml:  '<bean:message key="register.accepteula"/>'
				};
				
				$('#privacyId').CreateBubblePopup(options);
				$('#privacyId').ShowBubblePopup();
				$('#privacyId').FreezeBubblePopup();
				
				setTimeout("$('#privacyId').HideBubblePopup()", 1500);
			}
		}
		
	</tiles:put>
	<tiles:put name="onload" type="string">
	</tiles:put>
	<tiles:put name="body" type="string">
		<%
		ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LekMPASAAAAAOYS_k4BVU_2IBIL2PMPILOwtG_t", "6LekMPASAAAAAJ_znmNTHFZxmIHx6Ofx8rtm8Sf3", false);
		((ReCaptchaImpl) c).setRecaptchaServer("https://www.google.com/recaptcha/api");
		
		
		
		UserSession userSession = (UserSession)session.getAttribute( "userSession");
		if( userSession == null){%>
		<div id="pagina">
	    	<ul class="rastro">
	        	<li><a href="http://www.instantri.ch" class="home">Home</a></li>
	        	<li><a href="/register"><bean:message key="register.breadcrumb"/></a></li>
	        </ul>
	        <h2 class="tituloProducto"><bean:message key="register.title"/></h2>
	         <script type="text/javascript">
			 var RecaptchaOptions = {
			    theme : 'clean'
			 };
			 </script>
			 
			 
			
	        <html:form action="/user/register" onsubmit="if(!document.getElementById('privacyCheckBoxId').checked) {return false} else {document.getElementById('registro').disabled=true};">
	            <div class="producto noBorderBottom">
	                <p><bean:message key="register.text1"/></p>
	                
	                <%
	                	
	                PromotionDAO promotionDAO = new PromotionDAO();
	    			List<Promotion> promotionList = promotionDAO.getPromotionListByCriteria(new Criteria(), null);
	    			Date today = Utils.today();
	    			int promotionCredits = 0;
	    			Promotion thePromotion = null;
	    			Locale locale = new Locale( "en", "GB");
	    			for( Promotion promotion:promotionList){
	    				if( today.after( promotion.getStartDate()) && today.before( promotion.getEndDate())){
	    					if( promotion.getType() == Promotion.PROMOTION_TYPE_1_IN_REGISTER){
	    						promotionCredits = 1;
	    						thePromotion = promotion;
	    						break;
	    					} else if( promotion.getType() == Promotion.PROMOTION_TYPE_2_IN_REGISTER){
	    						promotionCredits = 2;
	    						thePromotion = promotion;
	    						break;
	    					} else if( promotion.getType() == Promotion.PROMOTION_TYPE_5_IN_REGISTER){
	    						promotionCredits = 5;
	    						thePromotion = promotion;
	    						break;
	    					} else if( promotion.getType() == Promotion.PROMOTION_TYPE_10_IN_REGISTER){
	    						promotionCredits = 10;
	    						thePromotion = promotion;
	    						break;
	    					} else if( promotion.getType() == Promotion.PROMOTION_TYPE_20_IN_REGISTER){
	    						promotionCredits = 20;
	    						thePromotion = promotion;
	    						break;
	    					} 
	    				}
	    			}
	    			if( promotionCredits != 0 && thePromotion != null){
	                %>
	                 <p style="font-size:14px;color:#F60068;">
	                 	<%if( promotionCredits == 1){%>
					 		Only until <%=new SimpleDateFormat ( "dd MMMM", locale).format( thePromotion.getEndDate()) %>, <strong>one free credit</strong> when registering. Email validation required.
					 	<%} else if( promotionCredits > 1){%>
					 		Only until <%=new SimpleDateFormat ( "dd MMMM", locale).format( thePromotion.getEndDate()) %>, <strong><%=promotionCredits %> free credits</strong> when registering. Email validation required.
					 	<%} %>
					 </p>
					 <%} %>
	                
					 
	                <fieldset>
	                    <p>
	                    	<label for="nombreusuario"><bean:message key="register.username"/></label>
	                    	<html:text property="login" onblur="validateLogin(this.value);" />
	                    	<strong id="loginId">
	                    		<logic:messagesPresent>
									<logic:messagesPresent property="login2">
										<html:errors property="login2"/>
									</logic:messagesPresent>
									<logic:messagesNotPresent property="login2">
										<span style='color:green'><bean:message key="correct"/></span>
									</logic:messagesNotPresent>				
								</logic:messagesPresent>
	                    	</strong>
	                    </p>
	                    <p>
	                    	<label for="contrasena"><bean:message key="register.password"/></label>
	                    	<html:password property="password" onkeyup="validatePassword(this.value)" styleId="password"/>
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
	                    	<label for="contrasena2"><bean:message key="register.repeatpassword"/></label>
	                    	<html:password property="password2" onkeyup="validatePassword2(this.value, document.getElementById('password').value)" styleId="password2"/>
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
	               
	                    <p>
	                    	<label for="email"><bean:message key="register.email"/></label>
	                    	<html:text property="email" onblur="validateEmail(this.value);" />
	                    	<strong id="emailId">
	                    		<logic:messagesPresent>
									<logic:messagesPresent property="email">
										<html:errors property="email"/>
									</logic:messagesPresent>
									<logic:messagesNotPresent property="email">
										<span style='color:green'><bean:message key="correct"/></span>
									</logic:messagesNotPresent>				
								</logic:messagesPresent>
	                    	</strong>
	                    </p>
	                    
	                    
	                    <p>
	                    	<label for="email">Autoclaim BTC prizes</label>
	                    	<html:checkbox property="autoPay"  styleId="autoPay" style="width:29px;" onclick="clickAutoPay();"/>
	                    	<input type="hidden" name="autoPay" value="false"/>
	                    	<a href="#" class="showDetails" rel=".info_002" >
   								<img src="/img/icon_help.png" alt="What is this" style="padding-bottom:5px;"/>
   							</a>
	                    	<strong id="autopayId">
	                    		<logic:messagesPresent>
									<logic:messagesPresent property="email">
										<html:errors property="email"/>
									</logic:messagesPresent>
									<logic:messagesNotPresent property="email">
										<span style='color:green'><bean:message key="correct"/></span>
									</logic:messagesNotPresent>				
								</logic:messagesPresent>
	                    	</strong>
	                    </p>
	                    
	                    <p id="bitcoinAddressP" style="display:none;">
	                    	<label for="email">Wallet address</label>
	                    	<html:text property="bitcoinAddress" style="width:290px;" onblur="validateBitcoinAddress(this.value);" />
	                    	 
	                    	<strong id="bitcoinAddressId">
	                    		<logic:messagesPresent>
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
	                
	                <%if (session.getAttribute("affiliate") != null){ %>
	                	<input type="hidden" name="recomendedUser" value="<%=((String)session.getAttribute("affiliate")) %>" />
			        	<fieldset style="display:none">
		                    <p>
		                    	<label for="affiliate"><bean:message key="register.affiliate"/><br/>(Optional)</label>
		                    	<span style="font-size:20px;"><%=((String)session.getAttribute("affiliate")) %></span>
		                    </p>
		                </fieldset>
	                
	                <%} else if (request.getParameter("affiliate") == null){ %>
	                	<logic:notEmpty name="userRegisterForm" property="recomendedUser">
	                	<html:hidden property="recomendedUser"/>
			        	<fieldset style="display:none">
		                    <p>
		                    	<label for="affiliate"><bean:message key="register.affiliate"/><br/>(Optional)</label>
		                    	<span style="font-size:20px;"><bean:write name="userRegisterForm" property="recomendedUser" /></span>
		                    </p>

		                </fieldset>
		                </logic:notEmpty>
		        	<%} else{
		        		session.setAttribute( "affiliate", request.getParameter("affiliate"));
		        	
		        	%>
		        		<input type="hidden" name="recomendedUser" value="<%=request.getParameter("affiliate") %>" />
			        	<fieldset style="display:none">
		                    <p>
		                    	<label for="affiliate"><bean:message key="register.affiliate"/><br/>(Optional)</label>
		                    	<span style="font-size:20px;"><%=request.getParameter("affiliate") %></span>
		                    </p>
		                </fieldset>
		        	<%} %>
	                
	                 <fieldset>
	                    <p>
	                    	<% out.print(c.createRecaptchaHtml(null, null)); %>
	                    	<strong id="captchaId">
	                    		<logic:messagesPresent>
									<logic:messagesPresent property="captcha">
										<html:errors property="captcha"/>
									</logic:messagesPresent>
									<logic:messagesNotPresent property="captcha">
										<span style='color:green'><bean:message key="correct"/></span>
									</logic:messagesNotPresent>				
								</logic:messagesPresent>
	                    	</strong>
	                    </p>
	                </fieldset>
	                
	                
	                <div style="padding:20px 0 10px 0px;color:#666;">
	                	<label id="privacyId">
	                		<input type="checkbox" id="privacyCheckBoxId"/> <bean:message key="register.accepteula1"/><a href="/conditions" target="_blank"><bean:message key="register.accepteula2"/></a>.
	                	</label>
	                </div>
	            </div>
	            <br class="clear" />
	       		<div class="botonera">
	       			<input name="registro" type="image" src="/img/btn_registrate_rojo.png" alt="<bean:message key="register.breadcrumb"/>" id="registro" onclick="showAlert(); "/>
	       		</div>
	            
	        </html:form>
	        <br class="clear" />
	    </div>
	    <%} %>
	</tiles:put>
</tiles:insert>
