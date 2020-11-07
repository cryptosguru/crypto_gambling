package com.madgrid.web.util.mail;

import java.net.URLEncoder;
import java.text.DecimalFormat;

import com.madgrid.model.Grid;
import com.madgrid.model.Item;
import com.madgrid.model.Win;

public class UserWinMailObject implements MailObject {

	private String 	login;
	private String 	baseUrl;
	private Item 	item;
	private Win 	win;
	private boolean autoPay;
	
	public UserWinMailObject( String login, Item item, Win win, boolean autoPay, String baseUrl)
	{
		this.login = login;
		this.baseUrl = baseUrl;
		this.item = item;
		this.win = win;
		this.autoPay = autoPay;
	}

	public String toHtml()
	{
		StringBuffer result = new StringBuffer( "");
		DecimalFormat numberFormat = new DecimalFormat("#.###");
		
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
		result.append( "        table[class='pinkGrad']{");
		result.append( "            background: #92003e; /* Old browsers */");
		result.append( "            background: -moz-linear-gradient(left,  #92003e 0%, #f60068 50%, #92003e 100%) !important; /* FF3.6+ */");
		result.append( "            background: -webkit-gradient(linear, left top, right top, color-stop(0%,#92003e), color-stop(50%,#f60068), color-stop(100%,#92003e)) !important; /* Chrome,Safari4+ */");
		result.append( "            background: -webkit-linear-gradient(left,  #92003e 0%,#f60068 50%,#92003e 100%) !important; /* Chrome10+,Safari5.1+ */");
		result.append( "            background: -o-linear-gradient(left,  #92003e 0%,#f60068 50%,#92003e 100%) !important; /* Opera 11.10+ */");
		result.append( "            background: -ms-linear-gradient(left,  #92003e 0%,#f60068 50%,#92003e 100%) !important; /* IE10+ */");
		result.append( "            background: linear-gradient(to right,  #92003e 0%,#f60068 50%,#92003e 100%) !important; /* W3C */");
		result.append( "            filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#92003e', endColorstr='#92003e',GradientType=1 ) !important; /* IE6-9 */");
		result.append( "        }");
		result.append( "        @media only screen and (max-width: 560px) {");
		result.append( "            h1{");
		result.append( "                font-size: 30px !important;");
		result.append( "            }");
		result.append( "            td[class='col'],");
		result.append( "            td[class='colCenterXS']{");
		result.append( "                display: block;");
		result.append( "                width: 100% !important;");
		result.append( "                -moz-box-sizing: border-box !important;");
		result.append( "                -webkit-box-sizing: border-box !important;");
		result.append( "                box-sizing: border-box !important;");
		result.append( "            }");
		result.append( "            td[class='colCenterXS']{");
		result.append( "                text-align: center !important;");
		result.append( "                padding-left: 0px !important;");
		result.append( "            }");
		result.append( "        }");
		result.append( "    </style>");
		result.append( "</head>");
		result.append( "<body background='" + baseUrl+ "/img/bg.jpg.jpg'>");
		result.append( "    <table cellpadding='0' cellspacing='0' align='center'  background='" + baseUrl+ "/img/header_bg.jpg' style='background-image:url(" + baseUrl+ "/img/header_bg.jpg);background-repeat:no-repeat;background-position:top right;background-color:#191919'>");
		result.append( "        <tr>");
		result.append( "            <td class='' align='left' valign='top' style='padding:18px 25px;width:100%;'>");
		result.append( "                <img src='" + baseUrl+ "/img/logo.png'>");
		result.append( "            </td>");
		result.append( "        </tr>");
		result.append( "    </table>");
		result.append( "    <table cellpadding='0' cellspacing='0' align='center'  background='" + baseUrl+ "/img/price_bg.jpg' style='background-image:url(" + baseUrl+ "/img/price_bg.jpg);background-repeat:no-repeat;background-position:center;background-color:#01407a;max-height:240px;height:240px;'>");
		result.append( "        <tr>");
		result.append( "            <td class='' align='center' valign='middle' style='padding:25px;width:100%;'>");
		result.append( "                <img src='" + baseUrl+ "/img/item/"+item.getPicture1Url()+"'>");
		result.append( "            </td>");
		result.append( "        </tr>");
		result.append( "    </table>");
		result.append( "    <table class='pinkGrad' cellpadding='0' cellspacing='0' align='center'  style='background:#92003e;'>");
		result.append( "        <tr>");
		result.append( "            <td class='' align='center' valign='middle' style='padding:25px;width:100%;color:#ffb8d6;'>");
		result.append( "                <h1 style='color:#fff;font-size:36px;margin:0px;'>Congratulations, "+login+"!</h1>");
		result.append( "                <h2 style='font-size:26px;margin:0px;'>You won the <span style='color:#fff;'>"+item.getName()+"</span> Prize!</h2>");
		result.append( "            </td>");
		result.append( "        </tr>");
		result.append( "    </table>");
		result.append( "    <table class='' cellpadding='0' cellspacing='0' align='center' style='background-color:#fff;padding:25px;'>");
		result.append( "        <tr>");
		result.append( "            <td class='col' align='center' valign='middle' style='width:80px;'>");
		result.append( "                <img src='" + baseUrl+ "/img/staricon.jpg'>");
		result.append( "            </td>");
		result.append( "            <td class='colCenterXS' align='left' valign='middle' style='padding-left:40px;'>");
		
		
		if( item.getType() == Item.ITEM_TYPE_ONLY_BITCOINS){
			if( autoPay){
				result.append( "<p>You activated the <strong>Autoclaim Bitcoin Prizes</strong> option so you don't have to do anything else. In the next 24 hours you'll receive the prize in your bitcoin wallet address.</p>");
			} else{
				result.append( "<p>Go to <a href='" + baseUrl + "/do/win/list'>Instantri.ch</a> and claim your prize so we can know your Bitcoin wallet address.</p>");
			}
		} else if( item.getType() == Item.ITEM_TYPE_ONLY_CREDITS){
			result.append( "Credit prizes are added to your account automatically so you can use them right now.");
		} else if( item.getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
			if( autoPay){
				result.append( "<p>It has "+ item.getCredits() +" credits and "+numberFormat.format(item.getBitcoins())+" Bitcoins. Credits are added to your account automatically so you can use them right now and since you activated the <strong>Autoclaim Bitcoin Prizes</strong> option, you will also receive the bitcoin prize in your wallet address within the next 24 hours.</p>");
			}else{
				result.append( "<p>It has "+ item.getCredits() +" credits and "+numberFormat.format(item.getBitcoins())+" Bitcoins. Credits are added to your account automatically so you can use them right now but Bitcoins need to be claimed so we can know your wallet address. Go to <a href='" + baseUrl + "/do/win/list'>Instantri.ch</a> and claim your prize.</p>");
			}
		}
		
		result.append( "            </td>");
		result.append( "        </tr>");
		result.append( "        <tr>");
		result.append( "            <td class='' colspan='2' align='center' valign='middle' style='padding-top:20px;'>");
		
		if( item.getType() == Item.ITEM_TYPE_ONLY_BITCOINS){
			if( !autoPay){
				result.append( "<a href='" + baseUrl + "/do/win/list'><img src='" + baseUrl+ "/img/claimprize.png'></a>");
			}
		} else if( item.getType() == Item.ITEM_TYPE_ONLY_CREDITS){
			result.append( "<a href='" + baseUrl + "'><img src='" + baseUrl+ "/img/playnow.png'></a>");
		} else if( item.getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
			if( autoPay){
				result.append( "<a href='" + baseUrl + "'><img src='" + baseUrl+ "/img/playnow.png'></a>");
			}else{
				result.append( "<a href='" + baseUrl + "/do/win/list'><img src='" + baseUrl+ "/img/claimprize.png'></a>");
			}
		}
		
		if( item.getType() == Item.ITEM_TYPE_ONLY_BITCOINS || item.getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
			
			if( win.getGridType() == Grid.GRID_TYPE_FREE || item.getBitcoins()>=0.01){
				try{
					result.append( "<p><a href='https://www.instantri.ch/do/tweet?id="+win.getId().toString()+"' target='_blank'>Tweet about this and you'll get <strong>1 credit</strong> for free!</a></p>");
					result.append( "<p><span style='font-size:11px;color:#aaaaaa;'>(The credit will be added manually after Instantri.ch verification)</span></p>");
				} catch (Exception e) {}
			}
		}
		
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
		DecimalFormat numberFormat = new DecimalFormat("#.###");
		
		if( item.getType() == Item.ITEM_TYPE_ONLY_BITCOINS){
			if( autoPay){
				result.append( "Congratulations! " + login + ". You won the "+item.getName()+" prize. You activated the Request Prize Payment Automatically option so you don't have to do anything else. In the next 24 hours you'll receive the prize in your bitcoin wallet address.");
			} else{
				result.append( "Congratulations! " + login + ". You won the "+item.getName()+" prize. Go to " + baseUrl + " and claim your prize so we can know your Bitcoin wallet address.");
			}
		} else if( item.getType() == Item.ITEM_TYPE_ONLY_CREDITS){
			result.append( "Congratulations! " + login + ". You won the "+item.getName()+" prize. Credit prizes are added to your account automatically so you can use them right now.");
		} else if( item.getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
			if( autoPay){
				result.append( "Congratulations! " + login + ". You won the "+item.getName()+" prize. It has "+ item.getCredits() +" credits and "+numberFormat.format(item.getBitcoins())+" Bitcoins. Credits are added to your account automatically so you can use them right now and since you activated the Request Prize Payment Automatically option, you will also receive the bitcoin prize in your wallet address within the next 24 hours.");
			}else{
				result.append( "Congratulations! " + login + ". You won the "+item.getName()+" prize. It has "+ item.getCredits() +" credits and "+numberFormat.format(item.getBitcoins())+" Bitcoins. Credits are added to your account automatically so you can use them right now but Bitcoins need to be claimed so we can know your wallet address. Go to " + baseUrl + " and claim your prize.");
			}
		}
				
		return result.toString();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Win getWin() {
		return win;
	}

	public void setWin(Win win) {
		this.win = win;
	}

	public boolean isAutoPay() {
		return autoPay;
	}

	public void setAutoPay(boolean autoPay) {
		this.autoPay = autoPay;
	}
	
	

}
