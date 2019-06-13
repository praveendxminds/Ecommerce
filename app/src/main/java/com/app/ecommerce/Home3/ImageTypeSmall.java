package com.app.ecommerce.Home3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.internal.BottomNavigationMenuView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import q.rorbin.badgeview.QBadgeView;
import com.app.ecommerce.productDetial;
import com.app.ecommerce.R;

import static com.app.ecommerce.Home3.HomeThree.bottomNavigationView;


/**
 * Created by janisharali on 19/08/16.
 */
//@Animate(Animation.CARD_TOP_IN_DESC)
@NonReusable
@Layout(R.layout.gallery_item_small)
public class ImageTypeSmall {

    @View(R.id.imageView)
    public ImageView imageView;

    @View(R.id.item_title)
    public TextView item_title;

    @View(R.id.item_price)
    public TextView item_price;

    @View(R.id.integer_number)
    public TextView integer_number;


    @View(R.id.qnty)
    public TextView item_qty;

    public String mUlr;
    public String mItem_title;
    public String mItem_price;
    public String mItem_qty;
    public Context mContext;
    public PlaceHolderView mPlaceHolderView;
    int minteger = 0;
    int cart_count = 0;
    public static String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;

    public ImageTypeSmall(Context context, PlaceHolderView placeHolderView, String ulr, String title, String price, String qty) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        mUlr = ulr;
        mItem_title = title;
        mItem_price = price;
        mItem_qty = qty;



    }

    @Resolve
    public void onResolved()
    {

        Glide.with(mContext).load(mUlr).into(imageView);
        item_title.setText(mItem_title);
        item_price.setText("₹"+" "+mItem_price);
        item_qty.setText(mItem_qty);


    }


    @Click(R.id.cart)
    public void AddToCartClick()
    {
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Integer name_session = sharedpreferences.getInt("count", 0);

        //  cart_count = cart_count + 1;

        name_session = name_session +1;

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("count", name_session);
        editor.commit();


        countCartDisplay(name_session);
    }

    @Click(R.id.imageView)
    public void ProductDetail()
    {
        Intent accountIntent = new Intent(mContext, productDetial.class);
        accountIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(accountIntent);
    }

    @Click(R.id.increase)
    public void onIncreaseClick() {
        minteger = minteger + 1;
        display(minteger);
    }

    @Click(R.id.decrease)
    public void onDecreaseClick() {
        if(minteger==0)
        {
            display(0);
        }
        else {
            minteger = minteger - 1;
            display(minteger);
        }
    }


    public void display(int number)
    {
        integer_number.setText("" + number);
    }


    public void countCartDisplay(int number)
    {

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        android.view.View v = bottomNavigationMenuView.getChildAt(4);

        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Integer name_session = sharedpreferences.getInt("count", 0);

        new QBadgeView(mContext).bindTarget(v).setBadgeTextColor(mContext.getResources()
                .getColor(R.color.white)).setGravityOffset(15, -2, true)
                .setBadgeNumber(number).setBadgeBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));

    }


}
