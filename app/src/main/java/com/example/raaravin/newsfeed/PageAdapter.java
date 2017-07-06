package com.example.raaravin.newsfeed;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by raaravin on 6/12/2017.
 */

class PageAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;

    public PageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }


    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentPage.newInstance("https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=298817106b56466fa6feea8f052d2f50");
            case 1:
                return FragmentPage.newInstance("https://newsapi.org/v1/articles?source=bbc-sport&sortBy=top&apiKey=298817106b56466fa6feea8f052d2f50");
            case 2:
                return FragmentPage.newInstance("https://newsapi.org/v1/articles?source=daily-mail&sortBy=top&apiKey=298817106b56466fa6feea8f052d2f50");
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {

    if(position==1)
        return "Sports News";
    else if(position==2)
        return "Entertainment";
    else
        return "Top News";

    }
}