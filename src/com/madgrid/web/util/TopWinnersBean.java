package com.madgrid.web.util;

import java.io.Serializable;

import com.madgrid.model.User;

public class TopWinnersBean implements Serializable{

	private User user;
	private double bitcoins;
	private int credits;
	
	
	
	
	public double getBitcoins() {
		return bitcoins;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setBitcoins(double bitcoins) {
		this.bitcoins = bitcoins;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
		
}
