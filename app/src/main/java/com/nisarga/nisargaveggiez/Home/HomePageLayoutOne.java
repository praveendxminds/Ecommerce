package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.retrofit.ProductslHomePage;

import java.util.ArrayList;
import java.util.List;


@NonReusable
@Layout(R.layout.home_page_layout_one)
public class HomePageLayoutOne {


    @View(R.id.ltone)
    public PlaceHolderView ltone;

    Context mContext;
    Object mList;
    SessionManager session;
     ArrayList<String> id = new ArrayList<>();
     ArrayList<String> title = new ArrayList<>();
     ArrayList<String> img_url = new ArrayList<>();


    public HomePageLayoutOne(Context context, Object list) {
        this.mContext = context;
        this.mList = list;
    }

    @Resolve
    public void onResolved()
    {
        ltone.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));


        JsonArray jsonElements = (JsonArray) new Gson().toJsonTree(mList);


        for (int j = 0; j < jsonElements.size(); j++)
        {
            id.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("id")).replace("\"", ""));
            title.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("title")).replace("\"", ""));
            img_url.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("image")).replace("\"", ""));

            Log.d("id'", String.valueOf(id));
        }


//        phvRecommendedList.addView(new HomePageRecommendedItemList(mContext, image.prd_id, image.image,
//                image.name, qtyspinner, image.add_product_quantity_in_cart,image.discount_price,image.price));

    }


}