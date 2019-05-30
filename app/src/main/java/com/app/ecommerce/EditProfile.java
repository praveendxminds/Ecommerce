package com.app.ecommerce;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.app.ecommerce.R;

/**
 * Created by praveen on 15/11/18.
 */



public class EditProfile extends AppCompatActivity {

    Toolbar toolbar;
    public static final String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;

    EditText fnme,lnme,email;
    TextView profile_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        fnme = (EditText) findViewById(R.id.input_fnme);
        lnme = (EditText) findViewById(R.id.input_lnme);
        email = (EditText) findViewById(R.id.input_email);
        profile_title = (TextView) findViewById(R.id.pro_title);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Boolean login_st_session = sharedpreferences.getBoolean("status",false);
        String name_session = sharedpreferences.getString("name", null);
        String fname_session = sharedpreferences.getString("fname", null);
        String lname_session = sharedpreferences.getString("lname", null);
        String mail_session = sharedpreferences.getString("email", null);


        if(login_st_session == true)
        {
            profile_title.setText(String.valueOf(name_session.charAt(0)).toUpperCase());
            fnme.setText(fname_session);
            lnme.setText(lname_session);
            email.setText(mail_session);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}