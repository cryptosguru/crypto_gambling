<%@page import="com.madgrid.web.util.UserSession"%>
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
		 <%
                   	 UserSession userSession = (UserSession)session.getAttribute( "userSession");
		 %>
	
		<div id="pagina">
	    	<ul class="rastro" style="padding-bottom: 50px;">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/affiliate">Affiliation program</a></li>
	        </ul>
	        
	        
	        
	        
	         <h2 class="tituloProducto">Get bitcoins with our Affiliation Program</h2>
	        
			<div class="producto noBorderBottom" style="width:620px;">
	        	<p>If you want to help Instantri.ch to grow up and <strong>win some Bitcoins</strong> doing that, you can join to our <strong>Affiliation Program</strong>.</p>
	        	<p>You will earn <strong>15% of all Bitcoins</strong> instantri.ch gets thanks to you. You don't need to be a company nor have a blog. You only need to be an active seeker of new Instantri.ch users.</p>
	        	<p>As an affiliated user there is a <strong>private control panel</strong> for you where you can see your activity and request the Bitcoin payment once you have enough balance.</p>
	        	<%if(userSession != null && userSession.getUser().getValidated() == true){ %>
	        		<p><a href="/do/user/joinAffiliationProgram" style="font-size:17px;text-decoration: underline;">Join our affiliation program now</a></p>
	        	<%} else if(userSession != null && userSession.getUser().getValidated() == false){ %>
	        		<p>You need to validate your account to join our affiliation program. Click on the validation link we sent you by email. </p>
	        		<p>If you didn't receive our email you can resend it from your <a href="http://www.instantri.ch/do/user/statistics">statistics</a> page.  </p>
	        	<%} else { %>
	        		<p>You need to be an Instantri.ch user to join our affiliation program. <a href="/register">Register</a> now.</p>
	        	<%} %>
	        	
	        </div>
	        
	        
	        <div class="producto noBorderBottom" style="width:890px;">
	        	<div style="float: left;margin: 15px;">
					<a href="javascript:tb_show('','/img/controlpanel1.png','');">
						<img src="/img/controlpanel1thumb.png" style="border:1px solid grey;border-radius: 10px;box-shadow: 5px 5px 10px #888888;"/>
					</a><br/>
					<div style="text-align: center;margin-top: 15px;">
						<a href="javascript:tb_show('','/img/controlpanel1.png','');">
							Affiliation control panel login
						</a>	
					</div>
				</div>
				<div style="float: left;margin: 15px;">
					<a href="javascript:tb_show('','/img/controlpanel2.png','');">
						<img src="/img/controlpanel2thumb.png" style="border:1px solid grey;border-radius: 10px;box-shadow: 5px 5px 10px #888888;"/>
					</a><br/>
					<div style="text-align: center;margin-top: 15px;">
						<a href="javascript:tb_show('','/img/controlpanel2.png','');">
							Affiliation control panel dashboard
						</a>
					</div>
				</div>
				<div style="float: left;margin: 15px;">
					<a href="javascript:tb_show('','/img/controlpanel3.png','');">
						<img src="/img/controlpanel3thumb.png" style="border:1px solid grey;border-radius: 10px;box-shadow: 5px 5px 10px #888888;"/>
					</a><br/>
					<div style="text-align: center;margin-top: 15px;">
						<a href="javascript:tb_show('','/img/controlpanel3.png','');">
							Affiliation control panel user list
						</a>
					</div>
				</div>
				
			</div>
			
	        
	    </div>
	</tiles:put>
</tiles:insert>


