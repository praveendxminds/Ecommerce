package com.app.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import com.app.ecommerce.MyOrder.MyOrders;
import com.app.ecommerce.R;
import com.app.ecommerce.addresbook.AddressBook;

/**
 * Created by praveen on 15/11/18.
 */



public class profile extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    Toolbar toolbar;
    public static String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;
    TextView nme,email,profile_title;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    private static final String TAG = "profile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        profile_title = (TextView) findViewById(R.id.pro_title);
        setSupportActionBar(toolbar);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Boolean login_st_session = sharedpreferences.getBoolean("status",false);
        String name_session = sharedpreferences.getString("name", null);
        String mail_session = sharedpreferences.getString("email", null);

        nme = (TextView) findViewById(R.id.nameTxt);
        email = (TextView) findViewById(R.id.emailTxt);


        if(login_st_session == true)
        {
            profile_title.setText(String.valueOf(name_session.charAt(0)).toUpperCase());
            String cap = String.valueOf(name_session.charAt(0)).toUpperCase() + name_session.subSequence(1, name_session.length());
            nme.setText(cap);
            email.setText(mail_session);
        }





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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                finish();
                return true;

            case R.id.edit:

                Intent myIntent = new Intent(getBaseContext(), EditProfile.class);
                startActivity(myIntent);

                break;
        }
        return true;
    }

    public void myorders(View view)
    {
        Intent myIntent = new Intent(getBaseContext(), MyOrders.class);
        startActivity(myIntent);
    }

    public void addressbook(View view)
    {
        Intent myIntent = new Intent(getBaseContext(), AddressBook.class);
        startActivity(myIntent);
    }

    public void change_pass(View view)
    {
        Intent myIntent = new Intent(getBaseContext(), ChangePassword.class);
        startActivity(myIntent);
    }

    public void logout(View view)
    {

        if(AccessToken.getCurrentAccessToken() != null)
        {
            LoginManager.getInstance().logOut();
        }




        if (mGoogleApiClient.isConnected())
        {

            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            finish();
                        }
                    });

            Log.d(TAG, "onConnectionFailed:" + "dssff");

        }



        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove("status");
        editor.remove("name");
        editor.remove("email");
        editor.remove("phone");
        editor.commit();
        finish();



    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}