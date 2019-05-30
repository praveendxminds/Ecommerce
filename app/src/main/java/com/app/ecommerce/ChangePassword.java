package com.app.ecommerce;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.app.ecommerce.R;

/**
 * Created by praveen on 15/11/18.
 */



public class ChangePassword extends AppCompatActivity {

    Toolbar toolbar;
    public static final String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;

    TextView profile_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        profile_title = (TextView) findViewById(R.id.pro_title);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Boolean login_st_session = sharedpreferences.getBoolean("status",false);
        String name_session = sharedpreferences.getString("name", null);

        if(login_st_session == true)
        {
            profile_title.setText(String.valueOf(name_session.charAt(0)).toUpperCase());
        }


        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}