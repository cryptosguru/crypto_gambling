package com.madgrid.web.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.madgrid.dao.AffiliationActivityDAO;
import com.madgrid.dao.AffiliationUserDAO;
import com.madgrid.dao.BitcoinPaymentDAO;
import com.madgrid.dao.BlockcypherDAO;
import com.madgrid.dao.PromoCodeDAO;
import com.madgrid.dao.PromotionDAO;
import com.madgrid.dao.UserDAO;
import com.madgrid.dao.UserHistoricDAO;
import com.madgrid.model.AffiliationActivity;
import com.madgrid.model.BitcoinPayment;
import com.madgrid.model.Blockcypher;
import com.madgrid.model.PromoCode;
import com.madgrid.model.Promotion;
import com.madgrid.model.User;
import com.madgrid.model.UserHistoric;
import com.madgrid.web.util.JSONBlockCypher;
import com.madgrid.web.util.JSONOrder;
import com.madgrid.web.util.Mail;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.dwr.DwrTools;
import com.madgrid.web.util.mail.CompanyAffiliatedUserCreditsBuyMailObject;
import com.madgrid.web.util.mail.CompanyAffiliatedUserMailObject;
import com.madgrid.web.util.mail.UserBuyCreditsMailObject;

public class CreditsBitcoinConfirmAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(CreditsBitcoinConfirmAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		String code = request.getParameter( "code");
		String hash = request.getParameter( "hash");
		String login = request.getParameter( "login");
		
		System.out.println( "la Ip de coinbase es " + request.getLocalAddr());
		logger.info( "CreditsBitcoinConfirmAction called from ip " + request.getLocalAddr() + " and code " + code);
		if( code.equals("akjsghdkjdfbkbkasimsdngljkhaslfkjsldj")){
			StringBuffer jb = new StringBuffer();
			  String line = null;
			  try {
			    BufferedReader reader = request.getReader();
			    while ((line = reader.readLine()) != null)
			      jb.append(line);
			  } catch (Exception ex1) { ex1.printStackTrace();}
	
			  try {
			    System.out.println("----------------" + jb.toString());
			    logger.info( "Data from CoinBase: " + jb.toString());
			    
			    
			    final String json = jb.toString();
			    final Gson gson = new Gson();
			    final JSONOrder jsonOrder= gson.fromJson(json, JSONOrder.class);
			    
			    PromotionDAO promotionDAO = new PromotionDAO();
				PromoCodeDAO promoCodeDAO = new PromoCodeDAO();
				UserDAO userDAO = new UserDAO();
				Date today = Utils.today();
				List<Promotion> promoList = new ArrayList<Promotion>();
				
				Double bitcoins = 0d;
				
				
				String bitcoinsString = jsonOrder.getValue();
				bitcoins = Double.parseDouble( bitcoinsString) * 0.00000001d;
				
				if (bitcoins < 0.0009d){
					return null;
				}
				
				Double creditsDouble = bitcoins * 1000d; //1 bitcoin = 1000 creditos
				Integer credits = new Double(Math.ceil(creditsDouble)).intValue();
				boolean alreadyHasPromotion = false;

				Criteria criteria = new Criteria();
				criteria = new Criteria();
				List<Promotion> promotionList = promotionDAO.getPromotionListByCriteria(criteria, null);
				
				for( Promotion promotion:promotionList){
					if( today.after( promotion.getStartDate()) && today.before( promotion.getEndDate())){
						if( promotion.getType() == Promotion.PROMOTION_TYPE_2x1){
							credits = credits * 2;
							alreadyHasPromotion= true;
						}
						
						if( promotion.getType() == Promotion.PROMOTION_TYPE_30_PERCENT_IN_BUY){
							credits = credits + (int)((double)credits * 0.30d);
							alreadyHasPromotion= true;
						}
						
						if( promotion.getType() == Promotion.PROMOTION_TYPE_20_PERCENT_IN_BUY){
							credits = credits + (int)((double)credits * 0.20d);
							alreadyHasPromotion= true;
						}
						
						if( promotion.getType() == Promotion.PROMOTION_TYPE_10_PERCENT_IN_BUY){
							credits = credits + (int)((double)credits * 0.10d);
							alreadyHasPromotion= true;
						}
						
						if( promotion.getType() == Promotion.PROMOTION_TYPE_10_IN_BUY){
							credits = credits + 10;
							alreadyHasPromotion= true;
						}
						
						if( promotion.getType() == Promotion.PROMOTION_TYPE_5_IN_BUY){
							credits = credits + 5;
							alreadyHasPromotion= true;
						}
						
						if( promotion.getType() == Promotion.PROMOTION_TYPE_1_IN_BUY){
							credits = credits + 1;
							alreadyHasPromotion= true;
						}
					}
				}
				
	
				System.out.println("Login = " + login + "  loginHash=" + hash); 
				logger.info( "El login es " + login + " y loginHash es " + hash);
				
				if( !Utils.nullOrBlank(login) && !Utils.nullOrBlank(hash)){
				
					Criteria userCriteria = new Criteria();
					userCriteria.addEqualTo("login", login);
					User user = userDAO.getUserByCriteria(userCriteria);
				
					if( user != null){
						if( Utils.digest("ikhrt6j" + user.getEmail() + user.getLogin()).equals(hash)){
							
							//Comprobamos si el usuario habia metido un codigo promocional
							if( alreadyHasPromotion == false && user.getBuyCreditsPromoCode() != null && !user.getBuyCreditsPromoCode().equals("")){
								criteria.addEqualTo("id", user.getBuyCreditsPromoCode());
								PromoCode promoCode = promoCodeDAO.getPromoCodeByCriteria(criteria);
								
								if( promoCode != null && promoCode.getCount()>= 1 && (promoCode.getType() >=5 && promoCode.getType() <= 9)){
									String text = "";
									
									switch( promoCode.getType()){
										case 5: credits = credits * 2;break;
										case 6: credits = credits + (int)((double)credits * 0.50d);break;
										case 7: credits = credits + (int)((double)credits * 0.20d);break;
										case 8: credits = credits + (int)((double)credits * 0.10d);break;
										case 9: credits = credits + 10;break;
									}
									
									promoCode.setCount( promoCode.getCount() - 1);
									promoCodeDAO.setPromoCode(promoCode);
									user.setBuyCreditsPromoCode( null);
									user.setBuyCreditsPromoCodeText( null);
								}
							}
							
							BitcoinPaymentDAO bitcoinPaymentDAO = new BitcoinPaymentDAO();
							
							//Comprobamos que no haya un bitcoinPayment con el mismo input_hash
							Criteria transactionCriteria = new Criteria();
							transactionCriteria.addEqualTo("transactionId",  jsonOrder.getInput_transaction_hash());
							BitcoinPayment bp = bitcoinPaymentDAO.getBitcoinPaymentByCriteria(transactionCriteria);
							if( bp == null){
		
								BitcoinPayment bitcoinPayment = new BitcoinPayment();
								
								bitcoinPayment.setTransactionId( jsonOrder.getInput_transaction_hash());
								Locale locale = new Locale( "en", "GB");
								
								String date = new SimpleDateFormat ( "yyyy-MM-dd", locale).format( today);
								String time = new SimpleDateFormat ( "HH:mm:ss", locale).format( today);
								
								String dateFormatted = date+"T"+time+"-07:00";
								
								bitcoinPayment.setCreated(dateFormatted);
								
								bitcoinPayment.setBitcoins(Double.parseDouble(jsonOrder.getValue()));
								
								bitcoinPayment.setReceiveAddress(jsonOrder.getInput_address());
								bitcoinPayment.setCredits(Integer.toString(credits)); 
								bitcoinPayment.setPayerId(login);
								bitcoinPayment.setUserId(user.getId());
								bitcoinPayment.setLoginHash(hash);
								bitcoinPayment.setIp(request.getLocalAddr());
								
								bitcoinPaymentDAO.setBitcoinPayment(bitcoinPayment);
								
								user.setCredits( user.getCredits() + credits);
								
								if( user.getValidated() == false){
									user.setValidated( true);
									user.setValidationCode( null);
								}
								
								user.setIsFraudulent( false);
								
								userDAO.setUser(user);
	
								UserHistoricDAO userHistoricDAO = new UserHistoricDAO();
							    UserHistoric userHistoricWin = new UserHistoric();
							    userHistoricWin.setCreated( Utils.today());
							    userHistoricWin.setUser(user);
							    userHistoricWin.setUserId(user.getId());
							    userHistoricWin.setType( UserHistoric.BUY_CREDITS);
							    userHistoricWin.setValue1(new Double(credits));
							    userHistoricDAO.setUserHistoric(userHistoricWin);
							    
							    UserBuyCreditsMailObject userBuyCreditsMailObject = new UserBuyCreditsMailObject( user.getLogin(), credits, Utils.getBaseUrl());
								Mail mail = new Mail( user.getEmail(), "Your credits have been added to your Instantri.ch account", userBuyCreditsMailObject);
								mail.start();
								
								logger.warn( "Se han añadido " + credits + " creditos al usuario " + user.getLogin() + "(" + user.getId()+")");
								
								DwrTools dwrTools = DwrTools.getInstance();
								try{
									synchronized (dwrTools) {
										dwrTools.manageBuyCredits(user, credits);
									}
								} catch (Exception e){
									e.printStackTrace();
								}
								
								
								//Affiliation program
								if( user.getCompanyAffiliationUser() != null){
									AffiliationUserDAO affiliationUserDAO = new AffiliationUserDAO();
									AffiliationActivityDAO affiliationActivityDAO = new AffiliationActivityDAO();
									
									AffiliationActivity affiliationActivity = new AffiliationActivity();
									affiliationActivity.setAffiliationUser(user.getCompanyAffiliationUser());
									affiliationActivity.setAffiliationUserId(user.getCompanyAffiliationUser().getId());
									affiliationActivity.setAssignedBitcoins(bitcoins * (user.getCompanyAffiliationUser().getPercentage() /100d ));
									affiliationActivity.setCreated(Utils.today());
									affiliationActivity.setTotalBitcoins(bitcoins);
									affiliationActivity.setType( AffiliationActivity.AFFILIATION_ACTIVITY_USER_CREDITS);
									affiliationActivity.setUser(user);
									affiliationActivity.setUserId(user.getId());
									affiliationActivity.setAlreadyPayed(false);
									
									affiliationActivityDAO.setAffiliationActivity(affiliationActivity);
									
									
									CompanyAffiliatedUserCreditsBuyMailObject companyAffiliatedUserCreditsBuyMailObject = new CompanyAffiliatedUserCreditsBuyMailObject(  user.getCompanyAffiliationUser().getLogin(),  user.getCompanyAffiliationUser().getEmail(), user.getLogin(), affiliationActivity.getTotalBitcoins(), affiliationActivity.getAssignedBitcoins(),Utils.getBaseUrl());
									
									DecimalFormat numberFormat = new DecimalFormat("#.########");
									
									Mail mail2 = new Mail( user.getCompanyAffiliationUser().getEmail(), "Congratulations, you've earned " + numberFormat.format(affiliationActivity.getAssignedBitcoins()) + " Bitcoins on Instantri.ch's affiliation program", companyAffiliatedUserCreditsBuyMailObject);
									mail2.start();
								}
								
								//Borramos cualquier elemento Blockcypher
								BlockcypherDAO blockcypherDAO = new BlockcypherDAO();
								criteria = new Criteria();
								criteria.addEqualTo("address", jsonOrder.getInput_address());
								criteria.addEqualTo("userId", user.getId());
								Blockcypher blockcypher = blockcypherDAO.getBlockcypherByCriteria(criteria);
								if( blockcypher != null){
									blockcypher.setReceived( true);
									blockcypherDAO.setBlockcypher(blockcypher);
									deleteBlockCypherEndpoint(blockcypher.getBlockcypherId());
								}
							}
							
						} else{
							logger.warn( "No concuerda el loginHash para el usuario " + login);
						}
							
					}
				}
				    
				    
			  } catch (Exception ex2) {
				  ex2.printStackTrace();
			  }
			
			
			
		} else{
			logger.warn( "CreditsBitcoinConfirmAction called from ip " + request.getLocalAddr() + " and code " + code + " which is incorrect");
		}
		
		return null;
	
	}
	
	
	
	private void deleteBlockCypherEndpoint(String id) throws Exception {
		 
		String url = "https://api.blockcypher.com/v1/btc/main/payments/"+id;
		HttpClient client = new DefaultHttpClient();
		HttpDelete delete = new HttpDelete(url);
 
		delete.addHeader("content-type", "application/x-www-form-urlencoded");
		HttpResponse response = client.execute(delete);
 
				
	}
	
	
}		
