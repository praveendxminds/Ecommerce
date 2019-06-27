package com.app.ecommerce.Home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.ecommerce.Home2.TabFragment;

import java.util.ArrayList;
import java.util.List;

public class TabAdapterProductDetail extends FragmentStatePagerAdapter {
    int numTabs;

    public TabAdapterProductDetail(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AboutFragmentProductDetail tab1 = new AboutFragmentProductDetail();
                return tab1;
            case 1:
                BenefitsFragmentProductDetail tab2 = new BenefitsFragmentProductDetail();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }

}
