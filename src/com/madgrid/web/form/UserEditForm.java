package com.madgrid.web.form;

import java.io.Serializable;

import org.apache.struts.validator.ValidatorForm;

public class UserEditForm extends ValidatorForm implements Serializable{

	private Integer id;
	
	private String login;
	private String oldPassword;
	private String password;
	private String password2;
	private String email;
	private Boolean requestPrizeSubscribed;
	private Boolean bitcoinSentSubscribed;
	private Boolean autoPay;
	private String bitcoinAddress;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public Boolean getRequestPrizeSubscribed() {
		return requestPrizeSubscribed;
	}
	public void setRequestPrizeSubscribed(Boolean requestPrizeSubscribed) {
		this.requestPrizeSubscribed = requestPrizeSubscribed;
	}
	public Boolean getBitcoinSentSubscribed() {
		return bitcoinSentSubscribed;
	}
	public void setBitcoinSentSubscribed(Boolean bitcoinSentSubscribed) {
		this.bitcoinSentSubscribed = bitcoinSentSubscribed;
	}
	public Boolean getAutoPay() {
		return autoPay;
	}
	public void setAutoPay(Boolean autoPay) {
		this.autoPay = autoPay;
	}
	public String getBitcoinAddress() {
		return bitcoinAddress;
	}
	public void setBitcoinAddress(String bitcoinAddress) {
		this.bitcoinAddress = bitcoinAddress;
	}

	
	
	
	
}
