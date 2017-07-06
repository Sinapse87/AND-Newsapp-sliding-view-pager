package com.example.raaravin.newsfeed;

import java.util.ArrayList;

/**
 * Created by raaravin on 6/14/2017.
 */

public class list extends ArrayList<list> {
    private String NewsTitle;
    private String AuthorName;
    private String Imageurl;
    private String Description;
    private String DateTime;
    private String NewsUrl;
    public list (String mBookTitle,String mAuthorName,String mImageurl,String mDescription,String mDateTime,String mNewsUrl){
        NewsTitle =mBookTitle;
        AuthorName=mAuthorName;
        Imageurl=mImageurl;
        Description=mDescription;
        DateTime=mDateTime;
        NewsUrl=mNewsUrl;
    }
    public String getNewsTitle() {
        return NewsTitle;
    }
    public String getAuthorName() {
        return AuthorName;
    }
    public String getImageurl() { return Imageurl; }
    public String getDescription() { return Description; }
    public String getDateTime() { return DateTime; }
    public String getUrl() { return NewsUrl; }
}
