package service;

import java.util.Scanner;
import java.util.regex.Pattern;

public class EmailValidation {
	@SuppressWarnings("resource")
	public String enterEmail() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your email");
		String email = sc.next();
		if(isValidEmail(email))
			return email;
		else {
			System.out.println("Please enter valid email");
			return enterEmail();
		}
	}

	public static boolean isValidEmail(String email) 
	{ 
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
	                            "[a-zA-Z0-9_+&*-]+)*@" + 
	                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
	                            "A-Z]{2,7}$"; 
	                              
	        Pattern pat = Pattern.compile(emailRegex); 
	        if (email == null) 
	            return false; 
	        return pat.matcher(email).matches(); 
    } 
}
