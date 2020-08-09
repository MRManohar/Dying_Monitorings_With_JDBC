package service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserNameValidation {
	public String enterUserName() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your user name");
		String name = sc.nextLine();
		if(isValidUserName(name))
			return name;
		else {
			System.out.println("Please enter valid user name");
			return enterUserName();
		}
	}
	public static boolean isValidUserName(String userName){
		// Regex to check valid password.
		String regex = "^[a-zA-Z0-9]+(.{5,10}+)$";

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

		// Return if the password
		// matched the ReGex
		return m.matches();
//		return false;

	}

}
