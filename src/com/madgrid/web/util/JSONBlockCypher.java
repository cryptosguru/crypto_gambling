package com.madgrid.web.util;




public class JSONBlockCypher {
	
	
		
	private String id;
	private String token;
	private String destination;
	private String input_address;
	private String callback_url;
	
	public JSONBlockCypher(String id, String token, String destination,String input_address,String callback_url) 
	{
		this.id = id;
		this.token = token;
		this.destination = destination;
		this.input_address = input_address;
		this.callback_url = callback_url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getInput_address() {
		return input_address;
	}

	public void setInput_address(String input_address) {
		this.input_address = input_address;
	}

	public String getCallback_url() {
		return callback_url;
	}

	public void setCallback_url(String callback_url) {
		this.callback_url = callback_url;
	}

	
		
	

 
	
    
     
}