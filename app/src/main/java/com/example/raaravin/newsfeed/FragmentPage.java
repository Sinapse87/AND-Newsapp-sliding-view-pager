package com.example.raaravin.newsfeed;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FragmentPage extends Fragment implements LoaderManager.LoaderCallbacks<List<list>>{
    private TextView mEmptyStateTextView;
    private static final int NEWS_LOADER_ID = 1;
    private static String USGS_REQUEST_URL ="https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=298817106b56466fa6feea8f052d2f50";
    private NewsAdapter mAdapter;

    public static FragmentPage newInstance(String title) {
        FragmentPage fragmentFirst = new FragmentPage();
        Bundle args = new Bundle();
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        USGS_REQUEST_URL=getArguments().getString("someTitle");
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        ListView NewsListView = (ListView) view.findViewById(R.id.list);
        mEmptyStateTextView=(TextView)view.findViewById(R.id.empty);
        NewsListView.setEmptyView(mEmptyStateTextView);
        mAdapter = new NewsAdapter(getActivity(), new ArrayList<list>());
        NewsListView.setAdapter(mAdapter);
        NewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                list currentNews = mAdapter.getItem(position);
                Uri earthquakeUri = Uri.parse(currentNews.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(websiteIntent);
            }
        });
        NewsAsynctask task = new NewsAsynctask();
        task.execute(USGS_REQUEST_URL);
        android.support.v4.app.LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID, null,this);
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        if(isConnected){
            loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        }
        else{
            ProgressBar loaderindicator=(ProgressBar)getView().findViewById(R.id.loading_spinner);
            loaderindicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }
        return view;
    }
    private class NewsAsynctask extends AsyncTask<String,Void,List<list>> {

        @Override
        protected List<list> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<list> result = QueryUtils.fetchNewsData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<list> data) {
            mAdapter.clear();
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }


    }
    public Loader<List<list>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(getActivity(),USGS_REQUEST_URL);
    }



    @Override
    public void onLoaderReset(Loader<List<list>> loader) {
        mAdapter.clear();
    }

    @Override
    public void onLoadFinished(Loader<List<list>> loader, List<list> data) {
        mAdapter.clear();
        mEmptyStateTextView.setText(R.string.no_news);
        ProgressBar loaderindicator=(ProgressBar)getView().findViewById(R.id.loading_spinner);
        loaderindicator.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }



}