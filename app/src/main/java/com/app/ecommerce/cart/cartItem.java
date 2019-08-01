package com.app.ecommerce.cart;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecommerce.Home.ProductDetailHome;
import com.app.ecommerce.SessionManager;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.InsertWishListItems;
import com.app.ecommerce.retrofit.RemoveWishListItem;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.app.ecommerce.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Sushmita on 24/06/2019.
 */


@NonReusable
@Layout(R.layout.cart_items)
public class cartItem {

    @View(R.id.itemIconMyCart)
    public ImageView itemIconMyCart;

    @View(R.id.prd_nameMyCart)
    public TextView prd_nameMyCart;

    @View(R.id.lltvQntyMyCart)
    public LinearLayout lltvQntyMyCart;

    @View(R.id.tvqntyMyCart)
    public TextView tvqntyMyCart;

    @View(R.id.llspnrQntyMyCart)
    public LinearLayout llspnrQntyMyCart;

    @View(R.id.spnr_qntyMyCart)
    public Spinner spnr_qntyMyCart;

    @View(R.id.tvpriceNewMyCart)
    public TextView tvpriceNewMyCart;

    @View(R.id.addWishListMyCart)
    public ImageButton addWishListMyCart;

    @View(R.id.tvProductCountMyCart)
    public TextView tvProductCountMyCart;

    @View(R.id.imgBtn_decreMyCart)
    public ImageButton imgBtnDecrMyCart;

    @View(R.id.imgBtn_increMyCart)
    public ImageButton imgBtnIncreMyCart;

    @View(R.id.lladdItemMyCart)
    public LinearLayout lladdItemMyCart;

    @View(R.id.llcountItemMyCart)
    public LinearLayout llcountItemMyCart;

    @View(R.id.btnAddItemMyCart)
    public ImageButton btnAddItemMyCart;

    public Context mcontext;
    int intCount = 0;
    public PlaceHolderView mplaceholderView;
    boolean statusCount = true;
    public static String MyPREFERENCES = "sessiondata";
    SessionManager session;
    public TextView mtextCartItemCount;
    public String mid;
    public String murl,mtitle,mprice,mqty;
    String[] qtyArray = {"100gm", "200gm", "300gm", "500gm", "1kg", "2kg", "500kg", "1000kg"};
    APIInterface apiInterface;
    public boolean chkState = false;


    public cartItem(Context context, TextView textCartItemCount, String id, String url, String title, String price, String qty) {
        mcontext = context;
        mid = id;
        murl = url;
        mtitle = title;
        mprice = price;
        mqty = qty;
        mtextCartItemCount = textCartItemCount;
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
        prd_nameMyCart.setText(mtitle);
        Glide.with(mcontext).load(murl).into(itemIconMyCart);
        tvpriceNewMyCart.setText(mprice);

        if (qtyArray.length > 1) {
            llspnrQntyMyCart.setVisibility(android.view.View.VISIBLE);
            lltvQntyMyCart.setVisibility(android.view.View.GONE);
            qtyArray[0] = mqty;
            final List<String> listQty = new ArrayList<>(Arrays.asList(qtyArray));
            //initializing array adapter
            final ArrayAdapter<String> arrayAdapterQty = new ArrayAdapter<String>(mcontext, R.layout.spnr_listitem_categ, listQty);
            arrayAdapterQty.setDropDownViewResource(R.layout.spinner_product_qtylist_home_two);
            spnr_qntyMyCart.setAdapter(arrayAdapterQty);
        } else {
            qtyArray[0] = mqty;
            tvqntyMyCart.setText(qtyArray[0]);
            lltvQntyMyCart.setVisibility(android.view.View.VISIBLE);
            llspnrQntyMyCart.setVisibility(android.view.View.GONE);
        }
    }

    @Click(R.id.llProductsListViewMyCart)
    public void onCardClick() {
        Intent myIntent = new Intent(mcontext, ProductDetailHome.class);
        myIntent.putExtra("prd_id", mid);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mcontext.startActivity(myIntent);

    }

    @Click(R.id.addWishListMyCart)
    public void onClick()
    {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        //---------------------------------------------------------
        if (chkState == false) {
            //------------------------------------------for adding to wishlist-----------------------------
            final InsertWishListItems add_item = new InsertWishListItems("1", mid);
            Call<InsertWishListItems> callAdd = apiInterface.addtoWishList(add_item);
            callAdd.enqueue(new Callback<InsertWishListItems>() {
                @Override
                public void onResponse(Call<InsertWishListItems> call, Response<InsertWishListItems> response) {
                    InsertWishListItems resource = response.body();
                    if (resource.status.equals("success")) {
                        Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                        addWishListMyCart.setBackgroundResource(R.drawable.wishlist_red);

                    } else {
                        Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<InsertWishListItems> call, Throwable t) {
                    call.cancel();
                }
            });
            chkState = true;
        } else {
            //---------------------for removing from wishlist---------------------------
            final RemoveWishListItem remove_item = new RemoveWishListItem("1", mid);
            Call<RemoveWishListItem> callRemove = apiInterface.removeWishListItem(remove_item);
            callRemove.enqueue(new Callback<RemoveWishListItem>() {
                @Override
                public void onResponse(Call<RemoveWishListItem> call, Response<RemoveWishListItem> response) {
                    RemoveWishListItem resource = response.body();
                    if (resource.status.equals("success")) {
                        Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                        addWishListMyCart.setBackgroundResource(R.drawable.wishlistgrey);
                    } else {
                        Toast.makeText(getApplicationContext(), resource.message, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RemoveWishListItem> call, Throwable t) {
                    call.cancel();
                }
            });
            chkState = false;

        }
    }

    @Click(R.id.btnAddItemMyCart)
    public void AddToCartClick() {
        if(statusCount == true) {
            intCount = intCount + 1;//display number in place of add to cart
            display(intCount);
            lladdItemMyCart.setVisibility(android.view.View.GONE);
            llcountItemMyCart.setVisibility(android.view.View.VISIBLE);
            statusCount = false;
        }
    }

    @Click(R.id.imgBtn_increMyCart)
    public void onIncreaseClick() {
        intCount = intCount + 1;//display number in place of add to cart
        display(intCount);
    }

    @Click(R.id.imgBtn_decreMyCart)
    public void onDecreaseClick() {
        if (intCount <= 1) {
            intCount = intCount - 1;
            display(intCount);
            lladdItemMyCart.setVisibility(android.view.View.VISIBLE);
            llcountItemMyCart.setVisibility(android.view.View.GONE);
            statusCount=true;

        } else {
            intCount = intCount - 1;
            display(intCount);
            }

    }

    public void display(int number) {
        tvProductCountMyCart.setText("" + number);
    }


}
