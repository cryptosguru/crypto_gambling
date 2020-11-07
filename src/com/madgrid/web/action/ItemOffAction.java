package com.madgrid.web.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.GridDAO;
import com.madgrid.dao.ItemDAO;
import com.madgrid.model.Grid;
import com.madgrid.model.Item;

public class ItemOffAction extends AbstractAction {
	
	public static final Logger logger = Logger.getLogger(ItemOffAction.class);

	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		ItemDAO itemDAO = new ItemDAO();
		GridDAO gridDAO = new GridDAO();
		String id = request.getParameter( "id");
		Integer itemId = Integer.parseInt( id);
		Item item = itemDAO.getItemById( itemId);

		logger.info( "ItemNoAction called with queryString" + request.getQueryString() + " (" + request.getPathTranslated() + ")");
		
		Criteria criteria = new Criteria();
		criteria.addEqualTo( "finished", false);
		criteria.addEqualTo( "ongoing", true);
		List<Grid> gridList = gridDAO.getGridListByCriteriaAndRange(criteria, "startDate", 0, 6);
		
		Collections.sort( gridList, new Comparator<Grid>() {
			public int compare( Grid g1, Grid g2) {
				if( g1.getStartDate().before(g2.getStartDate())) return -1;
				if( g1.getStartDate().after(g2.getStartDate())) return 1;
				return 0;
			}
		});
		   
		request.setAttribute( "item", item);
		request.setAttribute( "gridList", gridList);
		
		return mapping.findForward( "ok");
	}
}		
