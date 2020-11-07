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
	        	<li><a href="/faq"><bean:message key="faq.breadcrumb"/></a></li>
	        </ul>
	        <h2 class="tituloProducto"><bean:message key="faq.title"/></h2>
	        
			<div id="htmlFaq"> 
			  <ul id="faqList"> 
			    <li> 
			      <p class="question"><bean:message key="faq.question1"/></p> 
			      <div class="answer"> 
			        <p> 
			          	<bean:message key="faq.question1text1"/>
			        </p>
			        <br/>
			        <p> 
			          	<bean:message key="faq.question1text2"/> 
			        </p>
			        <br/>
			        <p> 
			          	<bean:message key="faq.question1text3"/>   
			        </p>
			        <br/>
			        <p> 
			          	<bean:message key="faq.question1text4"/>
			        </p>
			        
			      </div> 
			    </li> 
			    
			    <li> 
			      <p class="question">Tell me about those different types of games.</p> 
			      <div class="answer"> 
			        <p> 
			          	There are 6 games types.
			        </p>
			        <br/>
			        <p >
			        	<table>
			        		<tr>
			        			<td style="vertical-align: middle;width:36px;">
			        				<img src="/img/banderola_ico_sorteo_standard.png" width="32">
			        			</td>
			        			<td style="text-align: justify;">
			        				<strong>Normal</strong>: The credits you need to open a box depend on the amount of remaining boxes. Once you find the prize inside a box, you'll have to wait some time to finally get it. If somebody opens any other box before the time counter reaches 0 the prize moves to another random box and the game continues.
			        			</td>
			        		</tr> 
			          	</table>
			        </p>  	 
			        <br/>
			        <p>
			        	<table>
			        		<tr>
			        			<td style="vertical-align: middle;width:36px;">
			        				<img src="/img/banderola_ico_sorteo_primero.png" width="32">
			        			</td>
			        			<td style="text-align: justify;">
			        				<strong>Fast</strong>: The rules are the same as in the normal game but there is no waiting time. If you find the prize inside a box you get it immediately.
			        			</td>
			        		</tr> 
			          	</table> 
			        </p>
			        <br/>
			        <p> 
			        	<table>
			        		<tr>
			        			<td style="vertical-align: middle;width:36px;">
			        				<img src="/img/banderola_ico_sorteo_preciounico.png" width="32">
			        			</td>
			        			<td style="text-align: justify;">
			        				<strong>Fixed prize</strong>: The rules are the same as in the normal game but the credits you need to open a box are always the same. The game ends when the amount of remaining boxes times the credits you need to open on box equals the value of the prize.
			        			</td>
			        		</tr> 
			          	</table> 
			        </p>
			        <br/>
			        <p> 
			        	<table>
			        		<tr>
			        			<td style="vertical-align: middle;width:36px;">
			        				<img src="/img/banderola_ico_sorteo_conpistas.png" width="32">
			        			</td>
			        			<td style="text-align: justify;">
			        				<strong>Double or nothing</strong>: There are only two boxes, the amount of credits you need to open one of those two boxes is half the value of the prize. You have 50% chances to win the prize. Of course there is no waiting time.
			        			</td>
			        		</tr> 
			          	</table> 
			        </p>
			        <br/>
			        <p> 
			        	<table>
			        		<tr>
			        			<td style="vertical-align: middle;width:36px;">
			        				<img src="/img/banderola_ico_sorteo_principiantes.png" width="32">
			        			</td>
			        			<td style="text-align: justify;">
			        				<strong>Beginners</strong>: The rules are the same as in normal games but only user who never won a prize can play.
			        			</td>
			        		</tr> 
			          	</table> 
			          	 
			        </p>
			        <br/>
			        <p> 
			        	<table>
			        		<tr>
			        			<td style="vertical-align: middle;width:36px;">
			        				<img src="/img/banderola_ico_sorteo_gratis.png" width="32">
			        			</td>
			        			<td style="text-align: justify;">
			        				<strong>Free</strong>: The rules are the same as in normal games but opening a box has no cost at all. For every free game each user can only open one box though.
			        			</td>
			        		</tr> 
			          	</table> 
			          	 
			        </p>
			        <br/>
			        <p> 
			        	<table>
			        		<tr>
			        			<td style="vertical-align: middle;width:36px;">
			        				
			        			</td>
			        			<td style="text-align: justify;">
			        				<strong>Multiprize</strong> (soon): The rules are the same as in normal games but, besides the main prize, there are 9 more prizes hidden in the boxes. There are 5 prizes of 1 credits, 2 prizes of 2 credits, 1 prize of 5 credits and 1 prize of 10 credits. If you find one of those prizes the credits are added to your account immediately.
			        			</td>
			        		</tr> 
			          	</table> 
			        </p>
			      </div> 
			    </li> 
			    
			    
			    
			    
			    <li>
			    <p class="question"><bean:message key="faq.question2"/></p> 
			      <div class="answer"> 
			        <p> 
			          	<bean:message key="faq.question2text1"/>
			        </p>
			        <br/>
			        <p> 
					  	<bean:message key="faq.question2text2"/>			           
			        </p>
			        <br/>
			        <p> 
					  	<bean:message key="faq.question2text3"/> 			           
			        </p>
			        <br/>
			        <p> 
					  	<bean:message key="faq.question2text4"/>  			           
			        </p>
			      </div> 
			    </li> 
			    
			    <li> 
			      <p class="question"><bean:message key="faq.question3"/></p> 
			      <div class="answer"> 
			        <p> 
			          	<bean:message key="faq.question3text1"/> 
			        </p>
			        <br/>
			        <p> 
					  	<bean:message key="faq.question3text2"/>  			           
			        </p>
			      </div> 
			    </li>
			    
			    <li> 
			      <p class="question"><bean:message key="faq.question4"/></p> 
			      <div class="answer"> 
			        <p> 
			          	<bean:message key="faq.question4text1"/> 
			        </p>
			      </div> 
			    </li>
			    <li> 
			      <p class="question"><bean:message key="faq.question5"/></p> 
			      <div class="answer"> 
			        <p> 
						<bean:message key="faq.question5text1"/>			          	 
			        </p>
			        <br/>
			        <p> 
			          	<bean:message key="faq.question5text2"/>
			        </p>
			        <br/>
			        <p> 
			          	<bean:message key="faq.question5text3"/>
			        </p>
			        <br/>
			        <p> 
			          	<bean:message key="faq.question5text4"/>
			        </p>
			      </div> 
			    </li>
			    <li> 
			      <p class="question"><bean:message key="faq.question6"/></p> 
			      <div class="answer"> 
			        <p> 
						<bean:message key="faq.question6text1"/>          	 
			        </p>
			        <br/>
			        <p> 
			          	<bean:message key="faq.question6text2"/> 
			        </p>
			      </div> 
			    </li>
			    <li> 
			      <p class="question"><bean:message key="faq.question7"/></p> 
			      <div class="answer"> 
			        <p> 
						<bean:message key="faq.question7text1"/>          	 
			        </p>
			      </div> 
			    </li> 
			    <li> 
			      <p class="question">Is there any affiliation program?</p> 
			      <div class="answer"> 
			        
			        
			        <p> 
			          	Yes, with our <strong>Affiliation Program</strong> you'll get 15% of every Bitcoin an user who signs up thanks to you spends in Instantri.ch. You need to contact us so we can create a new affiliation account for you. Go to <strong><a href="/affiliate">Affiliation/Reward Program</a></strong> to get more information.
			        </p>
			      </div> 
			    </li> 
			    <li> 
			      <p class="question"><bean:message key="faq.question9"/></p> 
			      <div class="answer"> 
			        <p> 
						<bean:message key="faq.question9text1"/>          	 
			        </p>
			        <br/>
			        <p> 
			          	<bean:message key="faq.question9text2"/> 
			        </p>
			      </div> 
			    </li> 
			    <li> 
			      <p class="question">Why there are some last winner items with no transaction link?</p> 
			      <div class="answer"> 
			        <p>That happens in these cases:</p><br/>
					<p>1-The user won a prize but never claimed it. When it happens we try to contact them but it doesn't work everytime.</p><br/>
					<p>2-An user manages to win a prize before we notice it's a fraudulent user with duplicated or fake accounts. The database saves the item but we never pay the prize.</p>
			      </div> 
			    </li> 
			   
			    
			    
			  </ul> 
			</div> 
	    	
	    	
	    </div>
	</tiles:put>
</tiles:insert>


