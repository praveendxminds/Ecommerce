package com.app.ecommerce.ProfileSection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.ecommerce.R;

public class ResetPassword extends AppCompatActivity {
    Toolbar toolbar;
    EditText etConfirmPasswd, etNewPassword;
    ImageView ivHidePass1, ivShowPass1, ivHidePass, ivShowPass;
    Button btnSubmitRPasswd;
    String strInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        toolbar = findViewById(R.id.toolbar);
        ivShowPass = findViewById(R.id.ivShowPass);
        ivHidePass = findViewById(R.id.ivHidePass);
        ivShowPass1 = findViewById(R.id.ivShowPass1);
        ivHidePass1 = findViewById(R.id.ivHidePass1);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPasswd = findViewById(R.id.etConfirmPasswd);
        btnSubmitRPasswd = findViewById(R.id.btnSubmitRPasswd);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //-------------------------------------------------------------
        ivHidePass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHidePass1.setVisibility(View.GONE);
                ivShowPass1.setVisibility(View.VISIBLE);
                etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        ivShowPass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivShowPass1.setVisibility(View.GONE);
                ivHidePass1.setVisibility(View.VISIBLE);
                etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        ivHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivHidePass.setVisibility(View.GONE);
                ivShowPass.setVisibility(View.VISIBLE);
                etConfirmPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        ivShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivShowPass.setVisibility(View.GONE);
                ivHidePass.setVisibility(View.VISIBLE);
                etConfirmPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        //---------------------------------------------------------
        btnSubmitRPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoginPage = new Intent(ResetPassword.this,LoginSignup_act.class);
                intentLoginPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLoginPage);
            }
        });

    }

    public boolean validateNewPassword(String strVal) {

        if (strVal == null || strVal.trim().length() == 0) {
            etNewPassword.requestFocus();
            etNewPassword.setError("Please enter password");
            return false;
        } else {
            if (strVal.trim().length() < 4) {
                etNewPassword.requestFocus();
                etNewPassword.setError("Password should not be less than 4 digit");
                return false;
            }
            if (strVal == null || strVal.trim().length() == 0) {
                etConfirmPasswd.requestFocus();
                etConfirmPasswd.setError("Please enter password");
                return false;
            } else {
                if (strVal.trim().length() < 4) {
                    etConfirmPasswd.requestFocus();
                    etConfirmPasswd.setError("Password should not be less than 4 digit");
                    return false;
                } else {
                    return true;
                }
            }
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
