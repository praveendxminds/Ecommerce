package com.app.ecommerce.ProfileSection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.app.ecommerce.R;

public class SignUp_act extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_act);
        init();
    }

    Button btnLogIn;
    EditText etName, etMobileNo, etEmail, etPassword, etApartmentName, etDoorNo, etArea, etAddress, etPinCode;
    CheckBox cbNearByApartment;
    ImageButton ibtnSave;

    private void init() {
        btnLogIn = findViewById(R.id.btnLogIn);

        etName = findViewById(R.id.etName);
        etMobileNo = findViewById(R.id.etMobileNo);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etApartmentName = findViewById(R.id.etApartmentName);
        etDoorNo = findViewById(R.id.etDoorNo);
        etArea = findViewById(R.id.etArea);
        etAddress = findViewById(R.id.etAddress);
        etPinCode = findViewById(R.id.etPinCode);

        cbNearByApartment = findViewById(R.id.cbNearByApartment);

        ibtnSave = findViewById(R.id.ibtnSave);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp_act.this, Login_act.class);
                startActivity(intent);
            }
        });
    }
}
