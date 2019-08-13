package com.app.ecommerce.adapter;

/**
 * Created by praveen on 28/11/18.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import com.app.ecommerce.R;
import com.mindorks.placeholderview.ViewHolder;

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

    public interface StoreDataService {
        @POST("index.php?route=api/productsearch/productsearch")
        Call<ProductDataWrapper> getStoreData(@Body ProductDataWrapper str);
    }

    public void getStoreData(String strSearch) {

        final ProductDataWrapper prdDataWrapper = new ProductDataWrapper(strSearch);
        retrofit.create(StoreDataService.class).getStoreData(prdDataWrapper)
                .enqueue(new Callback<ProductDataWrapper>() {

                    @Override
                    public void onResponse(Call<ProductDataWrapper> call,
                                           Response<ProductDataWrapper> response) {

                        Log.d("Async Data RemoteData",
                                "Got REMOTE DATA " + response.body().getResult().size());

                        List<String> str = new ArrayList<String>();
                        for (ProductData s : response.body().getResult()) {
                            //str.add(s.getImage());
                            str.add(s.getName());
                        }

                        AutoCompleteTextView storeTV =
                                (AutoCompleteTextView) ((Activity) context).findViewById(android.support.v7.appcompat.R.id.search_src_text);

                        //   ListView lvstoreTV =
                        //    (ListView)((Activity)context).findViewById(R.id.lv);


                        //    ArrayAdapter<String> adapteo = new ArrayAdapter<String>(context,
                        //   android.R.layout.simple_dropdown_item_1line, str.toArray(new String[0]));

                        // ArrayAdapter<String> adapteo = new ArrayAdapter<String>(context,R.layout.custom_search_item, str.toArray(new String[0]));


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                android.R.layout.simple_list_item_1, str.toArray(new String[0])) {

                            @Override
                            public View getView(final int position, View convertView, ViewGroup parent) {
                             View view = super.getView(position, convertView, parent);
                               TextView text = (TextView) view.findViewById(android.R.id.text1);
                                text.setBackgroundResource(R.drawable.searchshadow);
                                text.setTextColor(Color.WHITE);
                                return view;
                            }
                        };

                        storeTV.setAdapter(adapter);
                        //  lvstoreTV.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<ProductDataWrapper> call, Throwable t) {
                        Log.e("Async Data RemoteData",
                                "error in getting remote data");
                    }
                });
    }
}
