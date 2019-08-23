package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.retrofit.ProductslHomePage;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.util.List;


@NonReusable
@Layout(R.layout.home_page_list_of_products)
public class HomePageListofProducts {

    @View(R.id.tvViewAll)
    public TextView tvViewAll;

    @View(R.id.phvProductList)
    public PlaceHolderView phvProductList;

    Context mContext;
    List<ProductslHomePage.Products> mImageList;

    public HomePageListofProducts(Context context, List<ProductslHomePage.Products> imageList) {
        this.mContext = context;
        this.mImageList = imageList;
    }

    @Resolve
    public void onResolved() {
        phvProductList.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        for (ProductslHomePage.Products image : mImageList) {
            phvProductList.addView(new HomePageListOfProductsItemList(mContext, image.product_id, image.image, image.name,
                    image.price, image.discount_price, image.quantity));
        }
    }

    @Click(R.id.tvViewAll)
    public void onClick() {
        Intent intent = new Intent(mContext, HomeCategory.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}