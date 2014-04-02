package com.carljackson.twitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.carljackson.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	TweetsAdapter adapter;

	public static final int COMPOSE_TWEET_ACTIVITY = 1;

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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == COMPOSE_TWEET_ACTIVITY && resultCode == Activity.RESULT_OK) {
			Tweet tweet = (Tweet) data.getSerializableExtra(Tweet.KEY_NAME);
			adapter.insert(tweet, 0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.timeline, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_compose:
			openCompose();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void openCompose() {
		Intent i = new Intent(getApplicationContext(), ComposeTweetActivity.class);
		startActivityForResult(i, COMPOSE_TWEET_ACTIVITY);
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
