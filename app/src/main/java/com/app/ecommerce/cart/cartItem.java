package com.app.ecommerce.cart;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.app.ecommerce.R;

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

    @View(R.id.qntyMyCart)
    public TextView qntyMyCart;

    @View(R.id.priceNewMyCart)
    public TextView priceNewMyCart;

    @View(R.id.btn_deleteMyCart)
    public TextView btn_deleteMyCart;

    @View(R.id.idProductCount)
    public  TextView idProductCount;

    public Context mcontext;
    int minteger = 0;
    public PlaceHolderView mplaceholderView;


    public cartItem(Context context) {
        mcontext=context;
    }

    @Resolve
    public void onResolved() {

    }
    @Click(R.id.idPlusIcon)
    public void onIncreaseClick() {
        minteger = minteger + 1;
        display(minteger);
    }

    @Click(R.id.idMinusICon)
    public void onDecreaseClick() {
        if(minteger==0)
        {
            display(0);
        }
        else {
            minteger = minteger - 1;
            display(minteger);
        }
    }
    public void display(int number)
    {
        idProductCount.setText("" + number);
    }

    @Click(R.id.btn_deleteMyCart)
    public void deleteRow()
    {
        mplaceholderView.removeView(this);
    }
}
