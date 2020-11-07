package com.madgrid.web.action;


import java.io.DataInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.TweetDAO;
import com.madgrid.dao.WinDAO;
import com.madgrid.model.Grid;
import com.madgrid.model.Item;
import com.madgrid.model.Tweet;
import com.madgrid.model.Win;
import com.madgrid.web.util.Mail;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.mail.MessageSentForAdminMailObject;
import com.madgrid.web.util.mail.TweetForAdminMailObject;

public class TweetAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(TweetAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		WinDAO winDAO = new WinDAO();
		TweetDAO tweetDAO = new TweetDAO();
		
		String id = request.getParameter("id");
		
		if( !Utils.nullOrBlank( id)){
			Win win = null;
			try{
				win = winDAO.getWinById(Integer.parseInt(id));
			} catch (Exception e){
				win = null;
			}
			
			if( win != null && (win.getItem().getType() == Item.ITEM_TYPE_ONLY_BITCOINS || win.getItem().getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS)){
				
				if( win.getGridType() == Grid.GRID_TYPE_FREE || win.getItem().getBitcoins()>=0.01){
				
					Criteria criteria = new Criteria();
					criteria.addEqualTo("win", win);
					Tweet tweetTest = tweetDAO.getTweetByCriteria(criteria);
					
					if( tweetTest != null && tweetTest.getPaid()){
						return mapping.findForward( "ko");
					}
					
					
					
					
					DecimalFormat numberFormat = new DecimalFormat("#.###");
					
					List<String> tweetList = new ArrayList<String>();
					tweetList.add("I won " + numberFormat.format(win.getItem().getBitcoins()) + " #Bitcoin on @instantri_ch. See it for yourself searching my username " + Utils.briefString(win.getUser().getLogin(), 30) + " at https://www.instantri.ch/winners");
					tweetList.add("I've just won " + numberFormat.format(win.getItem().getBitcoins()) + " BTC on https://www.instantri.ch . My username is " + Utils.briefString(win.getUser().getLogin(), 40));
					tweetList.add("Enter https://www.instantri.ch/winners and look for my username " + Utils.briefString(win.getUser().getLogin(), 30) + ". I've just won " + numberFormat.format(win.getItem().getBitcoins()) + " #Bitcoin. Thanks @instantri_ch");
					tweetList.add("It's really easy to win in @instantri_ch. I've just won " + numberFormat.format(win.getItem().getBitcoins()) + " #Bitcoin. ");
					tweetList.add("@instantri_ch thanks for the "+ numberFormat.format(win.getItem().getBitcoins()) + " #Bitcoin I won.");
					
					tweetList.add("Cool! I'won the "+ numberFormat.format(win.getItem().getBitcoins()) + " BTC prize in www.instantri.ch");
					
					if( win.getGridType().intValue() == Grid.GRID_TYPE_FREE){
						tweetList.add("Cool! I'won the "+ numberFormat.format(win.getItem().getBitcoins()) + " BTC prize in www.instantri.ch. And for free!!!");
						tweetList.add("I was playing a free game in www.instantri.ch and I won "+ numberFormat.format(win.getItem().getBitcoins()) + " Bitcoins. Cool!");
						tweetList.add("Instantri.ch's free games are awesome. I've just won "+ numberFormat.format(win.getItem().getBitcoins()) + " Bitcoins.");
					}
					
					Random generator = new Random( new Date().getTime());
					
					int pos = generator.nextInt( tweetList.size());
					
					
	
					Tweet tweet = new Tweet();
					tweet.setCreated( Utils.today());
					tweet.setUser( win.getUser());
					tweet.setWin(win);
					tweet.setUserId( win.getUserId());
					tweet.setPaid(false);
					tweet.setWinId(win.getId());
					tweet.setText(tweetList.get(pos));
					
					tweetDAO.setTweet(tweet);
					
					
					
					String urlString = "https://twitter.com/intent/tweet?&text="+ URLEncoder.encode(tweetList.get(pos), "utf-8");
					
					/*
					TweetForAdminMailObject tweetForAdminMailObject = new TweetForAdminMailObject( win.getUser(),win, tweetList.get(pos), Utils.getBaseUrl());
					Mail mail = new Mail(  "amandris@hotmail.com", "Gus, un usuario ha twiteado", tweetForAdminMailObject);
					mail.start();
					*/
					
					request.setAttribute("url", urlString);
			 	    	
					return mapping.findForward( "ok");
				} 

			}
		}
		
		
		
		return mapping.findForward( "ko");
	}
}		
