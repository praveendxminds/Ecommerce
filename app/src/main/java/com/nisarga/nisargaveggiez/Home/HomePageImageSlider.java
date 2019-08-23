package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.mindorks.placeholderview.annotations.NonReusable;
import com.nisarga.nisargaveggiez.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sushmita 26/06/2019
 */

@NonReusable
@Layout(R.layout.image_gallary_home_1)
public class HomePageImageSlider {

    @View(R.id.vpSliderImage)
    public ViewPager vpSliderImage;

    @View(R.id.tabIndicator)
    public TabLayout tabIndicator;

    Context mContext;
    ArrayList<String> mHeading;
    ArrayList<String> mSliderImage;
    ArrayList<String> XMENArray = new ArrayList<>();

    int defaultGap = 30;
    int currentPage = 0;

    public HomePageImageSlider(Context context, ArrayList<String> heading, ArrayList<String> slider_image) {
        mContext = context;
        mHeading = heading;
        mSliderImage = slider_image;

        for (int i = 0; i < mSliderImage.size(); i++) {
            XMENArray.add(mSliderImage.get(i));
        }
    }

    @Resolve
    public void onResolved() {
        vpSliderImage.setPadding(defaultGap, 0, defaultGap, 0);
        vpSliderImage.setClipToPadding(false);
        vpSliderImage.setPageMargin(15);
        vpSliderImage.setAdapter(new HomePageImageSliderAdapter(mContext, XMENArray));

        tabIndicator.setupWithViewPager(vpSliderImage);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == mSliderImage.size()) {
                    currentPage = 0;
                }
                vpSliderImage.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }
}




