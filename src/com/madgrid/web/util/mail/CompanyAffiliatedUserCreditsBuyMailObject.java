package com.madgrid.web.util.mail;

import java.text.DecimalFormat;

public class CompanyAffiliatedUserCreditsBuyMailObject implements MailObject {

	private String 	companyLogin;
	private String 	email;
	private String 	affiliatedUser;
	private int 	count;
	private String 	baseUrl;
	private Double totalBitcoins;
	private Double assignedBitcoins;
	
	
	public CompanyAffiliatedUserCreditsBuyMailObject( String companyLogin,  String email,  String affiliatedUser, Double totalBitcoins, Double assignedBitcoins, String baseUrl)
	{
		this.companyLogin = companyLogin;
		this.baseUrl = baseUrl;
		this.email = email;
		this.affiliatedUser = affiliatedUser;
		this.totalBitcoins = totalBitcoins;
		this.assignedBitcoins = assignedBitcoins;
	
		
	}

	public String toHtml()
	{
		
		DecimalFormat numberFormat = new DecimalFormat("#.########");
		StringBuffer result = new StringBuffer( "");
		
		
		
		
		
		
		result.append( "<!DOCTYPE html>");
		result.append( "<html>");
		result.append( "<head>");
		result.append( "<meta charset='utf-8'>");
		result.append( "<meta name='viewport' content='width=device-width, initial-scale=1'>");
		result.append( "<title>Instantri.ch</title>");
		result.append( "<style type='text/css'>");
		result.append( "body {");
		result.append( "background-color: #000;");
		result.append( "background-image: url(" + baseUrl+ "/img/bg.jpg);");
		result.append( "background-position: top;");
		result.append( "background-repeat: repeat-x;;");
		result.append( "font-family: Arial, sans-serif;");
		result.append( "font-size: 16px;");
		result.append( "padding-top: 40px;");
		result.append( "padding-bottom: 20px;");
		result.append( "color: #333;");
		result.append( "text-align: center;");
		result.append( "width: 100%;");
		result.append( "margin: 0;");
		result.append( "width: 100%;");
		result.append( "}");
		result.append( "table{");
		result.append( "max-width: 560px;");
		result.append( "width:100%");
		result.append( "}");
		result.append( "</style>");
		result.append( "</head>");
		result.append( "<body style='background-color:#000;background-image:url(" + baseUrl+ "/img/bg.jpg);background-position:top;background-repeat:repeat-x;font-family:Arial,sans-serif;font-size:16px;padding-top:40px;padding-bottom:20px;color:#333;text-align:center;width:100%;margin:0;width: 100%;'>");
		result.append( "    <table cellpadding='0' cellspacing='0' align='center'  background='" + baseUrl+ "/img/header_bg.jpg' style='background-image:url(" + baseUrl+ "/img/header_bg.jpg);background-repeat:no-repeat;background-position:top right;background-color:#191919'>");
		result.append( "        <tr>");
		result.append( "            <td class='' align='left' valign='top' style='padding:18px 25px;width:100%;'>");
		result.append( "                <img src='" + baseUrl+ "/img/logo.png'>");
		result.append( "            </td>");
		result.append( "        </tr>");
		result.append( "    </table>");
		result.append( "    <table class='' cellpadding='0' cellspacing='0' align='center' style='background-color:#fff;padding:50px;'>");
		result.append( "        <tr>");
		result.append( "            <td class='' align='left' valign='middle' style=''>");
		result.append( "                <h1 style='font-size:24px;'>Hi "+ companyLogin +"!</h1>");
		result.append( "				<p>Congratulations! The user with login " + affiliatedUser+ " wich was an affiliated of yours bought credits for " + numberFormat.format(totalBitcoins) + "BTC so you have earned " + numberFormat.format(assignedBitcoins) + " BTC. You can log in you affiliation program page to see it by yourself or to transfer your earnings to your personal Bitcoin wallet.</p>");
		result.append( "                <p align='right' style='margin-top:40px;'>InstantRi.ch team</p>");
		result.append( "            </td>");
		result.append( "        </tr>");
		result.append( "    </table>");
		result.append( "    <table class='footer' cellpadding='0' cellspacing='0' align='center' style='background-color:#454545;'>");
		result.append( "        <tr>");
		result.append( "            <td class='' align='center' valign='middle' style='width:100%;color:#999;'>");
		result.append( "                <p>Follow us on <a href='http://twitter.com/instantri_ch' style='font-weight:bold;color:#fff;text-decoration:none;'>Twitter</a> and <a href='http://www.facebook.com/instantri.ch.btc' style='font-weight:bold;color:#fff;text-decoration:none;'>Facebook</a></p>");
		result.append( "            </td>");
		result.append( "        </tr>");
		result.append( "    </table>");
		result.append( "    <table cellpadding='0' cellspacing='0' align='center' style='padding:25px;'>");
		result.append( "        <tr style=''>");
		result.append( "            <td class='' align='center' style='font-size:12px; color:#999;'>");
		result.append( "            </td>");
		result.append( "        </tr>");
		result.append( "    </table>");
		result.append( "</body>");
		result.append( "</html>");
		
		
		
		return result.toString();
	}
	
	public String toText()
	{
		StringBuffer result = new StringBuffer( "");
		
		result.append( "Congratulations " + companyLogin + ". The user with login " + affiliatedUser+ " wich was an affiliated of yours bought credits for " + totalBitcoins + "BTC so you have earned " + assignedBitcoins + " BTC\r\n");
		result.append( "You can log in you affiliation program page to see it by yourself or to transfer your earnings to your personal Bitcoin wallet.\r\n");
				
		return result.toString();
	}

	public String getCompanyLogin() {
		return companyLogin;
	}

	public void setCompanyLogin(String companyLogin) {
		this.companyLogin = companyLogin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAffiliatedUser() {
		return affiliatedUser;
	}

	public void setAffiliatedUser(String affiliatedUser) {
		this.affiliatedUser = affiliatedUser;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Double getTotalBitcoins() {
		return totalBitcoins;
	}

	public void setTotalBitcoins(Double totalBitcoins) {
		this.totalBitcoins = totalBitcoins;
	}

	public Double getAssignedBitcoins() {
		return assignedBitcoins;
	}

	public void setAssignedBitcoins(Double assignedBitcoins) {
		this.assignedBitcoins = assignedBitcoins;
	}

	

}
