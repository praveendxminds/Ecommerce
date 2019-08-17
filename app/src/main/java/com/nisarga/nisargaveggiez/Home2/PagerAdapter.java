package com.nisarga.nisargaveggiez.Home2;

/**
 * Created by praveen on 29/01/19.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numTabs;

    public PagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragment tab1 = new TabFragment();
                return tab1;
            case 1:
                TabFragment tab2 = new TabFragment();
                return tab2;
            case 2:
                TabFragment tab3 = new TabFragment();
                return tab3;
            case 3:
                TabFragment tab4 = new TabFragment();
                return tab4;
            case 4:
                TabFragment tab5 = new TabFragment();
                return tab5;
            case 5:
                TabFragment tab6 = new TabFragment();
                return tab6;
            case 6:
                TabFragment tab7 = new TabFragment();
                return tab7;
            case 7:
                TabFragment tab8 = new TabFragment();
                return tab8;
            case 8:
                TabFragment tab9 = new TabFragment();
                return tab9;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }

}
