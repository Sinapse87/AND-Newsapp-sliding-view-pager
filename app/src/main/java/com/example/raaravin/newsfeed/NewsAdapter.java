package com.example.raaravin.newsfeed;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by raaravin on 6/7/2017.
 */
public class NewsAdapter extends ArrayAdapter<list> {
    private Bitmap bmp;

    public NewsAdapter(Activity context, List<list> containers) {
        super(context,0, containers);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        final list CurrentPosition=getItem(position);
        TextView mtitle= (TextView)listItemView.findViewById(R.id.NewsTitle);
        mtitle.setText(CurrentPosition.getNewsTitle());
        TextView mAuthor=(TextView)listItemView.findViewById(R.id.author);
        mAuthor.setText(CurrentPosition.getAuthorName());
        TextView mDesc=(TextView)listItemView.findViewById(R.id.Description);
        mDesc.setText(CurrentPosition.getDescription());
        TextView mDate=(TextView)listItemView.findViewById(R.id.date);
        String DatePublished=CurrentPosition.getDateTime();
        mDate.setText(getDate(DatePublished));
        TextView mTime=(TextView)listItemView.findViewById(R.id.timeofnews);
        mTime.setText(formatTime(DatePublished));
        final ImageView Newsimage=(ImageView)listItemView.findViewById(R.id.list_item_image);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    InputStream in = new URL(CurrentPosition.getImageurl()).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                    return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (bmp != null)
                    Newsimage.setImageBitmap(bmp);
            }

        }.execute();

        return listItemView;
    }
    public String getDate(String date) {
        String s = date.substring(0, date.indexOf('T'));
        return s;
    }
    private String formatTime(String date) {
        String dateObject = date.substring(11,date.indexOf('Z'));
        DateFormat sdf = new SimpleDateFormat("hh:mm");
        Date Timeformat = null;
        try {
            Timeformat = sdf.parse(dateObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(Timeformat);
    }    }
