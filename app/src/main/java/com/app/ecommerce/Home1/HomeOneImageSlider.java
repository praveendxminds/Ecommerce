package com.app.ecommerce.Home1;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.app.ecommerce.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by praveen on 14/11/18.
 */

@Parent
@SingleTop
@Layout(R.layout.image_gallary_home_1)
public class HomeOneImageSlider {

    @View(R.id.pager_home_1)
    public ViewPager mPager;

    @View(R.id.indicator_home_1)
    public CircleIndicator indicator;

    public static int currentPage = 0;
  public static Integer[] XMEN= {R.drawable.flower, R.drawable.deep, R.drawable.flower, R.drawable.deep};
    public ArrayList<String> XMENArray = new ArrayList<String>();


    @ParentPosition
    public int mParentPosition;

    public Context mContext;
    public ArrayList<String> mHeading;
    public ArrayList<String> mCatImgUrl;

    public HomeOneImageSlider(Context context, ArrayList<String> heading, ArrayList<String> CatImgUrl) {
        mContext = context;
        mHeading = heading;
        mCatImgUrl = CatImgUrl;

        for (int i = 0; i < mCatImgUrl.size(); i++) {
            XMENArray.add(mCatImgUrl.get(i));
            Log.e("---images----", String.valueOf(mCatImgUrl.get(i)));
        }
    }

    @Resolve
    public void onResolved() {

        mPager.setAdapter(new HomeOneImageSliderAdapter(mContext, XMENArray));

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




