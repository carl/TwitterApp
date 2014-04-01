package com.carljackson.twitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.carljackson.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	TweetsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
		adapter = new TweetsAdapter(this, tweets);
		lvTweets.setAdapter(adapter);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				loadMoreTweets();
			}
		});

		loadMoreTweets();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	private void loadMoreTweets() {
		long maxId = 0;
		if (tweets.size() > 0) {
			maxId = tweets.get(tweets.size() - 1).getId();
		}
		TwitterClientApp.getRestClient().getHomeTimeline(maxId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				adapter.addAll(Tweet.fromJson(jsonTweets));
			}

			@Override
			public void onFailure(Throwable throwable) {
				Log.d("DEBUG", throwable.toString());
			}
		});


	}

}
