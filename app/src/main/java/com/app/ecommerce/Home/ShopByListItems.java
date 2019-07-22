package com.app.ecommerce.Home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.app.ecommerce.Home3.ImageTypeSmall;
import com.app.ecommerce.R;
import com.app.ecommerce.Utils;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.ImageScroll;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sushmita 22/07/2019
 */


@NonReusable
@Layout(R.layout.shopby_list_item)
public class ShopByListItems {

    @View(R.id.pv_listView)
    public PlaceHolderView mPlaceHolderView;

    public Context mContext;

    APIInterface apiInterface;
    public static String[] strArrItems = {"item1","item2","item3","item4","item5","item6","item7","item8","item9"};

    public ShopByListItems(Context context) {
        mContext = context;
    }

    @Resolve
    public void onResolved() {
        mPlaceHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        for(int i=0;i<strArrItems.length;i++)
        {
            mPlaceHolderView.addView(new ShopByFeedItems(mContext,strArrItems[i]));
        }
        /*apiInterface = APIClient.getClient().create(APIInterface.class);

        if (Utils.CheckInternetConnection(mContext)) {


            Call<ImageScroll> call = apiInterface.doGetListImages();
            call.enqueue(new Callback<ImageScroll>() {
                @Override
                public void onResponse(Call<ImageScroll> call, Response<ImageScroll> response) {

                    ImageScroll resource = response.body();
                    List<ImageScroll.Images> datumList = resource.data;
                    for (ImageScroll.Images imgs : datumList) {
                        mPlaceHolderView.addView(new ImageTypeSmall(mContext, mPlaceHolderView, imgs.imageUrl, imgs.title, imgs.price, imgs.qty));
                    }

                }

                @Override
                public void onFailure(Call<ImageScroll> call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(mContext, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
*/

    }
}
