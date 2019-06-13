package com.app.ecommerce.Home3;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.app.ecommerce.Home3.ImageTypeSmall;
import com.mindorks.placeholderview.Animation;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.app.ecommerce.Utils;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.ImageScroll;
import com.app.ecommerce.R;
import com.app.ecommerce.retrofit.APIInterface;


/**
 * Created by janisharali on 19/08/16.
 */


@NonReusable
@Layout(R.layout.feed_item)
public class ImageTypeSmallList {

    @View(R.id.galleryView)
    public PlaceHolderView mPlaceHolderView;

    public Context mContext;

    public Integer mid;
    APIInterface apiInterface;

    public ImageTypeSmallList(Context context,Integer id) {
        mContext = context;
        mid = id;
    }

    @Resolve
    public void onResolved() {
        mPlaceHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        apiInterface = APIClient.getClient().create(APIInterface.class);

        if(Utils.CheckInternetConnection(mContext))
        {


            Call<ImageScroll> call = apiInterface.doGetListImages();
        call.enqueue(new Callback<ImageScroll>() {
            @Override
            public void onResponse(Call<ImageScroll> call, Response<ImageScroll> response) {

                ImageScroll resource = response.body();
                List<ImageScroll.Images> datumList = resource.data;
                for (ImageScroll.Images imgs : datumList)
                {
                    mPlaceHolderView.addView(new ImageTypeSmall(mContext, mPlaceHolderView, imgs.imageUrl, imgs.title, imgs.price, imgs.qty));
                }

            }

            @Override
            public void onFailure(Call<ImageScroll> call, Throwable t) {
                call.cancel();
            }
        });
        }
        else
        {
            Toast.makeText(mContext,"No Internet. Please check internet connection",Toast.LENGTH_SHORT).show();
        }




    }
}
