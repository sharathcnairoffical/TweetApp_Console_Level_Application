package com.tweetapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tweetapp.model.Tweet;
import com.tweetapp.utils.DbConnectUtil;

public class TweetDao {
	private Connection conn = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	//Db table creation for TWeets
	public TweetDao() {
		try {

			conn = DbConnectUtil.getConnection();

			String sqlCreate = "CREATE TABLE IF NOT EXISTS TWEET" + "(id INTEGER AUTO_INCREMENT PRIMARY KEY,"
					+ "userid INTEGER," + "tweet VARCHAR(100)," + "created DATE)";
			Statement stmt = conn.createStatement();
			stmt.execute(sqlCreate);
		} catch (Exception e) {
			System.out.println("Something went wrong. Unable to create tweet table");
		}
	}

	public boolean createTweet(Tweet tweet) {
		try {
			preparedStatement = conn.prepareStatement("insert into tweet(userid,tweet,created) values(?,?,?)");
			preparedStatement.setInt(1, tweet.getUserId());
			preparedStatement.setString(2, tweet.getTweet());
			preparedStatement.setDate(3, tweet.getCreated());
			preparedStatement.executeUpdate();
			System.out.println("Tweet uploaded successfully");
		} catch (SQLException e) {
			System.out.println("Something went wrong! Tweet not uploaded..");
		}
		return true;
	}

	public ResultSet getAllTweets() {
		try {
			statement = conn.createStatement();
			String sql = "SELECT * FROM tweet";
			resultSet = statement.executeQuery(sql);
			return resultSet;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again..");
			return null;
		}
	}

	public ResultSet getTweetsByUserId(int userId) {

		try {
			statement = conn.createStatement();
			String sql = "SELECT * FROM tweet where userid = " + userId;
			resultSet = statement.executeQuery(sql);

			return resultSet;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again..");
			return null;
		}
	}
}
