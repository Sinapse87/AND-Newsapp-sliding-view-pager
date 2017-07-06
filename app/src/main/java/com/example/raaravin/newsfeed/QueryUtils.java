package com.example.raaravin.newsfeed;

/**
 * Created by raaravin on 6/19/2017.
 */

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static String SAMPLE_JSON_RESPONSE ;
    private QueryUtils() {
    }

    public static ArrayList<list> extractNewsList(String response) {

        ArrayList<list> NewsList = new ArrayList<>();
        SAMPLE_JSON_RESPONSE=response;
        if(SAMPLE_JSON_RESPONSE!=null) {
            try {
                JSONObject baseJsonResponse = new JSONObject(response);
                JSONArray items = baseJsonResponse.getJSONArray("articles");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject obj1 = items.getJSONObject(i);
                    String title = obj1.getString("title");
                    String author=obj1.getString("author");
                    String mDescription=obj1.getString("description");
                    String imageurl=obj1.getString("urlToImage");
                    String publidhed=obj1.getString("publishedAt");
                    String url=obj1.getString("url");
                    list bookitem = new list(title,author,imageurl,mDescription,publidhed,url);
                    NewsList.add(bookitem);
                }

            } catch (JSONException e) {
                Log.e("QueryUtils", "Problem parsing the books JSON results", e);
            }
        }
        return NewsList;
    }

    public static List<list> fetchNewsData(String requestUrl) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<list> mNewsList = extractNewsList(jsonResponse);

        // Return the {@link Event}
        return mNewsList;
    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int a=urlConnection.getResponseCode();
            int value=a;
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the books JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}