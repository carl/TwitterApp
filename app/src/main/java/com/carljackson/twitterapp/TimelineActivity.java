package com.carljackson.twitterapp;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.carljackson.twitterapp.fragments.HomeTimelineFragment;
import com.carljackson.twitterapp.fragments.MentionsFragment;
import com.carljackson.twitterapp.fragments.TweetsListFragment;
import com.carljackson.twitterapp.models.Tweet;

public class TimelineActivity extends FragmentActivity implements ActionBar.TabListener {
    TweetsListFragment fragmentTweets;

    public static final int COMPOSE_TWEET_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
//        fragmentTweets = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);
        setupNavigationTabs();
    }

    private void setupNavigationTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        ActionBar.Tab tabHome = actionBar.newTab()
                .setText("Home")
                .setTag("HomeTimelineFragment")
                .setIcon(R.drawable.ic_home)
                .setTabListener(this);
        ActionBar.Tab tabMentions = actionBar.newTab()
                .setText("Mentions")
                .setTag("MentionsFragment")
                .setIcon(R.drawable.ic_at)
                .setTabListener(this);
        actionBar.addTab(tabHome);
        actionBar.addTab(tabMentions);
        actionBar.selectTab(tabHome);
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
        if (tab.getTag() == "HomeTimelineFragment") {
            fts.replace(R.id.frameContainer, new HomeTimelineFragment());
        } else {
            fts.replace(R.id.frameContainer, new MentionsFragment());
        }
        fts.commit();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
