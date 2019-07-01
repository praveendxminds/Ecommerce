package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.v7.widget.CardView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    @View(R.id.llCountSimPrd)
    public LinearLayout llCountSimPrd;

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
    public String mHeading;
    public String mPrdImgUrl,mPrice,mQty;
    public Boolean status = true;
    int minteger = 0;
    public static String MyPREFERENCES = "sessiondata" ;
    public String imgUrl="http://3.213.33.73/Ecommerce/upload/image/";
    SharedPreferences sharedpreferences;
    String[] qtyArray = {"qty","100gm", "200gm", "300gm", "50gm", "500gm", "1kg"};

    public SimilarProductsListItem(Context context, TextView textCartItemCount,
                                   PlaceHolderView placeHolderView, String url, String heading, String price,String qty) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        mPrdImgUrl = url;
        mHeading = heading;
        mPrice = price;
        mQty = qty;
        mtextCartItemCount = textCartItemCount;
    }

    @Resolve
    public void onResolved() {
        Glide.with(mContext).load(imgUrl+mPrdImgUrl).into(imageCategorySimilarPrd);
        titleCategorySimilarPrd.setText(mHeading);
        newPriceCategorySimilarPrd.setText("₹"+" "+mPrice);
        qtyArray[0]=mQty;
        final List<String> qtyList = new ArrayList<>(Arrays.asList(qtyArray));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_product_qtylist_home_two, qtyList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_product_qtylist_home_two);
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
        if (status == true) {
            btnAddCategorySimilarPrd.setVisibility(android.view.View.GONE);
            llCountSimPrd.setVisibility(android.view.View.VISIBLE);
            status = false;

        }
        session = new SessionManager(mContext);
        Integer cnt = session.getCartCount();
        cnt = cnt +1;
        session.cartcount(cnt);

        mtextCartItemCount.setText(String.valueOf(cnt));

    }

    @Click(R.id.imgBtn_increSimPrd)
    public void onIncreaseClick() {
        minteger = minteger + 1;
        display(minteger);
    }

    @Click(R.id.imgBtn_decreSimPrd)
    public void onDecreaseClick() {

        if (minteger == 0) {
            display(0);
        } else {
            minteger = minteger - 1;
            display(minteger);
        }
    }


    public void display(int number) {
        tv_countSimPrd.setText("" + number);
    }


}
