package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.AddToCartModel;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Toggle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


@NonReusable
@Layout(R.layout.home_page_dealofday_item_list)
public class HomePageDealOfDayItemList {

    @View(R.id.llDealOfDay)
    public LinearLayout llDealOfDay;

    @View(R.id.imageViewDealofDay)
    public ImageView imageViewDealofDay;

    @View(R.id.headingTxtDealofDay)
    public TextView headingTxtDealofDay;

    @Toggle(R.id.productItemDealofDay)
    public CardView productItemDealofDay;

    @View(R.id.item_NewpriceDealofDay)
    public TextView item_priceDealofDay;

    @View(R.id.item_OldpriceDealofDay)
    public TextView item_OldpriceDealofDay;

    @View(R.id.qtydealSpinner)
    public Spinner qtyCategoryGrid;

    @View(R.id.llCountPrd)
    public LinearLayout llCountPrd;

    @View(R.id.decreaseDealofDay)
    public ImageButton decreaseDealofDay;

    @View(R.id.increaseDealofDay)
    public ImageButton increaseDealofDay;

    @View(R.id.tv_countDealofDay)
    public TextView tv_countDealofDay;

    @View(R.id.addcartDealofDay)
    public Button addcartDealofDay;

    APIInterface apiInterface;
    SessionManager session;
    public String getPrdId;
    public Context mContext;
    public String productId;
    public PlaceHolderView mPlaceHolderView;
    public String mHeading;
    public String mPrdImgUrl, mPrice, mQty,str_priceValue,str_disValue,mdiscount;
    public Boolean status = true;
    int minteger = 0;
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;
    public TextView mtextCartItemCount;
    public String imgUrl = "http://3.213.33.73/Ecommerce/upload/image/";
    String[] qtyArray = {"qty","100gm", "200gm", "300gm", "50gm", "500gm", "1kg"};
    public String prdQty;

    public HomePageDealOfDayItemList(Context context,TextView textCartItemCount, PlaceHolderView placeHolderView,String product_id, String url, String heading,
                                     String price, String discount, String qty) {
        mContext = context;
        mPlaceHolderView = placeHolderView;
        productId=product_id;
        mPrdImgUrl = url;
        mHeading = heading;
        mPrice = price;
        mdiscount=discount;
        mQty = qty;
        mtextCartItemCount = textCartItemCount;
    }

    @Resolve
    public void onResolved() {
        session = new SessionManager(mContext);
        Glide.with(mContext).load(mPrdImgUrl).into(imageViewDealofDay);
        headingTxtDealofDay.setText(mHeading);

        double dbl_Price = Double.parseDouble(mPrice);//need to convert string to decimal
        str_priceValue = String.format("%.2f",dbl_Price);//display only 2 decimal places of price
        item_priceDealofDay.setText("₹" + " " + str_priceValue);

        if(mdiscount.equals("null"))
        {
        item_OldpriceDealofDay.setVisibility(android.view.View.INVISIBLE);
        }
        else {
            double dbl_Discount = Double.parseDouble(mdiscount);//need to convert string to decimal
            str_disValue = String.format("%.2f", dbl_Discount);//display only 2 decimal places of price
            item_OldpriceDealofDay.setVisibility(android.view.View.VISIBLE);
            item_OldpriceDealofDay.setText("₹" + " " + str_disValue);
        }
        qtyArray[0]=mQty;
        final List<String> qtyList = new ArrayList<>(Arrays.asList(qtyArray));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_list_home_page, qtyList);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_list_home_page);
        qtyCategoryGrid.setAdapter(spinnerArrayAdapter);


    }

    @Click(R.id.llDealOfDay)
    public void onLongClick() {
        Intent intent = new Intent(mContext, ProductDetailHome.class);
        intent.putExtra("product_id",productId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


    @Click(R.id.addcartDealofDay)
    public void addtocart()
    {
        if(status == true) {
            session = new SessionManager(mContext);
            minteger = minteger + 1;//display number in place of add to cart
            Integer cnt = session.getCartCount();
            cnt = cnt + 1;//display number in cart icon
            session.cartcount(cnt);
            display(minteger);
            mtextCartItemCount.setText(String.valueOf(cnt));
            addcartDealofDay.setVisibility(android.view.View.GONE);
            llCountPrd.setVisibility(android.view.View.VISIBLE);
            prdQty = Integer.toString(minteger);
            apiInterface = APIClient.getClient().create(APIInterface.class);
            //------------------------------------------for adding to wishlist-----------------------------
            final AddToCartModel add_item = new AddToCartModel(prdQty, productId);
            Call<AddToCartModel> callAdd = apiInterface.callAddToCart(add_item);
            callAdd.enqueue(new Callback<AddToCartModel>() {
                @Override
                public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                    AddToCartModel resource = response.body();
                    Toast.makeText(getApplicationContext(), resource.status, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<AddToCartModel> call, Throwable t) {
                    call.cancel();
                }
            });
            status = false;
        }
    }

    @Click(R.id.fl_increaseDealofDay)
    public void onIncreaseClick() {
        //fl_increase.setBackgroundColor(Color.parseColor("#ffbb00")); //obackground color change on touch event
        session = new SessionManager(mContext);
        minteger = minteger + 1;//display number in place of add to cart
        Integer cnt = session.getCartCount();
        cnt = cnt +1;//display number in cart icon
        session.cartcount(cnt);
        display(minteger);
        mtextCartItemCount.setText(String.valueOf(cnt));
    }

    @Click(R.id.fl_decreaseDealofDay)
    public void onDecreaseClick() {

        if (minteger <= 1) {
            minteger = minteger - 1;
            session = new SessionManager(mContext);
            Integer cnt = session.getCartCount();
            cnt = cnt -1;
            session.cartcount(cnt);
            display(minteger);
            mtextCartItemCount.setText(String.valueOf(cnt));
            addcartDealofDay.setVisibility(android.view.View.VISIBLE);
            llCountPrd.setVisibility(android.view.View.GONE);
            status=true;

        } else {
           minteger = minteger - 1;
            session = new SessionManager(mContext);
           Integer cnt = session.getCartCount();
            cnt = cnt -1;
            session.cartcount(cnt);
            display(minteger);
            mtextCartItemCount.setText(String.valueOf(cnt));
        }
    }


    public void display(int number) {
        tv_countDealofDay.setText("" + number);
    }






    /*@LongClick(R.id.imageViewDealofDay)
    public void onLongClick(){
        mPlaceHolderView.removeView(this);
    }*/
}
