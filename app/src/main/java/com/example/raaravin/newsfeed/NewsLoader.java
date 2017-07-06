package com.example.raaravin.newsfeed;

import android.content.Context;

import java.util.List;

/**
 * Created by raaravin on 6/19/2017.
 */

public class NewsLoader extends android.support.v4.content.AsyncTaskLoader<List<list>> {
    private static final String LOG_TAG = NewsLoader.class.getName();
    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public List<list> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<list> NewsList = QueryUtils.fetchNewsData(mUrl);
        return NewsList;
    }
}
