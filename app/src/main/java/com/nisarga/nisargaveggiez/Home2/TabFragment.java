package com.nisarga.nisargaveggiez.Home2;

/**
 * Created by praveen on 29/01/19.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.ProductsHomeTwo;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nisarga.nisargaveggiez.Utils.CheckInternetConnection;


public class TabFragment extends Fragment {

    APIInterface apiInterface;
    public Context mContext;
    private PlaceHolderView mCartView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_home_2, container, false);
        final FragmentActivity c = getActivity();//for recycler grid view
        mCartView = (PlaceHolderView) view.findViewById(R.id.recycler_order);

        mCartView.setLayoutManager(new GridLayoutManager(getContext(),2));//for recycler grid view


        apiInterface = APIClient.getClient().create(APIInterface.class);

        if(CheckInternetConnection(getActivity().getApplicationContext()))
        {


            Call<ProductsHomeTwo> call = apiInterface.doGetListProducts();
            call.enqueue(new Callback<ProductsHomeTwo>() {
                @Override
                public void onResponse(Call<ProductsHomeTwo> call, Response<ProductsHomeTwo> response) {

                    ProductsHomeTwo resource = response.body();
                    List<ProductsHomeTwo.tab> datumList = resource.data;
                    for (ProductsHomeTwo.tab imgs : datumList)
                    {
                        mCartView.addView(new HomeTwoProductItem(getActivity().getApplicationContext(),imgs.title,imgs.url,imgs.price,imgs.qty));
                        Log.e("--------mcartview--------***********",imgs.qty+"   "+imgs.price+"  "+imgs.title);
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




        return view;
    }
}
