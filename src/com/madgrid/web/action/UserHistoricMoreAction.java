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
import com.madgrid.dao.UserHistoricDAO;
import com.madgrid.model.UserHistoric;
import com.madgrid.web.util.UserSession;

public class UserHistoricMoreAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(UserHistoricMoreAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		UserSession userSession = super.checkUserSession(request);
		logger.info( "UserHistoricMoreAction called");
		
		String lastDateString = request.getParameter("lastDate");
		Date lastDate = null;
		try{
			lastDate = Utils.parseDateAndTime(lastDateString);
		} catch (Exception e) {
			lastDate = null;
		}
		
		DecimalFormat numberFormat = new DecimalFormat("#.###");
		
		if( lastDate!= null){
			UserHistoricDAO userHistoricDAO = new UserHistoricDAO();
	    	Criteria criteria = new Criteria();
	    	criteria.addEqualTo("userId", userSession.getUser().getId());
	    	criteria.addLessThan( "created", lastDate);
	    	
	    	List<UserHistoric> userHistoricList = userHistoricDAO.getUserHistoricListByCriteriaAndRange( criteria, "created", 0, 100);
	    	
	    	Collections.sort( userHistoricList, new Comparator<UserHistoric>() {
	    		public int compare( UserHistoric g1, UserHistoric g2) {
					if( g1.getCreated().before(g2.getCreated())) return 1;
		            if( g1.getCreated().after(g2.getCreated())) return -1;
		            return g2.getType().compareTo(g1.getType());
		        }
	  	   });
		
			String out = "";
			String newLastDate = "end";
			
			Locale locale =  new Locale("en", "US");
			int index = 0;
			boolean isLastPage = false;
			boolean isLastItem = false;
			
			if( userHistoricList.size() < 100){
				isLastPage = true;
			}
			
			for( UserHistoric userHistoric:userHistoricList){
				if( index == userHistoricList.size() - 1){
					isLastItem = true;
				}
				if( index % 2 == 0){
					if( isLastPage && isLastItem){
						out = out + "<li style='background-color:#DDD;border-bottom-left-radius: 10px;border-bottom-right-radius: 10px;padding:10px;'>";
					} else{
						out = out + "<li style='background-color:#DDD;padding:10px;'>";
					}
				}
				if( index % 2 == 1){
					if( isLastPage && isLastItem){
						out = out + "<li style='background-color:#CCC;border-bottom-left-radius: 10px;border-bottom-right-radius: 10px;padding:10px;'>";
					} else{
						out = out + "<li style='background-color:#CCC;padding:10px;'>";
					}
				}
				
				
	
				out = out + "<table>";
				out = out + "<tr>";
				out = out + "<td class='date' style='vertical-align: middle;'>";
				out = out + new SimpleDateFormat ( "dd/MM/yyyy HH:mm:ss",locale).format( userHistoric.getCreated());
				out = out + "</td>";
				out = out + "<td style='vertical-align: middle;width:50px;'>";
						
				if( userHistoric.getType() == UserHistoric.BUY_CREDITS){
					out = out + "<img src='/img/qr.png' height='32'>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDITS_BY_PROMO){
				out = out + "<img src='/img/ico_creditos.png' height='32'>";
				}
				if( userHistoric.getType() == UserHistoric.BUY_BOX){
					out = out + "<img src='/img/sorteo_caja_abierta.png' height='32'>";
				}
				if( userHistoric.getType() == UserHistoric.PARTIAL_WIN){
					out = out + "<img src='/img/sorteo_caja_premiada.png' height='32'>";
				}
				if( userHistoric.getType() == UserHistoric.FINAL_WIN){
					out = out + "<img src='/img/banderola_ico_sorteo_standard.png' height='32'>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_BY_VALIDATION){
					out = out + "<img src='/img/mail_icon.png' height='32'>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_BY_AFFILIATE){
					out = out + "<img src='/img/ico_creditos.png' height='32'>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_BY_REFUND){
					out = out + "<img src='/img/ico_creditos_refund.png' height='32'>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_BY_ADMIN_MORE){
					out = out + "<img src='/img/plus_icon.png' height='32'>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_BY_ADMIN_LESS){
					out = out + "<img src='/img/minus_icon.png' height='32'>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_IN_MULTIPRIZE){
					if( userHistoric.getValue1() == 1d){
						out = out + "<img src='/img/sorteo_caja_premiada_1.png' height='32'>";
					}
					if( userHistoric.getValue1() == 2d){
						out = out + "<img src='/img/sorteo_caja_premiada_2.png' height='32'>";
					}
					if( userHistoric.getValue1() == 5d){
						out = out + "<img src='/img/sorteo_caja_premiada_5.png' height='32'>";
					}
					if( userHistoric.getValue1() == 10d){
						out = out + "<img src='/img/sorteo_caja_premiada_10.png' height='32'>";
					}
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_FOR_TWEET){
					out = out + "<img src='/img/ico_twitter.png' height='32'>";
				}
			
				out = out + "</td>";
				out = out + "<td class='text' style='vertical-align: middle;'>";
			
				
				
				if( userHistoric.getType() == UserHistoric.BUY_CREDITS){
					out = out + "<span style='color:darkred;'>You bought <strong>"+userHistoric.getValue1().intValue()+"</strong> credits</span>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDITS_BY_PROMO){
					out = out + "<span style='color:darkgreen;'>You got <strong>"+userHistoric.getValue1().intValue()+"</strong> credits for promotion <strong>"+userHistoric.getValue2()+"</strong></span>";
				}
				if( userHistoric.getType() == UserHistoric.BUY_BOX){
					out = out + "<span style='color:navy;'>You opened box <strong>"+userHistoric.getValue2()+"</strong> in game <strong>"+userHistoric.getValue3()+"</strong> for <strong>"+userHistoric.getValue1().intValue()+"</strong> ";
					if( userHistoric.getValue1() == 1d){
						out = out + "credit";
					} else{
						out = out + "credit2";
					}
					out = out + "</span>";
				}
				if( userHistoric.getType() == UserHistoric.PARTIAL_WIN){
					out = out + "<span style='color:darkgoldenrod;'>You found the prize <strong>"+userHistoric.getValue3()+"</strong> in box <strong>"+userHistoric.getValue2()+"</strong></span>";
				}
				if( userHistoric.getType() == UserHistoric.FINAL_WIN){
					out = out + "<span style='color:MediumVioletRed;'>You got the prize <strong>"+userHistoric.getValue3()+"</strong></span>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_BY_VALIDATION){
					out = out + "<span style='color:MediumVioletRed;'>For validating your email account you got <strong>"+userHistoric.getValue1().intValue()+" credit</strong></span>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_BY_AFFILIATE){
					out = out + "<span style='color:MediumVioletRed;'>For an affiliated user you got <strong>"+userHistoric.getValue1().intValue()+" credit</strong></span>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_BY_REFUND){
					out = out + "<span style='color:MediumVioletRed;'>You've got a "+userHistoric.getValue1().intValue()+" credits refund because nobody won the <strong>"+userHistoric.getValue2()+" game</strong></span>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_BY_ADMIN_MORE){
					out = out + "<span style='color:MediumVioletRed;'>Instantri.ch admin added <strong>"+userHistoric.getValue1().intValue()+" credits</strong> to your account</span>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_BY_ADMIN_LESS){
					out = out + "<span style='color:MediumVioletRed;'>Instantri.ch admin removed <strong>"+userHistoric.getValue1().intValue()+" credits</strong> from your account</span>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_IN_MULTIPRIZE){
					out = out + "<span style='color:MediumVioletRed;'>You won <strong>"+userHistoric.getValue1().intValue()+" credits</strong> hidden in box "+userHistoric.getValue2()+" of the "+userHistoric.getValue3()+" game</span>";
				}
				if( userHistoric.getType() == UserHistoric.GET_CREDIT_FOR_TWEET){
					out = out + "<span style='color:MediumVioletRed;'>We gave you <strong>1 credit</strong> for your tweet";
				}
				
				out = out + "</td>";
				out = out + "</tr>";
				out = out + "</table>";
				out = out + "</li>";
	

				index++;
		    	newLastDate = new SimpleDateFormat ( "dd/MM/yyyy HH:mm:ss").format( userHistoric.getCreated());
			}
			if( userHistoricList.size()< 100){
				newLastDate = "end";
			}
			out = out + "@" + newLastDate;
			
			response.getWriter().print(out);
		}
		
		return null;
		
	}
}		
