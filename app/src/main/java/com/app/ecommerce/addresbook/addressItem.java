package com.app.ecommerce.addresbook;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.app.ecommerce.R;

/**
 * Created by praveen on 19/12/18.
 */


@NonReusable
@Layout(R.layout.address_items)
public class addressItem {

    @View(R.id.imageView)
    private ImageView imageView;

    PlaceHolderView mplaceHolderView;

    private Context mContext;


    public addressItem(Context context,PlaceHolderView placeHolderView) {
        mContext = context;
        mplaceHolderView = placeHolderView;
    }

    @Resolve
    private void onResolved()
    {


    }


    @Click(R.id.delete)
    public void onCarddelete()
    {
        mplaceHolderView.removeView(this);
    }

    @Click(R.id.edit)
    public void onCardClick()
    {
        Intent addrIntent = new Intent(mContext, AddAddress.class);
        addrIntent.putExtra("ADDRESS", "aadr");
        addrIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(addrIntent);

    }

}

