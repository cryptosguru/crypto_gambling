package com.madgrid.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.BlockcypherDAO;
import com.madgrid.dao.UserDAO;
import com.madgrid.model.Blockcypher;
import com.madgrid.model.User;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;

public class CheckPaidAction extends AbstractAction {

	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserSession userSession = super.checkUserSession(request);
		UserDAO userDAO = new UserDAO();
		BlockcypherDAO blockcypherDAO = new BlockcypherDAO();
		
		if( userSession != null){
			String idString = request.getParameter( "id");
			String address = request.getParameter( "address");
			
			if( !Utils.nullOrBlank( idString) && !Utils.nullOrBlank(address)){
				
				Integer id = null;
				try{
					id = Integer.parseInt( idString);
				} catch (NumberFormatException e) {
					id= null;
				}
				
				if( id != null){
				
					User user = userDAO.getUserById( id);
					if( user != null && user.getId().intValue() == userSession.getUser().getId().intValue()){
						
						Criteria criteria = new Criteria();
						criteria.addEqualTo("userId", user.getId());
						criteria.addEqualTo("address", address);
						criteria.addEqualTo("received", true);
						Blockcypher blockcypher = blockcypherDAO.getBlockcypherByCriteria(criteria);
						
						if( blockcypher != null){
							response.getWriter().println( "ok#"+user.getCredits());
							return null;
						}
					} 
				}
			}
		}
		
		response.getWriter().println( "ko#0");
		return null;
	}
}		
