<%@page import="com.madgrid.web.util.TopWinnersBean"%>
<%@page import="com.madgrid.model.BitcoinPayment"%>
<%@page import="com.madgrid.dao.BitcoinPaymentDAO"%>
<%@page import="com.madgrid.web.util.Utils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.madgrid.model.User"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.madgrid.model.Item"%>
<%@page import="com.madgrid.model.Win"%>
<%@page import="java.util.List"%>
<%@page import="com.madgrid.dao.WinDAO"%>
<%@page import="org.apache.ojb.broker.query.Criteria"%>
<%@page import="com.madgrid.dao.UserDAO"%>
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
			$('ul#faqList').simpleFAQ();
			
		});
		
			
	</tiles:put>
	<tiles:put name="onload" type="string">
	
	</tiles:put>
	<tiles:put name="body" type="string">
		<%
			UserDAO userDAO = new UserDAO();
			int userCount = userDAO.getUserCountByCriteria(new Criteria());
			int userWhoWonAndPurchasedCredits = 0; 
			
			WinDAO winDAO = new WinDAO();
			List<Win> winList = winDAO.getWinListByCriteria(new Criteria(), null);
			
			Map<Integer, TopWinnersBean> userMap= new HashMap<Integer, TopWinnersBean>();
			
			double bitcoinPrizes = 0;
			int creditPrizes = 0;
			
			for(Win win:winList){
				
				bitcoinPrizes = bitcoinPrizes + win.getItem().getBitcoins();
				
					if( userMap.get(win.getUser().getId()) == null){
						TopWinnersBean topWinnersBean = new TopWinnersBean();
						topWinnersBean.setUser(win.getUser());
						if( win.getIsOnlyCredits() == false){
							topWinnersBean.setBitcoins(win.getItem().getBitcoins());
						}
						topWinnersBean.setCredits(win.getItem().getCredits());

						userMap.put( win.getUser().getId(), topWinnersBean);
					} else{
						TopWinnersBean topWinnersBean = userMap.get(win.getUser().getId());
						if( win.getIsOnlyCredits() == false){
							topWinnersBean.setBitcoins(userMap.get(win.getUser().getId()).getBitcoins() + win.getItem().getBitcoins().doubleValue());
						}
						topWinnersBean.setCredits(userMap.get(win.getUser().getId()).getCredits() + win.getItem().getCredits());
						userMap.put( win.getUser().getId(), topWinnersBean);
					}
				
				
				
				if( win.getItem().getType() == Item.ITEM_TYPE_ONLY_CREDITS || win.getItem().getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
					creditPrizes = creditPrizes + win.getItem().getCredits();
				}
				
			}
			
			List<TopWinnersBean> userWithPrizes = new ArrayList<TopWinnersBean>(userMap.values());
			
			Collections.sort( userWithPrizes, new Comparator<TopWinnersBean>() {
				public int compare( TopWinnersBean p1, TopWinnersBean p2) {
					return ((Double)p2.getBitcoins()).compareTo((Double)p1.getBitcoins());
				}
			});
			
			
			BitcoinPaymentDAO bitcoinPaymentDAO = new BitcoinPaymentDAO();
			Set<String> purchaseCreditsUserList = new HashSet<String>();
			List<BitcoinPayment> bitcoinPaymentList = bitcoinPaymentDAO.getBitcoinPaymentListByCriteria(new Criteria(), null);
			for( BitcoinPayment bitcoinPayment: bitcoinPaymentList){
				purchaseCreditsUserList.add( bitcoinPayment.getUserId().toString());
			}
			
			for( String id: purchaseCreditsUserList){
				User userWhoPurchasedCredits = userDAO.getUserById( Integer.parseInt(id));
				if( userMap.get( userWhoPurchasedCredits.getId()) != null){
					userWhoWonAndPurchasedCredits ++;
				}
			}
		%>
		
		<% DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(3);
			df.setMinimumFractionDigits(3);
			
			DecimalFormat df2 = new DecimalFormat();
			df2.setMaximumFractionDigits(2);
			df2.setMinimumFractionDigits(2);
		%>
	
		<div id="pagina">
	    	<ul class="rastro">
	        	<li><a href="/" class="home">Home</a></li>
	        	<li><a href="/statistics">Site statistics</a></li>
	        </ul>
	        <h2 class="tituloProducto">Site statistics</h2>
	        
			<br class="clear" />
			
			<table>
				<tr>
					<td style="vertical-align: top;padding-top: 18px;">
						<br class="clear" />
						<table class="statisticsTable" style="margin-left:80px;">
                               <thead class="statisticsTableThead">
                                   <tr>
                                    <th style="border-top-left-radius: 10px;padding:10px;">
                                        Ranking
                                    </th>
                                    <th style="padding:10px;">
                                        User
                                    </th>
                                    <th style="padding:10px;">
                                        Bitcoins<br/>won
                                    </th>
                                    <th style="padding:10px;">
                                        Credits<br/>won
                                    </th>
                                    <th style="padding:10px;border-top-right-radius: 10px;">
                                        Games<br/>won
                                    </th>
                               	</tr>
                               </thead>

                               <tbody>
                               	<%
							int i = 0;
							for( TopWinnersBean topWinnersBean:userWithPrizes){
								i++;
								if( i<=30){
									if( i % 2 == 0){%>
										<tr style="background-color: #fcefa1;">
									<%} else{ %>
										<tr style="background-color: #ebde90;">
									<%} %>
										<%if( i == 30) {%>
											<td style="padding: 10px;border-bottom-left-radius: 10px;">
                                        <%} else{ %>
                                        	<%if( i <= 3) {%>
												<td style="padding: 10px; font-weight: bold;font-size:14px;">
											<%} else{ %>
												<td style="padding: 10px;">
											<%} %>
										<%} %>
										#<%=i%>
										</td>
										<%if( i <= 3) {%>
												<td style="padding: 10px; font-weight: bold;font-size:14px;">
											<%} else{ %>
												<td style="padding: 10px;">
											<%} %>
										<%=Utils.briefString(topWinnersBean.getUser().getLogin(), 15) %>
										</td>
										<%if( i <= 3) {%>
												<td style="padding: 10px; font-weight: bold;font-size:14px;">
											<%} else{ %>
												<td style="padding: 10px;">
											<%} %>
											<%=df.format(topWinnersBean.getBitcoins()) %>
										</td>
										<%if( i <= 3) {%>
												<td style="padding: 10px; font-weight: bold;font-size:14px;">
											<%} else{ %>
												<td style="padding: 10px;">
											<%} %>
											<%=topWinnersBean.getCredits() %>
										</td>
										<%if( i == 30) {%>
											<td style="padding: 10px;border-bottom-right-radius: 10px;">
                                        <%} else{ %>
											<%if( i <= 3) {%>
												<td style="padding: 10px; font-weight: bold;font-size:14px;">
											<%} else{ %>
												<td style="padding: 10px;">
											<%} %>
										<%} %>
											<%if( topWinnersBean.getUser().getStatisticsWonGames().intValue() != 0){%>
												<%=topWinnersBean.getUser().getStatisticsWonGames() %>
												<%} else{%>
													1
												<%} %>
										</td>
									</tr>
									<%}} %>
							</tbody>
						</table>
					</td>
					<td style="vertical-align: top;">
			            <div class="statistics">
				            <ul style="background-color: e0e0e0;">
								<li style="background-color:#DDD;border-top-left-radius: 10px;border-top-right-radius: 10px;width:390px;" >
									<table>
										<tr>
											<td class="info" style="width:200px;">
												Registered users				
											</td>
											<td>
												<%= userCount %> users
											</td>
										</tr>
									</table>
								</li>
								<li style="background-color:#CCC;width:390px;">
									<table>
										<tr>
											<td class="info" style="width:200px;">
												Games played		
											</td>
											<td>
												<%= winList.size() %> games
											</td>
										</tr>
									</table>
								</li>
								<li style="background-color:#DDD;width:390px;">
									<table>
										<tr>
											<td class="info" style="width:200px;">
												Bitcoin prizes delivered			
											</td>
											<td>
												
												<%=df2.format(bitcoinPrizes) %> BTC
											</td>
										</tr>
									</table>
								</li>
								<li style="background-color:#CCC;width:390px;">
									<table>
										<tr>
											<td class="info" style="width:200px;">
												Credit prizes delivered		
											</td>
											<td>
												<%=creditPrizes %> credits
											</td>
										</tr>
									</table>
								</li>
								<li style="background-color:#DDD;width:390px;">
									<table>
										<tr>
											<td class="info" style="width:200px;">
												Users who won a Bitcoin prize
											</td>
											<td>
												<%
												float percentage = ((userMap.size() / (float)userCount) * (float)100);
												DecimalFormat numberFormat = new DecimalFormat("#.##");
												%>
												<%=userMap.size() %> (<%=numberFormat.format(percentage) %> % of all users)
											</td>
										</tr>
									</table>
								</li>
								<li style="background-color:#CCC;width:390px;">
									<table>
										<tr>
											<td class="info" style="width:200px;">
												Times someone purchased credits
											</td>
											<td>
												<%=bitcoinPaymentList.size() %>
											</td>
										</tr>
									</table>
								</li>
								 
								<li style="background-color:#DDD;border-bottom-left-radius: 10px;border-bottom-right-radius: 10px;width:390px;">
									<table>
										<tr>
											<td class="info" style="width:200px;">
												Users who bought credits<br/>
												and won a Bitcoin prize
											</td>
											<td>
												<%=userWhoWonAndPurchasedCredits %><br/>
												(<%=numberFormat.format(((float)userWhoWonAndPurchasedCredits/(float)purchaseCreditsUserList.size()) * 100f) %> % of all users who bought credits)
											</td>
										</tr>
									</table>
								</li>
								
								
							</ul>
						</div>
					</td>
					
				</tr>
			</table>
            
            
	    </div>
	    	
	    	
	</tiles:put>
</tiles:insert>


