package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mindorks.placeholderview.annotations.Click;
import com.nisarga.nisargaveggiez.R;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.InsertWishListItems;
import com.nisarga.nisargaveggiez.retrofit.RemoveWishListItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nisarga.nisargaveggiez.Home.ProductDetailHome.btnAddWishlist;

/**
 * Created by sushmita 26/06/2019
 */

@Parent
@SingleTop
@Layout(R.layout.image_slider_product_details)
public class ProductDetailsImageSlider {

    @View(R.id.pager_PrdDetails)
    public ViewPager pager_PrdDetails;

    @View(R.id.indicator_PrdDetails)
    public TabLayout indicator_PrdDetails;

    @View(R.id.btn_addtoWishlistPrdDetail)
    public static ImageButton btn_addtoWishlistPrdDetail;

    public ArrayList<String> XMENArray = new ArrayList<String>();
    public Button maddtoCartPrdDetail;

    @ParentPosition
    public int mParentPosition;

    Context mContext;
    SessionManager session;
    APIInterface apiInterface;

    public List<String> mCatImgUrl;
    String sWhishlistStatus;
    String sProductId;

    public ProductDetailsImageSlider(Context context, List<String> CatImgUrl, String wishlistStatus, String product_id) {
        mContext = context;
        mCatImgUrl = CatImgUrl;
        sWhishlistStatus = wishlistStatus;
        sProductId = product_id;

        for (int i = 0; i < mCatImgUrl.size(); i++) {
            XMENArray.add(mCatImgUrl.get(i));
            Log.e("---images----", String.valueOf(mCatImgUrl.get(i)));
        }
    }

    @Resolve
    public void onResolved() {
        session = new SessionManager(mContext);
        pager_PrdDetails.setAdapter(new ProductDetailImageSliderAdapter(mContext, XMENArray));
        indicator_PrdDetails.setupWithViewPager(pager_PrdDetails);//for tab layout

        if (sWhishlistStatus.equals("0")) {
            btn_addtoWishlistPrdDetail.setBackgroundResource(R.drawable.wishlistgrey);
        } else {
            btn_addtoWishlistPrdDetail.setBackgroundResource(R.drawable.wishlist_red);
            btn_addtoWishlistPrdDetail.setEnabled(false);
        }
    }

    @Click(R.id.btn_addtoWishlistPrdDetail)
    public void onClick() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        final InsertWishListItems add_item = new InsertWishListItems(session.getCustomerId(), sProductId);
        Call<InsertWishListItems> callAdd = apiInterface.addtoWishList(add_item);
        callAdd.enqueue(new Callback<InsertWishListItems>() {
            @Override
            public void onResponse(Call<InsertWishListItems> call, Response<InsertWishListItems> response) {
                InsertWishListItems resource = response.body();
                if (resource.status.equals("success")) {
                    Toast.makeText(mContext, "Added In Wishlist", Toast.LENGTH_LONG).show();
                    btn_addtoWishlistPrdDetail.setBackgroundResource(R.drawable.wishlist_red);
                    btn_addtoWishlistPrdDetail.setEnabled(false);
                    btnAddWishlist.setText("Added In Wishlist");
                    btnAddWishlist.setEnabled(false);
                    btnAddWishlist.setBackgroundResource(R.drawable.login_reg_border);
                    btnAddWishlist.setTextColor(mContext.getResources().getColor(R.color.black));
                } else {
                    Toast.makeText(mContext, resource.message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<InsertWishListItems> call, Throwable t) {
                call.cancel();
            }
        });
    }
}




