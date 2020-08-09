package service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidation {
	public String enterName() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your name");
		String name = sc.nextLine();
		if(isValidName(name))
			return name;
		else {
			System.out.println("Please enter valid name");
			return enterName();
		}
	}
	public static boolean isValidName(String name){
		String regex = "[a-zA-Z ]*$";

		// Compile the ReGex
		Pattern p = Pattern.compile(regex);

		// If the password is empty
		// return false
		if (name == null) {
			return false;
		}

		// Pattern class contains matcher() method
		// to find matching between given password
		// and regular expression.
		Matcher m = p.matcher(name);

		// Return if the password
		// matched the ReGex
		return m.matches();
//		return false;

	}

}
