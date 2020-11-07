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
		
	</tiles:put>
	<tiles:put name="onload" type="string">
	</tiles:put>
	<tiles:put name="body" type="string">
		<fmt:setLocale value="en_US" /> 
		<div id="pagina" style="height:800px;display:table;">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/do/win/list"><bean:message key="win.breadcrumb"/></a></li>
	        </ul>
	        <h2 class="tituloProducto"><bean:message key="win.title"/></h2>
            <div class="producto noBorderBottom" style="height:30px;">
                <p><strong>Prizes you won.</strong></p>
                
                <c:if test="${empty winList}">
                	<p>You still haven't won any game. Keep on playing on free games or buy credits to play in any game and eventually you'll win some prize.</p>
                </c:if>
                <c:if test="${not empty winList}">
                	<p>You have to click on the <strong>Request delivery</strong> button and give us your Bitcoin wallet address and we'll send you your prize.</p>
                </c:if>
                
            </div>
            <br class="clear" />
            <div class="userWinList">
	            <ul>
	            	<c:forEach var="win" items="${winList}" varStatus="index">
	            	
	            	
	            		<c:if test="${index.index == 0}">
            				<li style="background-color:#CCC;height:20px;padding-top:15px;font-size:15px;border-bottom: 1px solid #666;border-top-left-radius: 10px;border-top-right-radius: 10px;">
								<table style="padding-top:20px;">
									<tr style="font-weight:bold;line-height:17px;">
										<td class="game">Game</td>
										<td class="item"><bean:message key="win.prize"/></td>
										<td class="date"><bean:message key="win.date"/></td>
										<td class="nosent"><bean:message key="win.sent"/></td>
										<td class="nosent"><bean:message key="win.deliveryRequested"/></td>
									</tr>
								</table>
							</li>
						</c:if>
	            	
	            	
	            	
	            		<c:if test="${index.index % 2==0}">
	            			<c:if test="${!index.last}">
								<li style="background-color:#DDD;">
							</c:if>
							<c:if test="${index.last}">
								<li style="background-color:#DDD;border-bottom-left-radius: 10px;border-bottom-right-radius: 10px;margin-bottom:80px;">
							</c:if>
						</c:if>
						<c:if test="${index.index % 2==1}">
							<c:if test="${!index.last}">
								<li style="background-color:#CCC;">
							</c:if>
							<c:if test="${index.last}">
								<li style="background-color:#CCC;border-bottom-left-radius: 10px;border-bottom-right-radius: 10px;margin-bottom:80px;">
							</c:if>
						</c:if>
						
						
						
						
						
							<table>
								<tr>
									<td class="game">
										<img src="/img/item/<c:out value="${win.item.picture1Url}"/>" height="40" alt="<c:out value="${win.item.name}"/>" title="<c:out value="${win.item.name}"/>">		
									</td>
								
									<td class="item">
										<c:if test="${win.isOnlyCredits}">
											<c:out value="${win.item.credits}"/> <bean:message key="win.credits"/>
										</c:if>
										<c:if test="${!win.isOnlyCredits}">
											<c:if test="${!win.itemSent || empty win.transactionId }">
					            				<fmt:formatNumber value="${win.item.bitcoins}" maxFractionDigits="3"/> Bitcoin
					            			</c:if>
					            			<c:if test="${win.itemSent && not empty win.transactionId}">
					            				<a href="https://blockchain.info/tx/<c:out value="${win.transactionId}"/>"  title="See transaction" target="_blank"><fmt:formatNumber value="${win.item.bitcoins}" maxFractionDigits="3"/> Bitcoin</a>
					            			</c:if>
										</c:if>		
									</td>
									<td class="date">
										<c:out value="${win.created}"/>
									</td>
									
									<c:if test="${win.itemSent}">
										<td class="sent"></td>
									</c:if>
									<c:if test="${!win.itemSent}">
										<td class="nosent"></td>
									</c:if>

									
									<c:if test="${win.isOnlyCredits}">
										<td class="nosent"></td>
									</c:if>
									
									<c:if test="${!win.isOnlyCredits && win.deliveryRequested}">
										<td class="sent"></td>
									</c:if>
									
									<c:if test="${!win.isOnlyCredits && !win.deliveryRequested}">
										<td class="nosent">
											<input type="button" class="boton" value="Request delivery" onclick="document.location.href='/do/win/edit?id=<c:out value="${win.id}"/>'"/>
										</td>
									</c:if>
									
								</tr>
							</table>
						</li>
					</c:forEach>
				</ul>
			</div>
            
            <br class="clear" />
	    </div>
	</tiles:put>
</tiles:insert>
