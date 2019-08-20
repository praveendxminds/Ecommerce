package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
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


@NonReusable
@Layout(R.layout.home_page_recommended_item_list)
public class HomePageRecommendedItemList {
    @View(R.id.llRecommended)
    public LinearLayout llRecommended;

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

    @View(R.id.qntyRecommended)
    public Spinner qntyRecommended;

    @View(R.id.llCountPrdRecomnd)
    public LinearLayout llCountPrdRecomnd;

    @View(R.id.imgBtn_decreRecomnd)
    public ImageButton imgBtn_decreRecomnd;

    @View(R.id.imgBtn_increRecomnd)
    public ImageButton imgBtn_increRecomnd;

    @View(R.id.tv_countRecomnd)
    public TextView tv_countRecomnd;

    @View(R.id.addcartRecommended)
    public Button addcartRecommended;


    public Context mContext;
    public PlaceHolderView mPlaceHolderView;
    public String productId;
    public String productImage;
    public String title;
    public String mPrice;
    public String mdisPrice;
    public String mQty;
    public String str_PriceValue, str_disValue;
    SessionManager session;
    public TextView mtextCartItemCount;
    String[] qtyArray = {"qty", "100gm", "200gm", "300gm", "50gm", "500gm", "1kg"};

    public Boolean status = true;
    int minteger = 0;
    public static String MyPREFERENCES = "sessiondata";
    public String imgUrl = "http://3.213.33.73/Ecommerce/upload/image/";
    SharedPreferences sharedpreferences;

    public HomePageRecommendedItemList(Context context, TextView textCartItemCount, PlaceHolderView placeHolderView, String product_id, String image,
                                       String name, String price, String discount, String qty) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        productId = product_id;
        productImage = image;
        title = name;
        mPrice = price;
        mdisPrice = discount;
        mQty = qty;
        mtextCartItemCount = textCartItemCount;
    }

    @Resolve
    public void onResolved() {
        Glide.with(mContext).load(productImage).into(imageViewRecommended);
        headingTxtRecommended.setText(title);
        double dbl_Price = Double.parseDouble(mPrice);//need to convert string to decimal
        str_PriceValue = String.format("%.2f", dbl_Price);//display only 2 decimal places of price
        item_NewPriceRecommended.setText("₹" + " " + str_PriceValue);

        if (mdisPrice.equals("null")) {
            item_OldPriceRecommended.setVisibility(android.view.View.INVISIBLE);
        } else {
            item_OldPriceRecommended.setVisibility(android.view.View.VISIBLE);
            double dbl_Discount = Double.parseDouble(mdisPrice);//need to convert string to decimal
            str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
            item_OldPriceRecommended.setText("₹" + " " + str_disValue);
        }

        qtyArray[0] = mQty;
        final List<String> qtyList = new ArrayList<>(Arrays.asList(qtyArray));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_list_home_page, qtyList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_list_home_page);
        qntyRecommended.setAdapter(spinnerArrayAdapter);


    }

    @Click(R.id.llRecommended)
    public void onLongClick() {
        Intent intent = new Intent(mContext, ProductDetailHome.class);
        intent.putExtra("product_id", productId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


    @Click(R.id.addcartRecommended)
    public void addtocart() {
        if (status == true) {
            session = new SessionManager(mContext);
            minteger = minteger + 1;//display number in place of add to cart
            Integer cnt = session.getCartCount();
            cnt = cnt + 1;//display number in cart icon
            session.cartcount(cnt);
            display(minteger);
            mtextCartItemCount.setText(String.valueOf(cnt));
            addcartRecommended.setVisibility(android.view.View.GONE);
            llCountPrdRecomnd.setVisibility(android.view.View.VISIBLE);
            status = false;
        }
    }

    @Click(R.id.fl_increase)
    public void onIncreaseClick() {
        session = new SessionManager(mContext);
        minteger = minteger + 1;//display number in place of add to cart
        Integer cnt = session.getCartCount();
        cnt = cnt + 1;//display number in cart icon
        session.cartcount(cnt);
        display(minteger);
        mtextCartItemCount.setText(String.valueOf(cnt));
    }

    @Click(R.id.fl_decrease)
    public void onDecreaseClick() {
        if (minteger <= 1) {
            minteger = minteger - 1;
            session = new SessionManager(mContext);
            Integer cnt = session.getCartCount();
            cnt = cnt - 1;
            session.cartcount(cnt);
            display(minteger);
            mtextCartItemCount.setText(String.valueOf(cnt));
            addcartRecommended.setVisibility(android.view.View.VISIBLE);
            llCountPrdRecomnd.setVisibility(android.view.View.GONE);
            status = true;

        } else {
            minteger = minteger - 1;
            session = new SessionManager(mContext);
            Integer cnt = session.getCartCount();
            cnt = cnt - 1;
            session.cartcount(cnt);
            display(minteger);
            mtextCartItemCount.setText(String.valueOf(cnt));
        }
    }

    public void display(int number) {
        tv_countRecomnd.setText("" + number);
    }
}
