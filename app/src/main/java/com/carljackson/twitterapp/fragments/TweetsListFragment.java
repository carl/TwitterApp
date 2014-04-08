package com.carljackson.twitterapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.carljackson.twitterapp.EndlessScrollListener;
import com.carljackson.twitterapp.R;
import com.carljackson.twitterapp.TweetsAdapter;
import com.carljackson.twitterapp.models.Tweet;

import java.util.ArrayList;

public abstract class TweetsListFragment extends Fragment {
    TweetsAdapter adapter;
    ArrayList<Tweet> tweets;

    @Override
    public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
        return inf.inflate(R.layout.fragment_tweets_list, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        ListView lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
        adapter = new TweetsAdapter(getActivity(), tweets);
        lvTweets.setAdapter(adapter);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                long maxId = 0;
                if (tweets.size() > 0) {
                    maxId = tweets.get(tweets.size() - 1).getId();
                }
                loadMoreTweets(maxId);
            }
        });
    }

    protected abstract void loadMoreTweets(long maxId);

    public TweetsAdapter getAdapter() {
        return adapter;
    }
}
