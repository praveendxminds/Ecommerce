package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.ecommerce.R;
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
@Layout(R.layout.home_page_recommended_item_list)
public class HomePageRecommendedItemList {

    @View(R.id.imageViewRecommended)
    public ImageView imageViewRecommended;

    @View(R.id.headingTxtRecommended)
    public TextView headingTxtRecommended;

    @Toggle(R.id.productItemRecommended)
    public CardView productItemRecommended;

    @View(R.id.item_priceRecommended)
    public TextView item_priceRecommended;

    @View(R.id.integer_numberRecommended)
    public TextView integer_numberRecommended;

    @View(R.id.qntyRecommended)
    public TextView qntyRecommended;

    public Context mContext;
    public PlaceHolderView mPlaceHolderView;
    public String productId;
    public String productImage;
    public String title;
    public String mPrice;
    public String mQty;

    int minteger = 0;
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;

    public HomePageRecommendedItemList(Context context, PlaceHolderView placeHolderView, String product_id, String image,
                                       String name, String price, String qty) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        productId = product_id;
        productImage = image;
        title = name;
        mPrice = price;
        mQty = qty;
    }

    @Resolve
    public void onResolved() {
        Glide.with(mContext).load(productImage).into(imageViewRecommended);
        headingTxtRecommended.setText(title);
        item_priceRecommended.setText("₹" + " " + mPrice);
        qntyRecommended.setText(mQty);

    }

    @Click(R.id.productItemRecommended)
    public void onLongClick() {
        Intent intent = new Intent(mContext, ProductDetailHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Click(R.id.addcartRecommended)
    public void AddToCartClick() {
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Integer name_session = sharedpreferences.getInt("count", 0);

        name_session = name_session + 1;

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("count", name_session);
        editor.commit();

        countCartDisplay(name_session);
    }


    @Click(R.id.increaseRecommended)
    public void onIncreaseClick() {
        minteger = minteger + 1;
        display(minteger);
    }

    @Click(R.id.decreaseRecommended)
    public void onDecreaseClick() {
        if (minteger == 0) {
            display(0);
        } else {
            minteger = minteger - 1;
            display(minteger);
        }
    }

    public void display(int number) {
        integer_numberRecommended.setText("" + number);
    }

    public void countCartDisplay(int number) {
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        android.view.View v = bottomNavigationMenuView.getChildAt(4);

        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Integer name_session = sharedpreferences.getInt("count", 0);

        new QBadgeView(mContext).bindTarget(v).setBadgeTextColor(mContext.getResources().getColor(R.color.white))
                .setGravityOffset(15, -2, true).setBadgeNumber(number).setBadgeBackgroundColor
                (mContext.getResources().getColor(R.color.colorPrimaryDark));

    }
}
