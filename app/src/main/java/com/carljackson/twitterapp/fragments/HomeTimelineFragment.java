package com.carljackson.twitterapp.fragments;

import android.os.Bundle;
import android.util.Log;

import com.carljackson.twitterapp.TwitterClientApp;
import com.carljackson.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

public class HomeTimelineFragment extends TweetsListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMoreTweets(0);
    }

    protected void loadMoreTweets(long maxId) {
        TwitterClientApp.getRestClient().getHomeTimeline(maxId, new JsonHttpResponseHandler() {
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
