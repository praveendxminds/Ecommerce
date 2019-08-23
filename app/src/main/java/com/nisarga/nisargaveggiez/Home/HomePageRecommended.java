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
            phvRecommendedList.addView(new HomePageRecommendedItemList(mContext, image.product_id, image.image,
                    image.name, image.discount_price, image.add_product_quantity_in_cart));
        }
    }

    @Click(R.id.tvViewAll)
    public void onClick() {
        Intent intent = new Intent(mContext, HomeCategory.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}