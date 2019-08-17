package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;

public class AboutFragmentProductDetail extends Fragment {

    APIInterface apiInterface;
    public Context mContext;
    private TextView tvDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);
        tvDescription = view.findViewById(R.id.tvDescription);
  /*     tvDescription = view.findViewById(R.id.tvDescription);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (CheckInternetConnection(getActivity().getApplicationContext())) {
            final ProductDetailsModel productDetail = new ProductDetailsModel("46");
            Call<ProductDetailsModel> call = apiInterface.getProductDetails(productDetail);
            call.enqueue(new Callback<ProductDetailsModel>() {
                @Override
                public void onResponse(Call<ProductDetailsModel> call, Response<ProductDetailsModel> response) {
                    ProductDetailsModel productResponse = response.body();
                    List<ProductDetailsModel.Datum> datumList = productResponse.result;
                    for (ProductDetailsModel.Datum imgs : datumList) {
                        if (response.isSuccessful()) {
                            //tvDescription.setText(imgs.name);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProductDetailsModel> call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }*/

        return view;
    }
}
