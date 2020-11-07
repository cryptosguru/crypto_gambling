<%@page import="com.madgrid.web.util.Utils"%>
<%@page import="com.madgrid.model.Grid"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.ojb.broker.query.Criteria"%>
<%@page import="java.util.Date"%>
<%@page import="com.madgrid.web.util.UserSession"%>
<%@page import="com.madgrid.model.User"%>
<%@page import="com.madgrid.web.util.CookieManager"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@page import="com.madgrid.dao.UserDAO"%>
<%@page import="com.madgrid.dao.WinDAO"%>
<%@page import="com.madgrid.model.Win"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
			Date now = Utils.today();
			//oficina, casa, local, casa2
			if( true /*ip.equals("80.26.159.249") || ip.equals("80.26.159.251") || ip.equals ("77.228.91.164") || ip.equals ("127.0.0.1") || ip.equals("81.39.153.200")*/){
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script type="text/javascript">
			
			if (location.protocol != 'https:'){
			 location.href = 'https:' + window.location.href.substring(window.location.protocol.length);
			}
			
		</script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Instantri.ch - Open boxes, Win Bitcoins</title>
		<meta name="description" content="Bitcoin gambling site. Open boxes, win Bitcoins">
		<meta name="keywords" content="bitcoin,gambling,box,prize,game">
		<meta name="adbit-site-verification" content="e9039010bd8ec9790c2e360cc36c5772cb154aefc9557a044a52580ff51a9486"/>
		<meta property="og:title" content="Instantri.ch" />
    	<meta property="og:site_name" content="Open boxes. Win Bitcoin."/>
    	<meta property="og:type" content="website" />
    	<meta property="og:image" content="https://www.instantri.ch/img/fb.png" />
    	<meta property="og:url" content="https://www.instantri.ch" />
    	<meta property="og:description" content="Win some Bitcoin prizes. You can play for free or buy credits to increase your chances. Fun and easy." />
		<link href="/css/reset.css" rel="stylesheet" type="text/css" />
		<link href="/css/instantrich.css" rel="stylesheet" type="text/css" />
		<link href="/css/ir_animations.css" rel="stylesheet">
		<link href="/css/jquery.bubblepopup.v2.3.1.css" rel="stylesheet" type="text/css" />
		<link href="/css/jquery.cluetip.css" rel="stylesheet" type="text/css" />
		<link href="/css/jquery-ui.min.css" rel="stylesheet" type="text/css" />
		<link href="/css/thickbox.css" rel="stylesheet" type="text/css" />
		<link href="/css/simpleFAQ.css" rel="stylesheet" type="text/css" /> 
		
		
		<script type="text/javascript" src="/dwr/engine.js"> </script>
		<script type="text/javascript" src="/dwr/interface/JDwrTools.js"> </script>
		<script type="text/javascript" src="/dwr/util.js"></script>
		<script type="text/javascript" src="/js/confetti.js"></script>
		
		<link rel="alternate" type="application/rss+xml"  href="https://www.instantri.ch/index.rss" title="Instantri.ch">
		<link rel="shortcut icon" href="/favicon.ico" mce_href="/favicon.ico">
	</head>
<%
	UserSession userSession = (UserSession)session.getAttribute( "userSession");
	int winCount = 0;
	if( userSession == null){
		User user = CookieManager.getUserCookie( request);
		if( user != null){
			userSession = new UserSession();
			userSession.setLoggedIn( Utils.today());
			userSession.setUser( user);
			session.setAttribute( "userSession", userSession);
		}
	}
	
	if( userSession != null){
		UserDAO userDAO = new UserDAO();
		WinDAO winDAO = new WinDAO();
		User user = userDAO.getUserById( userSession.getUser().getId());
		userSession.setUser( user);
		session.setAttribute( "userSession", userSession);
		
		Criteria criteria = new Criteria();
		criteria.addEqualTo("userId", userSession.getUser().getId());
		criteria.addEqualTo("isOnlyCredits", false);
		criteria.addEqualTo("itemSent", false);
		criteria.addEqualTo("deliveryRequested", false);
		winCount = winDAO.getWinCountByCriteria(criteria);
	}
	
%>
	
	<body onload=" <tiles:get name="onload"/>">
		
		<!--
		<center style="padding-top: 10px;">
			<div class="adbit-display-ad" data-adspace-id="1986E00242"></div>
		</center>
		-->
		
		
		<div id="container">
			<div id="cabecera">
		    	<h1><bean:message key="h1"/>Instantri.ch</h1>
		        <a href="https://www.instantri.ch" class="logotipo"><img src="/img/logo_instantrich.png" alt="Instantri.ch" /></a>
		        <%if (userSession == null){ %>
		        	<div id="cajaLogin">
			        	<html:form action="/user/login">
			            	<label for="usuario" class="hide2"><bean:message key="login.user"/></label>
			                <html:text property="login" size="14" styleClass="usuario"/>
			                <label for="password" class="hide2"><bean:message key="login.password"/></label>
			                <html:password property="password" size="14" styleClass="password"/>
			                <input type="image" src="/img/btn_iniciarSesion.png" class="boton" alt="Log in"/>
			            </html:form>
			            <div id="errorLogin">
			            	<html:errors property="login"/>
    					</div>
			        </div>
			    <%} else{%>
			    	 <div id="cajaLogado">
			        	<span class="usuario"><bean:message key="menu.welcome"/>, <strong><c:out value="${userSession.user.login}"/></strong></span>
			            <span class="creditos" id="credits" alt="credits" title="credits"><c:out value="${userSession.user.credits}"/></span>
			            <span style="position: absolute;top: 16px;left: 185px;color: #E3701C;padding: 10px 0 10px 25px;text-decoration:underline;"><a href="/do/credits/buy">Buy credits</a></span>
			            <%if (winCount <= 0){ %>
			            	<a href="/do/win/list" class="premios canjeOff"><bean:message key="menu.prizes"/> (0)</a>
			            <%}else{ %>
			            	<a href="/do/win/list" class="premios canjeOn"><bean:message key="menu.prizes"/> (<%=winCount%>)</a>
			            <%} %>
			            <img src="/img/btn_configuracion.png" alt="Config" class="config_btn" />
						<ul class="config_options">
							<li><a href="/do/credits/buy">Buy credits</a></li>
							<li><a href="/do/user/edit"><bean:message key="menu.profile"/></a></li>
							<li><a href="/do/user/statistics"><bean:message key="menu.stats"/></a></li>
							<li><a href="/do/user/log"><bean:message key="menu.log"/></a></li>
						</ul>
			            <a href="/do/user/logoff" class="logout"><bean:message key="menu.logout"/></a>
			        </div>
			    <%} %>
		        <ul class="nav">

		        	<li class="first"><a href="/faq"><bean:message key="bar.faq"/></a></li>
		        	<%--<li><a href="http://blog.instantri.ch" target="_blank"><bean:message key="bar.blog"/></a></li>  --%>
		        	<li><a href="/message"><bean:message key="bar.contact"/></a></li>
		        	<li><a href="/winners"><bean:message key="bar.winners"/></a></li>
		        	<%if (userSession == null){ %>
		        		<li><a href="/password"><bean:message key="bar.forgotpassword"/></a></li>
		        	<%} %>
		        	<li><a href="/affiliate">Affiliation program</a></li>
		        	<li><a href="/statistics">Site statistics</a></li>
		        	<li id="gmtclock"><strong></strong></li>
		        </ul>
		    </div>
		    <tiles:get name="body"/>
		</div>
		<div id="pie">
			<div id="legal">
		        <ul class="nav">
		        	<li class="first"><a href="/faq"><bean:message key="bar.faq"/></a></li>
		        	<%-- <li><a href="http://blog.instantri.ch" target="_blank"><bean:message key="bar.blog"/></a></li> --%>
		        	<li><a href="/message"><bean:message key="bar.contact"/></a></li>
		        	<li><a href="/winners"><bean:message key="bar.winners"/></a></li>
		        	<%if (userSession == null){ %>
		        		<li><a href="/password"><bean:message key="bar.forgotpassword"/></a></li>
		        	<%} %>
		        	<li><a href="/affiliate">Affiliation program</a></li>
		        	<li><a href="/statistics">Site statistics</a></li>
		        </ul>
		        <span class="copyright"><bean:message key="bar.copyright"/></span>
		    </div>
		    <div id="social">
		    	<a target="_blank" href="http://www.twitter.com/instantri_ch" title="Follow us in Twitter">
		    		<div id="twitter">
		    			<span style="position: absolute;display: block;width: 0;height: 0;overflow: hidden;">
		    				<bean:message key="social.twitter"/>
		    			</span>
					</div>
				</a>
				<a target="_blank" href="http://facebook.com/instantri.ch.btc" title="See our Facebook page">
					<div id="facebook" >
						<span style="position: absolute;display: block;width: 0;height: 0;overflow: hidden;">
							<bean:message key="social.facebook"/>
						</span>
					</div>
				</a>
				<a href="/index.rss" rel="nofollow" title="Follow us by rss">
					<div id="rss">
						<span style="position: absolute;display: block;width: 0;height: 0;overflow: hidden;">
							<bean:message key="social.rss"/>
						</span>
					</div>
				</a>
			</div>
		</div>
		
		
		
		
		
		
		<div id="IRanimation" class="Animation-A">
			<!--<div class="blocker"></div>-->
			<div class="mainWrapper">

				<div class="animWrapper">
					<div class="textA congrats">Congratulations</div>
					<div class="textA userName"></div>
					
					<div class="box">				
						<img src="/img/box_base.png" class="boxPart">
						<img src="/img/item/credits1.png" class="prizeItem">
						<img src="/img/box_middle.png" class="boxPart">
						<img src="/img/box_top.png" class="boxPart top">
					</div>

					<div class="textB">You found</div>
					<div class="textB prize">The prize!</div>

					<div class="textC">
						<p>You activated the auto claim prizes option so we'll send the prize to your bitcoin wallet address in the next 24 hours</p>
					</div>

					<div class="buttonHolder">
						<button><img src="/img/bt_great.png"></button>
					</div>

					<div class="raysBG"><img src="/img/rays.svg"></div>
					<div class="gradientBG"></div>

				</div>
				
				
			</div>
		</div>
		
		<div id="IRanimationB" class="Animation-B">
			<div class="mainWrapper">

				<div class="animWrapper">
					<div class="textA congrats">Congratulations</div>
					<div class="textA userName"></div>
					
					<img src="/img/item/credits1.png" class="prizeItem">

					<div class="textB">You won</div>
					<div class="textB prize">The prize!</div>

					<div class="textC">
						<p>You activated the auto claim prizes option so we'll send the prize to your bitcoin wallet address in the next 24 hours</p>
					</div>

					<div class="buttonHolder">
						<button><img src="/img/bt_great.png"></button>
					</div>

					<div class="raysBG"><img src="/img/rays.svg"></div>
					<div class="gradientBG"></div>
				</div>

				<canvas id="confetti" class="confetti"></canvas>
				
				
			</div>
		</div>
		
		<div id="IRanimationC" class="Animation-C">
			<div class="mainWrapper">

				<div class="animWrapper">
					
					
					<div class="credit">
						<img src="/img/item/credits1.png" class="token">
						<img src="/img/starStroke.svg" class="star">	
						<img src="/img/starStroke.svg" class="star">	
						<img src="/img/starStroke.svg" class="star">	
					</div>

					<div class="textB">You found</div>
					<div class="textE prize">1 credit</div>
					
				</div>
	
			</div>
		</div>
				
				
				
		
		<%
		if( request.getRequestURI().toString().contains("/credits/buy.jsp")){
		%>
			<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
			<script type="text/javascript" src="/js/jquery-ui.new.min.js"></script>
		<%} else {%>
			
			<script type="text/javascript" src="/js/jquery-1.4.3.js"></script>
			<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
		<%}%>
		<script type="text/javascript" src="/js/TweenMax.min.js"></script>
		<script type="text/javascript" src="/js/jquery.bgColor.js"></script> 
		<script type="text/javascript" src="/js/jquery.highlightFade.js"></script>
		<script type="text/javascript" src="/js/jquery.bubblepopup.v2.3.1.min.js" ></script>
		<script type="text/javascript" src="/js/jquery.cluetip.min.js"></script>
		
		<script type="text/javascript" src="/js/jquery.hoverIntent.js"></script>
		<script type="text/javascript" src="/js/jquery.quicksilver.js"></script> 
    	<script type="text/javascript" src="/js/jquery.simpleFAQ-0.7.js"></script>
    	<script type="text/javascript" src="/js/jquery.ie6blocker.js"></script> 
		<script type="text/javascript" src="/js/thickbox.js"></script>
		<script type="text/javascript" src="/js/instantrich.js"></script>
		<script type="text/javascript">
			function showSessionThickbox(){
				tb_show("","/thickbox/session.html?height=110&width=290&modal=true",""); 
			}
			
			function showRestartServerThickbox(){
				tb_show("","/thickbox/restart.html?height=450&width=500&modal=true",""); 
			}
			
			function showRestartWarningThickbox(){
				tb_show("","/thickbox/restartWarning.html?height=220&width=400&modal=true",""); 
			}
						$.post("/do/log", { value: document.URL+'   <%=request.getRequestURI().toString()%>'});
			
			$(document).ready(function(){
				checkSessionTimeOut();
			});
		
			var today = <%=now.getTime() %>;
			var interval;
			function startTime(){
				var timeToShow = new Date(today);
				var h=timeToShow.getUTCHours();
				var m=timeToShow.getUTCMinutes();
				var s=timeToShow.getUTCSeconds();
				m=checkTime(m);
				s=checkTime(s);
				document.getElementById('gmtclock').innerHTML=h+":"+m+":"+s + " GMT";
				today = today + 1000;
				interval =setTimeout(function(){startTime()},1000);
			}
			
			function calibrateClock(ms){
				today = ms;
				clearInterval(interval);
				startTime();

			}
	
			function checkTime(i){
				if (i<10){
				  i="0" + i;
				}
				return i;
			}
			
			$(document).ready(function(){
				startTime();
			});
		
			<tiles:get name="script"/>
		</script>
				
	<%			
	String validationCorrect = (String)session.getAttribute("validationCorrect");
	
	if( validationCorrect != null && validationCorrect.equals("ok")){
		session.removeAttribute("validationCorrect");
	%>
	<script>
	$(document).ready(function(){
		tb_show("","/thickbox/validationCorrect.html?height=150&width=380&modal=false","");
	});
	</script>
	<%	
	}
	%>
				
		<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-49316641-1', 'instantri.ch');
  ga('require', 'displayfeatures');
  ga('send', 'pageview');
  </script>
	
	</body>
</html>
<%} else{%>

<html>
	<head>
		<title>
			Instantri.ch
		</title>
	</head>
	<body style="background:#000 url(/img/bg_body.jpg) center top no-repeat; margin:0; padding:0;">
		<div style="width:100%;text-align: center;padding-top: 100px;padding-left:25px;">
			<img src="/img/sorteo_caja_premiada.png" width="43" height="43"/>
		</div>
		<div style="width:100%;font-family: sans-serif;font-size: 32px;color:#DDDDDD;text-align: center;padding: 20px;text-shadow: 3px 3px 4px #A0A0A0;">
			Comming Soon
		</div>
		
	</body>
</hmtl>
<%} %>
	
	