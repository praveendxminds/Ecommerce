package com.app.ecommerce.drawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import com.app.ecommerce.login;
import com.app.ecommerce.profile;
import com.app.ecommerce.R;

import static com.app.ecommerce.R.id.profileImageView;

/**
 * Created by praveen on 13/11/18.
 */

@NonReusable
@Layout(R.layout.drawer_header)
public class DrawerHeader {

    private Context mContext;


    @View(profileImageView)
    private CircularImageView profileImage;

    @View(R.id.nameTxt)
    private TextView nameTxt;

    @View(R.id.pro_title)
    private TextView profile_title;



    public static final String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;

    public DrawerHeader(Context context) {
        mContext = context;
    }


    @Click(R.id.draw_lt)
    public void onCardClick()
    {
        Boolean login_st_session = sharedpreferences.getBoolean("status",false);
        if(login_st_session == true)
        {
            Intent myIntent = new Intent(mContext, profile.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(myIntent);
        }
        else
        {
            Intent myIntent = new Intent(mContext, login.class);
            myIntent.putExtra("Login_INTENT", "profile");
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(myIntent);
        }


    }

    @Resolve
    private void onResolved() {



        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Boolean login_st_session = sharedpreferences.getBoolean("status",false);
        String name_session = sharedpreferences.getString("name", null);


        if(login_st_session == true)
        {
            profile_title.setVisibility(android.view.View.VISIBLE);
            profileImage.setVisibility(android.view.View.GONE);
            profile_title.setText(String.valueOf(name_session.charAt(0)).toUpperCase());
            String cap = String.valueOf(name_session.charAt(0)).toUpperCase() + name_session.subSequence(1, name_session.length());
            nameTxt.setText(cap);
        }
        else
        {
            profile_title.setVisibility(android.view.View.VISIBLE);
            profileImage.setVisibility(android.view.View.GONE);
            profile_title.setText("G");
            nameTxt.setText("Login");
        }

    }



}