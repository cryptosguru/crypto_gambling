package com.madgrid.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.UserDAO;
import com.madgrid.model.User;
import com.madgrid.web.form.UserEditForm;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.Validate;

public class UserSaveAction extends AbstractAction {

	
	public static final Logger logger = Logger.getLogger(UserSaveAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		UserSession userSession = super.checkUserSession(request);
		UserDAO userDAO = new UserDAO();
		
		logger.info( "UserSaveAction called");
		
		if( userSession == null){
			logger.warn( "UserSaveAction called without userSession");
			return null;
		}
		
		UserEditForm userEditForm = (UserEditForm) form;
		
		ActionErrors errors	= new ActionErrors();
		
		if( !Utils.nullOrBlank( userEditForm.getOldPassword())){
			Criteria criteria = new Criteria();
			criteria.addEqualTo( "password", Utils.digest(userEditForm.getOldPassword()));
			criteria.addEqualTo( "id", userSession.getUser().getId());
			if (userDAO.getUserByCriteria(criteria) == null){
				errors.add( "oldPassword",new ActionError( "error.oldPassword.valid"));
			}
		
			
			if( !Utils.nullOrBlank( userEditForm.getPassword())){
				if( !Validate.passwordLong( userEditForm.getPassword())){
					errors.add( "password",new ActionError( "error.password.long"));
				} else{
					if( !Validate.passwordShort( userEditForm.getPassword())){
						errors.add( "password",new ActionError( "error.password.short"));
					}
				}
				
				if( !Validate.required( userEditForm.getPassword2())){
					errors.add( "password2",new ActionError( "error.password2.required"));
				} else{
					if( !userEditForm.getPassword().equals( userEditForm.getPassword2())){
						errors.add( "password2",new ActionError( "error.password2.equals"));
					}
				}
			}
		}
		
		if( userEditForm.getAutoPay()){
			if( !Validate.required( userEditForm.getBitcoinAddress())){
				errors.add( "bitcoinAddress", new ActionError( "error.bitcoinaddress.required"));
			} else{
				if( !Validate.bitcoinAddressShort( userEditForm.getBitcoinAddress())){
					errors.add( "bitcoinAddress", new ActionError( "error.bitcoinaddress.short"));
				} else{
					if( !Validate.bitcoinAddressLong( userEditForm.getBitcoinAddress())){
						errors.add( "bitcoinAddress", new ActionError( "error.bitcoinaddress.long"));
					} 
				}
			}
		}
		
		if( !errors.isEmpty()){
			saveErrors	( request, errors);
			return mapping.getInputForward();
		}
		
		User user = userDAO.getUserById( userSession.getUser().getId());
		
		user.setModified( Utils.today());
		
		user.setBitcoinSentSubscribed( userEditForm.getBitcoinSentSubscribed());
		user.setRequestPrizeSubscribed( userEditForm.getRequestPrizeSubscribed());
		user.setAutoPay( userEditForm.getAutoPay());
		
		
		
		if( userEditForm.getAutoPay()){
			user.setBitcoinAddress( userEditForm.getBitcoinAddress());
		} else{
			user.setBitcoinAddress( null);
		}
		
		if( !Utils.nullOrBlank(userEditForm.getOldPassword()) && !Utils.nullOrBlank(userEditForm.getPassword())){
			user.setPassword( Utils.digest(userEditForm.getPassword()));
		}
		
		logger.info( "UserSaveAction updated user " + user.getLogin());
		
		userDAO.setUser( user);
		
		return mapping.findForward("ok");
	}
	
	
}		
