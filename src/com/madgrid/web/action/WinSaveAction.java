package com.madgrid.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.UserDAO;
import com.madgrid.dao.WinDAO;
import com.madgrid.model.Item;
import com.madgrid.model.User;
import com.madgrid.model.Win;
import com.madgrid.web.form.WinEditForm;
import com.madgrid.web.util.Mail;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.mail.SendBitcoinsMailObject;

public class WinSaveAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(WinSaveAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		UserSession userSession = super.checkUserSession(request);
		WinDAO winDAO = new WinDAO();
		
		logger.info( "WinSaveAction called");
		
		if( userSession == null){
			logger.warn( "WinSaveAction called withou userSession");
			return null;
		}
		
		
		UserDAO userDAO = new UserDAO();
		User user = userDAO.getUserById(userSession.getUser().getId());
		
		if( user.getIsFraudulent()){
			return null;
		}
		
		WinEditForm winEditForm = (WinEditForm) form;
		
		Criteria criteria = new Criteria();
		criteria.addEqualTo("userId", userSession.getUser().getId());
		criteria.addEqualTo("id", winEditForm.getId());
		
		Win win = winDAO.getWinByCriteria(criteria);
		if( win != null && win.getItemSent() == false && win.getDeliveryRequested()== false && win.getUser().getId() == userSession.getUser().getId().intValue() && (win.getItem().getType().intValue() == Item.ITEM_TYPE_ONLY_BITCOINS || win.getItem().getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS)){
			win.setBitcoinAddress( winEditForm.getBitcoinAddress());
			win.setItemSent( false);
			win.setDeliveryRequested( true);
			winDAO.setWin( win);
			
			if( user.getRequestPrizeSubscribed()){
				SendBitcoinsMailObject sendBitcoinsMailObject = new SendBitcoinsMailObject( userSession.getUser().getLogin(), win.getItem().getBitcoins(), winEditForm.getBitcoinAddress(), Utils.getBaseUrl());
				Mail mail = new Mail(  userSession.getUser().getEmail(), "You requested the delivery of Bitcoins on Instantri.ch", sendBitcoinsMailObject);
				mail.start();
			}
		}
		
		return mapping.findForward("ok");
	}
}		
