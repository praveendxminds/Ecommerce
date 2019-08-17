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

    @View(R.id.llCountPrd)
    public LinearLayout llCountPrd;

    @View(R.id.imgBtn_decreSimPrd)
    public ImageButton imgBtn_decreSimPrd;

    @View(R.id.imgBtn_increSimPrd)
    public ImageButton imgBtn_increSimPrd;

    @View(R.id.tv_countSimPrd)
    public TextView tv_countSimPrd;

    @View(R.id.btnAddCategorySimilarPrd)
    public Button btnAddCategorySimilarPrd;



    SessionManager session;

    public String mUlr;
    public Context mContext;
    public PlaceHolderView mPlaceHolderView;
    public TextView mtextCartItemCount;
    public String mrelated_id;
    public String mPrd_id;
    public String mHeading;
    public String mPrdImgUrl,mPrice,mQty;
    public String mdiscount;
    public Boolean status = true;
    int minteger = 0;
    public static String MyPREFERENCES = "sessiondata" ;
    public String imgUrl="http://3.213.33.73/Ecommerce/upload/image/";
    SharedPreferences sharedpreferences;
    String[] qtyArray = {"qty","100gm", "200gm", "300gm", "50gm", "500gm", "1kg"};
    public String str_priceValue,str_disValue;

    public SimilarProductsListItem(Context context,TextView textCartItemCount,
                                   PlaceHolderView placeHolderView, String related_id, String prd_id,
                                   String url, String heading, String price,String qty) {
        mContext = context;
        mtextCartItemCount = textCartItemCount;
        mPlaceHolderView = placeHolderView;
        mrelated_id = related_id;
        mPrd_id = prd_id;
        mPrdImgUrl = url;
        mHeading = heading;
        //mdiscount=discount;
        mPrice = price;
        mQty = qty;

    }

    @Resolve
    public void onResolved() {
        Glide.with(mContext).load(imgUrl+mPrdImgUrl).into(imageCategorySimilarPrd);
        titleCategorySimilarPrd.setText(mHeading);

        double dbl_Price = Double.parseDouble(mPrice);//need to convert string to decimal
        str_priceValue = String.format("%.2f",dbl_Price);//display only 2 decimal places of price
        newPriceCategorySimilarPrd.setText("₹"+" "+str_priceValue);

      /*  if(mdiscount.equals("null")) {
            oldPriceCategorySimilarPrd.setVisibility(android.view.View.INVISIBLE);
        }
        else {
            double dbl_Discount = Double.parseDouble(mdiscount);//need to convert string to decimal
            str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
            oldPriceCategorySimilarPrd.setVisibility(android.view.View.VISIBLE);
            oldPriceCategorySimilarPrd.setText("₹" + " " + str_disValue);
        }*/
        qtyArray[0]=mQty;
        final List<String> qtyList = new ArrayList<>(Arrays.asList(qtyArray));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_list_home_page, qtyList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_list_home_page);
        qtyCategorySimilarPrd.setAdapter(spinnerArrayAdapter);




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
        if(status == true)
        {
            session = new SessionManager(mContext);
            minteger = minteger + 1;//display number in place of add to cart
            Integer cnt = session.getCartCount();
            cnt = cnt +1;//display number in cart icon
            session.cartcount(cnt);
            display(minteger);
            mtextCartItemCount.setText(String.valueOf(cnt));
            btnAddCategorySimilarPrd.setVisibility(android.view.View.GONE);
            llCountPrd.setVisibility(android.view.View.VISIBLE);
            status = false;

        }

    }

    @Click(R.id.imgBtn_increSimPrd)
    public void onIncreaseClick() {
        //fl_increase.setBackgroundColor(Color.parseColor("#ffbb00")); //obackground color change on touch event
        session = new SessionManager(mContext);
        minteger = minteger + 1;//display number in place of add to cart
        Integer cnt = session.getCartCount();
        cnt = cnt +1;//display number in cart icon
        session.cartcount(cnt);
        display(minteger);
        mtextCartItemCount.setText(String.valueOf(cnt));
    }

    @Click(R.id.imgBtn_decreSimPrd)
    public void onDecreaseClick() {

        if (minteger <= 1) {
            minteger = minteger - 1;
            session = new SessionManager(mContext);
            Integer cnt = session.getCartCount();
            cnt = cnt -1;
            session.cartcount(cnt);
            display(minteger);
            mtextCartItemCount.setText(String.valueOf(cnt));
            btnAddCategorySimilarPrd.setVisibility(android.view.View.VISIBLE);
            llCountPrd.setVisibility(android.view.View.GONE);
            status=true;

        } else {
            minteger = minteger - 1;
            session = new SessionManager(mContext);
            Integer cnt = session.getCartCount();
            cnt = cnt -1;
            session.cartcount(cnt);
            display(minteger);
            mtextCartItemCount.setText(String.valueOf(cnt));
        }
    }


    public void display(int number) {
        tv_countSimPrd.setText("" + number);
    }


}
