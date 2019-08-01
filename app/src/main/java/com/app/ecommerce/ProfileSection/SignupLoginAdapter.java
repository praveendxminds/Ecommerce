package com.app.ecommerce.ProfileSection;

/**
 * Created by praveen on 29/01/19.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.ecommerce.Home2.TabFragment;

public class SignupLoginAdapter extends FragmentStatePagerAdapter {
    int numTabs;

    public SignupLoginAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LoginFragment loginTab = new LoginFragment();
                return loginTab;
            case 1:
                SignUpFragment signupTab = new SignUpFragment();
                return signupTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }

}
