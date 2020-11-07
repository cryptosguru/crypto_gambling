package com.madgrid.web.util;

import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public final class Validate {

	
	public static boolean required(String text)
	{
		if( text == null || text.length() == 0){
			return false;
		}
		
		return true;
	}
	
	public static boolean loginLong(String text)
	{
		if( text.length() > 50){
			return false;
		}
		
		return true;
	}
	
	public static boolean passwordShort(String text)
	{
		if( text.length() < 3){
			return false;
		}
		
		return true;
	}
	
	public static boolean passwordLong(String text)
	{
		if( text.length() > 200){
			return false;
		}
		
		return true;
	}
	
	public static boolean loginShort(String text)
	{
		if( text.length() < 3){
			return false;
		}
		
		return true;
	}
	
	public static boolean loginCharacters(String text)
	{
		char letter;
		for ( int i = 0; i < text.length(); i ++) {
			letter = text.charAt( i);  
			
			switch( letter) {
				case 'á': case 'é':case 'í': case 'ó': case 'ú': case 'Á': case 'É': case 'Í': case 'Ó': case 'Ú': return false;
				case 'à': case 'è':case 'ì': case 'ò': case 'ù': case 'À': case 'È': case 'Ì': case 'Ò': case 'Ù': return false;
				case 'ä': case 'ë':case 'ï': case 'ö': case 'ü': case 'Ä': case 'Ë': case 'Ï': case 'Ö': case 'Ü': return false;
				case 'â': case 'ê':case 'î': case 'ô': case 'û': case 'Â': case 'Ê': case 'Î': case 'Ô': case 'Û': return false;
			}
			
	 		if ( ! Character.isLetterOrDigit( letter) && letter != '_'){
				return false;
	 		}
		}
		
		return true;
	}
	
	public static boolean nameLong(String text)
	{
		if( text.length() > 100){
			return false;
		}
		
		return true;
	}
	
	public static boolean surnameLong(String text)
	{
		if( text.length() > 100){
			return false;
		}
		
		return true;
	}
	
	public static boolean phoneNumberShort(String text)
	{
		if( text.length() < 9){
			return false;
		}
		
		return true;
	}
	
	public static boolean phoneNumberLong(String text)
	{
		if( text.length() > 9){
			return false;
		}
		
		return true;
	}
	
	
	public static boolean phoneNumberValid(String text)
	{
		Pattern phoneNumberPattern = Pattern.compile("^[6-7][0-9]*$");
		if( !phoneNumberPattern.matcher( text).find()){
			return false;
		}
		
		return true;
	}
	
	public static boolean emailLong(String text)
	{
		if( text.length() > 200){
			return false;
		}
		
		return true;
	}
	
	public static boolean emailShort(String text)
	{
		if( text.length() < 5){
			return false;
		}
		
		return true;
	}
	
	public static boolean bitcoinAddressLong(String text)
	{
		if( text.length() > 35){
			return false;
		}
		
		return true;
	}
	
	public static boolean bitcoinAddressShort(String text)
	{
		if( text.length() < 26){
			return false;
		}
		
		return true;
	}
	
	public static boolean emailValid(String text)
	{
		Pattern emailPattern  = Pattern.compile(".+@.+\\.[a-z]+");
		if( !emailPattern.matcher( text).find()){
			return false;
		}
		
		return true;
	}
	
	public static boolean birthYearValid(String text)
	{
		Pattern birthYearPattern = Pattern.compile("^[0-9][0-9]$");
		if( !birthYearPattern.matcher( text).find()){
			return false;
		}
		
		return true;
	}
	
	public static boolean birthYearOld(String text)
	{
		int now = ((GregorianCalendar)GregorianCalendar.getInstance()).get( GregorianCalendar.YEAR);
		try{
			int birthYear = Integer.parseInt( "19" + text);
			if (birthYear + 18 > now){
				return false;
			}
		}catch(Exception e){
			return false;
		}
		
		return true;
	}
	
	public static boolean postalCode(String province, String postalCode)
	{
		if( !postalCode.substring(0, 2).equals( province)){
			return false;
		}
		
		return true;
	}
	
}
