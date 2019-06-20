package com.app.ecommerce.ProfileSection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ecommerce.R;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final Integer[] icon;
    private final String[] menu_name;


    public MyListAdapter(Context mContext, Integer[] icon_items, String[] menu_items) {
        super(mContext, R.layout.menu_item_list, menu_items);
        this.context = mContext;
        this.icon = icon_items;
        this.menu_name = menu_items;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (view == null)
            view = inflater.inflate(R.layout.menu_item_list, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.icon);
        TextView titleText = (TextView) view.findViewById(R.id.title);

        imageView.setImageResource(icon[position]);
        titleText.setText(menu_name[position]);

        return view;
    }
}