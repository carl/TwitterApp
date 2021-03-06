package com.carljackson.twitterapp;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API.
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes:
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "wNrQBNN8UR9xvPGLQS8gqw";
	public static final String REST_CONSUMER_SECRET = "0vgrFbmasGoXMMAmCNM0AVp7BuYfdge40SRH4WJJs";
	public static final String REST_CALLBACK_URL = "oauth://twitterapp";
	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeline(long maxId, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", "25");
        if (maxId > 0) {
            params.put("max_id", String.format("%d", maxId - 1));
		}
		client.get(url, params, handler);
	}

	public void getMentionsTimeline(long maxId, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/mentions_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", "25");
        if (maxId > 0) {
            params.put("max_id", String.format("%d", maxId - 1));
		}
		client.get(url, params, handler);
	}

    public void getUserTimeline(long maxId, String screenName, AsyncHttpResponseHandler handler) {
        String url = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", "25");
        if (maxId > 0) {
            params.put("max_id", String.format("%d", maxId - 1));
        }
        if (screenName.length() > 0) {
            params.put("screen_name", screenName);
        }
        client.get(url, params, handler);
    }

	public void getMyInfo(AsyncHttpResponseHandler handler) {
		String url = getApiUrl("account/verify_credentials.json");
		client.get(url, null, handler);
	}

	public void getUserInfo(String screenName, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
		client.get(url, params, handler);
	}

	public void postStatus(String body, AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", body);
		client.post(url, params, handler);
	}

	/*
	 * 1. Define the endpoint URL with getApiUrl and pass a relative path to the
	 * endpoint i.e getApiUrl("statuses/home_timeline.json"); 2. Define the
	 * parameters to pass to the request (query or body) i.e RequestParams
	 * params = new RequestParams("foo", "bar"); 3. Define the request method
	 * and make a call to the client i.e client.get(apiUrl, params, handler);
	 * i.e client.post(apiUrl, params, handler);
	 */
}