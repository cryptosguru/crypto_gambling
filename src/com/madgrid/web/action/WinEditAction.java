package com.madgrid.web.action;

import java.util.List;

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
import com.madgrid.web.util.UserSession;

public class WinEditAction extends AbstractAction {
	
	public static final Logger logger = Logger.getLogger(WinEditAction.class);

	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserSession userSession = super.checkUserSession(request);
		WinDAO winDAO = new WinDAO();
		Win previousWin = null;
		
		logger.info( "WinEditAction called");
		
		if( userSession == null){
			logger.warn( "WinEditAction called without userSession");
			return mapping.findForward("session");
		}
		
		String id = request.getParameter( "id");
		Integer winId = Integer.parseInt( id);
		Criteria criteria = new Criteria();
		criteria.addEqualTo("userId", userSession.getUser().getId());
		criteria.addEqualTo("id", winId);
		
		Win win = winDAO.getWinByCriteria(criteria);
		WinEditForm winEditForm = new WinEditForm();
		
		UserDAO userDAO = new UserDAO();
		User user = userDAO.getUserById(userSession.getUser().getId());
		
		if( user.getIsFraudulent()){
			return mapping.findForward("fraudulent");
		}
		
		
		if( win.getItem().getType() == Item.ITEM_TYPE_ONLY_BITCOINS || win.getItem().getType() == Item.ITEM_TYPE_CREDITS_AND_BITCOINS){
			criteria = new Criteria();
			criteria.addEqualTo("userId", userSession.getUser().getId());
			criteria.addEqualTo("deliveryRequested", true);
			criteria.addNotNull("bitcoinAddress");
			List<Win> previousWinList = winDAO.getWinListByCriteria(criteria, "created");
	
			if( previousWinList != null && previousWinList.size() > 0){
				previousWin = previousWinList.get( 0);
			}
	
			if( win.getUserId().intValue() == userSession.getUser().getId().intValue()){
				winEditForm.setId(win.getId());
				if( previousWin != null){
					winEditForm.setBitcoinAddress( previousWin.getBitcoinAddress());
				}
			}
			
			request.setAttribute( "winEditForm", winEditForm);
			return mapping.findForward("bitcoin");
		} 
		
		return null;
	}
}		
