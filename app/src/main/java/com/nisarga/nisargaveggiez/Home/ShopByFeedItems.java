package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.R;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

/**
 * Created by janisharali on 19/08/16.
 */

@NonReusable
@Layout(R.layout.shopbyfeeditems)
public class ShopByFeedItems {

    @View(R.id.tv_listItems)
    public TextView tv_listItems;

    public Context mContext;
    public String categoryId;
    public String productId;
    public String productName;

    public ShopByFeedItems(Context context, String category_id, String product_id, String product_name) {
        this.mContext = context;
        this.categoryId = category_id;
        this.productId = product_id;
        this.productName = product_name;
    }

    @Resolve
    public void onResolved() {
        tv_listItems.setText(productName);
    }

    @Click(R.id.tv_listItems)
    public void onClick() {
        Intent intentPrDDetail = new Intent(mContext, ProductDetailHome.class);
        intentPrDDetail.putExtra("product_id",productId);
        intentPrDDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intentPrDDetail);
    }

}
