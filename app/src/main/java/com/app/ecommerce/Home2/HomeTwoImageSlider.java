package com.app.ecommerce.Home2;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ecommerce.R;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.mindorks.placeholderview.annotations.expand.Toggle;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by praveen on 14/11/18.
 */

@Parent
@SingleTop
@Layout(R.layout.image_gallary_home_2)
public class HomeTwoImageSlider {

    @View(R.id.pager)
    private ViewPager mPager;

    @View(R.id.indicator)
    private CircleIndicator indicator;

    private static int currentPage = 0;
    private static final Integer[] XMEN = {R.drawable.flower, R.drawable.deep, R.drawable.flower, R.drawable.deep};
    private ArrayList<String> XMENArray = new ArrayList<String>();


    @ParentPosition
    private int mParentPosition;

    private Context mContext;
    private ArrayList<String> mHeading;
    private ArrayList<String> mCatImgUrl;

    public HomeTwoImageSlider(Context context, ArrayList<String> heading, ArrayList<String> CatImgUrl) {
        mContext = context;
        mHeading = heading;
        mCatImgUrl = CatImgUrl;

      for (int i = 0; i < mCatImgUrl.size(); i++) {
            XMENArray.add(mCatImgUrl.get(i));
            Log.e("---images----", String.valueOf(mCatImgUrl.get(i)));
        }
    }

    @Resolve
    private void onResolved() {

        mPager.setAdapter(new ImageSliderAdapter(mContext, XMENArray));

        indicator.setViewPager(mPager);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == mCatImgUrl.size()) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
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



