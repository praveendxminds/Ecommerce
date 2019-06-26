package com.app.ecommerce.Home;

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
 * Created by sushmita 26/06/2019
 */

@Parent
@SingleTop
@Layout(R.layout.image_slider_product_details)
public class ProductDetailsImageSlider {

    @View(R.id.pager_PrdDetails)
    public ViewPager pager_PrdDetails;

    @View(R.id.indicator_PrdDetails)
    public CircleIndicator indicator_PrdDetails;

    public static int currentPage = 0;
    public static Integer[] XMEN = {R.drawable.flower, R.drawable.deep, R.drawable.flower, R.drawable.deep};
    public ArrayList<String> XMENArray = new ArrayList<String>();


    @ParentPosition
    public int mParentPosition;

    public Context mContext;
    public ArrayList<String> mHeading;
    public String[] mCatImgUrl;

    public ProductDetailsImageSlider(Context context,String[] CatImgUrl) {
        mContext = context;
        mCatImgUrl = CatImgUrl;

        for (int i = 0; i < mCatImgUrl.length; i++) {
            XMENArray.add(mCatImgUrl[i]);
            Log.e("---images----", String.valueOf(mCatImgUrl[i]));
        }
    }

    @Resolve
    public void onResolved() {

        pager_PrdDetails.setAdapter(new HomePageImageSliderAdapter(mContext, XMENArray));

        indicator_PrdDetails.setViewPager(pager_PrdDetails);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == mCatImgUrl.length) {
                    currentPage = 0;
                }
                pager_PrdDetails.setCurrentItem(currentPage++, true);
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




