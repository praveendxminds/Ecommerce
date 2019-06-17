package com.app.ecommerce.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.internal.BottomNavigationMenuView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.ecommerce.R;
import com.app.ecommerce.ProductDetails_act;
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
 * Created by sushmita on 17th June 2019
 */


@NonReusable
@Layout(R.layout.home_category_grid_item)
public class HomeCategoryItemGridView {

    @View(R.id.imageCategoryGrid)
    public ImageView imageCategoryGrid;

    @View(R.id.titleCategoryGrid)
    public TextView titleCategoryGrid;

    @View(R.id.newPriceCategoryGrid)
    public TextView newPriceCategoryGrid;

    @View(R.id.qtyCategoryGrid)
    public Spinner qtyCategoryGrid;

    @View(R.id.oldPriceCategoryGrid)
    public TextView oldPriceCategoryGrid;

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
    String[] qtyArray = {"100gm", "200gm", "300gm", "50gm", "500gm", "1kg"};


    public HomeCategoryItemGridView(Context context, String id, String url, String title, String price) {
        mContext = context;
        mid = id;
        murl = url;
        mtitle = title;
        mprice = price;
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
        titleCategoryGrid.setText(mtitle);
        Glide.with(mContext)
                .load(murl)
                .into(imageCategoryGrid);
        newPriceCategoryGrid.setText("\u20B9" + " " + mprice);


        final List<String> qtyList = new ArrayList<>(Arrays.asList(qtyArray));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_product_qtylist_home_two, qtyList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_product_qtylist_home_two);
        qtyCategoryGrid.setAdapter(spinnerArrayAdapter);

        useSharedPreferences = new UseSharedPreferences(mContext);

    }

    @Click(R.id.ord_itCategoryGrid)
    public void onCardClick() {
        Intent myIntent = new Intent(mContext, ProductDetailHome.class);
        myIntent.putExtra("prd_id", mid);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);

    }

    @Click(R.id.btnAddCategoryGrid)
    public void AddToCartClick() {
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Integer name_session = useSharedPreferences.getCountValue();

        Integer value = useSharedPreferences.createCountValue(name_session);
        Log.d("values of count", String.valueOf(value));
        countCartDisplay(value);
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
