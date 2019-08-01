package com.app.ecommerce.MyOrder;

import android.content.Context;
import android.widget.ArrayAdapter;

public class SpinnerAdapterReorder extends ArrayAdapter<String> {

    public SpinnerAdapterReorder(Context context, int textViewResourceId) {
        super(context, textViewResourceId);

    }

    @Override
    public int getCount() {

        int count = super.getCount();

        return count>0 ? count-1 : count ;


    }


}