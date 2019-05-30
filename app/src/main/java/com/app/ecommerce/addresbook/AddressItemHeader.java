package com.app.ecommerce.addresbook;

import android.content.Context;
import android.content.Intent;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;

import com.app.ecommerce.R;

/**
 * Created by praveen on 19/12/18.
 */


@NonReusable
@Layout(R.layout.address_item_header)
public class AddressItemHeader {

    private Context mContext;

    public AddressItemHeader(Context cnt)
    {
        mContext = cnt;
    }

    @Resolve
    private void onResolved() {

    }

    @Click(R.id.add)
    public void onClick()
    {
        Intent addrIntent = new Intent(mContext, AddAddress.class);
        addrIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(addrIntent);
    }


}
