<%@page import="org.apache.struts.util.MessageResources"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@page import="com.madgrid.model.Grid"%>



	
<tiles:insert template="/WEB-INF/jsp/mainTile.jsp">
	<tiles:put name="script" type="string">
			$(document).ready(function(){
				$(".config_options").hide();
				$(".config_btn").click(function () {
					$(".config_options").slideToggle("slow");
				});
				
				reloadCluetip();
				IR_Animation.init();
				IR_AnimationB.init();
				IR_AnimationC.init();
			});
			
			function reloadCluetip(){
				$('.showDetails').cluetip({
					cluetipClass: 'detalleServicio',
					positionBy: 'mouse',
					dropShadow: true,
					topOffset: 0,
					leftOffset: 10,
					width:550,
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
			
			function playAnimation(name, picture, text){
				IR_Animation.play(name, picture , text, 3);
			}
			
			function playAnimationB(name, picture, text){
				IR_Animation.close();
				IR_AnimationB.play(name,picture,text, 3);
			}
			function playAnimationC(credits, picture){
				IR_AnimationC.play(credits, picture , undefined);
			}
			
			
	</tiles:put>
	<tiles:put name="onload" type="string">
		if( navigator.userAgent.indexOf('MSIE 6')<0) {dwr.engine.setActiveReverseAjax(true);JDwrTools.reverseAjaxItem(<c:out value="${grid.id}"/>);}
	</tiles:put>
	<tiles:put name="body" type="string">
		<div id="pagina">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="<c:out value="${grid.virtualPath}"/>"><c:out value="${grid.item.name}" escapeXml="false"/></a></li>
	        </ul>
	        <h2 class="tituloProducto">
    				<c:out value="${grid.item.name}" escapeXml="false"/>
	        </h2>

	        
	        <c:choose>
	        	<c:when test="${grid.type==1}">
	        		<div class="describeSorteo sorteo_standard">
	        	</c:when>
	        	<c:when test="${grid.type==2}">
	        		<div class="describeSorteo sorteo_principiantes">
	        	</c:when>
	        	<c:when test="${grid.type==3}">
	        		<div class="describeSorteo sorteo_primero">
	        	</c:when>
	        	<c:when test="${grid.type==4}">
	        		<div class="describeSorteo sorteo_preciounico">
	        	</c:when>
	        	<c:when test="${grid.type==5}">
	        		<div class="describeSorteo sorteo_conpistas">
	        	</c:when>
	        	<c:when test="${grid.type==6}">
	        		<div class="describeSorteo sorteo_gratuito">
	        	</c:when>
	        	<c:when test="${grid.type==7}">
	        		<div class="describeSorteo sorteo_multipremio">
	        	</c:when>
	        </c:choose>
	        <%
	        	Grid grid = (Grid) request.getAttribute("grid");
	        	Integer seconds = grid.getPartialWinSeconds();
	        	
	        	MessageResources mr = MessageResources.getMessageResources("madgridMessageResources");
	        	 
	        	String secondsString = mr.getMessage( "item.indeterminedtime");
	        	if( seconds != null){
	        		if (seconds <= 60){
	        			secondsString = seconds + (seconds == 1?" " +  mr.getMessage("item.second")+".":" " +  mr.getMessage("item.seconds") + "."); 
	        		} else if( seconds <= 3600){
	        			secondsString = "" + (seconds / 60)+ " min.";
	        			if( seconds % 60 != 0){
	        				secondsString = secondsString + " " +  mr.getMessage("item.and") + " "+ (seconds % 60) + " " +  mr.getMessage("item.sec") + ".";
	        			}
	        		} else{
	        			secondsString = "en " + (seconds / 3600)+ (seconds / 3600==1? " "+  mr.getMessage("item.hour"): mr.getMessage("item.hours"));
	        			int remainSeconds = seconds % 3600;
	        			if( remainSeconds != 0){
	        				if( remainSeconds / 60 != 0){
	        					secondsString = secondsString + ", " + (remainSeconds / 60)+ " min.";
	        				}
		        			if( remainSeconds % 60 != 0){
		        				secondsString = secondsString + " y " + (remainSeconds % 60) + " " +  mr.getMessage("item.sec") +".";
		        			}
	        			} else{
	        				secondsString = secondsString + ".";
	        			}
	        		}
	        	}
	        %>
	        
	        	<div class="nombreSorteo">
	        		<c:choose>
			        	<c:when test="${grid.type==1}">
			        		<bean:message key="item.game1"/>
			        	</c:when>
			        	<c:when test="${grid.type==2}">
			        		<bean:message key="item.game2"/>
			        	</c:when>
			        	<c:when test="${grid.type==3}">
			        		<bean:message key="item.game3"/>
			        	</c:when>
			        	<c:when test="${grid.type==4}">
			        		<bean:message key="item.game4"/>
			        	</c:when>
			        	<c:when test="${grid.type==5}">
			        		<bean:message key="item.game5"/>
			        	</c:when>
			        	<c:when test="${grid.type==6}">
			        		<bean:message key="item.game6"/>
			        	</c:when>
			        	<c:when test="${grid.type==7}">
			        		<bean:message key="item.game7"/>
			        	</c:when>
			        </c:choose>
	        	</div>
	            <p>
	            	<c:choose>
			        	<c:when test="${grid.type==1}">
								<bean:message key="item.game1text1"/><br/><br/>
								<bean:message key="item.game1text2"/><br/><br/>
								<bean:message key="item.game1text3"/> <strong><%=secondsString%></strong>
			        	</c:when>
			        	<c:when test="${grid.type==2}">
								<bean:message key="item.game2text1"/><br/><br/>
								<bean:message key="item.game2text2"/><br/><br/>
								<bean:message key="item.game2text3"/> <strong><%=secondsString%></strong>
			        	</c:when>
			        	<c:when test="${grid.type==3}">
			        			<bean:message key="item.game3text1"/><br/><br/>
			        			<bean:message key="item.game3text2"/><br/><br/>
								<bean:message key="item.game3text3"/>
			        	</c:when>
			        	<c:when test="${grid.type==4}">
			        			<bean:message key="item.game4text1"/><br/><br/>
			        			<bean:message key="item.game4text2"/><br/><br/>
			        			Game will end when the value of the prize is bigger than the value of the credits you need to open the remain boxes<br/><br/>
								<bean:message key="item.game4text3"/> <strong><%=secondsString%></strong>
			        	</c:when>
			        	<c:when test="${grid.type==5}">
			        			<bean:message key="item.game5text1"/><br/><br/>
			        			<bean:message key="item.game5text2"/><br/><br/>
								<bean:message key="item.game5text3"/>
			        	</c:when>
			        	<c:when test="${grid.type==6}">
			        			<bean:message key="item.game6text1"/><br/><br/>
								<bean:message key="item.game6text2"/><br/><br/>
								<bean:message key="item.game6text3"/> <strong><%=secondsString%></strong>
			        	</c:when>
			        	<c:when test="${grid.type==7}">
			        			<bean:message key="item.game7text1"/><br/><br/>
								<bean:message key="item.game7text3"/><br/><br/>
								<bean:message key="item.game7text2"/> <strong><%=secondsString%></strong>
			        	</c:when>
			        </c:choose>
	            </p>
	            <br class="clear" />
	            <p>
	            	<%
	            		Integer freeBoxes = grid.getFreeBoxes();
	            		Float probability = (1f/(float)freeBoxes) * 100f;
	            		request.setAttribute( "probability", probability);
	            	%>
	            	<fmt:setLocale value="en_US" /> 
	            	Probability of finding the prize: <span id="probability"><fmt:formatNumber value="${probability}" maxFractionDigits="2"/>%</span>
	            </p>
	            <br class="clear" />
	            <ul class="caracteristicasSorteo">
	            	<li class="creditos"><strong><span id="boxPrice"><fmt:formatNumber value="${grid.boxPrice}" maxFractionDigits="0"/></span></strong><br /><bean:message key="item.creditsbybox"/></li>
	            	<li class="cajasLibres"><strong><span id="freeBoxes"><c:out value="${grid.freeBoxes}"/></span>/<c:out value="${grid.boxes}"/></strong><br /><bean:message key="item.boxes"/><br /><bean:message key="item.freetotal"/></li>
	            </ul>
	            <br class="clear" />
	            <p><bean:message key="item.start"/>:
	            <fmt:setLocale value="en_US" /> 
    					<fmt:formatDate value="${grid.startDate}" pattern="dd MMMM',' HH:mm:ss"/>
	            </p>
	            <div class="titulo"><bean:message key="item.state"/>:</div>
	            <div class="texto" id="status">
	            </div>
	            <div class="titulo"><bean:message key="item.lastmovements"/>:</div>
	            <div class="texto" id="log">
	            </div>
	        </div>
	        
	        <div class="producto">       
				<div class="visorFotos">
    				<img src='/img/item/<c:out value="${grid.item.picture1Url}"/>' alt='<c:out value="${grid.item.name}" />' class="imgProducto" id="imgProducto"/>
	          	</div>
   				<c:out value="${grid.item.htmlDescription}" escapeXml="false"/>
   				<div style="padding-top:15px;padding-bottom:5px;font-size: 14px;">
   					<strong>Box with the prize hash (MD5)</strong>
   					<a href="#" class="showDetails" rel=".info_002" >
   						<img src="/img/icon_help.png" alt="What is this" />
   					</a>
   				</div>
   				<div style="font-size: 14px;" id="winPosHash">
   					<c:out value="${grid.winPosHash}" escapeXml="false"/>
   				</div>
   				<div style="padding-top:15px;padding-bottom:5px;font-size: 14px;">
   					<strong>Box with the prize text</strong>
   					<a href="#" class="showDetails" rel=".info_003" >
   						<img src="/img/icon_help.png" alt="What is this" />
   					</a>
   				</div>
   				<div style="font-size: 14px;" id="winPosText">
   				</div>
				<a href="#" class="ayuda showDetails" rel=".info_001" ><img src="/img/btn_ayuda.png" alt="<bean:message key="item.help"/>" /></a>
			</div>
	        <div class="cajas">           
		        <p>
		        	<span id='buyButtonSpan'></span>
		        	<c:if test="${grid.ongoing && !grid.finished}">
		        		<bean:message key="item.text1"/>
			        </c:if>
		        </p>
	        	<br class="clear" />
	            <ol class="listaCajas" id="gridTable">
	            	<c:if test="${grid.ongoing}">
		            	<div style="height:300px;">
		    				<div style="text-align:center;height:90px;padding-top:10px;">
		    					<img src="/img/ajax-loader.gif"/>
		    				</div>
		    				<div style="text-align:center;color:#666;">
		    					<bean:message key="item.loading"/>
		    				</div>
		    			</div>
		    		</c:if>
	            
	            </ol>
	        </div>
			<br class="clear" />
	    </div>
	    
	    <div class="info_001" style="display:none;">
			<div class="infoTitle"><bean:message key="item.howto"/></div>
			<ul class="infoUl" style="width:500px;line-height:23px;">
				<li style="text-align:justify"><bean:message key="item.text2"/> <img src="/img/btn_destapar_aleatoria.png" width="114" height="18"/></li>
				<c:if test="${grid.type!=4 && grid.type!=6}">
					<li style="text-align:justify"><bean:message key="item.text3"/> <img src="/img/banderola_ico_creditos.png" width="16" height="18"/>. <bean:message key="item.text4"/></li>
				</c:if>
				<c:if test="${grid.type!=3}">
					<li style="text-align:justify"><bean:message key="item.text5"/></li>
				</c:if>
				<li style="text-align:justify"><bean:message key="item.text6"/> <img src="/img/bg_canje_on.png" width="68" height="18"/> <bean:message key="item.text7"/></li> 
			</ul>
		</div>
		
		<div class="info_002" style="display:none;">
			<div class="infoTitle">What is this?</div>
			<ul class="infoUl" style="width:500px;line-height:10px;">
				<li style="text-align:justify">This is the MD5 hash generated from the text containing the number of the box with the prize.</li>
				<li style="text-align:justify">When somebody finds the prize that text will be shown below.</li>
				<li style="text-align:justify">Then you can verify the prize position was set from the beginning and was not changed during the game.</li>
				<li style="text-align:justify">You can use a site like <a href="http://www.md5.net/">www.md5.net</a> to generate the MD5 hash of the text</li>
			</ul>
		</div>
		
		<div class="info_003" style="display:none;">
			<div class="infoTitle">What is this?</div>
			<ul class="infoUl" style="width:500px;line-height:10px;">
				<li style="text-align:justify">This text will only be shown when somebody finds the box with the prize.</li>
				<li style="text-align:justify">When it's shown you can generate the MD5 hash in a site like  <a href="http://www.md5.net/">www.md5.net</a> and check if it matches the MD5 hash above</li>
			</ul>
		</div>
	    
	</tiles:put>
</tiles:insert>


