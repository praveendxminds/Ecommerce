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
import com.app.ecommerce.ProductDetails_act;
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
@Layout(R.layout.home_page_listproducts_item_list)
public class HomePageListOfProductsItemList {

    @View(R.id.imageViewListProduct)
    public ImageView imageViewListProduct;

    @View(R.id.headingTxtListProduct)
    public TextView headingTxtListProduct;

    @Toggle(R.id.productItemListProduct)
    public CardView productItemListProduct;

    @View(R.id.item_NewPriceListProduct)
    public TextView item_NewPriceListProduct;

    @View(R.id.item_OldPriceListProduct)
    public TextView item_OldPriceListProduct;

    @View(R.id.qntyListProduct)
    public Spinner qntyListProduct;

    @View(R.id.llCountListPrd)
    public LinearLayout llCountListPrd;

    @View(R.id.imgBtn_decreListPrd)
    public ImageButton imgBtn_decreListPrd;

    @View(R.id.imgBtn_increListPrd)
    public ImageButton imgBtn_increListPrd;

    @View(R.id.tv_countListPrd)
    public TextView tv_countListPrd;

    @View(R.id.addcartListPrd)
    public Button addcartListPrd;




    public String mUlr;
    public Context mContext;
    public String productId;
    public PlaceHolderView mPlaceHolderView;
    public String mHeading;
    public String mPrdImgUrl,mPrice,mQty;
    SessionManager session;
    public Boolean status = true;
    int minteger = 0;
    public static String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;
    public TextView mtextCartItemCount;
    public String imgUrl="http://3.213.33.73/Ecommerce/upload/image/";
    String[] qtyArray = {"qty","100gm", "200gm", "300gm", "50gm", "500gm", "1kg"};

    public HomePageListOfProductsItemList(Context context,TextView textCartItemCount, PlaceHolderView placeHolderView, String product_id, String url, String heading, String price, String qty) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        productId=product_id;
        mPrdImgUrl = url;
        mHeading = heading;
        mPrice = price;
        mQty = qty;
        mtextCartItemCount = textCartItemCount;
    }

    @Resolve
    public void onResolved() {
        Glide.with(mContext).load(imgUrl+mPrdImgUrl).into(imageViewListProduct);
        headingTxtListProduct.setText(mHeading);
        item_NewPriceListProduct.setText("₹"+" "+mPrice);
        qtyArray[0]=mQty;
        final List<String> qtyList = new ArrayList<>(Arrays.asList(qtyArray));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_product_qtylist_home_two, qtyList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_product_qtylist_home_two);
        qntyListProduct.setAdapter(spinnerArrayAdapter);

    }
    @Click(R.id.productItemDealofDay)
    public void onLongClick(){
        Intent intent = new Intent(mContext, ProductDetailHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
    @Click(R.id.addcartListPrd)
    public void AddToCartClick()
    {
        if (status == true) {
            addcartListPrd.setVisibility(android.view.View.GONE);
            llCountListPrd.setVisibility(android.view.View.VISIBLE);
            status = false;

        }
        session = new SessionManager(mContext);
        Integer cnt = session.getCartCount();
        cnt = cnt + 1;
        session.cartcount(cnt);

        mtextCartItemCount.setText(String.valueOf(cnt));

    }

    @Click(R.id.imgBtn_increListPrd)
    public void onIncreaseClick() {
        minteger = minteger + 1;//display number in place of add to cart
        session = new SessionManager(mContext);
        Integer cnt = session.getCartCount();
        cnt = cnt +1;//display number in cart icon
        session.cartcount(cnt);
        display(minteger);
        mtextCartItemCount.setText(String.valueOf(cnt));
    }

    @Click(R.id.imgBtn_decreListPrd)
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
        tv_countListPrd.setText("" + number);
    }
}
