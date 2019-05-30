package com.app.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.app.ecommerce.R;
import com.app.ecommerce.billing.billingAddress;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.User;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;


/**
 * Created by praveen on 15/11/18.
 */



public class login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    Toolbar toolbar;
    APIInterface apiInterface;
    EditText email;
    TextInputEditText pass;
    LoginButton loginButton;
    CallbackManager callbackManager;

    public static final String MyPREFERENCES = "sessiondata" ;
    SharedPreferences sharedpreferences;

    Bundle intent_extras;
    String login_intent_val;

    private static final String TAG = "login";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInOptions gso;
    public static final String PROFILE_USER_ID = "USER_ID";

    private GoogleApiClient mGoogleApiClient;
    SignInButton mSignInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        email = (EditText) findViewById(R.id.input_email);
        pass = (TextInputEditText) findViewById(R.id.input_password);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        /* gplus */

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mSignInButton = (SignInButton)findViewById(R.id.gplus_sign_in);
        assert mSignInButton != null;
        mSignInButton.setSize(SignInButton.SIZE_STANDARD);
        mSignInButton.setScopes(gso.getScopeArray());
//        mSignInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//                startActivityForResult(signInIntent, RC_SIGN_IN);
//            }
//        });


        /* facebook */

        loginButton = (LoginButton) findViewById(R.id.login_button);






        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;

        if (!loggedOut) {
            //  Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(200, 200)).into(imageView);
            Log.d("TAG", "Username is: " + Profile.getCurrentProfile().getName());

            //  getUserProfile(AccessToken.getCurrentAccessToken());
        }


        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                //loginResult.getAccessToken();
                //loginResult.getRecentlyDeniedPermissions()
                //loginResult.getRecentlyGrantedPermissions()
                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
                Log.d("API123",AccessToken.getCurrentAccessToken().toString() + loggedIn + " ??");



                getUserProfile(AccessToken.getCurrentAccessToken());

                //Log.d("TAG", "Username is: " + loginResult.toString());


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        /* facebook */




    }
    public void register(View v)
    {
        Intent nfcIntent = new Intent(getBaseContext(), register.class);
        startActivity(nfcIntent);

    }


    public void facebookLogin(View v)
    {
        loginButton.performClick();
    }

    public void gplusLogin(View v)
    {
      //  mSignInButton.performClick();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public void forgot(View v)
    {
        Intent forgotIntent = new Intent(getBaseContext(), forgot.class);
        startActivity(forgotIntent);

    }

    public void login(View v)
    {


        //User user = new User("admin","admin");
        Call<User> call1 = apiInterface.doGetLogin(email.getText().toString(),pass.getText().toString());
        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User res = response.body();

                //  Toast.makeText(getApplicationContext(), user1.name + " " + user1.job + " " + user1.id + " " + user1.createdAt, Toast.LENGTH_SHORT).show();

                Log.e("TAG", "response 33: "+res.status.trim() );
                if(res.status.trim().equals("true"))
                {
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("status", true);
                    editor.putString("name", "admin");
                    editor.putString("fname", "First");
                    editor.putString("lname", "Last");
                    editor.putString("email", "admin@gmail.com");
                    editor.putString("phone", "9037007648");
                    editor.putInt("count", 0);
                    editor.commit();

                    intent_extras = getIntent().getExtras();
                    login_intent_val = intent_extras.getString("Login_INTENT");


                    if(login_intent_val.equals("cart"))
                    {
                        finish();
                        Intent accountIntent = new Intent(getBaseContext(), billingAddress.class);
                        startActivity(accountIntent);
                    }
                    else if(login_intent_val.equals("profile"))
                    {
                        finish();
                        Intent accountIntent = new Intent(getBaseContext(), profile.class);
                        startActivity(accountIntent);
                    }
                    else if(login_intent_val.equals("account"))
                    {
                        finish();
                        Intent accountIntent = new Intent(getBaseContext(), profile.class);
                        startActivity(accountIntent);
                    }
                    else
                    {
                        finish();
                        Intent accountIntent = new Intent(getBaseContext(), profile.class);
                        startActivity(accountIntent);
                    }



                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
            }
        });






    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }




        super.onActivityResult(requestCode, resultCode, data);
    }


    /* facebook */

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";


                            Log.d("API1256543",AccessToken.getCurrentAccessToken().toString() + first_name + " ??");

                            //  txtUsername.setText("First Name: " + first_name + "\nLast Name: " + last_name);
                            // txtEmail.setText(email);
                            //  Picasso.with(HomeThree.this).load(image_url).into(imageView);

                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean("status", true);
                            editor.putString("name", first_name+" "+last_name);
                            editor.putString("fname", first_name);
                            editor.putString("lname", last_name);
                            editor.putString("email", email);
                            editor.putString("phone", " ");
                            editor.putInt("count", 0);
                            editor.commit();

                            intent_extras = getIntent().getExtras();
                            login_intent_val = intent_extras.getString("Login_INTENT");


                            if(login_intent_val.equals("cart"))
                            {
                                finish();
                                Intent accountIntent = new Intent(getBaseContext(), billingAddress.class);
                                startActivity(accountIntent);
                            }
                            else if(login_intent_val.equals("profile"))
                            {
                                finish();
                                Intent accountIntent = new Intent(getBaseContext(), profile.class);
                                startActivity(accountIntent);
                            }
                            else if(login_intent_val.equals("account"))
                            {
                                finish();
                                Intent accountIntent = new Intent(getBaseContext(), profile.class);
                                startActivity(accountIntent);
                            }
                            else
                            {
                                finish();
                                Intent accountIntent = new Intent(getBaseContext(), profile.class);
                                startActivity(accountIntent);
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    /* facebook */



    /* gplus */

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());

        String statusCode = result.getStatus().getStatusMessage();

        Log.d(TAG, "handleSignInResult:" + statusCode);

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount userAccount = result.getSignInAccount();
            String userId = userAccount.getId();
            String displayedUsername = userAccount.getDisplayName();
            String FirstUsername = userAccount.getFamilyName();
            String LastUsername = userAccount.getGivenName();
            String userEmail = userAccount.getEmail();
            String userProfilePhoto = userAccount.getPhotoUrl().toString();





            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("status", true);
            editor.putString("name", displayedUsername);
            editor.putString("fname", FirstUsername);
            editor.putString("lname", LastUsername);
            editor.putString("email", userEmail);
            editor.putString("phone", " ");
            editor.putInt("count", 0);
            editor.commit();

            intent_extras = getIntent().getExtras();
            login_intent_val = intent_extras.getString("Login_INTENT");


            if(login_intent_val.equals("cart"))
            {
                finish();
                Intent accountIntent = new Intent(getBaseContext(), billingAddress.class);
                startActivity(accountIntent);
            }
            else if(login_intent_val.equals("profile"))
            {
                finish();
                Intent accountIntent = new Intent(getBaseContext(), profile.class);
                startActivity(accountIntent);
            }
            else if(login_intent_val.equals("account"))
            {
                finish();
                Intent accountIntent = new Intent(getBaseContext(), profile.class);
                startActivity(accountIntent);
            }
            else
            {
                finish();
                Intent accountIntent = new Intent(getBaseContext(), profile.class);
                startActivity(accountIntent);
            }



            // Intent googleSignInIntent = new Intent(GoogleSignInActivity.this, ProfileActivity.class);
            // googleSignInIntent.putExtra(PROFILE_USER_ID, userId);
            // googleSignInIntent.putExtra(PROFILE_DISPLAY_NAME, displayedUsername);
            // googleSignInIntent.putExtra(PROFILE_USER_EMAIL, userEmail);
            //  googleSignInIntent.putExtra(PROFILE_IMAGE_URL, userProfilePhoto);
            // startActivity(googleSignInIntent);


        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    //hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

}