package com.madgrid.web.util;

import java.io.Serializable;
import java.util.Date;

import com.madgrid.model.User;

public class UserSession implements Serializable{

	private User user;
	private Date loggedIn;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(Date loggedIn) {
		this.loggedIn = loggedIn;
	}

	
}
