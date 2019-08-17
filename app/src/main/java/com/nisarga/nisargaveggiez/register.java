package com.nisarga.nisargaveggiez;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.RegisterUser;

/**
 * Created by praveen on 15/11/18.
 */



public class register extends AppCompatActivity {

    Toolbar toolbar;
    APIInterface apiInterface;
    EditText email,mobile,f_name,l_name,edittext_otp;

    //firebase auth object
    private FirebaseAuth mAuth;
    private String mVerificationId;


    TextInputLayout OTP_LYT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        email = (EditText) findViewById(R.id.input_email);
        mobile = (EditText) findViewById(R.id.input_mobile);
        f_name = (EditText) findViewById(R.id.fname_txt);
        l_name = (EditText) findViewById(R.id.lname_txt);

        edittext_otp = (EditText) findViewById(R.id.otp_text);
        OTP_LYT = (TextInputLayout) findViewById(R.id.otp_lyt);



        apiInterface = APIClient.getClient().create(APIInterface.class);


        mAuth = FirebaseAuth.getInstance();



    }

    public void login(View v)
    {
        finish();
        Intent pIntent = new Intent(getBaseContext(), login.class);
        pIntent.putExtra("Login_INTENT", "profile");
        startActivity(pIntent);
    }


    public void sendotp(View v)
    {
        OTP_LYT.setVisibility(View.VISIBLE);
        sendVerificationCode(mobile.getText().toString());
    }

    /* phone num send to firebase */


    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                edittext_otp.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(register.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
           // mResendToken = forceResendingToken;
        }
    };

 /* verify */

    private void verifyVerificationCode(String otp) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            Toast.makeText(getApplicationContext(), "verification successful", Toast.LENGTH_SHORT).show();

                            //verification successful we will start the profile activity

//                            Intent intent = new Intent(register.this, ProfileActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);


                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

//                            Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
//                            snackbar.setAction("Dismiss", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            });
//                            snackbar.show();


                        }
                    }
                });
    }


    public void register(View v)
    {


        //User user = new User("admin","admin");
        Call<RegisterUser> call1 = apiInterface.doGetRegister(f_name.getText().toString(),l_name.getText().toString(),email.getText().toString(),mobile.getText().toString());
        call1.enqueue(new Callback<RegisterUser>() {
            @Override
            public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {
                RegisterUser res = response.body();

                //  Toast.makeText(getApplicationContext(), user1.name + " " + user1.job + " " + user1.id + " " + user1.createdAt, Toast.LENGTH_SHORT).show();

                Log.e("TAG", "response 33: "+res.status.trim() );
                if(res.status.trim().equals("true"))
                {
                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<RegisterUser> call, Throwable t) {
                call.cancel();
            }
        });






    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}