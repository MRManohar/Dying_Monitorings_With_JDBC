package service;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddressValidation {
		public String enterAddress() {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter worker address");
			String name = sc.nextLine();
			if(isValidAddress(name))
				return name;
			else {
				System.out.println("Please enter valid address");
				return enterAddress();
			}
		}
		public static boolean isValidAddress(String address){
			String regex = "[a-zA-Z0-9 ]+$";

			// Compile the ReGex
			Pattern p = Pattern.compile(regex);

			// If the password is empty
			// return false
			if (address == null) {
				return false;
			}

			// Pattern class contains matcher() method
			// to find matching between given password
			// and regular expression.
			Matcher m = p.matcher(address);

			// Return if the password
			// matched the ReGex
			return m.matches();
//			return false;

		}
}
/*package service;


}
*/
