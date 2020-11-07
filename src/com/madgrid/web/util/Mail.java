package com.madgrid.web.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;

import com.madgrid.web.util.mail.AffiliationUserRequestMailObject;
import com.madgrid.web.util.mail.CompanyAffiliatedUserCreditsBuyMailObject;
import com.madgrid.web.util.mail.MailObject;
import com.madgrid.web.util.mail.ReturnCreditsMailObject;
import com.madgrid.web.util.mail.ReturnCreditsPromotionMailObject;
import com.madgrid.web.util.mail.SendBitcoinsMailObject;
import com.madgrid.web.util.mail.SendValidationMailObject;
import com.madgrid.web.util.mail.UserBuyCreditsMailObject;
import com.madgrid.web.util.mail.UserRegisterMailObject;
import com.madgrid.web.util.mail.UserRegisterNotValidatedMailObject;
import com.madgrid.web.util.mail.UserWinMailObject;

public class Mail extends Thread{

	public static final Logger logger = Logger.getLogger(Mail.class);
	
	private String email;
	private String title;
	private MailObject mailObject;
	
	
	public Mail(String email, String title, MailObject mailObject){
		this.email = email;
		this.title = title;
		this.mailObject = mailObject;
	}

	public void run() {
		 sendMail(email, title, mailObject);
	}
	
	private synchronized boolean sendMail( String email, String title, MailObject mailObject)
	{
        final String  	from  			= ConfigurationParameterManager.getParameterString( "madgridEmail");
        final String  	host  			= ConfigurationParameterManager.getParameterString( "smptHost");
        final String  	mailUser  		= ConfigurationParameterManager.getParameterString( "mailUser");
        final String  	mailPassword  	= ConfigurationParameterManager.getParameterString( "mailPassword");
        Properties 		properties 		= new Properties();

        properties.put( "mail.smtp.host", host);
        properties.put( "mail.smtp.port", ConfigurationParameterManager.getParameterString( "smptPort"));
        properties.put( "mail.smtp.starttls.enable", "false");
        properties.put( "mail.smtp.auth", "true");
        properties.put( "mail.user", mailUser);
        properties.put( "mail.password", mailPassword);
        
        Session session = Session.getInstance( properties, new Authenticator (  )   {  
	       public PasswordAuthentication  
	          getPasswordAuthentication (  )   {  
	            return new PasswordAuthentication ( mailUser, mailPassword ) ; 
	        }  
	    }); 

        try {
        	
        	
        	boolean sendCopy = false;
        	
        	if( mailObject.getClass() == CompanyAffiliatedUserCreditsBuyMailObject.class ||
        		mailObject.getClass() == ReturnCreditsPromotionMailObject.class ||
        		mailObject.getClass() == UserBuyCreditsMailObject.class ||
        		mailObject.getClass() == UserWinMailObject.class ||
        		mailObject.getClass() == AffiliationUserRequestMailObject.class){
        		sendCopy = true;
        	}
        	  
        	if( sendCopy){
	            //Enviar copia
	            
	            MimeMessage 		messageCopy 	= new MimeMessage( session);
	            InternetAddress  	fromAddressCopy = new InternetAddress( from, ConfigurationParameterManager.getParameterString( "mailSender"));
	            InternetAddress  	toAddressCopy 	= null;
	            Multipart			multipartCopy	= null;
	            MimeBodyPart 		bodyPart1Copy 	= new MimeBodyPart();
	            MimeBodyPart 		bodyPart2Copy 	= new MimeBodyPart();
	
	            messageCopy.setFrom( fromAddressCopy);
	            
	            toAddressCopy 		= new InternetAddress( "amandris@hotmail.com", "");
	            InternetAddress[] addressCopy = { toAddressCopy};
	            
	            messageCopy.setRecipients	( Message.RecipientType.TO, addressCopy);
	            
	            messageCopy.setSubject		( ( title != null ? title : ""));
	            messageCopy.setSentDate		( Utils.today());
	
	            bodyPart1Copy.setText			( mailObject.toText());
	            bodyPart2Copy.setDataHandler	( new DataHandler( new ByteArrayDataSource( mailObject.toHtml(), "text/html")));
	            
	            multipartCopy = new MimeMultipart( "alternative");
	            multipartCopy.addBodyPart( bodyPart1Copy);
	            multipartCopy.addBodyPart( bodyPart2Copy);
	            
	            messageCopy.setContent		( multipartCopy);
	            Transport tCopy = session.getTransport("smtp");
	            tCopy.connect( mailUser,mailPassword);
	            tCopy.sendMessage( messageCopy,messageCopy.getAllRecipients());
	            tCopy.close();
        	}
        	

        	
        	
            MimeMessage 		message 	= new MimeMessage( session);
            InternetAddress  	fromAddress = new InternetAddress( from, ConfigurationParameterManager.getParameterString( "mailSender"));
            InternetAddress  	toAddress1 	= null;
            InternetAddress  	toAddress2 	= null;
            Multipart			multipart	= null;
            MimeBodyPart 		bodyPart1 	= new MimeBodyPart();
            MimeBodyPart 		bodyPart2 	= new MimeBodyPart();

            message.setFrom( fromAddress);
            
            toAddress1 		= new InternetAddress( email, "");
            InternetAddress[] address = { toAddress1};
            InternetAddress[] address2 = { toAddress2};
            
            message.setRecipients	( Message.RecipientType.TO, address);
            //message.setRecipients	( Message.RecipientType.BCC, address2);
            
            message.setSubject		( ( title != null ? title : ""));
            message.setSentDate		( Utils.today());

            bodyPart1.setText			( mailObject.toText());
            bodyPart2.setDataHandler	( new DataHandler( new ByteArrayDataSource( mailObject.toHtml(), "text/html")));
            
            multipart = new MimeMultipart( "alternative");
            multipart.addBodyPart( bodyPart1);
            multipart.addBodyPart( bodyPart2);
            
            message.setContent		( multipart);
            Transport t = session.getTransport("smtp");
            t.connect( mailUser,mailPassword);
            t.sendMessage( message,message.getAllRecipients());
            t.close();
            
            logger.info( "Email sent to " + email + " of type " + mailObject.getClass().getName()) ;
          
            //Fin enviar copia
            
            
        } catch ( Exception e) {
        		e.printStackTrace();
            return false;
        }
        return true;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MailObject getMailObject() {
		return mailObject;
	}

	public void setMailObject(MailObject mailObject) {
		this.mailObject = mailObject;
	}
}		
