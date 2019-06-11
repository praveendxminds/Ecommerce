package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.design.internal.BottomNavigationMenuView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.app.ecommerce.MyOrder.OrderDetial;
import com.app.ecommerce.R;
import com.app.ecommerce.productDetial;
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
import static com.app.ecommerce.R.layout.spinner_product_qtylist_home_two;

/**
 * Created by praveen on 19/12/18.
 */


@NonReusable
@Layout(R.layout.home_category_item)
public class HomeCategoryItem {

    @View(R.id.itemIconHomePage)
    private ImageView itemIconHomePage;

    @View(R.id.headingTxtHomePage)
    private TextView headingTxtHomePage;

    @View(R.id.qntyHomePage)
    private Spinner qntyHomePage;

    @View(R.id.priceNewHomePage)
    private TextView priceNewHomePage;

    @ParentPosition
    private int mParentPosition;

   private String murl,mprice,mqty,mtitle;
    private Context mContext;
    public static final String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;
    final String[] qtyArray = {"100gm", "200gm", "300gm","50gm","500gm","1kg"};


    public HomeCategoryItem(Context context, String url,String title,String price) {
        mContext = context;
        murl = url;
        mtitle = title;
        mprice = price;
    }

    @Resolve
    private void onResolved() {
        headingTxtHomePage.setText(mtitle+", Organically grown");
        Glide.with(mContext).load(murl).into(itemIconHomePage);
        priceNewHomePage.setText("Rs."+" "+mprice);

        final List<String> qtyList = new ArrayList<>(Arrays.asList(qtyArray));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext,R.layout.spinner_product_qtylist_home_two,qtyList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_product_qtylist_home_two);
        qntyHomePage.setAdapter(spinnerArrayAdapter);

    }

    @Click(R.id.ord_itHomePage)
    public void onCardClick()
    {
        Intent myIntent = new Intent(mContext, productDetial.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }
    @Click(R.id.addcartHomePage)
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
    private void countCartDisplay(int number)
    {

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        android.view.View v = bottomNavigationMenuView.getChildAt(4);

        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Integer name_session = sharedpreferences.getInt("count", 0);

        new QBadgeView(mContext).bindTarget(v).setBadgeTextColor(mContext.getResources().getColor(R.color.white)).setGravityOffset(15, -2, true).setBadgeNumber(number).setBadgeBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));

    }


}
