package com.nisarga.nisargaveggiez.ProfileSection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nisarga.nisargaveggiez.R;

public class ForgotPasswdOtp extends AppCompatActivity implements TextWatcher, View.OnKeyListener, View.OnFocusChangeListener {
    Toolbar toolbar;
    EditText etOtp1, etOtp2, etOtp3, etOtp4;
    Button btnVerifyEmailMobile;
    String strInput;
    private int whoHasFocus;
    char[] code = new char[4];//Store the digits in charArray.

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        toolbar = findViewById(R.id.toolbar);
        btnVerifyEmailMobile = findViewById(R.id.btnVerifyEmailMobile);
        etOtp1 = findViewById(R.id.etOtp1);
        etOtp2 = findViewById(R.id.etOtp2);
        etOtp3 = findViewById(R.id.etOtp3);
        etOtp4 = findViewById(R.id.etOtp4);
        etOtp1.requestFocus();//Left digit gets focus after adding of fragment in Container

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        btnVerifyEmailMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentResetPassword = new Intent(ForgotPasswdOtp.this,ResetPassword.class);
                intentResetPassword.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentResetPassword);
            }
        });

    }

    private void setListners() {
        etOtp1.addTextChangedListener(this);
        etOtp2.addTextChangedListener(this);
        etOtp3.addTextChangedListener(this);
        etOtp4.addTextChangedListener(this);

        etOtp1.setOnKeyListener(this);
        etOtp2.setOnKeyListener(this);
        etOtp3.setOnKeyListener(this);
        etOtp4.setOnKeyListener(this);

        etOtp1.setOnFocusChangeListener(this);
        etOtp2.setOnFocusChangeListener(this);
        etOtp3.setOnFocusChangeListener(this);
        etOtp4.setOnFocusChangeListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        /*storing text in chararray by index and once the user enter the number in
        EditText the next EditText will get focus by requestfocus method*/
        switch (whoHasFocus) {
            case 1:
                if (!etOtp1.getText().toString().isEmpty()) {
                    code[0] = etOtp1.getText().toString().charAt(0);
                    etOtp2.requestFocus();
                }
                break;

            case 2:
                if (!etOtp2.getText().toString().isEmpty()) {
                    code[1] = etOtp2.getText().toString().charAt(0);
                    etOtp2.requestFocus();
                }
                break;

            case 3:
                if (!etOtp3.getText().toString().isEmpty()) {
                    code[2] = etOtp3.getText().toString().charAt(0);
                    etOtp4.requestFocus();
                }
                break;

            case 4:
                if (!etOtp4.getText().toString().isEmpty()) {
                    code[3] = etOtp4.getText().toString().charAt(0);
                }
                break;

            default:
                break;
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        /*  checking which EditText currently has focus from where it is useful to fetch number from respective EditText Boxes*/
        switch (v.getId()) {
            case R.id.etOtp1:
                whoHasFocus = 1;
                break;

            case R.id.etOtp2:
                whoHasFocus = 2;
                break;

            case R.id.etOtp3:
                whoHasFocus = 3;
                break;

            case R.id.etOtp4:
                whoHasFocus = 4;
                break;

            default:
                break;
        }

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
       /* This method will functionate as delete(BackSpace) key
        and checking whether EditText is empty and DEL(backspace
         in keypad is pressed). if true the previous EditText will get focus.*/
        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if (keyCode == KeyEvent.KEYCODE_DEL)
            {
                switch(v.getId())
                {
                    case R.id.etOtp2:
                        if (etOtp2.getText().toString().isEmpty())
                            etOtp1.requestFocus();
                        break;

                    case R.id.etOtp3:
                        if (etOtp3.getText().toString().isEmpty())
                            etOtp2.requestFocus();
                        break;

                    case R.id.etOtp4:
                        if (etOtp4.getText().toString().isEmpty())
                            etOtp3.requestFocus();
                        break;

                    default:
                        break;
                }
            }
        }
        return false;
    }
}
