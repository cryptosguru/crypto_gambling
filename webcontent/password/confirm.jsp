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
			showTime();
		});
		
		var seconds = 9;
		function showTime(){
			seconds--;
			if(seconds <=0){
				window.location.href="/";
			} else{
				var elm = document.getElementById('timeId');
				elm.innerHTML = seconds;
				setTimeout("showTime()", 1000);
			}
		}
		
	</tiles:put>
	<tiles:put name="onload" type="string">
	</tiles:put>
	<tiles:put name="body" type="string">
		<div id="pagina" style="height:800px;">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/message"><bean:message key="password.breadcrumb"/></a></li>
	        </ul>
	        <h2 class="tituloProducto"><bean:message key="password.title"/></h2>
            <div class="producto noBorderBottom">
                <p><strong><bean:message key="password.confirmtext1"/></strong></p>
                <p><bean:message key="password.confirmtext2"/></p>
            </div>
            <br class="clear" />
	    </div>
	</tiles:put>
</tiles:insert>

