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
    public String mQty;
    SessionManager session;
    public TextView mtextCartItemCount;
    String[] qtyArray = {"qty", "100gm", "200gm", "300gm", "50gm", "500gm", "1kg"};

    public Boolean status = true;
    int minteger = 0;
    public static String MyPREFERENCES = "sessiondata";
    public String imgUrl = "http://3.213.33.73/Ecommerce/upload/image/";
    SharedPreferences sharedpreferences;

    public HomePageRecommendedItemList(Context context, TextView textCartItemCount, PlaceHolderView placeHolderView, String product_id, String image,
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
        Glide.with(mContext).load(imgUrl + productImage).into(imageViewRecommended);
        headingTxtRecommended.setText(title);
        item_NewPriceRecommended.setText("₹" + " " + mPrice);
        qtyArray[0] = mQty;
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
    public void addtocart() {

        if (status == true) {
            addcartRecommended.setVisibility(android.view.View.GONE);
            llCountPrdRecomnd.setVisibility(android.view.View.VISIBLE);
            status = false;

        }
        session = new SessionManager(mContext);
        Integer cnt = session.getCartCount();
        cnt = cnt + 1;
        session.cartcount(cnt);

        mtextCartItemCount.setText(String.valueOf(cnt));

    }

    @Click(R.id.imgBtn_increRecomnd)
    public void onIncreaseClick() {
        minteger = minteger + 1;//display number in place of add to cart
        session = new SessionManager(mContext);
        Integer cnt = session.getCartCount();
        cnt = cnt +1;//display number in cart icon
        session.cartcount(cnt);
        display(minteger);
        mtextCartItemCount.setText(String.valueOf(cnt));
    }

    @Click(R.id.imgBtn_decreRecomnd)
    public void onDecreaseClick() {

        if (minteger == 0) {
            display(0);
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
        tv_countRecomnd.setText("" + number);
    }


}
