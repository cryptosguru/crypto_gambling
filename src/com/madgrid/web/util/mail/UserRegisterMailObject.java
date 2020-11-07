package com.madgrid.web.util.mail;

import java.util.List;

import com.madgrid.model.Grid;

public class UserRegisterMailObject implements MailObject {

	private String 	login;
	private String 	email;
	private String 	code;
	private String 	baseUrl;
	private Integer promotionCredits;
	private List<Grid> gridList;
	
	
	public UserRegisterMailObject( String login,  String email,  String code, List<Grid> gridList, Integer promotionCredits, String baseUrl)
	{
		this.login = login;
		this.baseUrl = baseUrl;
		this.email = email;
		this.code = code;
		this.gridList = gridList;
		this.promotionCredits = promotionCredits;
		
	}

	public String toHtml()
	{
		StringBuffer result = new StringBuffer( "");
		
		
		
		result.append( "<!DOCTYPE html>");
		result.append( "<html>");
		result.append( "<head>");
		result.append( "    <meta charset='utf-8'>");
		result.append( "    <meta name='viewport' content='width=device-width, initial-scale=1'>");
		result.append( "    <title>Instantri.ch</title>");
		result.append( "    <style type='text/css'>");
		result.append( "        body {");
		result.append( "            background-color: #000;");
		result.append( "            background-image: url(" + baseUrl+ "/img/bg.jpg);");
		result.append( "            background-position: top;");
		result.append( "            background-repeat: repeat-x;;");
		result.append( "            font-family: Arial, sans-serif;");
		result.append( "            font-size: 16px;");
		result.append( "            padding-top: 40px;");
		result.append( "            padding-bottom: 20px;");
		result.append( "            color: #333;");
		result.append( "            text-align: center;");
		result.append( "            width: 100%;");
		result.append( "            margin: 0;");
		result.append( "            width: 100%;");
		result.append( "        }");
		result.append( "        table{");
		result.append( "            max-width: 560px;");
		result.append( "            width:100%");
		result.append( "        }");
		result.append( "        @media only screen and (max-width: 560px) {");
		result.append( "            h1{");
		result.append( "                font-size: 30px !important;");
		result.append( "            }");
		result.append( "            td[class='game']{");
		result.append( "                display: block;");
		result.append( "                width: 100% !important;");
		result.append( "                -moz-box-sizing: border-box !important;");
		result.append( "                -webkit-box-sizing: border-box !important;");
		result.append( "                box-sizing: border-box !important;");
		result.append( "                margin-bottom: 15px !important;");
		result.append( "            }");
		result.append( "        }");
		result.append( "    </style>");
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
		result.append( "                <h1 style='font-size:24px;font-weight:bold'>Hi "+login+"!</h1>");
		result.append( "                <p>You've just signed up in Instantri.ch. <a href='"+baseUrl+"/do/validateEmail?email="+email+"&user="+login+"&code="+code+"' style='font-weight:bold;color:#f60068;'>Click on this link</a> to validate your email address and you will be able to play in our free games or buy credits to play in any game. </p>");
		if( promotionCredits != null && promotionCredits.intValue() > 0){
			if( promotionCredits.intValue() == 1){
				result.append( "                <p>There is a <strong>" + promotionCredits.intValue() + " free credit</strong> promotion at this moment so, right after you click the above link, we'll add " + promotionCredits + " credit to your account. You can use it in any game you want.</p>");
			} else{
				result.append( "                <p>There is a <strong>" + promotionCredits.intValue() + " free credits</strong> promotion at this moment so, right after you click the above link, we'll add " + promotionCredits + " credits to your account. You can use them in any game you want.</p>");
			}
		}
		
		result.append( "                <p align='right' style='margin-top:40px;'>Instantri.ch team</p>");
		result.append( "            </td>");
		result.append( "        </tr>");
		result.append( "    </table>");
		result.append( "    <table class='' cellpadding='0' cellspacing='5' align='center' style='background-color:#7a9d15;padding:15px 25px;color:#fff;'>");
		result.append( "        <tr>");
		result.append( "            <td class='' align='center' valign='middle' style='' colspan='3'>");
		result.append( "               <h2 style='font-size:20px;color:#fff;'>Current Games</h2>");
		result.append( "           </td>");
		result.append( "        </tr>");
		result.append( "        <tr>");
		
		if( gridList.size()>=3){
			for( int i=0;i<3;i++){
				Grid grid = gridList.get(i);
			
				result.append( "          <td class='game' style=''>");
				result.append( "                <table class='gameTable' style='background-color:#fefefe;color:#333;border-radius:6px;'>");
				result.append( "                    <tr>");
				result.append( "                        <td colspan='2' style='color:#191919;'>");
				result.append( "                            <h3 style='font-size:16px;'>"+grid.getItem().getName()+"</h3>");
				result.append( "                        </td>");
				result.append( "                    </tr>");
				result.append( "                    <tr style='background-color:#d6d6d6;'>");
				result.append( "                        <td colspan='2' style='padding:5px;'>");
				result.append( "                            <img src='"+baseUrl+"/img/item/"+grid.getItem().getPicture1Url()+"' style='width:100%;max-width:190px;'>");
				result.append( "                        </td>");
				result.append( "                    </tr>");
				result.append( "                    <tr>");
				result.append( "                        <td style='text-align:left;font-size:14px;padding-top:0px;padding-bottom:0px;text-align:center;' valign='middle'>");
				result.append( "                            <img src='" + baseUrl+ "/img/ico_creditos.png' style='max-width:30px;vertical-align:middle;'>");
				result.append( "                            <span style='font-weight:bold;display:inline-block;'>"+new Double(grid.getBoxPrice()).intValue()+"</span>");
				result.append( "                        </td>");
				result.append( "                        <td style='text-align:left;font-size:14px;padding-top:10px;padding-bottom:10px;text-align:center;' valign='middle'>");
				result.append( "                            <img src='" + baseUrl+ "/img/ico_cajas.png' style='max-width:30px;vertical-align:middle;'>");
				result.append( "                            <span style='font-weight:bold;display:inline-block;'>"+grid.getFreeBoxes()+"</span>");
				result.append( "                        </td>");
				result.append( "                   </tr>");
				result.append( "                   <tr>");
				result.append( "                        <td colspan='2' style='padding:10px;'>");
				result.append( "                            <a href='"+baseUrl+grid.getVirtualPath()+"' style='font-size:16px;color:#fff;font-weight:bold;background-color:#f6006a;padding:10px;display:block;text-decoration:none;border-radius:7px;'>PLAY NOW!</a>"); 
				result.append( "                        </td>");
				result.append( "                    </tr>");
				result.append( "                </table>");
				result.append( "           </td>");
			}
		}
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
		result.append( "       <tr style=''>");
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
		
		result.append( "Hi " + login + ". You've just signed up in Instantri.ch. You already can start to play.\r\n");
		result.append( "Click on this link to validate your email address and you will be able to play in our free games or buy credits to play in any game.\r\n.");
		result.append( baseUrl+"/do/validateEmail?email="+email+"&user="+login+"&code="+code);
				
		return result.toString();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<Grid> getGridList() {
		return gridList;
	}

	public void setGridList(List<Grid> gridList) {
		this.gridList = gridList;
	}
	

}
