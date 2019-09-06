package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.retrofit.ProductslHomePage;

import java.util.List;


@NonReusable
@Layout(R.layout.home_page_layout_two)
public class HomePageLayoutTwo {


    @View(R.id.phvRecommendedList)
    public PlaceHolderView phvRecommendedList;

    Context mContext;
    Object mlist;
    SessionManager session;

    public HomePageLayoutTwo(Context context, Object list) {
        this.mContext = context;
        this.mlist = list;
    }

    @Resolve
    public void onResolved() {
        phvRecommendedList.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));


    }


}