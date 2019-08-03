package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.app.ecommerce.R;
import com.app.ecommerce.retrofit.ProductslHomePage;
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

    @View(R.id.placeholderviewListProducts)
    public PlaceHolderView placeholderviewListProducts;

    public Context mContext;
    public List<ProductslHomePage.Products> mImageList;
    public TextView mtextCartItemCount;

    public HomePageListofProducts(Context context, TextView textCartItemCount, List<ProductslHomePage.Products> imageList) {
        mContext = context;
        mImageList = imageList;
        mtextCartItemCount = textCartItemCount;
    }

    @Resolve
    public void onResolved() {
        placeholderviewListProducts.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        for (ProductslHomePage.Products image : mImageList) {
            placeholderviewListProducts.addView(new HomePageListOfProductsItemList(mContext,mtextCartItemCount, placeholderviewListProducts,
                    image.product_id, image.image, image.name, image.price,image.discount_price, image.quantity));
        }
    }
    @Click(R.id.tv_seeAllPrd)
    public void onClick() {
        Intent intent = new Intent(mContext, HomeCategory.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}