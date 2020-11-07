package com.madgrid.web.util.quartz;

import java.util.GregorianCalendar;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;

import com.madgrid.dao.UserDAO;
import com.madgrid.model.User;
import com.madgrid.web.util.Utils;
 
public class DeleteUserTask 
{
	public void deleteUser() {
		UserDAO userDAO = new UserDAO();
		GregorianCalendar today = new GregorianCalendar();
		today.setTime( Utils.today());
		
		try{
			Criteria criteria = new Criteria();
			criteria.addEqualTo("active", false);
			List<User> userList = userDAO.getUserListByCriteria(criteria);
			
			for( User user:userList){
				GregorianCalendar created = new GregorianCalendar();
				created.setTime( user.getCreated());
				created.add( GregorianCalendar.HOUR_OF_DAY, 24);
				if( created.before( today)){
					userDAO.deleteUser(user);
				}
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}