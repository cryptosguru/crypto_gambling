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
		
		function sendValidationEmail(){
				$("#spinner").show();
				$.post("/do/user/sendValidationEmail", { }, function(data){
	    			
					$("#validationEmailOutput").html(data);
					$("#spinner").hide();
				});
			}
		
	</tiles:put>
	<tiles:put name="onload" type="string">
	</tiles:put>
	<tiles:put name="body" type="string">
		<div id="pagina" style="height:800px;">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/do/user/statistics">User statistics</a></li>
	        </ul>
	        <h2 class="tituloProducto">User statistics</h2>
            <div class="producto noBorderBottom">
                <p><bean:message key="stats.text1"/></p>
            </div>
            <br class="clear" />
            <div class="statistics">
	            <ul>
	            	<li style="background-color:#CCC;border-top-left-radius: 10px;border-top-right-radius: 10px;">
						<table>
							<tr>
								<td class="info">
									Created				
								</td>
								<td>
									<strong><fmt:formatDate value="${userSession.user.created}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></strong>
								</td>
							</tr>
						</table>
					</li>
					<li style="background-color:#DDD;">
						<table>
							<tr>
								<td class="info">
									<bean:message key="stats.gamesparticipated"/>				
								</td>
								<td>
									<strong><c:out value="${playedGames}"/> games</strong>
								</td>
							</tr>
						</table>
					</li>
					<li style="background-color:#CCC;">
						<table>
							<tr>
								<td class="info">
									<bean:message key="stats.gamesplayed"/>				
								</td>
								<td>
									<strong><c:out value="${wonGames}"/> games</strong>
								</td>
							</tr>
						</table>
					</li>
					<li style="background-color:#DDD;">
						<table>
							<tr>
								<td class="info">
									<bean:message key="stats.boxopened"/>				
								</td>
								<td>
									<strong><c:out value="${boughtBoxes}"/> boxes</strong>
								</td>
							</tr>
						</table>
					</li>
					<li style="background-color:#CCC;">
						<table>
							<tr>
								<td class="info">
									Bitcoin spent purchasing credits				
								</td>
								<td>
									<strong><fmt:formatNumber value="${bitcoinSpent}" minFractionDigits="0" maxFractionDigits="3"/> BTC</strong>
								</td>
							</tr>
						</table>
					</li>
					<li style="background-color:#DDD;">
						<table>
							<tr>
								<td class="info">
									Bitcoin won in prizes				
								</td>
								<td>
									<strong><fmt:formatNumber value="${bitcoinWon}" minFractionDigits="0" maxFractionDigits="3"/> BTC</strong>
								</td>
							</tr>
						</table>
					</li>
					<li style="background-color:#CCC;">
						<table>
							<tr>
								<td class="info">
									Used credits				
								</td>
								<td>
									<strong><c:out value="${usedCredits}"/> credits</strong>
								</td>
							</tr>
						</table>
					</li>
					<li style="background-color:#DDD;">
						<table>
							<tr>
								<td class="info">
									Affiliated users				
								</td>
								<td>
									<c:if test="${isAffiliationUser}">
										<c:if test="${affiliatedUsers == 1}">
											<strong><c:out value="${affiliatedUsers}"/> user</strong><br/>
										</c:if>
										<c:if test="${affiliatedUsers > 1}">
											<strong><c:out value="${affiliatedUsers}"/> users</strong><br/>
										</c:if>
										Go to your <a href="http://www.instantri.ch/affiliation" target="_blank">affiliation control panel</a>
									</c:if>
									<c:if test="${!isAffiliationUser}">
										You are not an affiliation user.<br/>
										<a href="/do/user/joinAffiliationProgram">Become an affiliation user now</a><br/>
									</c:if>
								</td>
							</tr>
						</table>
					</li>
					<li style="background-color:#CCC;">
						<table>
							<tr>
								<td class="info">
									Marked as fraudulent
									<c:if test="${isFraudulent == true}">
										<br/><span style="color:gray;">(Fraudulent mark removed if credits are purchased)</span>
									</c:if>	
								</td>
								<td>
									<strong>
										<c:if test="${isFraudulent == true}">
											<span style="color:red">Yes</span>
										</c:if>
										<c:if test="${isFraudulent == false}">
											No
										</c:if>
									</strong>
								</td>
							</tr>
						</table>
					</li>
					<li style="background-color:#DDD;">
						<table>
							<tr>
								<td class="info">
									Beginner (no prizes won yet)			
								</td>
								<td>
									<strong>
										<c:if test="${isBeginner == true}">
											Yes
										</c:if>
										<c:if test="${isBeginner == false}">
											No
										</c:if>
									</strong>
								</td>
							</tr>
						</table>
					</li>
					<li style="background-color:#CCC;border-bottom-left-radius: 10px;border-bottom-right-radius: 10px;">
						<table>
							<tr>
								<td class="info">
									Email address validated	
									<c:if test="${isValidated == false}">
										<br/><span style="color:gray;">(If you purchase credits you'll be able to play in<br/>free games even if email address is not validated)</span>
										
										<br/><span style="line-height:25px;"><a href="javascript:sendValidationEmail();">Send validation email again</a><img src="/img/ajax-loader-small.gif" style="padding-left:20px;display:none;" id="spinner"></span>
										<br><span id="validationEmailOutput"></span>
									</c:if>				
								</td>
								<td>
									<strong>
										<c:if test="${isValidated == true}">
											Yes
										</c:if>
										<c:if test="${isValidated == false}">
											<span style="color:red">No</span>
										</c:if>
									</strong>
								</td>
							</tr>
						</table>
					</li>
				</ul>
			</div>
            
            <br class="clear" />
	    </div>
	</tiles:put>
</tiles:insert>
