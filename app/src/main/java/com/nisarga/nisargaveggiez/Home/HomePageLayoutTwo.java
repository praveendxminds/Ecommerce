package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
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
@Layout(R.layout.home_page_layout_two)
public class HomePageLayoutTwo {


    @View(R.id.lttwo)
    public PlaceHolderView lttwo;

    @View(R.id.tvTitle)
    public TextView tvTitle;

    Context mContext;
    Object mlist;
    SessionManager session;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> img_url = new ArrayList<>();
    String mTitle;

    public HomePageLayoutTwo(Context context, Object list,String title) {
        this.mContext = context;
        this.mlist = list;
       this.mTitle  = title;
    }

    @Resolve
    public void onResolved() {

        if(mTitle != null && !mTitle.isEmpty() && !mTitle.equals("null")) {
            tvTitle.setText(mTitle);
        }else
        {
            tvTitle.setText("Offers");
        }
        lttwo.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(mContext, 2));

        JsonArray jsonElements = (JsonArray) new Gson().toJsonTree(mlist);
        for (int j = 0; j < jsonElements.size(); j++) {
            id.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("id")).replace("\"", ""));
            title.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("title")).replace("\"", ""));
            img_url.add(String.valueOf(jsonElements.get(j).getAsJsonObject().get("image")).replace("\"", ""));
            lttwo.addView(new HomePageLayoutTwoItem(mContext,
                    (String.valueOf(jsonElements.get(j).getAsJsonObject().get("image")).replace("\"", "")),
                    (String.valueOf(jsonElements.get(j).getAsJsonObject().get("id")).replace("\"", "")),
                    (String.valueOf(jsonElements.get(j).getAsJsonObject().get("title")).replace("\"", "")),j));
            Log.d("id", String.valueOf(id));
        }

    }


}