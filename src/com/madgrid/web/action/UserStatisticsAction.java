package com.madgrid.web.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.ojb.broker.query.Criteria;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.madgrid.dao.AffiliationActivityDAO;
import com.madgrid.dao.AffiliationUserDAO;
import com.madgrid.dao.BitcoinPaymentDAO;
import com.madgrid.dao.UserDAO;
import com.madgrid.dao.UserHistoricDAO;
import com.madgrid.dao.WinDAO;
import com.madgrid.model.AffiliationActivity;
import com.madgrid.model.AffiliationUser;
import com.madgrid.model.BitcoinPayment;
import com.madgrid.model.User;
import com.madgrid.model.UserHistoric;
import com.madgrid.model.Win;
import com.madgrid.web.util.UserSession;

public class UserStatisticsAction extends AbstractAction {

	public static final Logger logger = Logger.getLogger(UserStatisticsAction.class);
	
	public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		UserSession userSession = super.checkUserSession(request);
		UserDAO userDAO = new UserDAO();
		UserHistoricDAO userHistoricDAO = new UserHistoricDAO();
		
		logger.info( "UserStatisticsAction called");
		
		if( userSession == null){
			logger.warn( "UserStatisticsAction called withou userSession");
			return null;
		}
		
		User user = userDAO.getUserById(userSession.getUser().getId());

		int boughtBoxes = 0;
		int usedCredits = 0;
		
		double btcSpent = 0d;
		double btcWon = 0;
		
		Criteria criteria = new Criteria();
		criteria.addEqualTo("userId", userSession.getUser().getId());
		
		List<UserHistoric> userHistoricList = userHistoricDAO.getUserHistoricListByCriteria(criteria, null);
		Set<String> gameSet = new HashSet<String>();
		
		
		for( UserHistoric userHistoric:userHistoricList){
			if(userHistoric.getType() == UserHistoric.BUY_BOX){
				boughtBoxes = boughtBoxes + 1;
			}
			
			if(userHistoric.getType() == UserHistoric.BUY_BOX){
				usedCredits = usedCredits + userHistoric.getValue1().intValue();
			}
			
			gameSet.add( userHistoric.getValue3());
		}
		
		
		
		//Calculo de bt gastados y ganados
		criteria = new Criteria();
		criteria.addEqualTo( "userId", user.getId());
		BitcoinPaymentDAO bitcoinPaymentDAO = new BitcoinPaymentDAO();
		List<BitcoinPayment> bitcoinPaymentList = bitcoinPaymentDAO.getBitcoinPaymentListByCriteria(criteria, "id");
		double bitcoinSpent = 0d;
		for( BitcoinPayment bitcoinPayment:bitcoinPaymentList){
			bitcoinSpent = bitcoinSpent + (bitcoinPayment.getBitcoins() / 100000000d);
		}
		
		double bitcoinWon = 0d;
		WinDAO winDAO = new WinDAO();
		criteria = new Criteria();
		criteria.addEqualTo( "userId", user.getId());
		List<Win> winList = (List<Win>)winDAO.getWinListByCriteria(criteria, null);
		for( Win win:winList){
			if( !win.getIsOnlyCredits() && win.getItem().getBitcoins() != null && win.getItem().getBitcoins().doubleValue() != 0){
				bitcoinWon = bitcoinWon + win.getItem().getBitcoins();
			}
		}
		
		//get affiliated users
		AffiliationUserDAO affiliationUserDAO = new AffiliationUserDAO();
		criteria = new Criteria();
		criteria.addEqualTo("login", userSession.getUser().getLogin());
		AffiliationUser affiliationUser = affiliationUserDAO.getAffiliationUserByCriteria(criteria);
		if( affiliationUser != null){
			AffiliationActivityDAO affiliationActivityDAO = new AffiliationActivityDAO();
			criteria = new Criteria();
			criteria.addEqualTo("affiliationUserId", affiliationUser.getId());
			criteria.addEqualTo("type", AffiliationActivity.AFFILIATION_ACTIVITY_USER_REGISTRATION);
			Integer affiliationActivityCount = new Integer(affiliationActivityDAO.getAffiliationActivityCountByCriteria(criteria));
			request.setAttribute( "affiliatedUsers", affiliationActivityCount);
			request.setAttribute( "isAffiliationUser", true);
		} else{
			request.setAttribute( "affiliatedUsers", 0);
			request.setAttribute( "isAffiliationUser", false);
		}
		
		request.setAttribute( "bitcoinSpent", bitcoinSpent);
		request.setAttribute( "bitcoinWon", bitcoinWon);
		
		
		request.setAttribute( "boughtBoxes", boughtBoxes);
		request.setAttribute( "playedGames", user.getStatisticsPlayedGames());
		request.setAttribute( "usedCredits", usedCredits);
		request.setAttribute( "wonGames", user.getStatisticsWonGames());
		
		request.setAttribute( "isFraudulent", user.getIsFraudulent());
		request.setAttribute( "isBeginner", user.getIsBeginner());
		request.setAttribute( "isValidated", user.getValidated());
		
		return mapping.findForward("ok");
	}
}		
