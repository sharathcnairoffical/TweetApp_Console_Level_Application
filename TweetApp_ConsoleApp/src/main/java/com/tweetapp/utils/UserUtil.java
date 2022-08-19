package com.tweetapp.utils;

import java.util.Map;
import java.util.Scanner;

import com.tweetapp.model.User;
import com.tweetapp.service.UserService;

public class UserUtil {

	public String firstName;
	public String lastName;
	public String gender;
	public String dateOfBirth;
	public String email;
	public String password;

	Scanner sc = new Scanner(System.in);
	UserService userService = new UserService();

	// Register user
	public Boolean registerUser() {
		User user = new User();
		while (true) {
			System.out.print("Enter your first name : ");
			firstName = sc.nextLine();
			if (firstName.length() >= 3) {
				user.setFirstName(firstName);
				break;
			}
			System.out.println("Firstname must have a length of 3 characters or more");
		}
		while (true) {
			System.out.print("Enter your last name : ");
			lastName = sc.nextLine();
			if (lastName.length() >= 3) {
				user.setLastName(lastName);
				break;
			}
			System.out.println("Lastname must have a length of 3 characters or more");
		}
		while (true) {
			System.out.print("Enter your gender : ");
			gender = sc.nextLine();
			if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female")) {
				user.setGender(gender);
				break;
			}
			System.out.println("Please enter \'male\' or \'female\'");
		}
		while (true) {
			System.out.print("Enter your dateOfBirth : ");
			dateOfBirth = sc.nextLine();
			if (dateOfBirth.matches("^\\d{4}\\-(0[1-9]|1[0-2])\\-(0[1-9]|[12][0-9]|3[01])$")) {//' /^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/ '
				user.setdateOfBirth(dateOfBirth);
				break;
			}
			System.out.println("Please enter valid date in yyyy-mm-dd format");
		}

		while (true) {
			System.out.print("Enter your email : ");
			email = sc.nextLine();
			if (email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
				user.setEmail(email);
				break;
			}
			System.out.println("Please enter a valid email address");
		}

		while (true) {
			System.out.print("Enter your password: ");
			password = sc.nextLine();
			if (password.length() >= 6) {
				user.setPassword(password);
				break;
			}
			System.out.println("Password must be of minimum 6 characters");
		}

		userService.registerUser(user);
		return true;
	}

	// Login
	public Map<String, Integer> login() {
		while (true) {
			System.out.print("Enter your email : ");
			email = sc.nextLine();
			if (email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
				break;
			}
			System.out.println("Please enter valid email address");
		}

		while (true) {
			System.out.print("Enter your password : ");
			password = sc.nextLine();
			if (password.length() >= 6) {
				break;
			}
			System.out.println("Password must be a length of 6 to 10");
		}

		return userService.login(email, password);

	}

	// Reset password
	public Boolean updateUser(int userId) {
		String newPassword, oldPassword;
		while (true) {
			System.out.print("Enter your password : ");
			oldPassword = sc.nextLine();
			if (oldPassword.length() >= 6) {
				break;
			}
			System.out.println("Password must be a length of 6 to 10");
		}
		while (true) {
			System.out.print("Enter your new password : ");
			newPassword = sc.nextLine();
			if (newPassword.length() >= 6) {
				break;
			}
			System.out.println("Password must be a length of 6 to 10");
		}
		userService.updatePassword(userId, oldPassword, newPassword);
		return true;
	}

	// logout
	public boolean logout(int userId) {
		return userService.logout(userId);
	}

	// forgot password
	public boolean forgotPassword() {
		String newPassword;
		while (true) {
			System.out.print("Enter your email : ");
			email = sc.nextLine();
			if (email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
				break;
			}
			System.out.println("please enter valid email address");
		}

		while (true) {
			System.out.print("Enter your password : ");
			newPassword = sc.nextLine();
			if (newPassword.length() >= 6) {
				break;
			}
			System.out.println("password must be a length of 6 to 10");
		}
		userService.forgot(email, newPassword);
		return true;
	}

	// get all users
	public boolean getAllUsers() {
		userService.getAllUsers();
		return true;
	}
}
