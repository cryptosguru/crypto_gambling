package com.madgrid.web.util.mail;

import java.util.List;

import com.madgrid.model.User;
import com.madgrid.model.Win;
import com.madgrid.web.util.Utils;

public class TweetForAdminMailObject implements MailObject {

	private User 	user;
	private Win 	win;
	private String 	tweet;
	private String 	baseUrl;
	
	
	
	public TweetForAdminMailObject( User user,  Win win, String tweet, String baseUrl)
	{
		this.user = user;
		this.baseUrl = baseUrl;
		this.win = win;
		this.tweet = tweet;
	}

	public String toHtml()
	{
		StringBuffer result = new StringBuffer( "");
		
		result.append( "<html>");
		result.append( "<head>");
		result.append( "</head>");
		result.append( "<body>");
		result.append( "<table width='100%' cellpadding='0' cellspacing='0' border='0'>");
		result.append( "<tr>");
		result.append( "<td align='center'>");
		result.append( "<a href='" + baseUrl + "'><img src='" + baseUrl+ "/img/logo_instantrich_small.jpg' border='0'/></a>");
		result.append( "</td>");
		result.append( "</tr>");
		result.append( "<tr>");
		result.append( "<td>");
		result.append( "<table width='100%' cellpadding='0' cellspacing='15' border='0'>");
		
		result.append( "<tr>");
		result.append( "<td align='center' style='font-family:arial; font-size:20;'>");
		result.append( "Hola Gus. Un usuario ha twiteado.");
		result.append( "</td>"); 
		result.append( "</tr>");
		
		result.append( "<tr>");
		result.append( "<td align='center' style='font-family:arial; font-size:20;'>");
		result.append( "Login: " + user.getLogin());
		result.append( "</td>"); 
		result.append( "</tr>");
		result.append( "<tr>");
		result.append( "<td align='center' style='font-family:arial; font-size:20;'>");
		result.append( "Bitcoins: " + win.getItem().getBitcoins() + " ("+ win.getId() + ")");
		result.append( "</td>"); 
		result.append( "</tr>");
		result.append( "<tr>");
		result.append( "<td align='center' style='font-family:arial; font-size:20;'>");
		result.append( "Tweet: " + tweet);
		result.append( "</td>"); 
		result.append( "</tr>");
		
		result.append( "</table>");
		result.append( "</td>");
		result.append( "</tr>");
		result.append( "</table>"); 
		result.append( "</body>");
		result.append( "</html>");
		
		return result.toString();
	}
	
	public String toText()
	{
		StringBuffer result = new StringBuffer( "");
		
		
				
		return result.toString();
	}


	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Win getWin() {
		return win;
	}

	public void setWin(Win win) {
		this.win = win;
	}

	
	
}
