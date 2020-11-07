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
	</tiles:put>
	<tiles:put name="onload" type="string">
	</tiles:put>
	<tiles:put name="body" type="string">
		<div id="pagina">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/faq">Terms and conditions</a></li>
	        </ul>
	        <h2 class="tituloProducto">Terms and conditions</h2>
	        
			<div id="htmlFaq"> 
			  <ul id="faqList"> 
			    <li> 
			      <p class="question" style="background:none;">1. GRANT OF LICENSE</p> 
			      <div class="answer"> 
			        <p> 
			          	1.1. Subject to the terms and conditions contained herein, Instantri.ch grants the User a non-exclusive, personal, non-transferable right to use the Service on your personal computer or other device that accesses the Internet in order to access the games available and described on the Instantri.ch website (the website and games together being the "Service").
			        </p>
			        <br/>
			        <p> 
			          	1.2.  The Service is not for use by (i) individuals under 18 years of age, (ii) individuals under the legal age of majority in their jurisdiction and (iii) individuals accessing the Service from jurisdictions from which it is illegal to do so. Instantri.ch is not able to verify the legality of the Service in each jurisdiction and it is the User's responsibility to ensure that their use of the Service is lawful.
			        </p>
			        <br/>
			        <p> 
			         	 1.3. Instantri.ch and its licensors are the sole holders of all rights in and to the Service and code, structure and organization, including copyright, trade secrets, intellectual property and other rights. You may not, within the limits prescribed by applicable laws: (a) copy, distribute, publish, reverse engineer, decompile, disassemble, modify, or translate the website; or (b) use the Service in a manner prohibited by applicable laws or regulations (each of the above is an "Unauthorized Use"). Instantri.ch reserves any and all rights implied or otherwise, which are not expressly granted to the User hereunder and retain all rights, title and interest in and to the Service. You agree that you will be solely liable for any damage, costs or expenses arising out of or in connection with the commission by you of any Unauthorized Use. You shall notify Instantri.ch immediately upon becoming aware of the commission by any person of any Unauthorized Use and shall provide Instantri.ch with reasonable assistance with any investigations it conducts in light of the information provided by you in this respect.
			        </p>
			        <br/>
			        <p> 
			          	1.4. The term "Instantri.ch", its domain names and any other trade marks, or service marks used by Instantri.ch as part of the Service (the "Trade Marks"), are solely owned by Instantri.ch. In addition, all content on the website, including, but not limited to, the images, pictures, graphics, photographs, animations, videos, music, audio and text (the "Site Content") belongs to Instantri.ch and is protected by copyright and/or other intellectual property or other rights. You hereby acknowledge that by using the Service, you obtain no rights in the Site Content and/or the Trade Marks, or any part thereof. Under no circumstances may you use the Site Content and/or the Trade Marks without Instantri.ch's prior written consent. Additionally, you agree not to do anything that will harm or potentially harm the rights, including the intellectual property rights of Instantri.ch.
			        </p>
			      </div> 
			    </li> 
			    <li> 
			      <p class="question" style="background:none;">2. NO WARRANTIES.</p> 
			      <div class="answer"> 
			        <p> 
			          	2.1. INSTANTRI.CH DISCLAIMS ANY AND ALL WARRANTIES, EXPRESSED OR IMPLIED, IN CONNECTION WITH THE SERVICE WHICH IS PROVIDED TO YOU "AS IS" AND WE PROVIDE YOU WITH NO WARRANTY OR REPRESENTATION WHATSOEVER REGARDING ITS QUALITY, FITNESS FOR PURPOSE, COMPLETENESS OR ACCURACY.
			        </p>
			        <br/>
			        <p> 
			          	2.2. REGARDLESS OF OUR EFFORTS, WE MAKE NO WARRANTY THAT THE SERVICE WILL BE UNINTERRUPTED, TIMELY OR ERROR-FREE, OR THAT DEFECTS WILL BE CORRECTED.
			        </p>
			      </div> 
			    </li> 
			    <li> 
			      <p class="question" style="background:none;">3. AUTHORITY/TERMS OF SERVICE</p> 
			      <div class="answer"> 
			        <p> 
			          	You agree to the game rules described on the Instantri.ch website. Instantri.ch retains authority over the issuing, maintenance, and closing of the Service. The decision of Instantri.ch management, as regards any use of the Service, or dispute resolution, is final and shall not be open to review or appeal.
			        </p>
			      </div> 
			    </li> 
			    <li> 
			      <p class="question" style="background:none;">4. YOUR REPRESENTATIONS AND WARRANTIES</p> 
			      <div class="answer"> 
			        <p> 
			          	Prior to your use of the Service and on an ongoing basis you represent, warrant, covenant and agree that:
			        </p>
			        <br/>
			        <p> 
			          	4.1. there is a risk of losing bitcoins when using the Service and that Instantri.ch has no responsibility to you for any such loss;
			        </p>
			        <br/>
			        <p> 
			          	4.2. your use of the Service is at your sole option, discretion and risk;
			        </p>
			        <br/>
			        <p> 
			          	4.3. you are solely responsible for any applicable taxes which may be payable on bitcoins awarded to you through your using the Service;
			        </p>
			        <br/>
			        <p> 
			          	4.4. the telecommunications networks and Internet access services required for you to access and use the Service are entirely beyond the control of Instantri.ch and Instantri.ch shall have no liability whatsoever for any outages, slowness, capacity constraints or other deficiencies affecting the same; and
			        </p>
			        <br/>
			        <p> 
			          	4.5. you are aged 18 or over and that you are not currently self-excluded from any gambling site or gambling premises and that you will inform us immediately if you enter into a self-exclusion agreement with any gambling provider.
			        </p>
			      </div> 
			    </li>
			    <li> 
			      <p class="question" style="background:none;">5. PROHIBITED USES</p> 
			      <div class="answer"> 
			        <p> 
			          	5.1. PERSONAL USE. The Service is intended solely for the User's personal use. The User is only allowed to wager for his/her personal entertainment.
			        </p>
			        <br/>
			        <p> 
			          	5.2. NO SHARED WALLET. The User will not transmit Bitcoins to the address indicated on the website from a shared wallet or any other address not solely controlled by the User as any amounts sent back to the initiating address may not be properly credited to the User.
			        </p>
			        <br/>
			        <p> 
			          	5.3 JURISDICTIONS. Persons located in or residents of the United States and the United States Territories (the Prohibited Jurisdiction) are not permitted make use of the Service.  For the avoidance of doubt, the foregoing restrictions on engaging in real-money play from Prohibited Jurisdictions applies equally to residents and citizens of other nations while located in a Prohibited Jurisdiction. Any attempt to circumvent the restrictions on play by any persons located in a Prohibited Jurisdiction or Restricted Jurisdiction, is a breach of this Agreement.  An attempt at circumvention includes, but is not limited to, manipulating the information used by Instantri.ch to identify your location and providing Instantri.ch with false or misleading information regarding your location or place of residence.
			        </p>
			      </div> 
			    </li> 
			    <li> 
			      <p class="question" style="background:none;">6. BREACH</p> 
			      <div class="answer"> 
			        <p> 
			          	6.1. Without prejudice to any other rights, if a User breaches in whole or in part any provision contained herein, Instantri.ch reserves the right to take such action as it sees fit, including terminating this Agreement or any other agreement in place with the User and/or taking legal action against such User.
			        </p>
			        <br/>
			        <p> 
			          	6.2. You agree to fully indemnify, defend and hold harmless Instantri.ch and its shareholders, directors, agents and employees from and against all claims, demands, liabilities, damages, losses, costs and expenses, including legal fees and any other charges whatsoever, howsoever caused, that may arise as a result of: (i) your breach of this Agreement, in whole or in part; (ii) violation by you of any law or any third party rights; and (iii) use by you of the Service.
			        </p>
			        
			      </div> 
			    </li> 
			    <li> 
			      <p class="question" style="background:none;">7. LIMITATION OF LIABILITY.</p> 
			      <div class="answer"> 
			        <p> 
			          	7.1. Under no circumstances, including negligence, shall Instantri.ch be liable for any special, incidental, direct, indirect or consequential damages whatsoever (including, without limitation, damages for loss of business profits, business interruption, loss of business information, or any other pecuniary loss) arising out of the use (or misuse) of the Service even if Instantri.ch had prior knowledge of the possibility of such damages.
			        </p>
			        <br/>
			        <p> 
			          	7.2. Nothing in this Agreement shall exclude or limit Instantri.ch's liability for death or personal injury resulting from its negligence.
			        </p>
			        
			      </div> 
			    </li>
			    <li> 
			      <p class="question" style="background:none;">8. DISPUTES</p> 
			      <div class="answer"> 
			        <p> 
			          	If a User wishes to make a complaint, please contact our customer service team clicking in the contact link. Should any dispute not be resolved to your satisfaction you may pursue remedies in the governing law jurisdiction set forth below.
			        </p>
			      </div> 
			    </li>
			    <li> 
			      <p class="question" style="background:none;">9. AMENDMENT</p> 
			      <div class="answer"> 
			        <p> 
			          	Instantri.ch reserves the right to update or modify this Agreement or any part thereof at any time or otherwise change the Service without notice and you will be bound by such amended Agreement upon posting. Therefore, we encourage you check the terms and conditions contained in the version of the Agreement in force at such time. Your continued use of the Service shall be deemed to attest to your agreement to any amendments to the Agreement.
			        </p>
			      </div> 
			    </li>
			    <li> 
			      <p class="question" style="background:none;">10. GOVERNING LAW</p> 
			      <div class="answer"> 
			        <p> 
			          	The Agreement and any matters relating hereto shall be governed by, and construed in accordance with, the laws of Canada. You irrevocably agree that, subject as provided below, the courts of Canada shall have exclusive jurisdiction in relation to any claim, dispute or difference concerning the Agreement and any matter arising therefrom and irrevocably waive any right that it may have to object to an action being brought in those courts, or to claim that the action has been brought in an inconvenient forum, or that those courts do not have jurisdiction. Nothing in this clause shall limit the right of Instantri.ch to take proceedings against you in any other court of competent jurisdiction, nor shall the taking of proceedings in any one or more jurisdictions preclude the taking of proceedings in any other jurisdictions, whether concurrently or not, to the extent permitted by the law of such other jurisdiction.
			        </p>
			      </div> 
			    </li>
			    <li> 
			      <p class="question" style="background:none;">11. SEVERABILITY</p> 
			      <div class="answer"> 
			        <p> 
			          	If a provision of this Agreement is or becomes illegal, invalid or unenforceable in any jurisdiction, that shall not affect the validity or enforceability in that jurisdiction of any other provision hereof or the validity or enforceability in other jurisdictions of that or any other provision hereof.
			        </p>
			      </div> 
			    </li>
			     <li> 
			      <p class="question" style="background:none;">12. ASSIGNMENT</p> 
			      <div class="answer"> 
			        <p> 
			          	Instantri.ch reserves the right to assign this agreement, in whole or in part, at any time without notice. The User may not assign any of his/her rights or obligations under this Agreement.
			        </p>
			      </div> 
			    </li>
			     <li> 
			      <p class="question" style="background:none;">13. MISCELLANEOUS</p> 
			      <div class="answer"> 
			        <p> 
			          	13.1. No waiver by Instantri.ch of any breach of any provision of this Agreement (including the failure of Instantri.ch to require strict and literal performance of or compliance with any provision of this Agreement) shall in any way be construed as a waiver of any subsequent breach of such provision or of any breach of any other provision of this Agreement.
			        </p>
			        <br/>
			        <p> 
			          	13.2. Nothing in this Agreement shall create or confer any rights or other benefits in favour of any third parties not party to this Agreement other than with an affiliate of Instantri.ch.
			        </p>
			        <br/>
			        <p> 
			          	13.3. Nothing in this Agreement shall create or be deemed to create a partnership, agency, trust arrangement, fiduciary relationship or joint venture between you and us.
			        </p>
			        <br/>
			        <p> 
			          	13.4. This Agreement constitutes the entire understanding and agreement between you and us regarding the Service and supersedes any prior agreement, understanding, or arrangement between you and us.
			        </p>
			        
			      </div> 
			    </li>
			  </ul> 
			</div> 
	    	
	    	
	    </div>
	</tiles:put>
</tiles:insert>


