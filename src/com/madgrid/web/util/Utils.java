package com.madgrid.web.util;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.ojb.broker.query.Criteria;

import com.madgrid.dao.ParameterDAO;
import com.madgrid.model.Parameter;


public final class Utils {

	
	public static boolean nullOrBlank(String text)
	{
		if( text == null || text.equals("")){
			return true;
		}
		return false;
	}
	
	public static String removeCR( String string)
	{
		if ( string == null)
			return "";
		
		string = string.replace('\r', ' ');
		string = string.replace('\n', ' ');
		string = string.replaceAll("\r\n", "");
		
		return string;
	}
	
	public static String briefString( String string, int n)
	{
		if ( string == null)
			return "";
		
		if ( string.length() > n)
			return string.substring( 0, n-2) + "&hellip;";
		
		
		return string;
	}

	public static Integer parseInteger( String string, int defaultValue)
	{
		try {
			return new Integer( Integer.parseInt( string));
		} catch ( NumberFormatException e) {
			return new Integer( defaultValue);
		}
	}
	
	public static Double parseDouble( String string, double defaultValue)
	{
		string = string.replaceAll( ",", ".");
		
		try {
			return new Double( Double.parseDouble( string));
		} catch ( NumberFormatException e) {
			return new Double( defaultValue);
		}
	}
	
