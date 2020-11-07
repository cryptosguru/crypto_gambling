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
	        	<li><a href="/message">Reset password</a></li>
	        </ul>
	        <h2 class="tituloProducto">Reset password</h2>
            <div class="producto noBorderBottom">
                <p><strong>Your password has been changed.</strong></p>
                <p>You will be redirected automatically to the main page in <span id="timeId">8</span> seconds. Click <a href="/">here</a> if you don't want to wait that much.</p>
            </div>
            <br class="clear" />
	    </div>
	</tiles:put>
</tiles:insert>

