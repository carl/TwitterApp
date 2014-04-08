package com.carljackson.twitterapp;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.carljackson.twitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweetActivity extends Activity {
	EditText etTweetBody;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		etTweetBody = (EditText) findViewById(R.id.etTweetBody);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onTweetClicked(View view) {
		String body = etTweetBody.getText().toString();

		TwitterClientApp.getRestClient().postStatus(body, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonTweet) {
				Intent data = new Intent();
				Tweet tweet = Tweet.fromJson(jsonTweet);
				data.putExtra(Tweet.KEY_NAME, tweet);
				setResult(Activity.RESULT_OK, data);
				finish();
			}

			@Override
			public void onFailure(Throwable throwable) {
				Log.d("DEBUG", throwable.toString());
			}
		});
	}
}
