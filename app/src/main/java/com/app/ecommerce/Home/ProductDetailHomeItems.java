package com.app.ecommerce.Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.ecommerce.R;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sushmita
 */


@NonReusable
@Layout(R.layout.product_detail_home)
public class ProductDetailHomeItems {

    @View(R.id.prd_imgGrid)
    public ImageView prd_imgGrid;

    @View(R.id.titleGrid)
    public TextView titleGrid;

    @View(R.id.qntyGrid)
    public TextView qntyGrid;

    @View(R.id.original_priceGrid)
    public TextView original_priceGrid;

    @ParentPosition
    public int mParentPosition;

    public String murl;
    public String mprice;
    public String mqty;
    public String mtitle;
    public String mid;
    public Context mContext;

    public ProductDetailHomeItems(Context context, String id, String url, String title, String price, String qty) {
        mContext = context;
        mid = id;
        murl = url;
        mtitle = title;
        mprice = price;
        mqty = qty;
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
        titleGrid.setText(mtitle);
        Glide.with(mContext).load(murl).into(prd_imgGrid);
        original_priceGrid.setText("Rs." + " " + mprice);
        qntyGrid.setText(mqty);

       /* final List<String> qtyList = new ArrayList<>(Arrays.asList(qtyArray));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_product_qtylist_home_two, qtyList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_product_qtylist_home_two);
        qntyHomePage.setAdapter(spinnerArrayAdapter);

        useSharedPreferences = new UseSharedPreferences(mContext);
*/
    }
}
