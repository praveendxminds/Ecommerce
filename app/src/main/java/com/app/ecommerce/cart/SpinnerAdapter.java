package com.app.ecommerce.cart;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String> {

    public SpinnerAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);

    }

    @Override
    public int getCount() {

        int count = super.getCount();

        return count>0 ? count-1 : count ;


    }


}