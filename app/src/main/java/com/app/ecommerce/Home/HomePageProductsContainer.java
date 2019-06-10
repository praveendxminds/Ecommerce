package com.app.ecommerce.Home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.app.ecommerce.R;
import com.app.ecommerce.retrofit.ProductslHomePage;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.util.List;


@NonReusable
@Layout(R.layout.home_page_deal_of_day)
public class HomePageProductsContainer {

    @View(R.id.placeholderviewDealofDay)
    private PlaceHolderView placeholderviewDealofDay;

    private Context mContext;
    private List<ProductslHomePage.productslist> mImageList;

    public HomePageProductsContainer(Context context, List<ProductslHomePage.productslist> imageList) {
        mContext = context;
        mImageList = imageList;
    }

    @Resolve
    private void onResolved() {
        placeholderviewDealofDay.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        for(ProductslHomePage.productslist image : mImageList) {
            placeholderviewDealofDay.addView(new HomePageProductsItem(mContext, placeholderviewDealofDay, image.url,image.title,image.price,image.qty));
        }
    }
}