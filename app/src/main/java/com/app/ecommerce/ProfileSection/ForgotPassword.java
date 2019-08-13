package com.app.ecommerce.ProfileSection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.ecommerce.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ForgotPassword extends AppCompatActivity {
    Toolbar toolbar;
    EditText etEmailorMobile;
    Button btnSubmitFPasswd;
    String strInput;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        toolbar = findViewById(R.id.toolbar);
        etEmailorMobile = findViewById(R.id.etEmailorMobile);
        btnSubmitFPasswd = findViewById(R.id.btnSubmitFPasswd);

        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        btnSubmitFPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOtpForgotPassword = new Intent(ForgotPassword.this,ForgotPasswdOtp.class);
                intentOtpForgotPassword.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentOtpForgotPassword);
            }
        });

    }
    public boolean validateInput(String strVal) {
        if (strVal == null || strVal.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Invalid User Id", Toast.LENGTH_SHORT).show();
            return false;

        } else {
            if (strVal.matches("[0-9]+")) {
                if (strVal.length() < 10 && strVal.length() > 10) {
                    etEmailorMobile.setError("Please Enter valid phone number");
                    etEmailorMobile.requestFocus();
                    return false;
                }
            } else {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(strVal).matches()) {
                    etEmailorMobile.setError("Please Enter valid email");
                    etEmailorMobile.requestFocus();
                    return false;
                }
            }
        }
        return false;
    }
    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
