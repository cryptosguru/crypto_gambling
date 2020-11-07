package com.madgrid.web.action;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Comparator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.admin.util.Utils;
import com.madgrid.dao.WinDAO;
import com.madgrid.model.Grid;
import com.madgrid.model.Win;
import com.madgrid.web.util.UserSession;

public class WinnersSeeMoreAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(WinnersSeeMoreAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		UserSession userSession = super.checkUserSession(request);
		logger.info( "WinnersSeeMoreAction called");
		
		String lastDateString = request.getParameter("lastDate");
		Date lastDate = null;
		try{
			lastDate = Utils.parseDateAndTime(lastDateString);
		} catch (Exception e) {
			lastDate = null;
		}
		
		DecimalFormat numberFormat = new DecimalFormat("#.###");
		
		if( lastDate!= null){
			WinDAO winDAO = new WinDAO();
	    	Criteria criteria = new Criteria();
	    	criteria.addLessThan( "created", lastDate);
	    	criteria.addEqualTo("isOnlyCredits", false);
	    	
	    	List<Win> winList = winDAO.getWinListByCriteriaAndRange( criteria, "created", 0, 24);
	    	
	    	Collections.sort( winList, new Comparator<Win>() {
	            public int compare( Win w1, Win w2) {
	                if( w1.getCreated().before(w2.getCreated())) return 1;
	                if( w1.getCreated().after(w2.getCreated())) return -1;
	                return w2.getUser().getLogin().compareTo(w1.getUser().getLogin());
	            }
	  	   });
		
			String out = "";
			String newLastDate = "end";
			
			Locale locale =  new Locale("en", "US");
			
			for( Win win:winList){
				out = out + "<li style='background: #FFFFFF url(/img/item/"+win.getItem().getPicture1Url()+") top left no-repeat; -moz-border-radius: 10px;border-radius: 10px;'>";
				
				out = out + "<div style='position: absolute;margin: 5px;' >";
        		if( win.getGridType() == Grid.GRID_TYPE_REGULAR){
        			out = out + "<img src='/img/banderola_ico_sorteo_standard.png' alt='Normal game' title='Normal game'>";
        		}
        		if( win.getGridType() == Grid.GRID_TYPE_BEGINNER){
        			out = out + "<img src='/img/banderola_ico_sorteo_principiantes.png' alt='Beginners game' title='Beginners game'>";
        		}
        		if( win.getGridType() == Grid.GRID_TYPE_WINNER_IS_FIRST){
        			out = out + "<img src='/img/banderola_ico_sorteo_primero.png' alt='Fast game' title='Fast game'>";
        		}
        		if( win.getGridType() == Grid.GRID_TYPE_FIXED_PRICE){
        			out = out + "<img src='/img/banderola_ico_sorteo_preciounico.png' alt='Fixed price game' title='Fixed price game'>";
        		}
        		if( win.getGridType() == Grid.GRID_TYPE_DOUBLE_OR_NOTHING){
        			out = out + "<img src='/img/banderola_ico_sorteo_conpistas.png' alt='Double or nothing game' title='Double or nothing game'>";
        		}
        		if( win.getGridType() == Grid.GRID_TYPE_FREE){
        			out = out + "<img src='/img/banderola_ico_sorteo_gratis.png' alt='Free game' title='Free game'>";
        		}
        		if( win.getGridType() == Grid.GRID_TYPE_MULTIPRIZE){
        			out = out + "<img src='/img/banderola_ico_sorteo_multipremio.png' alt='Multiprize game' title='Multiprize game'>";
        		}
        			
        		out = out + "</div>";
				
				out = out + "<span>";
					out = out + "<strong>";
		    		if( win.getItem().getName().equalsIgnoreCase("Treasure chest")){
		    			out = out +numberFormat.format( win.getItem().getBitcoins())+" Bitcoin";
		    			
		    		}
		    		
		    		if( !win.getItem().getName().equalsIgnoreCase("Treasure chest")){
		    			out = out + win.getItem().getName();
		    		}
		    		out = out + "</strong>";
		    		
		    		
		    		if( win.getGridType() == Grid.GRID_TYPE_REGULAR){
	        			out = out + " (Normal)";
	        		}
	        		if( win.getGridType() == Grid.GRID_TYPE_BEGINNER){
	        			out = out + " (Beginners)";
	        		}
	        		if( win.getGridType() == Grid.GRID_TYPE_WINNER_IS_FIRST){
	        			out = out + " (Fast)";
	        		}
	        		if( win.getGridType() == Grid.GRID_TYPE_FIXED_PRICE){
	        			out = out + " (Fixed price)";
	        		}
	        		if( win.getGridType() == Grid.GRID_TYPE_DOUBLE_OR_NOTHING){
	        			out = out + " (Double or nothing)";
	        		}
	        		if( win.getGridType() == Grid.GRID_TYPE_FREE){
	        			out = out + " (Free)";
	        		}
	        		if( win.getGridType() == Grid.GRID_TYPE_MULTIPRIZE){
	        			out = out + " (Multiprize)";
	        		}

		    		
		    		if( Utils.nullOrBlank(win.getTransactionId())){
		    			out = out + "<br/><br/>";
		        		out = out + "User: "+win.getUser().getLogin()+"<br/>";
		        		out = out + new SimpleDateFormat ( "yyyy-MMMM-dd HH:mm",locale).format( win.getCreated());
		    		} else{
		    			out = out + "</strong><br/>";
		    			out = out + "User: "+win.getUser().getLogin()+"<br/>";
		    			out = out + new SimpleDateFormat ( "yyyy-MMMM-dd HH:mm",locale).format( win.getCreated())+"<br/><br/>";
		        		out = out + "<a href='https://blockchain.info/tx/"+win.getTransactionId()+"' target='_blank'>Transaction (Blockchain)</a>";
		    		}
		    		
		    		
		    	out = out + "</span>";
		    	out = out + "</li>";
		    	newLastDate = new SimpleDateFormat ( "dd/MM/yyyy HH:mm:ss").format( win.getCreated());
			}
			if( winList.size()< 24){
				newLastDate = "end";
			}
			out = out + "@" + newLastDate;
			
			response.getWriter().print(out);
		}
		
		return null;
		
	}
}		
