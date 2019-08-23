package com.nisarga.nisargaveggiez.cart;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.R;

import java.util.List;

public class DeliverydateAdapter extends BaseAdapter {
    Context context;
    List<String> week;
    List<String> dates;
    LayoutInflater inflter;

    public DeliverydateAdapter(Context applicationContext, List<String> week, List<String> dates)
    {
        this.context = applicationContext;
        this.week = week;
        this.dates = dates;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount()
    {
        return week.size();
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.delveryspinner, null);
        TextView names = (TextView) view.findViewById(R.id.tvLanguage);
       // names.setText(countryNames[i]);
        names.setText(week.get(i));
        return view;
    }
}


