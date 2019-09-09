package com.nisarga.nisargaveggiez.Home;

import android.content.Context;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.nisarga.nisargaveggiez.R;

@NonReusable
@Layout(R.layout.rate_review_items)
public class RateReviewItems {


    @View(R.id.tvName)
    public TextView tvName;

    @View(R.id.tvComments)
    public TextView tvComments;


    Context mContext;
    public String strFName,strLName,strComments;
    public RateReviewItems(Context contxt,String fname,String lname,String comments)
    {
        this.mContext = contxt;
        this.strFName = fname;
        this.strLName = lname;
        this.strComments = comments;
    }

    @Resolve
    public void onResolved()
    {
        if(!strFName.equals("null") && !strFName.equals(null) && !strFName.isEmpty())
        {
            if(!strLName.equals("null") && !strLName.equals(null) && !strLName.isEmpty())
            {
                tvName.setText(strFName+" "+strLName);
            }
            else
            {
                tvName.setText(strFName);
            }
        }
        else
        {
            tvName.setText("User");
        }
        //comments
        if(!strComments.equals("null") && !strComments.equals(null) && !strComments.isEmpty())
        {
            tvComments.setText(strComments);
        }
        else
        {
            tvComments.setText("No Comments");
        }
    }
}
