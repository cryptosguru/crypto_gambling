<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<tiles:insert template="/WEB-INF/jsp/mainTile.jsp">
	<tiles:put name="script" type="string">
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
		<div id="pagina">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="<c:out value="${item.virtualPath}"/>"><c:out value="${item.name}" escapeXml="false"/></a></li>
	        </ul>
	        <h2 class="tituloProducto"><c:out value="${item.name}" escapeXml="false"/></h2>
	        
	        <div class="producto" style="height:800px;">       
				<div class="visorFotos" style="height:180px;">
	                <img src='/img/item/<c:out value="${item.picture1Url}"/>' alt='<c:out value="${item.name}" />' class="imgProducto" id="imgProducto"/>
	          	</div>
	            <p style="font-size:15px;">
					We don't have any item with name <strong><c:out value="${item.name}" escapeXml="false"/></strong> at this moment.
				</p>
				<p>
					But you can't take a look at the rest of the games users are playing now. You'll find some interesting prizes there.
				</p>
	            <div class="itemOff">
	            	 <ul class="itemOffList">
	            	 	<c:forEach items="${gridList}" var="grid">
	            	 		<a href="<c:out value="${grid.virtualPath}" />" onmouseover="document.getElementById('io<c:out value="${grid.item.id}"/>').style.color='#D8216F';" onmouseout="document.getElementById('io<c:out value="${grid.item.id}"/>').style.color='#666';">
			            	 	<li style="-moz-border-radius: 10px;border-radius: 10px;background: #FFFFFF url(/img/item/<c:out value="${grid.item.picture1Url}"/>) top left no-repeat;">
				            		<span>
				            			<br/>
					            		<strong id="io<c:out value="${grid.item.id}"/>"><c:out value="${grid.item.name}" escapeXml="false"/></strong>
					            		<br/><br/>
					            		<c:out value="${grid.freeBoxes}" />/<c:out value="${grid.boxes}" /> boxes
					            		<br/>
					            		<c:if test="${grid.boxPrice == 1}">
					            			<fmt:formatNumber value="${grid.boxPrice}" maxFractionDigits="0"/> credit per box
					            		</c:if>
					            		<c:if test="${grid.boxPrice != 1}">
					            			<fmt:formatNumber value="${grid.boxPrice}" maxFractionDigits="0"/> credits per box
					            		</c:if>
					            	</span>
				            	</li>
				            </a>
			            </c:forEach>
	            	 
	            	 </ul>
	            </div>
			</div>
			<br class="clear" />
	    </div>
	</tiles:put>
</tiles:insert>

