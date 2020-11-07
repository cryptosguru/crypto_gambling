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
		
		function seeMore(lastDate){
			
			$.post("/do/user/logMore", { lastDate: lastDate}, function(data){
				var tokens = data.split("@");
				$( ".logList").append( tokens[0] );
				if( tokens[1] == 'end'){
					document.getElementById('seeMoreDiv').innerHTML="";
				} else{
					var url = "<a href='javascript:seeMore(\""+tokens[1]+"\");'>See more</a>";
					document.getElementById('seeMoreDiv').innerHTML=url;
				}
				
			});
		}
		
		  
	</tiles:put>
	<tiles:put name="onload" type="string">
	</tiles:put>
	<tiles:put name="body" type="string">
	
		<div id="pagina" style="height:800px;display:table;">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/do/user/log"><bean:message key="log.breadcrumb"/></a></li>
	        </ul>
	        <h2 class="tituloProducto"><bean:message key="log.title"/></h2>
            <div class="producto noBorderBottom" style="height:30px;">
                <p style="margin-left:20px;"><bean:message key="log.text1"/></p>
            </div>
            <br class="clear" />
            
           
           
            <div class="log" style="top:350px;">
            	

	    			<ul class="logList">
						<c:forEach items="${fullUserHistoricList}" var="userHistoric" varStatus="index">
							<c:if test="${index.count mod 2 ==1}">
									<c:choose>
										<c:when test="${index.first}">
											<li style="background-color:#DDD;border-top-left-radius: 10px;border-top-right-radius: 10px;padding:10px;">
										</c:when>
										<c:when test="${index.last && isLastPage}">
											<li style="background-color:#DDD;border-bottom-left-radius: 10px;border-bottom-right-radius: 10px;padding:10px;">
										</c:when>
										<c:otherwise>
											<li style="background-color:#DDD;padding:10px;">
										</c:otherwise>
									</c:choose>
							</c:if>
							<c:if test="${index.count mod 2 ==0}">
								<c:choose>
										<c:when test="${index.first}">
											<li style="background-color:#CCC;border-top-left-radius: 10px;border-top-right-radius: 10px;padding:10px;">
										</c:when>
										<c:when test="${index.last && isLastPage}">
											<li style="background-color:#CCC;border-bottom-left-radius: 10px;border-bottom-right-radius: 10px;padding:10px;">
										</c:when>
										<c:otherwise>
											<li style="background-color:#CCC;padding:10px;">
										</c:otherwise>
									</c:choose>
							</c:if>
								<table>
									<tr>
										<td class="date" style="vertical-align: middle;">
											<fmt:setLocale value="en_US" /> 
											<fmt:formatDate value="${userHistoric.created}" type="both" pattern="dd/MM/yyyy   HH:mm:ss" /></strong>		
										</td>
										<td style="vertical-align: middle;width:50px;">
											<c:choose>
												<c:when test="${userHistoric.type==1}">
													<img src="/img/qr.png" height="32">
												</c:when>
												<c:when test="${userHistoric.type==2}">
													<img src="/img/ico_creditos.png" height="32">
												</c:when>
												<c:when test="${userHistoric.type==3}">
													<img src="/img/sorteo_caja_abierta.png" height="32">
												</c:when>
												<c:when test="${userHistoric.type==4}">
													<img src="/img/sorteo_caja_premiada.png" height="32">
												</c:when>
												<c:when test="${userHistoric.type==5}">
													<img src="/img/banderola_ico_sorteo_standard.png" height="32">
												</c:when>
												<c:when test="${userHistoric.type==6}">
													<img src="/img/mail_icon.png" height="32">
												</c:when>
												<c:when test="${userHistoric.type==7}">
													<img src="/img/ico_creditos.png" height="32">
												</c:when>
												<c:when test="${userHistoric.type==8}">
													<img src="/img/ico_creditos_refund.png" height="32">
												</c:when>
												<c:when test="${userHistoric.type==9}">
													<img src="/img/plus_icon.png" height="32">
												</c:when>
												<c:when test="${userHistoric.type==10}">
													<img src="/img/minus_icon.png" height="32">
												</c:when>
												<c:when test="${userHistoric.type==11}">
													<c:if test="${userHistoric.value1 == 1 }">
														<img src="/img/sorteo_caja_premiada_1.png" height="32">
													</c:if>
													<c:if test="${userHistoric.value1 == 2 }">
														<img src="/img/sorteo_caja_premiada_2.png" height="32">
													</c:if>
													<c:if test="${userHistoric.value1 == 5 }">
														<img src="/img/sorteo_caja_premiada_5.png" height="32">
													</c:if>
													<c:if test="${userHistoric.value1 == 10 }">
														<img src="/img/sorteo_caja_premiada_10.png" height="32">
													</c:if>
												</c:when>
												<c:when test="${userHistoric.type==12}">
													<img src="/img/ico_twitter.png" height="32">
												</c:when>
											</c:choose>	
										</td>
										<td class="text" style="vertical-align: middle;">
											<c:choose>
												<c:when test="${userHistoric.type==1}">
													<span style="color:darkred;">You bought <strong><fmt:formatNumber value="${userHistoric.value1}" maxFractionDigits="0"/></strong> credits</span>
												</c:when>
												<c:when test="${userHistoric.type==2}">
													<span style="color:darkgreen;">You got <strong><c:out value="${userHistoric.value1}"/></strong> credits for promotion <strong><c:out value="${userHistoric.value2}"/></strong></span>
												</c:when>
												<c:when test="${userHistoric.type==3}">
													<span style="color:navy;">You opened box <strong><c:out value="${userHistoric.value2}"/></strong> in game <strong><c:out value="${userHistoric.value3}" escapeXml="false"/></strong> for <strong><fmt:formatNumber value="${userHistoric.value1}" maxFractionDigits="0"/></strong> <c:if test="${userHistoric.value1==1}">credit</c:if><c:if test="${userHistoric.value1!=1}">credits</c:if></span>
												</c:when>
												<c:when test="${userHistoric.type==4}">
													<span style="color:darkgoldenrod;">You found the prize <strong><c:out value="${userHistoric.value3}" escapeXml="false"/></strong> in box <strong><c:out value="${userHistoric.value2}"/></strong></span>
												</c:when>
												<c:when test="${userHistoric.type==5}">
													<span style="color:MediumVioletRed;">You got the prize <strong><c:out value="${userHistoric.value3}" escapeXml="false"/></strong></span>
												</c:when>
												<c:when test="${userHistoric.type==6}">
													<span style="color:MediumVioletRed;">For validating your email account you got <strong><c:out value="${userHistoric.value1}" escapeXml="false"/> credit</strong></span>
												</c:when>
												<c:when test="${userHistoric.type==7}">
													<span style="color:MediumVioletRed;">For an affiliated user you got <strong><c:out value="${userHistoric.value1}" escapeXml="false"/> credit</strong></span>
												</c:when>
												<c:when test="${userHistoric.type==8}">
													<span style="color:MediumVioletRed;">You've got a <fmt:formatNumber value="${userHistoric.value1}" maxFractionDigits="0"/> credits refund because nobody won the <strong><c:out value="${userHistoric.value2}" escapeXml="false"/> game</strong></span>
												</c:when>
												<c:when test="${userHistoric.type==9}">
													<span style="color:MediumVioletRed;">Instantri.ch admin added <strong><fmt:formatNumber value="${userHistoric.value1}" maxFractionDigits="0"/> credits</strong> to your account</span>
												</c:when>
												<c:when test="${userHistoric.type==10}">
													<span style="color:MediumVioletRed;">Instantri.ch admin removed <strong><fmt:formatNumber value="${userHistoric.value1}" maxFractionDigits="0"/> credits</strong> from your account</span>
												</c:when>
												<c:when test="${userHistoric.type==11}">
													<span style="color:MediumVioletRed;">You won <strong><fmt:formatNumber value="${userHistoric.value1}" maxFractionDigits="0"/> credits</strong> hidden in box <c:out value="${userHistoric.value2}"/> of the <c:out value="${userHistoric.value3}" escapeXml="false"/> game</span>
												</c:when>
												<c:when test="${userHistoric.type==12}">
													<span style="color:MediumVioletRed;">We gave you <strong>1 credit</strong> for your tweet
												</c:when>
											</c:choose>
										</td>
									</tr>
								</table>
							</li>
						</c:forEach>
					</ul>
					<c:if test="${not empty lastDate && !isLastPage}">
						<div style="width:790px;padding-left: auto;text-align: center;padding-top:15px;" id="seeMoreDiv">
	            			<a href="javascript:seeMore('<c:out value="${lastDate}"/>');">See more</a>
	            		</div>
	            	</c:if>
					
	    		
			</div>
            <br class="clear" />
	    </div>
	</tiles:put>
</tiles:insert>

