package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.internal.BottomNavigationMenuView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.ecommerce.R;
import com.app.ecommerce.ProductDetails_act;
import com.app.ecommerce.SessionManager;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

import static com.app.ecommerce.Home2.HomeTwoCategory.bottomNavigationView;

/**
 * Created by sushmita
 */


@NonReusable
@Layout(R.layout.home_category_item)
public class HomeCategoryItem {

    @View(R.id.itemIconHomePage)
    public ImageView itemIconHomePage;

    @View(R.id.prd_name)
    public TextView prd_name;

    @View(R.id.lltvQnty)
    public LinearLayout lltvQnty;

    @View(R.id.tv_qntyHomePage)
    public TextView tv_qntyHomePage;

    @View(R.id.llspnrQnty)
    public LinearLayout llspnrQnty;

    @View(R.id.spnr_qntyHomePage)
    public Spinner spnr_qntyHomePage;

    @View(R.id.priceNewHomePage)
    public TextView priceNewHomePage;

    @View(R.id.priceOldHomePage)
    public TextView priceOldHomePage;

    @View(R.id.lladdItem)
    public LinearLayout lladdItem;

    @View(R.id.llcountItem)
    public LinearLayout llcountItem;

    @View(R.id.imgBtn_incre)
    public ImageButton imgBtn_inc;

    @View(R.id.imgBtn_decre)
    public ImageButton imgBtn_dec;

    @View(R.id.tv_productCount)
    public TextView tv_productCount;


    @ParentPosition
    public int mParentPosition;

    public String murl;
    public String mprice;
    public String mqty;
    public String mtitle;
    public String mid;
    public Context mContext;
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;
    UseSharedPreferences useSharedPreferences;
    String[] qtyArray = {"100gm","200gm","300gm","500gm","1kg","2kg","500kg","1000kg"};
    boolean status1 = true;
    SessionManager session;
    int countVal = 0;
    public TextView mtextCartItemCount;


    public HomeCategoryItem(Context context, TextView textCartItemCount, String id, String url, String title, String price, String qty) {
        mContext = context;
        mid = id;
        murl = url;
        mtitle = title;
        mprice = price;
        mqty = qty;
        mtextCartItemCount=textCartItemCount;
    }

    public String getTitle() {
        return mtitle;
    }

    public String getPrice() {
        return mprice;
    }

    public String getUrl() {
        return murl;
    }


    @Resolve
    public void onResolved() {
        prd_name.setText(mtitle);
        Glide.with(mContext).load(murl).into(itemIconHomePage);
        priceNewHomePage.setText("\u20B9" + " " + mprice);


        if (qtyArray.length > 1) {
            llspnrQnty.setVisibility(android.view.View.VISIBLE);
            lltvQnty.setVisibility(android.view.View.GONE);
            qtyArray[0]=mqty;
            final List<String> qtyList = new ArrayList<>(Arrays.asList(qtyArray));

            // Initializing an ArrayAdapter
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.spnr_listitem_categ, qtyList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_product_qtylist_home_two);
            spnr_qntyHomePage.setAdapter(spinnerArrayAdapter);



        } else {
            qtyArray[0]=mqty;
            tv_qntyHomePage.setText(qtyArray[0]);
            lltvQnty.setVisibility(android.view.View.VISIBLE);
            llspnrQnty.setVisibility(android.view.View.GONE);
        }


    }

    @Click(R.id.llProductsListView)
    public void onCardClick() {
        Intent myIntent = new Intent(mContext, ProductDetailHome.class);
        myIntent.putExtra("prd_id", mid);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }

    @Click(R.id.btnAddItem)
    public void AddToCartClick() {
        if (status1 == true) {
            session = new SessionManager(mContext);
            countVal = countVal + 1;//display number in place of add to cart
            Integer cnt = session.getCartCount();
            cnt = cnt +1;//display number in cart icon
            session.cartcount(cnt);
            display(countVal);
            mtextCartItemCount.setText(String.valueOf(cnt));
            lladdItem.setVisibility(android.view.View.GONE);
            llcountItem.setVisibility(android.view.View.VISIBLE);
            status1 = false;
        }
    }

    @Click(R.id.imgBtn_incre)
    public void AddItem() {
        session = new SessionManager(mContext);
        countVal = countVal + 1;//display number in place of add to cart
        Integer cnt = session.getCartCount();
        cnt = cnt + 1;//display number in cart icon
        session.cartcount(cnt);
        display(countVal);
        mtextCartItemCount.setText(String.valueOf(cnt));
    }

    @Click(R.id.imgBtn_decre)
    public void removeItem() {
        if (countVal <= 1) {
            countVal = countVal - 1;
            session = new SessionManager(mContext);
            Integer cnt = session.getCartCount();
            cnt = cnt - 1;
            session.cartcount(cnt);
            display(countVal);
            mtextCartItemCount.setText(String.valueOf(cnt));
            lladdItem.setVisibility(android.view.View.VISIBLE);
            llcountItem.setVisibility(android.view.View.GONE);
            status1=true;

        } else {
            countVal = countVal - 1;
            session = new SessionManager(mContext);
            Integer cnt = session.getCartCount();
            cnt = cnt - 1;
            session.cartcount(cnt);
            display(countVal);
            mtextCartItemCount.setText(String.valueOf(cnt));
        }
    }

    public void display(int number) {
        tv_productCount.setText("" + number);
    }

    public void countCartDisplay(int number) {

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        android.view.View v = bottomNavigationMenuView.getChildAt(4);
        Integer name_session = useSharedPreferences.getCountValue();

        new QBadgeView(mContext).bindTarget(v).setBadgeTextColor(mContext.getResources()
                .getColor(R.color.white)).setGravityOffset(15, -2, true)
                .setBadgeNumber(name_session).setBadgeBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));

    }


}
