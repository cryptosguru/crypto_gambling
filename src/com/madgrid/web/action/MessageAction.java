package com.madgrid.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.MessageDAO;
import com.madgrid.model.Message;
import com.madgrid.web.form.MessageForm;
import com.madgrid.web.util.Mail;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.mail.MessageSentForAdminMailObject;

public class MessageAction extends AbstractAction {
	
	public static final Logger logger = Logger.getLogger(MessageAction.class);

	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		MessageForm messageForm = (MessageForm) form;
		Message message = new Message();
		MessageDAO messageDAO = new MessageDAO();
		UserSession userSession = super.checkUserSession(request);
		boolean error = false;
		
		String ip = request.getRemoteAddr();
		
		if( !ip.equals("91.213.8.84") && !ip.equals("37.157.192.208") && !ip.equals("188.138.1.229") && !ip.equals("174.228.194.12") && !ip.equals("46.173.164.190") && !ip.equals("63.224.22.184") && !ip.equals("89.109.90.105") && !ip.equals("175.156.71.197") && !ip.equals("80.26.159.249")){
			
			message.setCreated( Utils.today());
			message.setIsRead( false);
			message.setSubject( messageForm.getSubject());
			message.setText( messageForm.getText());
			try{
				message.setType( Integer.parseInt(messageForm.getMessageType()));
			}catch (NumberFormatException e){
				System.out.println("-Error en MessageAction: Se ha intentado parsear " + messageForm.getMessageType());
				error = true;
			}
			
			if( error == false){
			
				if( userSession != null){
					logger.info( "MessageAction for user " + userSession.getUser().getLogin() + "(" + userSession.getUser().getId() +")");
					message.setUser( userSession.getUser());
					message.setUserId( userSession.getUser().getId());
					message.setEmail(userSession.getUser().getEmail());
				} else{
					message.setEmail(messageForm.getEmail());
				}
		
				logger.info( "MessageAction text = " + messageForm.getText());
				
				
				messageDAO.setMessage( message);
				
				if( userSession != null){
					MessageSentForAdminMailObject messageSentForAdminMailObject = new MessageSentForAdminMailObject( userSession.getUser().getLogin(),message.getSubject(), message.getText(), Utils.getBaseUrl());
					Mail mail = new Mail(  "amandris@hotmail.com", "Gus, un usuario ha escrito un mensaje", messageSentForAdminMailObject);
					mail.start();
				}
				return mapping.findForward( "ok");
			}
		}
		
		return null;
		
	}
}		
