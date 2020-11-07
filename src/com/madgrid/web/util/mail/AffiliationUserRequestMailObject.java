package com.madgrid.web.util.mail;

public class AffiliationUserRequestMailObject implements MailObject {

	private String 	baseUrl; 
	private String 	login;
	
	public AffiliationUserRequestMailObject( String login, String baseUrl)
	{
		this.baseUrl = baseUrl;
		this.login = login;
	}

	public String toHtml()
	{
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
		result.append( "                <h1 style='font-size:24px;'>Hi "+login+"!</h1>");
		result.append( "<p>We've created an affiliation user for you right now. You can see your profile, your affilitaion activity and your Bitcoin balance in your private affiliation control panel. There are also some banners you can use. The link to your affiliation control panel is:<a href='http://www.instantri.ch/affiliation'>http://www.instantri.ch/affiliation</a> (remove any ad blocker you have installed or it won't work propertly)</p>");
		result.append( "<p><strong>username:</strong>"+login+"</p>");
		result.append( "<p><strong>password:</strong>(the same you have in your Instantri.ch account)</p>");										
		result.append( "<p>You can now give the url <a href='https://www.instantri.ch?affiliate="+login+"'>https://www.instantri.ch?affiliate="+login+"</a> to everyone you want. You can put a banner in your web page, put a link in your social networks, advertise it, etc...</p>");
		result.append( "<p>You'll get 15% of every Bitcoin an user registered with your code spends in Instantri.ch</p>");
		result.append( "<p>Also you'll receive an email alert when an user signs up with your affiliation code and when any of your users spend some Bitcoins</p>");
		result.append( "<p>Complete your profile loggin in your affiliation control panel and start to earn Bitcoins right now.<br/><br/>");
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
		result.append( "We've created an affiliation user for you right now. You can see your profile, your affilitaion activity and your Bitcoin balance in your private affiliation control panel. There are also some banners you can use. The link to your affiliation control panel is:\r\n");
		result.append( "http://www.instantri.ch/affiliation (remove any ad blocker you have installed or it won't work propertly)\r\n");
		result.append( "username:"+login+"\r\n");
		result.append( "password:(the same you have in your Instantri.ch account)\r\n");										
		result.append( "You can now give the url https://www.instantri.ch?affiliate="+login+" to everyone you want. You can put a banner in your web page, put a link in your social networks, advertise it, etc...\r\n");
		result.append( "You'll get 15% of every Bitcoin an user registered with your code spends in Instantri.ch\r\n");
		result.append( "Also you'll receive an email alert when an user signs up with your affiliation code and when any of your users spend some Bitcoins\r\n");
		result.append( "Complete your profile loggin in your affiliation control panel and start to earn Bitcoins right now.\r\n");
		result.append( "Thank you.");		
				
		return result.toString();
	}

	

}
