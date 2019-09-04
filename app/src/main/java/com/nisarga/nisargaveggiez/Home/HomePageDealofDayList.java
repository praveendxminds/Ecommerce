package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.retrofit.DealItemModel;
import com.nisarga.nisargaveggiez.retrofit.ProductslHomePage;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@NonReusable
@Layout(R.layout.home_page_deal_of_day)
public class HomePageDealofDayList {

    @View(R.id.tvViewAll)
    public TextView tvViewAll;

    @View(R.id.phvDealOfDay)
    public PlaceHolderView phvDealOfDay;

    Context mContext;
    List<ProductslHomePage.DealOfDayList> mImageList;

    public HomePageDealofDayList(Context context, List<ProductslHomePage.DealOfDayList> imageList) {
        this.mContext = context;
        this.mImageList = imageList;
    }

    @Resolve
    public void onResolved() {
        phvDealOfDay.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        for (ProductslHomePage.DealOfDayList image : mImageList) {
            Object qtyspinner = "null";

            if ((image.options.equals("null")) && (!image.weight_classes.equals("null"))) {
                qtyspinner = image.weight_classes;
                phvDealOfDay.addView(new HomePageDealOfDayItemList(mContext, image.prd_id, image.image, image.name, qtyspinner,
                        image.add_product_quantity_in_cart,image.discount_price,image.price));
            } else if ((!image.options.equals("null")) && (image.weight_classes.equals("null"))) {
                qtyspinner = image.options;
                phvDealOfDay.addView(new HomePageDealOfDayItemList(mContext, image.prd_id, image.image, image.name, qtyspinner,
                        image.add_product_quantity_in_cart,image.discount_price,image.price));

            } else if ((image.options.equals("null")) && (image.weight_classes.equals("null"))) {
                phvDealOfDay.addView(new HomePageDealOfDayItemList(mContext, image.prd_id, image.image, image.name, qtyspinner,
                        image.add_product_quantity_in_cart,image.discount_price,image.price));
            }
        }
    }

    @Click(R.id.tvViewAll)
    public void onClick() {
        Intent intent = new Intent(mContext, HomeCategory.class);
        intent.putExtra("ViewAll", "2");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}