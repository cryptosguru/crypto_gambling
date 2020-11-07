package com.madgrid.web.form;

import java.io.Serializable;

import org.apache.struts.validator.ValidatorForm;

public class WinEditForm extends ValidatorForm implements Serializable{

	private Integer id;
	
	private String bitcoinAddress;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBitcoinAddress() {
		return bitcoinAddress;
	}
	public void setBitcoinAddress(String bitcoinAddress) {
		this.bitcoinAddress = bitcoinAddress;
	}
	
	
}
