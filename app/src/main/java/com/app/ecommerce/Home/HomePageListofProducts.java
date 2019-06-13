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
@Layout(R.layout.home_page_list_of_products)
public class HomePageListofProducts {

    @View(R.id.placeholderviewListProducts)
    public PlaceHolderView placeholderviewListProducts;

    public Context mContext;
    public List<ProductslHomePage.DealOfDayList> mImageList;

    public HomePageListofProducts(Context context, List<ProductslHomePage.DealOfDayList> imageList) {
        mContext = context;
        mImageList = imageList;
    }

    @Resolve
    public void onResolved() {
        placeholderviewListProducts.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        for(ProductslHomePage.DealOfDayList image : mImageList) {
            placeholderviewListProducts.addView(new HomePageDealOfDayItemList(mContext, placeholderviewListProducts, image.url,image.title,image.price,image.qty));
        }
    }
}