	public static String parseInteger( String string, String defaultValue)
	{
		try {
			return new Integer(Integer.parseInt( string)).toString();
		} catch ( NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public static String boolean2String( boolean value)
	{
		if ( value)
			return "1";

		return "0";
	}

	public static Integer boolean2Integer( boolean value)
	{
		if ( value)
			return new Integer( 1);

		return new Integer( 0);
	}
	
	
	
	public static boolean integer2Boolean( Integer value)
	{
		if ( value.intValue() == 0)
			return false;

		return true;
	}
	
	public static boolean string2Boolean( String value)
	{
		if ( value.trim().equals( "0"))
			return false;

		return true;
	}

	
	public static String getDate( Date date)
	{
		return getDate( date, 1);
	}
	
	public static String getDate( Date date, int type)
	{
		Locale locale = new Locale( "en", "GB");
		
		if (date == null) 
			return "";
		
		switch( type){
			case 1: return new SimpleDateFormat ( "dd/MM/yyyy", locale).format( date);
			case 2: return new SimpleDateFormat ( "dd MMM yyyy", locale).format( date);
			case 3: return new SimpleDateFormat ( "dd MMMM yyyy", locale).format( date);
			case 4: return new SimpleDateFormat ( "EEEE, dd MMMM yyyy", locale).format( date);
			case 5: return new SimpleDateFormat ( "EEEE, dd 'de' MMMM", locale).format( date);
		}
		
		return new SimpleDateFormat ( "dd/MM/yyyy").format( date);
	}
	
	public static String getTime( Date date)
	{
		if (date == null) 
			return "";
		
		return new SimpleDateFormat ( "HH:mm:ss").format( date);
	}
	
	public static String getTimeShort( Date date)
	{
		if (date == null) 
			return "";
		
		return new SimpleDateFormat ( "HH:mm").format( date);
	}

	public static Date parseDate( String string)
	{
		Date 				date 	= null;
		GregorianCalendar 	cal 	= new GregorianCalendar();
		int 				day 	= 0;
		int 				month 	= 0;
		int 				year 	= 0;
		
		if (( string == null) || ( string.length() < 1) || ( string.trim().equals( ""))){
				return null;
		} else {
			SimpleDateFormat 	formatter	= new SimpleDateFormat ( "dd/MM/yyyy");
			ParsePosition 		pos 		= new ParsePosition( 0);
			
			date = formatter.parse( string, pos);
			
			if ( date == null) {
				return null;
			} else {
				cal 	= ( GregorianCalendar)formatter.getCalendar();
				day 	= cal.get( GregorianCalendar.DAY_OF_MONTH);
				month 	= cal.get( GregorianCalendar.MONTH)+1;
				year 	= cal.get( GregorianCalendar.YEAR);

				if (( day < 1) || ( day > 31) || ( month < 1) || ( month > 12) || ( year < 1900) || ( year > 2200)) {
					return null;
				}
			}
		} 		
		
		return date;
	}
	
	public static Date parseDateAndTime( String string)
	{
		Date date 	= null;
		
		if (nullOrBlank( string)){
				return null;
		} 
		SimpleDateFormat 	formatter	= new SimpleDateFormat ( "dd/MM/yyyy HH:mm:ss");
		ParsePosition 		pos 		= new ParsePosition( 0);
		
		date = formatter.parse( string, pos);
		
		if ( date == null) {
			return null;
		}
		
		return date;
	}
	
	public static String controlNull( Double number)
	{
		if ( number == null) 
			return "";
		
		try{
			 return new DecimalFormat( "0.00").format(number).replaceAll( ",", ".");
		}catch ( Throwable e){
			return "";
		}
	}
	
	public static String controlNull( Integer number, int size)
	{
		if ( number == null) 
			return "";
		
		try{
			switch( size) {
				case 1: return new DecimalFormat( "0").format( number);
				case 2: return new DecimalFormat( "00").format( number);
				case 3: return new DecimalFormat( "000").format( number);
				case 4: return new DecimalFormat( "0000").format( number);
				case 5: return new DecimalFormat( "00000").format( number);
				case 6: return new DecimalFormat( "000000").format( number);
			}

			return Integer.toString( number.intValue());
		} catch ( Throwable e){
			return "";
		}
	}
	
	public static Integer controlNullInteger( Integer number, int defaultValue)
	{
		if ( number == null) 
			return new Integer( defaultValue);
		
		return number;
		
	}
	
	public static String controlNull( Double number, int decimals)
	{
		if ( number == null) 
			return "";
		
		try{
			switch( decimals) {
				case 0:return new DecimalFormat( "0").format(number);
				case 1:return new DecimalFormat( "0.0").format(number);
				case 2:return new DecimalFormat( "0.00").format(number);
				case 3:return new DecimalFormat( "0.000").format(number);
			}
			return new DecimalFormat( "0.00").format(number);
		}catch ( Throwable e){
			return "";
		}
	}
	
	
	public static String controlNull( Integer number)
	{
		if ( number == null) 
			return "";

		return number.toString();
	}
	
	public static String controlNull( String string)
	{
		if ((string == null) || (string.equals("null")))
			return "";
		
		return string;
	}

	public static String controlNull( String string, int maxCharacters)
	{
		if ((string == null) || (string.equals("null")))
			return "";
		
		return briefString( string, maxCharacters);
	}

	
	public static int getAge( Date date, Locale locale)
	{
	    Calendar 	dateOfBirth 	= new GregorianCalendar();
	    Calendar 	today 			= Calendar.getInstance( locale);
	    int 		age				= 0;
	    
	    dateOfBirth.setTime( date);
	    
	    age = today.get( Calendar.YEAR) - dateOfBirth.get( Calendar.YEAR);
	    
	    dateOfBirth.add(Calendar.YEAR, age);
	    
	    if ( today.before( dateOfBirth)) {
	        age = age - 1;
	    }
		
		return age;
	}
	
	public static Date getBirthDate( int age, Locale locale)
	{
		 Calendar today = Calendar.getInstance( locale);
		 
		 today.add(Calendar.YEAR, -age);
		 
		 return today.getTime();
	}
	
	
	
	public static int getDaysFrom( Date date, Date today)
	{
		long diference	= 0;

		diference = today.getTime() - date.getTime();

		long days =  diference / 86400000;
		
		return ( int)days;
	}
	
	public static int getTimeFrom( Date date, boolean isFrom)
	{

		long today = Calendar.getInstance().getTime().getTime();
		long userDate	= date.getTime();
		long diference	= 0;

		if( isFrom) {
			diference = today - userDate;
		} else {
			diference = userDate - today;
		}

		long minutes = ( diference / 1000) / 60;
		
		if( minutes < 60)
			return ( int) minutes;
		
		long hours = minutes / 60;
		
		if( hours < 24)
			return ( int) hours;
		
		return ( int)( hours / 24);
	}

	public static String getTimeFromType( Date date, boolean isFrom)
	{
		long today = Calendar.getInstance( ).getTime().getTime();
		long userDate	= date.getTime();
		long diference	= 0;
		
		if( isFrom)
			diference = today - userDate;
		else
			diference = userDate - today;
		
		long minutes = ( diference / 1000) / 60;
		
		if( minutes < 60)
			return "minute";
		
		long hours = minutes / 60;
		
		if( hours < 24)
			return "hour";
		
		return "day";
	}

	public static String fillString( String string, int size){
		
		if ( string.length() > size){
			string = string.substring( 0, size);
		} else {
			String blankString = "";
			
			for ( int i = 0; i < size - string.length(); i++){
				blankString = blankString + " ";
			}
			string = string + blankString;
		}
		
		return string;
	}
	
	private static String byteToHex( byte[] b)
	{
		StringBuffer 	stringHex	= new StringBuffer( 2*b.length);
		String 			valorHex	= new String();
		
		for ( int i=0; i < b.length; i++) { 
			valorHex = java.lang.Integer.toHexString( ( b[ i])&0xff);
			
			if ( valorHex.length() == 1){
				 stringHex.append( "0"); 
			}
			stringHex.append( valorHex);
		}
		return ( stringHex.toString());
	}


	public static String digest( String string)
	{
		byte[] output 	= null;
		String o 		= "";
			
		if( string == null ){
			return null;
		}
		
		try {
			MessageDigest md = MessageDigest.getInstance( "SHA-256");
				
			md.update( string.getBytes());
				
			output = md.digest();
				
		} catch ( Exception e) {
			System.out.println( "Could not encrypt");
		}
			
		return byteToHex( output);	
	}
	
	 public static String getBaseUrl() throws Exception
	{
		ParameterDAO parameterDAO = new ParameterDAO();
		Criteria criteria = new Criteria();
		criteria.addEqualTo("name", "baseUrl");
		Parameter parameter = parameterDAO.getParameterByCriteria( criteria);
		if( parameter != null){
			return parameter.getValue();
		}
		return null;
	}
	
	
	public static Date today(){
		 Locale locale = new Locale( "en", "GB");
		 
		 GregorianCalendar today = (GregorianCalendar) GregorianCalendar.getInstance( TimeZone.getTimeZone("UTC"), locale);
		 String string = today.get(Calendar.DAY_OF_MONTH) + "/"+(today.get(Calendar.MONTH) + 1)+"/"+today.get(Calendar.YEAR)+" "+today.get(Calendar.HOUR_OF_DAY)+":"+today.get(Calendar.MINUTE)+":"+today.get(Calendar.SECOND)+":"+today.get(Calendar.MILLISECOND);
		 
		 SimpleDateFormat formatter	= new SimpleDateFormat ( "dd/MM/yyyy HH:mm:ss:SSS");
		 ParsePosition pos = new ParsePosition( 0);
			
		 Date date = formatter.parse( string, pos);
		 
		 return date;
	}
	
	public static String digestMD5( String string)
	{
		byte[] output 	= null;
		String o 		= "";
			
		if( (string == null) || ( string.length() == 0))
			return null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
				
			md.update( string.getBytes());
				
			output = md.digest();
				
		} catch ( Exception e) {
			System.out.println( "Could not encrypt");
		}
			
		return byteToHex( output);	
	}
}
