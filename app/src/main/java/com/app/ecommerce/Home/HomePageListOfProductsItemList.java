package com.app.ecommerce.Home;

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
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Toggle;

import q.rorbin.badgeview.QBadgeView;

import static com.app.ecommerce.Home1.HomeOne.bottomNavigationView;


@NonReusable
@Layout(R.layout.home_page_listproducts_item_list)
public class HomePageListOfProductsItemList {

    @View(R.id.imageViewListProduct)
    public ImageView imageViewListProduct;

    @View(R.id.headingTxtListProduct)
    public TextView headingTxtListProduct;

    @Toggle(R.id.productItemListProduct)
    public CardView productItemListProduct;

    @View(R.id.item_priceListProduct)
    public TextView item_priceListProduct;

    @View(R.id.integer_numberListProduct)
    public TextView integer_numberListProduct;

    @View(R.id.qntyListProduct)
    public TextView qntyListProduct;



    public String mUlr;
    public Context mContext;
    public PlaceHolderView mPlaceHolderView;
    public String mHeading;
    public String mPrdImgUrl,mPrice,mQty;
    int minteger = 0;
    int cart_count = 0;
    public static String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;

    public HomePageListOfProductsItemList(Context context, PlaceHolderView placeHolderView, String ulr, String heading, String price, String qty) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        mPrdImgUrl = ulr;
        mHeading = heading;
        mPrice = price;
        mQty = qty;
    }

    @Resolve
    public void onResolved() {
        Glide.with(mContext).load(mPrdImgUrl).into(imageViewListProduct);
        headingTxtListProduct.setText(mHeading);
        item_priceListProduct.setText("₹"+" "+mPrice);
        qntyListProduct.setText(mQty);

    }
    @Click(R.id.productItemDealofDay)
    public void onLongClick(){
        Intent intent = new Intent(mContext, ProductDetails_act.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
    @Click(R.id.addcartDealofDay)
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


    @Click(R.id.increaseDealofDay)
    public void onIncreaseClick() {
        minteger = minteger + 1;
        display(minteger);
    }

    @Click(R.id.decreaseDealofDay)
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
        integer_numberListProduct.setText("" + number);
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



    /*@LongClick(R.id.imageViewDealofDay)
    public void onLongClick(){
        mPlaceHolderView.removeView(this);
    }*/
}
