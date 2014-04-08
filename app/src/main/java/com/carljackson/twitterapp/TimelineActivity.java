package com.carljackson.twitterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.carljackson.twitterapp.fragments.TweetsListFragment;
import com.carljackson.twitterapp.models.Tweet;

public class TimelineActivity extends FragmentActivity {
    TweetsListFragment fragmentTweets;

	public static final int COMPOSE_TWEET_ACTIVITY = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
        fragmentTweets = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == COMPOSE_TWEET_ACTIVITY && resultCode == Activity.RESULT_OK) {
			Tweet tweet = (Tweet) data.getSerializableExtra(Tweet.KEY_NAME);
			fragmentTweets.getAdapter().insert(tweet, 0);
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

}
