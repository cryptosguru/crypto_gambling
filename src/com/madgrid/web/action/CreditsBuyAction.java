package com.madgrid.web.action;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.madgrid.dao.BlockcypherDAO;
import com.madgrid.dao.PromotionDAO;
import com.madgrid.model.Blockcypher;
import com.madgrid.model.Promotion;
import com.madgrid.web.util.JSONBlockCypher;
import com.madgrid.web.util.JSONOrder;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

 

public class CreditsBuyAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(CreditsBuyAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserSession userSession = super.checkUserSession(request);
		PromotionDAO promotionDAO = new PromotionDAO();
		Date today = Utils.today(); 
		List<Promotion> promoList = new ArrayList<Promotion>();
		logger.info( "CreditsBuyAction called");
		
		if( userSession == null){
			logger.warn( "CreditsBuyAction without userSession");
			return null;
		}
		Criteria criteria = new Criteria();
		List<Promotion> promotionList = promotionDAO.getPromotionListByCriteria(criteria, null);
		
		for( Promotion promotion:promotionList){
			if( today.after( promotion.getStartDate()) && today.before( promotion.getEndDate())){
				if( promotion.getType() == Promotion.PROMOTION_TYPE_2x1 || promotion.getType() == Promotion.PROMOTION_TYPE_10_PERCENT_IN_BUY || promotion.getType() == Promotion.PROMOTION_TYPE_20_PERCENT_IN_BUY || promotion.getType() == Promotion.PROMOTION_TYPE_30_PERCENT_IN_BUY){
					promoList.add( promotion);
					
				}
				if( promotion.getType() == Promotion.PROMOTION_TYPE_10_IN_BUY || promotion.getType() == Promotion.PROMOTION_TYPE_5_IN_BUY || promotion.getType() == Promotion.PROMOTION_TYPE_1_IN_BUY){
					promoList.add( promotion);
				}
			}
		}
		
		String loginHash = Utils.digest( "ikhrt6j" + userSession.getUser().getEmail() + userSession.getUser().getLogin());
		
		//Comprobamos si ya hay un elemento blockcypher no caducado en la base de datos
		BlockcypherDAO blockcypherDAO = new BlockcypherDAO();
		criteria = new Criteria();
		criteria.addEqualTo("userId", userSession.getUser().getId());
		criteria.addGreaterThan("expiration", today);
		criteria.addEqualTo("received", false);
		Blockcypher blockcypher = blockcypherDAO.getBlockcypherByCriteria(criteria);
		
		if( blockcypher != null){
			request.setAttribute( "address", blockcypher.getAddress());
		} else{
			//Si no existe blockcypher borramos cualquiera caducado que pudiera haber y creamos uno nuevo 
			criteria = new Criteria();
			criteria.addEqualTo("userId", userSession.getUser().getId());
			List<Blockcypher> blockcypherList = blockcypherDAO.getBlockcypherListByCriteria(criteria, null);
			for( Blockcypher bc:blockcypherList){
				blockcypherDAO.deleteBlockcypher(bc);
			}
			
			String callback_url = "https://www.instantri.ch/do/credits/bitcoinConfirm?code=akjsghdkjdfbkbkasimsdngljkhaslfkjsldj&login="+userSession.getUser().getLogin()+"&hash="+loginHash;
			JSONBlockCypher jsonBlockCypher = callBlockCypher(callback_url);
			
			blockcypher = new Blockcypher();
			blockcypher.setCreated( Utils.today());
			blockcypher.setExpiration(new Date(today.getTime() + 2592000000l)); //30 días 
			blockcypher.setUser( userSession.getUser());
			blockcypher.setUserId( userSession.getUser().getId());
			blockcypher.setReceived( false);
			blockcypher.setAddress( jsonBlockCypher.getInput_address());
			blockcypher.setBlockcypherId(jsonBlockCypher.getId());
			
			blockcypherDAO.setBlockcypher(blockcypher);
			
			request.setAttribute( "address", jsonBlockCypher.getInput_address());
		}
		
		
		request.setAttribute( "promotionList", promoList);
		
		
		return mapping.findForward( "ok");
	}
	
	
	private JSONBlockCypher callBlockCypher(String callback_url) throws Exception {
		 
		String url = "https://api.blockcypher.com/v1/btc/main/payments";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
 
		StringEntity params =new StringEntity("{\"destination\":\"35JBxSyefxmVj34obKC2od3r98MuaJ34am\",\"callback_url\":\""+callback_url+"\",\"token\":\"c6bb2e9a5119424cbc9422fec9df7d19\"}");
 
		post.addHeader("content-type", "application/x-www-form-urlencoded");
		post.setEntity(params);
		HttpResponse response = client.execute(post);
 
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		final String json = result.toString();
		final Gson gson = new Gson();
		final JSONBlockCypher jsonBlockCypher= gson.fromJson(json, JSONBlockCypher.class);
		 
		System.out.println("------jsonBlockCypher " + jsonBlockCypher.getDestination() + "   " +jsonBlockCypher.getInput_address() + "    " + jsonBlockCypher.getCallback_url());
		
		return jsonBlockCypher;
	}

	
	
}		
