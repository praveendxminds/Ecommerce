package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.internal.BottomNavigationMenuView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ecommerce.ProductDetails_act;
import com.app.ecommerce.R;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import org.w3c.dom.Text;

import q.rorbin.badgeview.QBadgeView;

import static com.app.ecommerce.Home3.HomeThree.bottomNavigationView;

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
