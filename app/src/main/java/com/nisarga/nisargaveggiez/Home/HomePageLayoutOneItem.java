package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.nisarga.nisargaveggiez.ProfileSection.QuantityList;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.AddCartNullSpinner;
import com.nisarga.nisargaveggiez.retrofit.AddToCartModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.nisarga.nisargaveggiez.Home.HomeCategory.cartItemCount;
import static com.nisarga.nisargaveggiez.Home.HomePage.hometotalCartItemCount;

/**
 * Created by sushmita
 */

@NonReusable
@Layout(R.layout.homepagelayoutone_item)
public class HomePageLayoutOneItem {

    @View(R.id.ivBanner)
    public ImageView ivBanner;

    Context mContext;
    String mId;
    String mImage;

    public HomePageLayoutOneItem(Context context, String imageUrl, String id) {

        this.mContext = context;
        this.mImage = imageUrl;
        this.mId = id;
    }

    @Resolve
    public void onResolved() {
        if (mImage != null && !mImage.isEmpty() && !mImage.equals("null")) {
            Glide.with(mContext).load(mImage).into(ivBanner);
        } else {
            Glide.with(mContext).load(R.drawable.englishitem).into(ivBanner);
        }

    }

    @Click(R.id.cardViewLT1)
    public void onCardClick() {
        Intent myIntent = new Intent(mContext, HomeCategory.class);
        myIntent.putExtra("ViewAll", mId);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);
    }
}
