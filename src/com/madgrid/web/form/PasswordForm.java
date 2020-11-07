package com.madgrid.web.form;

import java.io.Serializable;

import org.apache.struts.validator.ValidatorForm;

public class PasswordForm extends ValidatorForm implements Serializable{

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
