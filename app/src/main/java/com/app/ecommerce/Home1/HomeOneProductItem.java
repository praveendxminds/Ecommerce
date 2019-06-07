package com.app.ecommerce.Home1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.v7.widget.CardView;
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
import com.mindorks.placeholderview.annotations.expand.Toggle;

import q.rorbin.badgeview.QBadgeView;

import static com.app.ecommerce.Home1.HomeOne.bottomNavigationView;

/**
 * Created by praveen on 14/11/18.
 */

@Parent
@SingleTop
@Layout(R.layout.home_one_product_item)
public class HomeOneProductItem {

    @View(R.id.headingTxt)
    private TextView headingTxt;


    @View(R.id.itemIcon)
    private ImageView imageView;

    @Toggle(R.id.productItem)
    private CardView productItem;

    @View(R.id.item_price)
    private TextView item_price;

    @View(R.id.integer_number)
    private TextView integer_number;

    @View(R.id.qnty)
    private TextView item_qty;


    @ParentPosition
    private int mParentPosition;

    private Context mContext;
    private String mHeading;
    private String mPrdImgUrl,mPrice,mQty;
    int minteger = 0;
    int cart_count = 0;
    public static final String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;



    public HomeOneProductItem(Context context, String heading, String PrdImgUrl, String price, String qty) {
        mContext = context;
        mHeading = heading;
        mPrdImgUrl = PrdImgUrl;
        mPrice = price;
        mQty = qty;
    }

    @Resolve
    private void onResolved() {
        headingTxt.setText(mHeading);
        Glide.with(mContext).load(mPrdImgUrl).into(imageView);
        item_price.setText("₹"+" "+mPrice);
        item_qty.setText(mQty);

    }

    @Click(R.id.productItem)
    private void onLongClick(){
        Intent intent = new Intent(mContext, productDetial.class);
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
