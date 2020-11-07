package com.madgrid.web.form;

import java.io.Serializable;

import org.apache.struts.validator.ValidatorForm;

public class CreditsForm extends ValidatorForm implements Serializable{

	private String euros;
	private String promoCode;
	private String paymentMethod;

	public String getEuros() {
		return euros;
	}

	public void setEuros(String euros) {
		this.euros = euros;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
}
