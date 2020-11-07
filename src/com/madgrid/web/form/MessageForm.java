package com.madgrid.web.form;

import java.io.Serializable;

import org.apache.struts.validator.ValidatorForm;

public class MessageForm extends ValidatorForm implements Serializable{

	private String messageType;
	private String subject;
	private String email;
	private String text;
	
	
	
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
