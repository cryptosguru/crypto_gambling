<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@page import="java.util.List"%>
<%@page import="com.madgrid.model.UserHistoric"%>

<%
	List<UserHistoric> userHistoricList = (List<UserHistoric>)request.getAttribute("userHistoricList");
	out.print(userHistoricList.size());
%>*
<ul>
	<c:forEach items="${userHistoricList}" var="userHistoric" varStatus="index">
		<c:if test="${index.count mod 2 ==0}">
			<li style="background-color:#DDD;">
		</c:if>
		<c:if test="${index.count mod 2 ==1}">
			<li style="background-color:#CCC;">
		</c:if>
			<table>
				<tr>
					<td class="date">
					<fmt:setLocale value="en_US" /> 
						<fmt:formatDate value="${userHistoric.created}" type="both" dateStyle="long" /></strong>		
					</td>
					<td class="text">
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
			

