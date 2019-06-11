package com.app.ecommerce.Home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.app.ecommerce.Home2.HomeTwoCategoryItem;
import com.app.ecommerce.Home2.HomeTwoImageSlider;
import com.app.ecommerce.Home2.HomeTwoProductItem;
import com.app.ecommerce.MyOrder.orderItem;
import com.app.ecommerce.MyOrder.orderItemHeader;
import com.app.ecommerce.R;
import com.app.ecommerce.Utils;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.CategoriesHomeTwo;
import com.app.ecommerce.retrofit.ProductsHomeTwo;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.ecommerce.Utils.CheckInternetConnection;

/**
 * Created by praveen on 15/11/18.
 */


public class HomeCategory extends AppCompatActivity {

    Toolbar toolbar;
    private PlaceHolderView mCartView;
    APIInterface apiInterface;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_category);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AndroidNetworking.initialize(getApplicationContext());
        mContext = this.getApplicationContext();
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mCartView = (PlaceHolderView) findViewById(R.id.recycler_order);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (Utils.CheckInternetConnection(getApplicationContext()))
        {


            Call<ProductsHomeTwo> call = apiInterface.doGetListProducts();
            call.enqueue(new Callback<ProductsHomeTwo>() {
                @Override
                public void onResponse(Call<ProductsHomeTwo> call, Response<ProductsHomeTwo> response) {

                    ProductsHomeTwo resource = response.body();
                    List<ProductsHomeTwo.tab> datumList = resource.data;
                    for (ProductsHomeTwo.tab imgs : datumList)
                    {
                        mCartView.addView(new HomeCategoryItem(mContext,imgs.url,imgs.title,imgs.price));
                        Log.e("--------mcartview------",imgs.qty+"   "+imgs.price+"  "+imgs.title);
                    }

                }

                @Override
                public void onFailure(Call<ProductsHomeTwo> call, Throwable t) {
                    call.cancel();
                }
            });
        }
        else
        {
            Toast.makeText(mContext,"No Internet. Please check internet connection",Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}