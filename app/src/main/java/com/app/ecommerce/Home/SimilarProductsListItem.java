package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.Spinner;
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
@Layout(R.layout.similar_products_list)
public class SimilarProductsListItem {

    @View(R.id.imageCategorySimilarPrd)
    public ImageView imageCategorySimilarPrd;

    @View(R.id.titleCategorySimilarPrd)
    public TextView titleCategorySimilarPrd;

    @Toggle(R.id.ord_itCategorySimilarPrd)
    public CardView ord_itCategorySimilarPrd;

    @View(R.id.newPriceCategorySimilarPrd)
    public TextView newPriceCategorySimilarPrd;

    @View(R.id.oldPriceCategorySimilarPrd)
    public TextView oldPriceCategorySimilarPrd;

    @View(R.id.qtyCategorySimilarPrd)
    public Spinner qtyCategorySimilarPrd;



    public String mUlr;
    public Context mContext;
    public PlaceHolderView mPlaceHolderView;
    public String mHeading;
    public String mPrdImgUrl,mPrice,mQty;
    int minteger = 0;
    int cart_count = 0;
    public static String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;

    public SimilarProductsListItem(Context context, PlaceHolderView placeHolderView, String ulr, String heading, String price) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        mPrdImgUrl = ulr;
        mHeading = heading;
        mPrice = price;
    }

    @Resolve
    public void onResolved() {
        Glide.with(mContext).load(mPrdImgUrl).into(imageCategorySimilarPrd);
        titleCategorySimilarPrd.setText(mHeading);
        newPriceCategorySimilarPrd.setText("₹"+" "+mPrice);

    }
    @Click(R.id.ord_itCategorySimilarPrd)
    public void onLongClick(){
        Intent intent = new Intent(mContext, ProductDetailHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
    @Click(R.id.btnAddCategorySimilarPrd)
    public void AddToCartClick()
    {
      /*  sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Integer name_session = sharedpreferences.getInt("count", 0);

        //  cart_count = cart_count + 1;

        name_session = name_session +1;

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("count", 3);
        editor.commit();
*/

        countCartDisplay(3);
    }
    public void countCartDisplay(int number)
    {

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        android.view.View v = bottomNavigationMenuView.getChildAt(4);

        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Integer name_session = sharedpreferences.getInt("count", 0);

        new QBadgeView(mContext).bindTarget(v).setBadgeTextColor(mContext.getResources().getColor(R.color.white))
                .setGravityOffset(15, -2, true).setBadgeNumber(number)
                .setBadgeBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));

    }

}
