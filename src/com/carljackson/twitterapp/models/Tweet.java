package com.carljackson.twitterapp.models;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.carljackson.twitterapp.HumanTime;

public class Tweet {
	private String body;
	private long uid;
	private boolean favorited;
	private boolean retweeted;
	private String createdAt;
	private User user;
	final DateTimeFormatter df = DateTimeFormat
			.forPattern("EEE MMM dd HH:mm:ss Z yyyy");

	public User getUser() {
		return user;
	}

	public String getBody() {
		return body;
	}

	public long getId() {
		return uid;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public boolean isRetweeted() {
		return retweeted;
	}

	public String relativeTime() {
		DateTime dt = df.withOffsetParsed().parseDateTime(createdAt);
		DateTime now = new DateTime();
		return HumanTime.approximately(now.getMillis() - dt.getMillis());
	}

	public static Tweet fromJson(JSONObject jsonObject) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = jsonObject.getString("text");
			tweet.uid = jsonObject.getLong("id");
			tweet.favorited = jsonObject.getBoolean("favorited");
			tweet.retweeted = jsonObject.getBoolean("retweeted");
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

		for (int i=0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Tweet tweet = Tweet.fromJson(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}

		return tweets;
	}
}