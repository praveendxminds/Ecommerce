package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.retrofit.ProductslHomePage;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.util.List;


@NonReusable
@Layout(R.layout.home_page_recommended_for_you)
public class HomePageRecommended {

    @View(R.id.tvViewAll)
    public TextView tvViewAll;

    @View(R.id.phvRecommendedList)
    public PlaceHolderView phvRecommendedList;

    Context mContext;
    List<ProductslHomePage.RecommendedList> mImageList;
    SessionManager session;

    public HomePageRecommended(Context context, List<ProductslHomePage.RecommendedList> imageList) {
        this.mContext = context;
        this.mImageList = imageList;
    }

    @Resolve
    public void onResolved() {
        phvRecommendedList.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        for (ProductslHomePage.RecommendedList image : mImageList) {
            Object qtyspinner = "null";

            if ((image.options.equals("null")) && (!image.weight_classes.equals("null"))) {
                qtyspinner = image.weight_classes;
                phvRecommendedList.addView(new HomePageRecommendedItemList(mContext, image.prd_id, image.image,
                        image.name, qtyspinner, image.add_product_quantity_in_cart,image.discount_price,image.price));

            } else if ((!image.options.equals("null")) && (image.weight_classes.equals("null"))) {
                qtyspinner = image.options;
                phvRecommendedList.addView(new HomePageRecommendedItemList(mContext, image.prd_id, image.image,
                        image.name, qtyspinner, image.add_product_quantity_in_cart,image.discount_price,image.price));

            } else if ((image.options.equals("null")) && (image.weight_classes.equals("null"))) {
                phvRecommendedList.addView(new HomePageRecommendedItemList(mContext, image.prd_id, image.image,
                        image.name, qtyspinner, image.add_product_quantity_in_cart,image.discount_price,image.price));
            }
        }
    }

    @Click(R.id.tvViewAll)
    public void onClick() {
        Intent intent = new Intent(mContext, HomeCategory.class);
        intent.putExtra("ViewAll", "3");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}