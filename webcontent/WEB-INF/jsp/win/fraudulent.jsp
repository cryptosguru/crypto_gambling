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
			$('ul#faqList').simpleFAQ();
			
		});
		
		
	</tiles:put>
	<tiles:put name="onload" type="string">
	</tiles:put>
	<tiles:put name="body" type="string">
		<div id="pagina">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/faq"><bean:message key="win.breadcrumb"/></a></li>
	        </ul>
	        <h2 class="tituloProducto">Sorry, you are not allowed to claim any Bitcoin prize</h2>
	        
			<div class="producto noBorderBottom">
	        	<p>As you may know for the emails we sent you, you are marked as a fraudulent user and therefore you cannot receive any Bitcoin prize.</p>
	        	<p>The main reason for being marked as fraudulent is creating multiple or fake accounts to get free credits. As we state in our user conditions and F.A.Q. that behaviour is forbidden in Instantri.ch</p>
	        	<p>Hope you understand.</p>
	                
	        </div>
	    </div>
	</tiles:put>
</tiles:insert>


