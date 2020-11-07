package com.madgrid.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ojb.broker.query.Criteria;

import com.madgrid.dao.GridDAO;
import com.madgrid.dao.ItemDAO;
import com.madgrid.model.Grid;
import com.madgrid.model.Item;

public class VirtualPathServlet extends HttpServlet {
	
	
	public void init(ServletConfig arg0) throws ServletException {
		super.init(arg0);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
		try {
			forward(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
    	try {
			forward(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void forward(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	String uri=request.getRequestURI().toString();
        String contextPath = request.getContextPath();
        if (contextPath != null && contextPath.length() > 0) {
            uri = uri.substring(contextPath.length());
        }
    	
    	if(uri!= null){
    		GridDAO gridDAO = new GridDAO();
	    	Criteria criteria = new Criteria();
	    	criteria.addEqualTo( "virtualPath", uri);
	    	String out = "";
	    	
	    	Grid grid = gridDAO.getGridByCriteria( criteria);
	    	if( grid != null){
	    		out="/do/item?id="+grid.getId();
	    	} else{
	    		ItemDAO itemDAO = new ItemDAO();
	    		Item item = itemDAO.getItemByCriteria( criteria);
	    		if( item != null){
	    			out="/do/itemOff?id="+item.getId();
	    		} else{
	    			out="/do/itemNo";
	    		}
	    	}

	    	getServletConfig().getServletContext().getRequestDispatcher(out).forward(request, response);
    	}
    }
}
