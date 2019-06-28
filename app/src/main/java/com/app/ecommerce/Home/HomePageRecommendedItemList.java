package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.v7.widget.CardView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.ecommerce.R;
import com.app.ecommerce.SessionManager;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Toggle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @View(R.id.item_NewPriceRecommended)
    public TextView item_NewPriceRecommended;

    @View(R.id.item_OldPriceRecommended)
    public TextView item_OldPriceRecommended;

 /*   @View(R.id.integer_numberRecommended)
    public TextView integer_numberRecommended;
*/
    @View(R.id.qntyRecommended)
    public Spinner qntyRecommended;

    public Context mContext;
    public PlaceHolderView mPlaceHolderView;
    public String productId;
    public String productImage;
    public String title;
    public String mPrice;
    public String mQty;
    SessionManager session;
    public TextView mtextCartItemCount;
    String[] qtyArray = {"qty","100gm", "200gm", "300gm", "50gm", "500gm", "1kg"};


  /*  int minteger = 0;*/
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;

    public HomePageRecommendedItemList(Context context,TextView textCartItemCount, PlaceHolderView placeHolderView, String product_id, String image,
                                       String name, String price, String qty) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        productId = product_id;
        productImage = image;
        title = name;
        mPrice = price;
        mQty = qty;
        mtextCartItemCount = textCartItemCount;
    }

    @Resolve
    public void onResolved() {
        Glide.with(mContext).load(productImage).into(imageViewRecommended);
        headingTxtRecommended.setText(title);
        item_NewPriceRecommended.setText("₹" + " " + mPrice);
        qtyArray[0]=mQty;
        final List<String> qtyList = new ArrayList<>(Arrays.asList(qtyArray));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_product_qtylist_home_two, qtyList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_product_qtylist_home_two);
        qntyRecommended.setAdapter(spinnerArrayAdapter);


    }

    @Click(R.id.productItemRecommended)
    public void onLongClick() {
        Intent intent = new Intent(mContext, ProductDetailHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Click(R.id.addcartRecommended)
    public void AddToCartClick()
    {
        session = new SessionManager(mContext);
        Integer cnt = session.getCartCount();
        cnt = cnt +1;
        session.cartcount(cnt);
        mtextCartItemCount.setText(String.valueOf(cnt));
    }

/*
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

    }*/
}
