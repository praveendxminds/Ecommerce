package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.R;

import java.util.ArrayList;

public class QtyspinnerAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> name;
    ArrayList<String> product_option_id;
    ArrayList<String> product_option_value_id;
    ArrayList<String> cart_count;
    ArrayList<String> price;
    ArrayList<String> discount_price;
    LayoutInflater inflter;


    public QtyspinnerAdapter(Context applicationContext, ArrayList<String> name, ArrayList<String> product_option_id,
                             ArrayList<String> product_option_value_id, ArrayList<String> cart_count,
                             ArrayList<String> price, ArrayList<String> discount_price) {

        this.context = applicationContext;
        this.name = name;
        this.product_option_id = product_option_id;
        this.product_option_value_id = product_option_value_id;
        this.cart_count = cart_count;
        this.price = price;
        this.discount_price = discount_price;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.quntityspinner, null);
        TextView names = (TextView) view.findViewById(R.id.quntity);
        names.setText(name.get(i));
        return view;
    }
}