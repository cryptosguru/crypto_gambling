package com.madgrid.web.util.mail;

import java.util.List;

import com.madgrid.model.User;
import com.madgrid.web.util.Utils;

public class UserRegisterForAdminMailObject implements MailObject {

	private String 	login;
	private String 	email;
	private String 	password;
	private String 	ip;
	private String 	baseUrl;
	private List<User> 	sameIpUserList;
	private List<User> 	samePasswordUserList;
	private List<User> 	sameBitcoinAddress;
	private boolean suspiciousEmail;
	private String rewardUser;
	private String affiliationUser;
	
	
	public UserRegisterForAdminMailObject( String login,  String email,  String password , String ip, List<User> sameIpUserList,List<User> samePasswordUserList, List<User> sameBitcoinAddress, boolean suspiciousEmail, String rewardUser, String affiliationUser, String baseUrl)
	{
		this.login = login;
		this.baseUrl = baseUrl;
		this.email = email;
		this.password = password;
		this.ip = ip;
		this.sameIpUserList = sameIpUserList;
		this.samePasswordUserList = samePasswordUserList;
		this.sameBitcoinAddress = sameBitcoinAddress;
		this.suspiciousEmail= suspiciousEmail;
		this.rewardUser = rewardUser;
		this.affiliationUser = affiliationUser;
		
		
	}

	public String toHtml()
	{
		StringBuffer result = new StringBuffer( "");
		
		result.append( "<html>");
		result.append( "<head>");
		result.append( "</head>");
		result.append( "<body>");
		result.append( "<table width='100%' cellpadding='0' cellspacing='0' border='0'>");
		result.append( "<tr>");
		result.append( "<td>");
		result.append( "<table width='100%' cellpadding='0' cellspacing='15' border='0'>");
		
		result.append( "<tr>");
		result.append( "<td align='center' style='font-family:arial; font-size:20;'>");
		result.append( "Hola Gus. Se ha registrado un usuario con estos datos");
		result.append( "</td>"); 
		result.append( "</tr>");
		
		result.append( "<tr>");
		result.append( "<td align='center' style='font-family:arial; font-size:20;'>");
		result.append( "Login: " + login);
		result.append( "</td>"); 
		result.append( "</tr>");
		result.append( "<tr>");
		result.append( "<td align='center' style='font-family:arial; font-size:20;'>");
		result.append( "Email: " + email);
		result.append( "</td>"); 
		result.append( "</tr>");
		result.append( "<tr>");
		result.append( "<td align='center' style='font-family:arial; font-size:20;'>");
		result.append( "ip: " + ip);
		result.append( "</td>"); 
		result.append( "</tr>");
		
		result.append( "<tr>");
		result.append( "<td align='center' style='font-family:arial; font-size:20;'>");
		result.append( "Login igual a password: " + (login.equalsIgnoreCase(password) ? "<span style='color:red'>SÍ</span>":"NO"));
		result.append( "</td>"); 
		result.append( "</tr>");
		
		if( sameIpUserList!= null && sameIpUserList.size() > 1){
			result.append( "<tr>");
			result.append( "<td align='center' style='font-family:arial; font-size:20;color:red;'>");
			result.append( "Otros con esa IP: " + sameIpUserList.size());
			result.append( "</td>"); 
			result.append( "</tr>");
			for( User userIp:sameIpUserList){
				if( !userIp.getLogin().equals( login)){
					result.append( "<tr>");
					result.append( "<td align='center' style='font-family:arial; font-size:20;padding-left:15px;'>");
					result.append( "<span style='font-weight:bold'>" + userIp.getLogin() + "</span>  ("+userIp.getEmail()+")  " + Utils.getDate( userIp.getCreated(), 5) + "  " +  Utils.getTime( userIp.getCreated())) ;
					if( userIp.getIsFraudulent()){
						result.append( "<br/><span style='color:red'>Fraudulento</span>") ;
					}if( !userIp.getValidated()){
						result.append( "<br/><span style='color:green'>No validado</span>") ;
					}
					result.append( "</td>"); 
					result.append( "</tr>");
				}
			}
		}
		
		if( samePasswordUserList!= null && samePasswordUserList.size() > 1){
			result.append( "<tr>");
			result.append( "<td align='center' style='font-family:arial; font-size:20;color:red;'>");
			result.append( "Otros con esa password: " + samePasswordUserList.size());
			result.append( "</td>"); 
			result.append( "</tr>");
			result.append( "<tr>");
			for( User userPassword:samePasswordUserList){
				if( !userPassword.getLogin().equals( login)){
					result.append( "<tr>");
					result.append( "<td align='center' style='font-family:arial; font-size:20;padding-left:15px;'>");
					result.append( "<span style='font-weight:bold'>" + userPassword.getLogin() + "</span>  ("+userPassword.getEmail()+")  " + Utils.getDate( userPassword.getCreated(), 5) + "  " +  Utils.getTime( userPassword.getCreated())) ;
					if( userPassword.getIsFraudulent()){
						result.append( "<br/><span style='color:red'>Fraudulento</span>") ;
					}
					if( !userPassword.getValidated()){
						result.append( "<br/><span style='color:green'>No validado</span>") ;
					}
					result.append( "</td>"); 
					result.append( "</tr>");
				}
			}
		}
		
		
		if( sameBitcoinAddress!= null && sameBitcoinAddress.size() > 1){
			result.append( "<tr>");
			result.append( "<td align='center' style='font-family:arial; font-size:20;color:red;'>");
			result.append( "Otros con direccion bitcoin: " + sameBitcoinAddress.size());
			result.append( "</td>"); 
			result.append( "</tr>");
			result.append( "<tr>");
			for( User userAddress:sameBitcoinAddress){
				if( !userAddress.getLogin().equals( login)){
					result.append( "<tr>");
					result.append( "<td align='center' style='font-family:arial; font-size:20;padding-left:15px;'>");
					result.append( "<span style='font-weight:bold'>" + userAddress.getLogin() + "</span>  ("+userAddress.getEmail()+")  " + Utils.getDate( userAddress.getCreated(), 5) + "  " +  Utils.getTime( userAddress.getCreated())) ;
					if( userAddress.getIsFraudulent()){
						result.append( "<br/><span style='color:red'>Fraudulento</span>") ;
					}
					if( !userAddress.getValidated()){
						result.append( "<br/><span style='color:green'>No validado</span>") ;
					}
					result.append( "</td>"); 
					result.append( "</tr>");
				}
			}
		}
		
		result.append( "<td align='center' style='font-family:arial; font-size:20;"+(suspiciousEmail?"color:red;":"")+"'>");
		result.append( "Email sospechoso: " + suspiciousEmail);
		result.append( "</td>"); 
		result.append( "</tr>");
		
		if( !Utils.nullOrBlank(rewardUser )){
			result.append( "<tr>");
			result.append( "<td align='center' style='font-family:arial; font-size:20;'>");
			result.append( "Programa recompensa: " + rewardUser);
			result.append( "</td>"); 
			result.append( "</tr>");
		}
		
		if( !Utils.nullOrBlank(affiliationUser )){
			result.append( "<tr>");
			result.append( "<td align='center' style='font-family:arial; font-size:20;'>");
			result.append( "Programa affiliacion: " + affiliationUser);
			result.append( "</td>"); 
			result.append( "</tr>");
		}
		
		result.append( "<tr>");
		result.append( "<td align='center' style='font-family:arial; font-size:20;'>");
		result.append( "<a href='"+baseUrl+"/do/markAsFraudulent?user="+login+"&password="+password+"'>Mark as fraudulent</a>");
		result.append( "</td>"); 
		result.append( "</tr>");

		result.append( "</table>");
		result.append( "</td>");
		result.append( "</tr>");
		result.append( "</table>"); 
		result.append( "</body>");
		result.append( "</html>");
		
		return result.toString();
	}
	
