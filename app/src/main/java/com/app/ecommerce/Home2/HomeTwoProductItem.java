package com.app.ecommerce.Home2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ecommerce.R;
import com.app.ecommerce.ProductDetails_act;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.mindorks.placeholderview.annotations.expand.Toggle;

import q.rorbin.badgeview.QBadgeView;

import static com.app.ecommerce.Home2.HomeTwoCategory.bottomNavigationView;

/**
 * Created by praveen on 14/11/18.
 */

@Parent
@SingleTop
@Layout(R.layout.home_two_product_item)
public class HomeTwoProductItem {

    @View(R.id.headingTxt)
    public TextView headingTxt;


    @View(R.id.itemIcon)
    public ImageView imageView;

    @Toggle(R.id.productItem)
    public CardView productItem;

    @View(R.id.item_price)
    public TextView item_price;

    @View(R.id.integer_number)
    public TextView integer_number;

    @View(R.id.qnty)
    public TextView item_qty;


    @ParentPosition
    public int mParentPosition;

    public Context mContext;
    public String mHeading;
    public String mPrdImgUrl,mPrice,mQty;
    int minteger = 0;
    int cart_count = 0;
    public static String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;



    public HomeTwoProductItem(Context context, String heading, String PrdImgUrl, String price, String qty) {
        mContext = context;
        mHeading = heading;
        mPrdImgUrl = PrdImgUrl;
        mPrice = price;
        mQty = qty;
    }

    @Resolve
    public void onResolved() {
        headingTxt.setText(mHeading);
        Glide.with(mContext).load(mPrdImgUrl).into(imageView);
        item_price.setText("₹"+" "+mPrice);
        item_qty.setText(mQty);

    }

    @Click(R.id.productItem)
    public void onLongClick(){
        Intent intent = new Intent(mContext, ProductDetails_act.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Click(R.id.addcart)
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

        new QBadgeView(mContext).bindTarget(v).setBadgeTextColor(mContext.getResources().getColor(R.color.white)).setGravityOffset(15, -2, true).setBadgeNumber(number).setBadgeBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));

    }


}
