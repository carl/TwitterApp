package com.carljackson.twitterapp.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.carljackson.twitterapp.TwitterClientApp;
import com.carljackson.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

public class UserTimelineFragment extends TweetsListFragment {
    private String screenName = "";

    public UserTimelineFragment(String name) {
        screenName = name;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMoreTweets(0);
    }

    protected void loadMoreTweets(long maxId) {
        if (screenName.length() == 0) {
            TwitterClientApp.getRestClient().getMyTimeline(maxId, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONArray jsonTweets) {
                    getAdapter().addAll(Tweet.fromJson(jsonTweets));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.d("DEBUG", throwable.toString());
                }
            });
        } else {
            TwitterClientApp.getRestClient().getUserTimeline(maxId, screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONArray jsonTweets) {
                    getAdapter().addAll(Tweet.fromJson(jsonTweets));
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.d("DEBUG", throwable.toString());
                }
            });
        }
    }
}
