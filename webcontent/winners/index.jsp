<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@page import="com.madgrid.web.util.UserSession"%>
<%@page import="org.apache.ojb.broker.query.Criteria"%>
<%@page import="com.madgrid.dao.WinDAO"%>
<%@page import="com.madgrid.web.util.Utils"%>
<%@page import="java.util.List"%>
<%@page import="com.madgrid.model.Win"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>


<tiles:insert template='/WEB-INF/jsp/mainTile.jsp'>
	<tiles:put name='script' type='string'>
		$(document).ready(function(){
			$(".config_options").hide();
			$(".config_btn").click(function () {
				$(".config_options").slideToggle("slow");
			});

		});
		
		function seeMore(lastDate){
			
			$.post("/do/winners/more", { lastDate: lastDate}, function(data){
				var tokens = data.split("@");
				$( ".winList").append( tokens[0] );
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
		<div id="pagina">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/winners"><bean:message key="winners.breadcrumb"/></a></li>
	        </ul>
	        <h2 class="tituloProducto"><bean:message key="winners.title"/></h2>
            <div class="producto noBorderBottom">
                <p><strong><bean:message key="winners.text1"/></strong></p>
                <p><bean:message key="winners.text2"/></p>
            </div>
            <br class="clear" />
            <ul class="winList">
            
            <%
            	WinDAO winDAO = new WinDAO();
            	Criteria criteria = new Criteria();
            	criteria.addLessOrEqualThan( "created", Utils.today());
            	criteria.addEqualTo("isOnlyCredits", false);
            	
            	List<Win> winList = winDAO.getWinListByCriteriaAndRange( criteria, "created", 0, 24);
            	
            	Collections.sort( winList, new Comparator<Win>() {
                    public int compare( Win w1, Win w2) {
                        if( w1.getCreated().before(w2.getCreated())) return 1;
                        if( w1.getCreated().after(w2.getCreated())) return -1;
                        return w2.getUser().getLogin().compareTo(w1.getUser().getLogin());
                    }
          	   });
            	
            	String lastDate = null;
            	for( Win win: winList){
            		request.setAttribute("win", win);
            %>
            	<li style="background: #FFFFFF url(/img/item/<c:out value="${win.item.picture1Url}"/>) top left no-repeat; -moz-border-radius: 10px;border-radius: 10px;">
            		<div style="position: absolute;margin: 5px;" >
	            		<c:choose>
	            			<c:when test="${win.gridType == 1 }">
	            				<img src="/img/banderola_ico_sorteo_standard.png" alt="Normal game" title="Normal game">
	            			</c:when>
	            			<c:when test="${win.gridType == 2 }">
	            				<img src="/img/banderola_ico_sorteo_principiantes.png" alt="Beginners game" title="Beginners game">
	            			</c:when>
	            			<c:when test="${win.gridType == 3 }">
	            				<img src="/img/banderola_ico_sorteo_primero.png" alt="Fast game" title="Fast game">
	            			</c:when>
	            			<c:when test="${win.gridType == 4 }">
	            				<img src="/img/banderola_ico_sorteo_preciounico.png" alt="Fixed price game" title="Fixed price game">
	            			</c:when>
	            			<c:when test="${win.gridType == 5 }">
	            				<img src="/img/banderola_ico_sorteo_conpistas.png" alt="Double or nothing game" title="Double or nothing game">
	            			</c:when>
	            			<c:when test="${win.gridType == 6 }">
	            				<img src="/img/banderola_ico_sorteo_gratis.png" alt="Free game" title="Free game">
	            			</c:when>
	            			<c:when test="${win.gridType == 7 }">
	            				<img src="/img/banderola_ico_sorteo_multipremio.png" alt="Multiprize game" title="Multiprize game">
	            			</c:when>
	            		</c:choose>
	            	</div>
            		
            		<span>
	            		<strong>
	            		<c:if test="${win.item.name == 'Treasure chest'}">
	            			<fmt:formatNumber value="${win.item.bitcoins}" maxFractionDigits="3"/> Bitcoin
	            		</c:if>
	            		
	            		<c:if test="${win.item.name != 'Treasure chest'}">
	            			<c:out value="${win.item.name}" escapeXml="false"/>
	            		</c:if>
	            		</strong>
	            		<c:choose>
	            			<c:when test="${win.gridType == 1 }">
	            				(Normal)
	            			</c:when>
	            			<c:when test="${win.gridType == 2 }">
	            				(Beginners)
	            			</c:when>
	            			<c:when test="${win.gridType == 3 }">
	            				(Fast)
	            			</c:when>
	            			<c:when test="${win.gridType == 4 }">
	            				(Fixed price)
	            			</c:when>
	            			<c:when test="${win.gridType == 5 }">
	            				(Double or nothing)
	            			</c:when>
	            			<c:when test="${win.gridType == 6 }">
	            				(Free)
	            			</c:when>
	            			<c:when test="${win.gridType == 7 }">
	            				(Multiprize)
	            			</c:when>
	            		</c:choose>
	            		
	            		<c:if test="${empty win.transactionId }">
		            		<br/><br/>
		            		<fmt:setLocale value="en_US" /> 
		            		User: <c:out value="${win.user.login}"/><br/><br/>
		            		<fmt:formatDate value="${win.created}" pattern="yyyy-MMMM-dd HH:mm" timeZone="UTC"/>
	            		</c:if>
	            		
	            		<c:if test="${not empty win.transactionId }">
		            		</strong><br/>
		            		<fmt:setLocale value="en_US" /> 
		            		User: <c:out value="${win.user.login}"/><br/>
		            		<fmt:formatDate value="${win.created}" pattern="yyyy-MMMM-dd HH:mm" timeZone="UTC"/><br/><br/>
		            		<a href="https://blockchain.info/tx/<c:out value="${win.transactionId}"/>" target="_blank">Transaction (Blockchain)</a>
	            		</c:if>
	            		
	            		
	            	</span>
            	</li>
            <%
            lastDate = new SimpleDateFormat ( "dd/MM/yyyy HH:mm:ss").format( win.getCreated());
            } %>
            </ul>
            
            <div style="width:100%;padding-left: auto;text-align: center;" id="seeMoreDiv">
            	<a href="javascript:seeMore('<%=lastDate%>');">See more</a>
            </div>
	    </div>
	</tiles:put>
</tiles:insert>
