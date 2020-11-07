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
			
			updateFreeCredits(1);
			
		});
		
		$(function() {
		    var spinner = $( "#spinner" ).spinner({ 
		    incremental: true, 
		    max: 10000,
		    min: 1,
		    step: 1 });
		});
		
		$( "#spinner" ).spinner({
			  spin: function( event, ui ) {
			 	 $("#bitcoinToSend").html((ui.value / 1000) + " BTC")
			 	 updateFreeCredits(ui.value);
			  }
			}
		);
		
		function updateFreeCredits(credits){
			var promotionType = 0;
			<c:forEach items="${promotionList}" var="promotion">
            promotionType = <c:out value="${promotion.type }"/>;  
			</c:forEach>
			var freeCredits = 0;
			 	 
		 	if( promotionType == 1){
		 	 	freeCredits  = credits;
		 	}
		 	if( promotionType == 4){
		 	 	freeCredits  = 10;
		 	}
		 	if( promotionType == 13){
		 	 	freeCredits  = 5;
		 	}
		 	if( promotionType == 14){
		 	 	freeCredits  = 1;
		 	}
		 	if( promotionType == 10){
		 	 	freeCredits  = parseInt(credits * 0.1, 10);
		 	}
		 	if( promotionType == 11){
		 	 	freeCredits  = parseInt(credits * 0.2, 10);
		 	}
		 	if( promotionType == 12){
		 	 	freeCredits  = parseInt(credits * 0.3, 10);
		 	}
		 	 
		 	if( freeCredits == 0){
		 	 	$("#promotionCredits").hide();
		 	} else{
		 	 	$("#promotionCredits").show();
		 	 	if( freeCredits == 1){
		 	 		$("#freeCredits").html( " another credit");
		 	 	} else{
		 	 		$("#freeCredits").html( freeCredits + " more credits");
		 	 	}
		 	}
		}
		
		var checkPaidInterval;
		
		function checkPaid(){
		
			$.post("/do/checkPaid", { id:'<c:out value="${userSession.user.id}"/>' , address: '<c:out value="${address}"/>'}, function(data){
				var tokens = data.split("#");
				if( tokens[0] == 'ok'){
					$("#paymentDone").show();
					$("#credits").html(tokens[1]);
					clearInterval(checkPaidInterval);
				}		
			});
			
			
		}
		
		$(document).ready(function(){
			checkPaidInterval=setInterval(function(){checkPaid()},20000);
		});
		
	
			
	</tiles:put>
	<tiles:put name="onload" type="string">
	</tiles:put>
	<tiles:put name="body" type="string">
		<div id="pagina" style="height:850px;display:table;">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/do/credits/buy"><bean:message key="credits.breadcrumb"/></a></li>
	        </ul>
	        <h2 class="tituloProducto"><bean:message key="credits.title"/></h2>
			<form action="#" styleId="form1">
	            <div class="producto noBorderBottom" style="width:800px;">
	                <p><bean:message key="credits.text1"/> <strong>We only accept <strong>Bitcoins</strong> as currency.</p>

	                <p>You choose how many credits you want to purchase. The minimun amount is 1 credit which costs 0.001 BTC.</p>
	                <p>The credits will be added to your account immediately after the payment. No confirmations needed.</p>
	                <fmt:setLocale value="en_US" /> 
	                <c:forEach items="${promotionList}" var="promotion">
		                <p class="buyCreditsPromo" style="font-size:14px;color:#F60068;">
		                	Only until <fmt:formatDate value="${promotion.endDate}" pattern="dd MMMM"/>, <strong><c:out value="${promotion.name}" escapeXml="false"/></strong>.
		                	<span style="font-size:14px;color:#bbbbbb;">(No promotion code required)</span>
		                </p>
		                
		                <p class="buyCreditsPromoDescription">
		                	<c:out value="${promotion.description}" escapeXml="false"/>
		                </p>
	                </c:forEach>
	                
	                
	                <fieldset style="border:0px;">
	                    <p>
	                    	<label for="codigo" style="width:200px;">
	                    		<bean:message key="credits.promocode"/><br/>
	                    		<span style="font-size:10px;font-weight:normal;line-height:5px;">(<bean:message key="credits.optional"/>)</span>
	                    	</label>
	                    	<input type="text" name="buyCreditsPromoCode" id="buyCreditsPromoCode" size="10" style="width:100px;" maxlength="20"/> 
	                    	<input type="button" value="Validate code" class="boton" onclick="validatePromoCode(document.getElementById('buyCreditsPromoCode').value);"/>
	                    </p>
	                    <p class="padleft" style="padding-left:200px;">
	                    	<strong class="dato_ko" id="promoCodeId">
	                    		<c:if test="${not empty userSession.user.buyCreditsPromoCode}">
	                    		 <c:out value="${userSession.user.buyCreditsPromoCodeText}"/>
	                    		</c:if>
	                    	</strong>
	                    </p>
	                </fieldset>
	            </div>
	            
	       		<div class="botonera producto" style="width:800px;font-size:16px;">
	       			<div style="padding:20px;">
				        <span style="font-size:16px;color:#666666;">I want to buy</span> <div style="display: inline-block"><input type="text" id="spinner" value="1" size="5"/> <span style="font-size:20px;color:#666666;">Credits</span></div>
				    </div>
				    <div style="font-size: 16px;color: #666666;padding-bottom:5px;" id="promotionCredits">
				    	You'll get <span id="freeCredits"></span> for free
				    </div>
				    
				    <p style="font-size:16px;">Send <span id="bitcoinToSend">0.001 BTC</span> to <span style="color:#F50067;"><c:out value="${address}"/></span></p>
				    
				    <div style="padding:15px;">
				    	<img src="https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=<c:out value="${address}"/>"/>
				    </div>
				    
				    
				    <p style="font-size:12px;">You'll receive an email as soon as the credits are added to your account.<br/>
				    No confirmations are required. However, if you win any Bitcoin prize we'll send it only if your payment is confirmed.</p>
	       			
	       			<p style="font-size:16px;color:green;display:none;" id="paymentDone">The payment has been received</p>
	       			
	       		</div>
	            
	        <form>
	        <br class="clear" />
	    </div>
	</tiles:put>
</tiles:insert>

