package com.app.ecommerce.ProfileSection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.ecommerce.Home2.PagerAdapter;
import com.app.ecommerce.R;

public class LoginSignup_act extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    LinearLayout vscroll_height,signup_frg,log_frag;
    int dheight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginsignup_act);

        vscroll_height = (LinearLayout) findViewById(R.id.vscroll_height);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
         dheight = displayMetrics.heightPixels;


//        ViewGroup.LayoutParams params = vscroll_height.getLayoutParams();
//        params.height =  dheight;
//        vscroll_height.setLayoutParams(params);


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
     //   signup_frg = (LinearLayout) findViewById(R.id.signup_frg);
//        log_frag = (LinearLayout) findViewById(R.id.log_frag);








        tabLayout.addTab(tabLayout.newTab().setText("Log In"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));

        SignupLoginAdapter adapter = new SignupLoginAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
//                if(tab.getPosition() == 0)
//                {
//                    ViewGroup.LayoutParams params = vscroll_height.getLayoutParams();
//                    params.height = dheight;
//                    vscroll_height.setLayoutParams(params);
//
//                }
//                else
//                {
//
////                    SignUpFragment sgn= new SignUpFragment();
////                    sgn.signupheight();
//
//                    ViewGroup.LayoutParams params = vscroll_height.getLayoutParams();
//
//                    params.height = (int)(dheight*2.6);
//                    vscroll_height.setLayoutParams(params);
//
//                }
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    public void navigateFragment(int position){
        viewPager.setCurrentItem(position, true);

    }
}
