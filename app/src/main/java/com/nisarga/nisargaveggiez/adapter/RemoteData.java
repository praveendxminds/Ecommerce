package com.nisarga.nisargaveggiez.adapter;

/**
 * Created by praveen on 28/11/18.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.nisarga.nisargaveggiez.ProfileSection.MyProfileModel;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.retrofit.ProductListModel;

public class RemoteData {
    private Context context;
    Inflater mInflater;


    public static final String BASE_URL = "http://3.213.33.73/Ecommerce/upload/";
    private static Retrofit retrofit = null;

    public RemoteData(Context contextIn) {
        context = contextIn;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public interface StoreDataService
    {
        @POST("index.php?route=api/productsearch/productsearch")
        Call<ProductDataWrapper> getStoreData(@Body ProductDataWrapper uid);
    }

    public void getStoreData(){




        final ProductDataWrapper myProfileModel = new ProductDataWrapper("92");
        retrofit.create(StoreDataService.class).getStoreData(myProfileModel)
                .enqueue(new Callback<ProductDataWrapper>() {

                    @Override
                    public void onResponse(Call<ProductDataWrapper> call,
                                           Response<ProductDataWrapper> response)
                    {

                        AutoCompleteTextView storeTV =
                                (AutoCompleteTextView)((Activity)context).findViewById(R.id.store);
                        AutoCompleteAdapter adapter = null;
                        ArrayList<AutocompleteModel> autocompletelst = null;

                        autocompletelst = new ArrayList<>();
                        storeTV.setThreshold(1);
                        storeTV.requestFocus();




                        for(ProductData s : response.body().getResult())
                        {
                           // str.add(s.getName());
                            autocompletelst.add(new AutocompleteModel(s.getName(), s.getImage(), new Integer(s.getPrdId()),s.getmeta_title(),s.getKeyword()));

                        }

                        adapter = new AutoCompleteAdapter(context, autocompletelst);
                        storeTV.setAdapter(adapter);


                    }
                    @Override
                    public void onFailure(Call<ProductDataWrapper> call, Throwable t) {
                        Log.e("Async Data RemoteData",
                                "error in getting remote data");
                    }
                });
    }



}
