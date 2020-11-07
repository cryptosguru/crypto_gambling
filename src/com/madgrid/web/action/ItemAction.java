package com.madgrid.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.GridDAO;
import com.madgrid.model.Grid;

public class ItemAction extends AbstractAction {

	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		GridDAO gridDAO = new GridDAO();
		
		String id = request.getParameter( "id");
		
		Integer gridId = Integer.parseInt( id);
		
		Grid grid = gridDAO.getGridById( gridId);
		
		request.setAttribute( "grid", grid);
		
		return mapping.findForward( "ok");
	}
}		
