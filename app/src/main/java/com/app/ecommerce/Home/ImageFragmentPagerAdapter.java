package com.app.ecommerce.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ImageFragmentPagerAdapter extends FragmentPagerAdapter {
    static final int NUM_ITEMS = 6;
    public ImageFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        SwipeFragment fragment = new SwipeFragment();
        return SwipeFragment.newInstance(position);
    }
}