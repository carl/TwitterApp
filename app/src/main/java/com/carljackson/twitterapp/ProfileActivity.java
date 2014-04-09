package com.carljackson.twitterapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.carljackson.twitterapp.fragments.UserTimelineFragment;
import com.carljackson.twitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

public class ProfileActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String screenName = getIntent().getExtras().getString("screen_name");
        loadProfile(screenName);
        android.support.v4.app.FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        fts.replace(R.id.flUserTimelineContainer, new UserTimelineFragment(screenName)).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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

    protected void loadProfile(String screenName) {
        if (screenName.length() == 0) {
            TwitterClientApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONObject json) {
                    User user = User.fromJson(json);
                    getActionBar().setTitle("@" + user.getScreenName());
                    populateProfileHeader(user);
                    super.onSuccess(json);
                }

                @Override
                public void onFailure(Throwable throwable, String s) {
                    Log.d("DEBUG", throwable.toString());
                    super.onFailure(throwable, s);
                }
            });
        } else {
            TwitterClientApp.getRestClient().getUserInfo(screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONObject json) {
                    User user = User.fromJson(json);
                    getActionBar().setTitle("@" + user.getScreenName());
                    populateProfileHeader(user);
                    super.onSuccess(json);
                }

                @Override
                public void onFailure(Throwable throwable, String s) {
                    Log.d("DEBUG", throwable.toString());
                    super.onFailure(throwable, s);
                }
            });
        }
    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFriendsCount() + " Following");
        ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
    }
}
