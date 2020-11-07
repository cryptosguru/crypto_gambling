package com.madgrid.web.form;

import java.io.Serializable;

import org.apache.struts.validator.ValidatorForm;

public class UserRegisterForm extends ValidatorForm implements Serializable{

	private String login;
	private String password;
	private String password2;
	private String email;
	private String promoCode;
	private String jcaptcha;
	private String recomendedUser;
	private Boolean autoPay;
	private String bitcoinAddress;
	
	
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
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public String getJcaptcha() {
		return jcaptcha;
	}
	public void setJcaptcha(String jcaptcha) {
		this.jcaptcha = jcaptcha;
	}
	public String getRecomendedUser() {
		return recomendedUser;
	}
	public void setRecomendedUser(String recomendedUser) {
		this.recomendedUser = recomendedUser;
	}
	public String getBitcoinAddress() {
		return bitcoinAddress;
	}
	public void setBitcoinAddress(String bitcoinAddress) {
		this.bitcoinAddress = bitcoinAddress;
	}
	public Boolean getAutoPay() {
		return autoPay;
	}
	public void setAutoPay(Boolean autoPay) {
		this.autoPay = autoPay;
	}
	
	
	
}