	public String toText()
	{
		StringBuffer result = new StringBuffer( "");
		
		result.append( "Hola Gus. Se ha registrado un usuario con estos datos\r\n");
		result.append( "Email: " + email+"\r\n");
		result.append( "ip: " + ip+"\r\n");
		if( sameIpUserList!= null && sameIpUserList.size() > 1){
			result.append( "Otros con esa IP: " + sameIpUserList.size()+"\r\n");
			for( User userIp:sameIpUserList){
				if( !userIp.getLogin().equals( login)){
					result.append( userIp.getLogin() + " ("+userIp.getEmail()+")  " + Utils.getDate( userIp.getCreated(), 5) + "  " +  Utils.getTime( userIp.getCreated()) + "\r\n") ;
					if( userIp.getIsFraudulent()){
						result.append( "Fraudulento\r\n") ;
					}if( !userIp.getValidated()){
						result.append( "No validado\r\n") ;
					}
				}
			}
		}
		
		if( samePasswordUserList!= null && samePasswordUserList.size() > 1){
			result.append( "Otros con esa password: " + samePasswordUserList.size()+"\r\n");
			for( User userPassword:samePasswordUserList){
				if( !userPassword.getLogin().equals( login)){
					result.append( userPassword.getLogin() + " ("+userPassword.getEmail()+")  " + Utils.getDate( userPassword.getCreated(), 5) + "  " +  Utils.getTime( userPassword.getCreated()) + "\r\n") ;
					if( userPassword.getIsFraudulent()){
						result.append( "Fraudulento\r\n") ;
					}
					if( !userPassword.getValidated()){
						result.append( "No validado\r\n") ;
					}
				}
			}
		}
		
		result.append( "Email sospechoso: " + suspiciousEmail+"\r\n");
		
		if( !Utils.nullOrBlank(rewardUser )){
			result.append( "Programa recompensa: " + rewardUser + "\r\n");
		}
		
		if( !Utils.nullOrBlank(affiliationUser )){
			result.append( "Programa affiliacion: " + affiliationUser+"\r\n");
		}
		
		result.append( baseUrl+"/do/markAsFraudulent?user="+login+"&password="+password + "\r\n");
		
				
		return result.toString();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	
	public List<User> getSameIpUserList() {
		return sameIpUserList;
	}

	public void setSameIpUserList(List<User> sameIpUserList) {
		this.sameIpUserList = sameIpUserList;
	}

	public List<User> getSamePasswordUserList() {
		return samePasswordUserList;
	}

	public void setSamePasswordUserList(List<User> samePasswordUserList) {
		this.samePasswordUserList = samePasswordUserList;
	}

	public boolean isSuspiciousEmail() {
		return suspiciousEmail;
	}

	public void setSuspiciousEmail(boolean suspiciousEmail) {
		this.suspiciousEmail = suspiciousEmail;
	}

	public String getRewardUser() {
		return rewardUser;
	}

	public void setRewardUser(String rewardUser) {
		this.rewardUser = rewardUser;
	}

	public String getAffiliationUser() {
		return affiliationUser;
	}

	public void setAffiliationUser(String affiliationUser) {
		this.affiliationUser = affiliationUser;
	}

	public List<User> getSameBitcoinAddress() {
		return sameBitcoinAddress;
	}

	public void setSameBitcoinAddress(List<User> sameBitcoinAddress) {
		this.sameBitcoinAddress = sameBitcoinAddress;
	}

}
