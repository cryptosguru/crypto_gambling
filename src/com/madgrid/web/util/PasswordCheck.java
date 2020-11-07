package com.madgrid.web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordCheck {
	
	public static int CheckPasswordStrength(String passwd) {
		int upper = 0, lower = 0, numbers = 0, special = 0, length = 0;
		int strength = 0, intScore = 0;
		Pattern p;
		Matcher m;
		if (passwd == null){
			return 0;
		}
		
		length = passwd.length();
		if (length < 5){
			intScore = (intScore + 3);
		} else if (length > 4 && passwd.length() < 8){
			intScore = (intScore + 6);
		} else if (
			length > 7 && passwd.length() < 16){
			intScore = (intScore + 12);
		} else if (length > 15){
			intScore = (intScore + 18);
		}
 
		p = Pattern.compile(".??[a-z]");
		m = p.matcher(passwd);
		while (m.find()){
			lower += 1;
		}
		if (lower > 0) {
			intScore = (intScore + 1);
		}
		p = Pattern.compile(".??[A-Z]");
		m = p.matcher(passwd);
		while (m.find())
			{
			upper += 1;
		}
		if (upper > 0) {
			intScore = (intScore + 5);
		}

		p = Pattern.compile(".??[0-9]");
		m = p.matcher(passwd);
		while (m.find()){
			numbers += 1;
		}
		if (numbers > 0) {
			intScore = (intScore + 5);
			if (numbers > 1) {
				intScore = (intScore + 2);
				if (numbers > 2) {
					intScore = (intScore + 3);
				}
			}
		}
		p = Pattern.compile(".??[:,!,@,#,$,%,^,&,*,?,_,~]");
		m = p.matcher(passwd);
		while (m.find()){
			special += 1;
		}
		if (special > 0) {
			intScore = (intScore + 5);
			if (special > 1) {
				intScore += (intScore + 5);
			}
		}

		if (upper > 0 && lower > 0) {
			intScore = (intScore + 2);
		}
		if ((upper > 0 || lower > 0) && numbers > 0) {
			intScore = (intScore + 2);
		}
		if ((upper > 0 || lower > 0) && numbers > 0 && special > 0){
			intScore = (intScore + 2);
		}
		if (upper > 0 && lower > 0 && numbers > 0 && special > 0){
			intScore = (intScore + 2);
		}
		
		return intScore;
	}

	
}
