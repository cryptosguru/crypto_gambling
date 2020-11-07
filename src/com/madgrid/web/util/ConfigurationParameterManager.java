package com.madgrid.web.util;

import java.util.Properties;
import java.io.InputStream;

import org.apache.struts.util.MessageResources;


public final class ConfigurationParameterManager
{
	private static final 	String 		CONFIGURACION_FILE_NAME = "configurationParameters.properties";
	private static 			Properties 	ConfigurationParameter;

    private ConfigurationParameterManager() 
    {
    }

    static 
    { 
        try 
        {
            Class 		managerClass 	= ConfigurationParameterManager.class;
            ClassLoader classLoader 	= managerClass.getClassLoader();
            InputStream inputStream 	= classLoader.getResourceAsStream( CONFIGURACION_FILE_NAME);
            
            ConfigurationParameter = new Properties();
            
            ConfigurationParameter.load( inputStream);
            
            inputStream.close();
        } 
        catch (Exception e) 
        {
        	System.err.println( "Error loading configuration parameters.");
            e.printStackTrace();
        } 
    }

   
    public static int getParameterInt(String key) 
    {
    	try{
    		return Integer.parseInt( ConfigurationParameter.getProperty(key));
    	} catch ( NumberFormatException e) {
    		return 0;
    	}
    }

    public static String getParameterString( String key) 
    {
  		return ConfigurationParameter.getProperty( key);
    }

    
  public static MessageResources messages = MessageResources.getMessageResources( "madgridMessageResources");

  public static MessageResources getMessages ()
  {
  	return MessageResources.getMessageResources( "madgridMessageResources");
  }
}
