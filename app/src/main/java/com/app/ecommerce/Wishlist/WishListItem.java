package com.app.ecommerce.Wishlist;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.ecommerce.R;
import com.app.ecommerce.productDetial;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;

/**
 * Created by sushmita 14/06/2019
 */

@Parent
@SingleTop
@Layout(R.layout.wishlist_items)
public class WishListItem {

    @View(R.id.prd_nameWishList)
    public TextView prd_nameWishList;

    @View(R.id.itemIconWishList)
    public ImageView itemIconWishList;

    @View(R.id.qntyWishList)
    public TextView qntyWishList;

    @View(R.id.priceNewWishList)
    public TextView priceNewWishList;


    @ParentPosition
    public int mParentPosition;

    public String murl;
    public String mprice;
    public String mqty;
    public String mtitle;
    public Context mContext;


    public WishListItem(Context context, String url, String title, String price,String qty) {
        mContext = context;
        murl = url;
        mtitle = title;
        mprice = price;
        mqty = qty;
    }

    public String getTitle() {
        return mtitle;
    }

    public String getPrice() {
        return mprice;
    }

    public String getUrl() {
        return murl;
    }


    @Resolve
    public void onResolved() {
        prd_nameWishList.setText(mtitle);
        Glide.with(mContext).load(murl).into(itemIconWishList);
        priceNewWishList.setText("\u20B9" + " " + mprice);
        qntyWishList.setText(mqty);

    }

    @Click(R.id.ord_itWishList)
    public void onCardClick() {
        Intent myIntent = new Intent(mContext, productDetial.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }
    @Click(R.id.btn_deleteWishList)
    public void deleteWishListItem()
    {

    }

    @Click(R.id.btn_movetoCartWishList)
    public void moveToCartClick() {
        /*sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Integer name_session = useSharedPreferences.getCountValue();

        Integer value = useSharedPreferences.createCountValue(name_session);
        Log.d("values of count", String.valueOf(value));
        countCartDisplay(value);*/
    }


}
