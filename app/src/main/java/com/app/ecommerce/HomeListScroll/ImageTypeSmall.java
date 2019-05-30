package com.app.ecommerce.HomeListScroll;

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

import static com.app.ecommerce.MainActivity.bottomNavigationView;

/**
 * Created by janisharali on 19/08/16.
 */
//@Animate(Animation.CARD_TOP_IN_DESC)
@NonReusable
@Layout(R.layout.gallery_item_small)
public class ImageTypeSmall {

    @View(R.id.imageView)
    private ImageView imageView;

    @View(R.id.item_title)
    private TextView item_title;

    @View(R.id.item_price)
    private TextView item_price;

    @View(R.id.integer_number)
    private TextView integer_number;


    @View(R.id.qnty)
    private TextView item_qty;

    private String mUlr;
    private String mItem_title;
    private String mItem_price;
    private String mItem_qty;
    private Context mContext;
    private PlaceHolderView mPlaceHolderView;
    int minteger = 0;
    int cart_count = 0;
    public static final String MyPREFERENCES = "sessiondata" ;
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
    private void onResolved()
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
        minteger = minteger - 1;
        display(minteger);
    }


    private void display(int number)
    {
        integer_number.setText("" + number);
    }


    private void countCartDisplay(int number)
    {

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        android.view.View v = bottomNavigationMenuView.getChildAt(4);

        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Integer name_session = sharedpreferences.getInt("count", 0);

        new QBadgeView(mContext).bindTarget(v).setBadgeTextColor(mContext.getResources().getColor(R.color.white)).setGravityOffset(15, -2, true).setBadgeNumber(number).setBadgeBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));

    }


}
