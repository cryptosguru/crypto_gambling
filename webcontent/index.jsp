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
					leftOffset: 0,
					width:300,
					sticky: false,
					activation: 'hover',
					mouseOutClose: true, 
					local: true,
					hideLocal:true,
					showTitle: false,
					closePosition: 'top',
					closeText: '<img src="/img/btnCloseHome.gif" alt="<bean:message key="close"/>" title="<bean:message key="close"/>"></img>',
					arrows: false,
				});
			}
			
			function playAnimation(name, picture, text){
				IR_Animation.play(name, picture , text, 3);
			}
			function playAnimationB(name, picture, text){
				IR_AnimationB.play(name, picture,text, 3);
			}
			
			function playAnimationC(credits, picture){
				IR_AnimationC.play(credits, picture , undefined);
			}
			
	</tiles:put>
	<tiles:put name="onload" type="string">
		if( navigator.userAgent.indexOf('MSIE 6') <0) {dwr.engine.setActiveReverseAjax(true);JDwrTools.reverseAjaxIndex();}
	</tiles:put>
	<tiles:put name="body" type="string">
		<div id="pagina">
	    	<div style="height:700px;">
	    		<div style="text-align:center;height:90px;padding-top:200px;">
	    			<img src="/img/ajax-loader.gif"/>
	    		</div>
	    		<div style="text-align:center;color:#666;">
	    			<bean:message key="loading"/>
	    		</div>
	    	</div>
	    </div>
	    <div class="info_002" style="display:none">
	    	<div class="infoTitle"><bean:message key="index.game2"/></div>
			<ul class="infoUl">
				<li><bean:message key="index.game2text1"/></li>
				<li><bean:message key="index.game2text2"/></li>
				<li><bean:message key="index.game2text3"/></li> 
			</ul>
		</div>
		<div class="info_003" style="display:none">
			<div class="infoTitle"><bean:message key="index.game3"/></div>
			<ul class="infoUl">
				<li><bean:message key="index.game3text1"/></li>
				<li><bean:message key="index.game3text2"/></li>
			</ul>
		</div>
		<div class="info_004" style="display:none">
			<div class="infoTitle"><bean:message key="index.game4"/></div>
			<ul class="infoUl">
				<li><bean:message key="index.game4text1"/></li>
				<li>Game will end when the value of the prize is bigger than the value of the credits you need to open the remain boxes</li>
				<li><bean:message key="index.game4text2"/></li> 
			</ul>
		</div>
		<div class="info_005" style="display:none">
			<div class="infoTitle"><bean:message key="index.game5"/></div>
			<ul class="infoUl">
				<li><bean:message key="index.game5text1"/></li>
				<li><bean:message key="index.game5text2"/></li> 
			</ul>
		</div>
		<div class="info_006" style="display:none">
			<div class="infoTitle"><bean:message key="index.game6"/></div>
			<ul class="infoUl">
				<li><bean:message key="index.game6text1"/></li>
				<li><bean:message key="index.game6text2"/></li>
				<li><bean:message key="index.game6text3"/></li> 
			</ul>
		</div>
		<div class="info_007" style="display:none">
			<div class="infoTitle"><bean:message key="index.game7"/></div>
			<ul class="infoUl">
				<li><bean:message key="index.game7text1"/></li>
				<li><bean:message key="index.game7text2"/></li>
				<li><bean:message key="index.game7text3"/></li> 
				<li><bean:message key="index.game7text4"/></li>
			</ul>
		</div>
	</tiles:put>
</tiles:insert>


