package com.tweetapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tweetapp.model.User;
import com.tweetapp.utils.DbConnectUtil;

public class UserDao {
	List<User> userList = new ArrayList<User>();
	private Connection conn = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	//User Table creation in DB
	public UserDao() {
		try {
			//calling DbcontractUtil class
			conn = DbConnectUtil.getConnection();

			String sqlCreate = "CREATE TABLE IF NOT EXISTS USER" + "(id INTEGER AUTO_INCREMENT PRIMARY KEY,"
					+ "firstname VARCHAR(20)," + "lastname VARCHAR(20)," + "gender VARCHAR(20),"
					+ "dateOfBirth VARCHAR(15)," + " email VARCHAR(30)," + "password VARCHAR(15)," + "status BOOLEAN)";

			Statement stmt = conn.createStatement();
			stmt.execute(sqlCreate);
		} catch (Exception e) {
			System.out.println("Something went wrong. User table not created");
		}
	}

	// To Register user
	public Boolean registerUser(User user) {

		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"insert into user(firstname,lastname,gender,dateOfBirth,email,password,status) values (?,?,?,?,?,?,?)");
			pstmt.setString(1, user.getFirstName());
			pstmt.setString(2, user.getLastName());
			pstmt.setString(3, user.getGender());
			pstmt.setString(4, user.getdateOfBirth());
			pstmt.setString(5, user.getEmail());
			pstmt.setString(6, user.getPassword());
			pstmt.setBoolean(7, false);
			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("Something went wrong! User not registered..");
		}
		return true;
	}

	// Login
	public Map<String, Integer> login(String email, String password) {
		Map<String, Integer> res = new HashMap<String, Integer>();
		res.put("status", -1);
		try {
			statement = conn.createStatement();
			String sql = "SELECT id,email,password from user";
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				if (email.equalsIgnoreCase(resultSet.getString("email")) && password.equalsIgnoreCase(resultSet.getString("password"))) {
					res.put("userId", resultSet.getInt("id"));
					res.put("status", 1);
					preparedStatement = conn.prepareStatement("UPDATE user SET status = ? WHERE id = ?");
					preparedStatement.setBoolean(1, true);
					preparedStatement.setInt(2, resultSet.getInt("id"));
					preparedStatement.executeUpdate();
					return res;
				}
			}
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again..");
		}
		return res;
	}

	// Forgot password
	public void forgotPassword(String email, String newPassword) {
		try {
			preparedStatement = conn.prepareStatement("UPDATE user SET password = ? where email = ?");
			preparedStatement.setString(1, newPassword);
			preparedStatement.setString(2, email);
			if (preparedStatement.executeUpdate() == 1) {
				System.out.println("Password Changed successfully...");
			} else {
				System.out.println("Invalid email/user provided");
			}
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again..");
		}

	}

	// Update password
	public Boolean updatePassword(int userId, String oldPassword, String newPassword) {
		try {
			preparedStatement = conn.prepareStatement("UPDATE user SET password = ? where id = ? AND password = ?");
			preparedStatement.setString(1, newPassword);
			preparedStatement.setInt(2, userId);
			preparedStatement.setString(3, oldPassword);
			if (preparedStatement.executeUpdate() == 1) {
				System.out.println("Password changed successfully...");
			} else {
				System.out.println("Invalid password...");
			}
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again..");
		}
		return true;
	}

	// retrieve all users
	public ResultSet getAllUsers() {
		try {
			statement = conn.createStatement();
			String sql = "SELECT firstname, lastname FROM user";
			resultSet = statement.executeQuery(sql);
			return resultSet;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again..");
			return null;
		}
	}

	// Logout
	public boolean logout(int userId) {
		try {
			preparedStatement = conn.prepareStatement("UPDATE user SET status = ? where id = ?");
			preparedStatement.setBoolean(1, false);
			preparedStatement.setInt(2, userId);
			if (preparedStatement.executeUpdate() == 1) {
				System.out.println("Logged out...");
				return true;
			} else {
				System.out.println("Invalid email or user provided");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again..");
			return false;
		}
	}

}
