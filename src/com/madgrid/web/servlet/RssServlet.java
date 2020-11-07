package com.madgrid.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.ojb.broker.query.Criteria;

import com.madgrid.dao.GridDAO;
import com.madgrid.model.Grid;
import com.madgrid.web.util.Utils;

public class RssServlet extends HttpServlet {
	
	
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
    		criteria.addEqualTo( "finished", false);
    		List<Grid> gridList = gridDAO.getGridListByCriteriaAndRange(criteria, "-startDate", 0, 15);
    		
    		String baseUrl = Utils.getBaseUrl();
    			
    		Collections.sort( gridList, new Comparator<Grid>() {
    			public int compare( Grid g1, Grid g2) {
    				if( g1.getStartDate().before(g2.getStartDate())) return -1;
    				if( g1.getStartDate().after(g2.getStartDate())) return 1;
    				return 0;
    			}
    		});
    		
    		GregorianCalendar today = new GregorianCalendar();
    		today.setTime( Utils.today());
    		today.set( GregorianCalendar.MINUTE, 0);
    		today.set( GregorianCalendar.SECOND, 0);
    		today.set( GregorianCalendar.MILLISECOND, 0);
    		String out = "";

    		out = out + "<?xml version=\"1.0\"?>\r\n"; 
    		out = out + "<rss version=\"2.0\">\r\n";
    		out = out + "<channel>\r\n";
    		out = out + "<title>Instantri.ch</title>\r\n"; 
    		out = out + "<link>"+baseUrl+"</link>\r\n";
    		out = out + "<description>Win prizes opening boxes.</description>\r\n"; 
    		out = out + "<pubDate>"+ Utils.today().toGMTString() +"</pubDate>\r\n";
    		for(Grid grid: gridList){
	    		out = out + "<item>\r\n";
	    		out = out + "<title>" +  StringEscapeUtils.escapeXml(grid.getItem().getName()) + " ("+new SimpleDateFormat ( "dd-MM-yyyy").format( grid.getCreated())+")</title>\r\n"; 
	    		out = out + "<link>"+baseUrl+ grid.getVirtualPath() + "?d="+ new SimpleDateFormat ( "dd_MM_yyyy").format( grid.getCreated())+"</link>\r\n"; 
	    		out = out + "<description>\r\n";
	    		out = out + "&lt;img src='"+baseUrl+"/img/item/"+grid.getItem().getPicture1Url()+"'&gt;&lt;br/&gt;\r\n";
	    		out = out + "&lt;strong&gt;Game type:&lt;/strong&gt; \r\n";
	    		switch(grid.getType()){
	    			case Grid.GRID_TYPE_REGULAR: out = out + "Normal";break;
	    			case Grid.GRID_TYPE_BEGINNER: out = out + "For beginners";break;
	    			case Grid.GRID_TYPE_WINNER_IS_FIRST: out = out + "Fast";break;
	    			case Grid.GRID_TYPE_FIXED_PRICE: out = out + "Fixed price";break;
	    			case Grid.GRID_TYPE_DOUBLE_OR_NOTHING: out = out + "Double or nothing";break;
	    			case Grid.GRID_TYPE_FREE: out = out + "Free";break;
	    			case Grid.GRID_TYPE_MULTIPRIZE: out = out + "Multiprize";break;
	    		}
	    		out = out + "&lt;br/&gt;\r\n";
	    		out = out + "&lt;strong&gt;Boxes:&lt;/strong&gt; " + grid.getBoxes() + "&lt;br/&gt;\r\n";
	    		out = out + "&lt;strong&gt;Opening box price:&lt;/strong&gt; " + grid.getBoxPrice() + " credit&lt;br/&gt;\r\n";
	    		out = out + "&lt;br/&gt;\r\n";
	    		out = out +  StringEscapeUtils.escapeXml(grid.getItem().getHtmlDescription()) + "\r\n";
	    		out = out + "</description>\r\n"; 
	    		out = out + "<pubDate>" + grid.getCreated().toGMTString() + "</pubDate>\r\n";
	    		out = out + "</item>\r\n";
    		}
    		out = out + "</channel>\r\n"; 
    		out = out + "</rss>\r\n";
    		
			response.getWriter().println( out);
    	}
    }
}
