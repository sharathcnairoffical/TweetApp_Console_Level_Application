package com.tweetapp.utils;

import java.util.Scanner;

import com.tweetapp.model.Tweet;
import com.tweetapp.service.TweetService;

public class TweetUtil {


	public int userId;
	public int tweetId;
	public String tweet;

	Scanner scanner = new Scanner(System.in);
	
	//calling service class
	TweetService tweetService = new TweetService();

	// Post a tweet
	public Boolean createTweet(int userId) {
		Tweet tweetObj = new Tweet();
		while (true) {
			System.out.print("type Something.. : ");
			tweet = scanner.nextLine();
			if (tweet.length() > 0) {
				break;
			}
			System.out.println("tweet should not be empty");
		}
		tweetObj.setUserId(userId);
		tweetObj.setTweet(tweet);
		tweetService.createTweet(tweetObj);
		return true;
	}

	// get tweets by id
	public boolean getMyTweets(int userId) {
		return tweetService.getTweetsByUserId(userId);
	}

	// get all tweets
	public boolean getAllTweets(){
		return tweetService.getAllTweets();
	}

}
