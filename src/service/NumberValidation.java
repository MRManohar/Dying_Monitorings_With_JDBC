package service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberValidation {
	public String enterNumber() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		if(isValidNumber(name))
			return name;
		else {
			System.out.println("Please enter valid number");
			return enterNumber();
		}
	}
	public static boolean isValidNumber(String number){
		// Regex to check valid password.
		String regex = "\\d+";

		// Compile the ReGex
		Pattern p = Pattern.compile(regex);

		// If the password is empty
		// return false
		if (number == null) {
			return false;
		}

		// Pattern class contains matcher() method
		// to find matching between given password
		// and regular expression.
		Matcher m = p.matcher(number);
		return (m.find() && m.group().equals(number));
	}
}
