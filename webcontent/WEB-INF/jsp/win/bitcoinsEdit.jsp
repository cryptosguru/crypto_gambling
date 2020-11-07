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
		
	</tiles:put>
	<tiles:put name="onload" type="string">
	</tiles:put>
	<tiles:put name="body" type="string">
	
		<div id="pagina" style="height:800px;">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/do/win/list"><bean:message key="win.breadcrumb"/></a></li>
	        </ul>
	        <h2 class="tituloProducto"><bean:message key="win.address"/></h2>
	        <html:form action="/win/save">
	        	<html:hidden property="id"/>
	            <div class="producto noBorderBottom">
	                <p><strong><bean:message key="win.text3"/></strong></p>
	                <p><bean:message key="win.text4"/></p>
	                <fieldset>
	                     <p>
	                    	<label for="nombre"><bean:message key="win.bitcoinaddress"/></label>
	                    	<html:text property="bitcoinAddress"  size="50" styleClass="width280"/>
	                    </p>
	            </div>
	            <br class="clear" />
	       		<div class="botonera">
	       			<input class="boton" type="button" value="Cancelar" onclick="document.location.href='/do/win/list'"/>&nbsp;
	       			<html:submit styleClass="boton"><bean:message key="win.request"/></html:submit>
	       		</div>
	            
	        </html:form>
	        <br class="clear" />
	    </div>
	</tiles:put>
</tiles:insert>







