package com.app.ecommerce.addresbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;


import com.app.ecommerce.R;

/**
 * Created by praveen on 30/12/18.
 */

public class AddAddress extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    Toolbar toolbar;
    APIInterface apiInterface;
    private static final String LOG_TAG = "AddAddress";
    Bundle intent_extras;
    String address_intent_val;


    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    private EditText fname_editText,lname_editText,address_editText,landmrk_editText,city_editText,state_editText,cntry_editText,pin_editText,phone_editText;

    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;

    private String addr;

    private CheckBox bill_chk,ship_chk;


    public static final String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        apiInterface = APIClient.getClient().create(APIInterface.class);
        mGoogleApiClient = new GoogleApiClient.Builder(AddAddress.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id
                .autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);



        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        fname_editText = (EditText) findViewById(R.id.fname);
        lname_editText = (EditText) findViewById(R.id.lname);
        address_editText = (EditText) findViewById(R.id.current_address);
        landmrk_editText = (EditText) findViewById(R.id.landmark);
        city_editText = (EditText) findViewById(R.id.city);
        state_editText = (EditText) findViewById(R.id.state);
        cntry_editText = (EditText) findViewById(R.id.cntry);
        pin_editText = (EditText) findViewById(R.id.pin);
        phone_editText = (EditText) findViewById(R.id.phone);

        bill_chk = (CheckBox) findViewById(R.id.checkBox_billing);
        ship_chk = (CheckBox) findViewById(R.id.checkBox_shipping);


        intent_extras = getIntent().getExtras();
        address_intent_val = intent_extras.getString("ADDRESS");


        Boolean login_st_session = sharedpreferences.getBoolean("status", false);
        String fname_session = sharedpreferences.getString("fname", null);
        String lname_session = sharedpreferences.getString("lname", null);
        String phone_session = sharedpreferences.getString("phone", null);

        if(login_st_session == true)
        {
            fname_editText.setText(fname_session.toString());
            lname_editText.setText(lname_session.toString());
            phone_editText.setText(phone_session.toString());
            address_editText.setText(address_intent_val);
        }





    }


    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };


    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

            address_editText.setText(Html.fromHtml(place.getName() + ""));

        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }


    public void saveaddr(View v)
    {

//        addr = address_editText.getText().toString()+','+landmrk_editText.getText().toString()+','+city_editText.getText().toString()+','+state_editText.getText().toString()+','+cntry_editText.getText().toString()+','+pin_editText.getText().toString();
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString("address", addr);
//        editor.commit();

        Toast.makeText(getApplicationContext(),"Saved Address",Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                finish();
                return true;


        }
        return true;
    }

}