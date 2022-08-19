package com.tweetapp.utils;

import java.io.FileInputStream;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Properties;

public class DbConnectUtil {
	private static final String DB_DRIVER_CLASS = "driver.class.name";
	private static final String DB_USERNAME = "db.username";
	private static final String DB_PASSWORD = "db.password";
	private static final String DB_URL = "db.url";

	private static Connection con = null;
	private static Properties prop = null;
	static {
		try {
			prop = new Properties();
			prop.load(new FileInputStream("src/db.properties"));
			Class.forName(prop.getProperty(DB_DRIVER_CLASS));
			con = DriverManager.getConnection(prop.getProperty(DB_URL),prop.getProperty(DB_USERNAME), prop.getProperty(DB_PASSWORD));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return con;
	}

}
