package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.nisarga.nisargaveggiez.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@NonReusable
@Layout(R.layout.rate_review_items)
public class RateReviewItems {


    @View(R.id.tvName)
    public TextView tvName;

    @View(R.id.tvComments)
    public TextView tvComments;

    @View(R.id.tvDate)
    public TextView tvDate;


    Context mContext;
    public String strFName, strLName, strComments,strDate;

    public RateReviewItems(Context contxt, String fname, String lname, String comments,String date) {
        this.mContext = contxt;
        this.strFName = fname;
        this.strLName = lname;
        this.strComments = comments;
        this.strDate = date;
    }

    @Resolve
    public void onResolved() {

        Date localTime = null;
        try {
            localTime = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS", Locale.getDefault()).parse(strDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");

        String delivDate = sdf.format(new Date(localTime.getTime()));
        Log.e("-----date-------------", delivDate);
        tvDate.setText(delivDate);




        if (!strFName.equals("null") && !strFName.equals(null) && !strFName.isEmpty()) {
            if (!strLName.equals("null") && !strLName.equals(null) && !strLName.isEmpty()) {
                tvName.setText(strFName + " " + strLName);
            } else {
                tvName.setText(strFName);
            }
        } else {
            tvName.setText("User");
        }
        //comments
        if (!strComments.equals("null") && !strComments.equals(null) && !strComments.isEmpty()) {
            tvComments.setText(strComments.trim().replace("\n",""));
        } else {
            tvComments.setText("No Comments");
        }
    }
}
