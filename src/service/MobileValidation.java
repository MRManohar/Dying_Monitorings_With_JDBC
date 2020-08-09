package service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileValidation {
	public String enterMobileNumber() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your mobile number");
		String name = sc.nextLine();
		if(isValidMobileNumber(name))
			return name;
		else {
			System.out.println("Please enter valid mobile number");
			return enterMobileNumber();
		}
	}
	public static boolean isValidMobileNumber(String userName){
		// Regex to check valid password.
		String regex = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$";

		// Compile the ReGex
		Pattern p = Pattern.compile(regex);

		// If the password is empty
		// return false
		if (userName == null) {
			return false;
		}

		// Pattern class contains matcher() method
		// to find matching between given password
		// and regular expression.
		Matcher m = p.matcher(userName);
		return (m.find() && m.group().equals(userName));

	}

}
