package com.madgrid.web.action;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.BoxDAO;
import com.madgrid.dao.GridDAO;
import com.madgrid.dao.UserDAO;
import com.madgrid.model.Box;
import com.madgrid.model.Grid;
import com.madgrid.model.User;
import com.madgrid.web.util.UserSession;
import com.madgrid.web.util.Utils;
import com.madgrid.web.util.dwr.DwrTools;

public class BoxBuyAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(CreditsBuyAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		UserSession userSession = super.checkUserSession(request);
		UserDAO userDAO = new UserDAO();
		GridDAO gridDAO = new GridDAO();
		BoxDAO boxDAO = new BoxDAO();
		
		logger.info( "BoxBuyAction called");
		
		
		if( userSession == null){
			String out = "";
			out = out + "4#";
			response.getWriter().println( out);
			logger.warn( "BoxBuyAction without userSession");
			return null;
		
		}else {
			String g = request.getParameter( "g");
			String p = request.getParameter( "p");
			String b = request.getParameter( "b");
			
			
			p = p.replace("<font>", "");
			p = p.replace("</font>", "");
			p = p.trim();
			
			try{
				Grid grid = gridDAO.getGridById( Integer.parseInt( g));
				User user = userDAO.getUserById( userSession.getUser().getId());
				Double boxPrice = Double.parseDouble( p);
				
				if( user == null || !user.getActive() || user.getIsFraudulent()){
					String out = "a#";
					
					response.getWriter().println( out);
					return null;
				}
				
				
				if( grid.getBoxPrice().doubleValue() != boxPrice.doubleValue()){
					String out = "1#" + new DecimalFormat( "0").format(grid.getBoxPrice());
					
					response.getWriter().println( out);
					return null;
				}
				
				if ( grid.getBoxPrice().intValue() > user.getCredits()){ 
					String out = "2#";
					
					response.getWriter().println( out);
					return null;
				}
				
				if ( grid.getType().intValue() == Grid.GRID_TYPE_BEGINNER && !user.getIsBeginner()){ 
					String out = "6#";
					response.getWriter().println( out);
					return null;
				}
				
				if ( grid.getType().intValue() == Grid.GRID_TYPE_FREE){
					Criteria criteria = new Criteria();
					criteria.addEqualTo("id", user.getId());
					criteria.addEqualTo("validated", true);
					if(userDAO.getUserByCriteria(criteria) == null){
						String out = "9#";
						response.getWriter().println( out);
						return null;
					}
					
					criteria = new Criteria();
					criteria.addEqualTo("gridId", grid.getId());
					criteria.addEqualTo("userId", user.getId());
					if(boxDAO.getBoxByCriteria(criteria) != null){
						String out = "8#";
						response.getWriter().println( out);
						return null;
					}
				}
				
				if( grid.getIsInPartialWin()){
					long timeLeft = Utils.today().getTime() - grid.getPartialWinStartTime().getTime();
					timeLeft = grid.getPartialWinSeconds() - (timeLeft/1000);
					if( timeLeft <= 0){
						String out = "3#";
						response.getWriter().println( out);
						return null;
					} else{
						if( user.getId().intValue() == grid.getPartialWinUserId().intValue()){
							String out = "5#";
							response.getWriter().println( out);
							return null;
						}
					}
				} 
				
				if(grid.getFinished() || !grid.getOngoing()){
					String out = "3#";
					response.getWriter().println( out);
					return null;
				}
					
					
				user.setCredits( user.getCredits() - grid.getBoxPrice().intValue());
				userDAO.setUser( user);
				userSession.setUser(user);
				request.getSession().setAttribute( "userSession", userSession);
				
				synchronized(this) {
					DwrTools dwrTools = DwrTools.getInstance();
					Box box = null;
					if( !b.equals("-1")){
						Integer boxPos = Integer.parseInt( b);
						Criteria criteria = new Criteria();
						criteria.addEqualTo("gridId", grid.getId());
						criteria.addEqualTo("pos", boxPos);
						criteria.addEqualTo("type", Box.TYPE_FREE);
						box = boxDAO.getBoxByCriteria( criteria);
						
						if( box == null){
							String out = "";
							out = out + "7#";
							response.getWriter().println( out);
							return null;
						}
					}
					
					String out = "";
					out = out + "0#"+user.getCredits();
					response.getWriter().println( out);
				
					String hash = Utils.digest( "6SjeTkd8n" + user.getEmail() + grid.getId() + grid.getItem().getId() + (box == null ? "null":box.getId()));
					
					dwrTools.buyBoxRegularGrid( user, grid, box, hash);
			    }
				
				return null;
					
				
			}catch (Exception e){
				e.printStackTrace();
				return null;
			}
		}
	}
}		
