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
import com.app.ecommerce.SessionManager;
import com.app.ecommerce.Utils;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.EditProfileModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile_act extends AppCompatActivity {

    APIInterface apiInterface;
    String custId;
    SessionManager session;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_act);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());
        init();
    }

    Toolbar toolbar;
    ImageView ivProfile, ivOldHidePass, ivOldShowPass, ivNewHidePass, ivNewShowPass,
            ivConfHidePass, ivConfShowPass;

    EditText etFName,etLName, etEmail, etMobileNo, etOldPass, etNewPass, etConfirmPass,
            etApartmentName, etDoorNo, etArea, etAddress, etPinCode;

    Button btnNearBy, btnUpdate;

    String sFName,sLName, sEmail, sMobileNo,sPassword, sOldPass, sNewPass, sConfirmPass, sApartmentName,
            sDoorNo, sArea, sAddress, sPinCode;

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Edit Profile");

        ivProfile = findViewById(R.id.ivProfile);
        ivOldHidePass = findViewById(R.id.ivOldHidePass);
        ivOldShowPass = findViewById(R.id.ivOldShowPass);
        ivNewHidePass = findViewById(R.id.ivNewHidePass);
        ivNewShowPass = findViewById(R.id.ivNewShowPass);
        ivConfHidePass = findViewById(R.id.ivConfHidePass);
        ivConfShowPass = findViewById(R.id.ivConfShowPass);

        etFName = findViewById(R.id.etFName);
        etLName = findViewById(R.id.etLName);
        etEmail = findViewById(R.id.etEmail);
        etMobileNo = findViewById(R.id.etMobileNo);
        etOldPass = findViewById(R.id.etOldPass);
        etNewPass = findViewById(R.id.etNewPass);
        etConfirmPass = findViewById(R.id.etConfirmPass);
        etApartmentName = findViewById(R.id.etApartmentName);
        etDoorNo = findViewById(R.id.etDoorNo);
        etArea = findViewById(R.id.etArea);
        etAddress = findViewById(R.id.etAddress);
        etPinCode = findViewById(R.id.etPinCode);

        btnNearBy = findViewById(R.id.btnNearBy);
        btnUpdate = findViewById(R.id.btnUpdate);

        ivOldHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivOldHidePass.setVisibility(View.GONE);
                ivOldShowPass.setVisibility(View.VISIBLE);
                etOldPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        ivOldShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivOldShowPass.setVisibility(View.GONE);
                ivOldHidePass.setVisibility(View.VISIBLE);
                etOldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        ivNewHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivNewHidePass.setVisibility(View.GONE);
                ivNewShowPass.setVisibility(View.VISIBLE);
                etNewPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        ivNewShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivNewShowPass.setVisibility(View.GONE);
                ivNewHidePass.setVisibility(View.VISIBLE);
                etNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        ivConfHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivConfHidePass.setVisibility(View.GONE);
                ivConfShowPass.setVisibility(View.VISIBLE);
                etConfirmPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        ivConfShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivConfShowPass.setVisibility(View.GONE);
                ivConfHidePass.setVisibility(View.VISIBLE);
                etConfirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

       editProfile();

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            //------------------------------------- My profile view section------------------------------------------------
            custId = session.getCustomerId();
            final MyProfileModel myProfileModel = new MyProfileModel(custId);
            Call<MyProfileModel> call = apiInterface.showMyProfile(myProfileModel);
            call.enqueue(new Callback<MyProfileModel>() {
                @Override
                public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {
                    MyProfileModel resourceMyProfile = response.body();
                    if(resourceMyProfile.status.equals("success"))
                    {
                        // Toast.makeText(getApplicationContext(),resourceMyProfile.message,Toast.LENGTH_SHORT).show();
                        List<MyProfileModel.Datum> mpmDatum = resourceMyProfile.resultdata;
                        for(MyProfileModel.Datum mpmResult : mpmDatum)
                        {
                            etFName.setText(mpmResult.firstname);
                            etLName.setText(mpmResult.lastname);
                            etEmail.setText(mpmResult.email);
                            etMobileNo.setText(mpmResult.telephone);
                            etApartmentName.setText(mpmResult.apartment);
                            etDoorNo.setText(mpmResult.door);
                            etArea.setText(mpmResult.area);
                            etAddress.setText(mpmResult.address_1);
                            etPinCode.setText(mpmResult.postcode);

                        }

                    }
                    else if(resourceMyProfile.status.equals("error"))
                    {
                        Toast.makeText(getApplicationContext(),resourceMyProfile.message,Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MyProfileModel> call, Throwable t) {

                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }
    public void editProfile()
    {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sFName = etFName.getText().toString();
                sLName = etLName.getText().toString();
                sEmail = etEmail.getText().toString();
                sMobileNo = etMobileNo.getText().toString();
                sNewPass = etNewPass.getText().toString();
                sConfirmPass = etConfirmPass.getText().toString();
                if((sConfirmPass.trim()).equals(sNewPass.trim()))
                {
                    sPassword = sConfirmPass;
                }
                else
                {
                    etConfirmPass.requestFocus();
                    etConfirmPass.setError("Password mismatch");
                }
                sApartmentName = etApartmentName.getText().toString();
                sDoorNo = etDoorNo.getText().toString();
                sArea = etArea.getText().toString();
                sAddress = etAddress.getText().toString();
                sPinCode = etPinCode.getText().toString();
                if (Utils.CheckInternetConnection(getApplicationContext())) {
                    //------------------------------------- My profile view section------------------------------------------------
                    custId = session.getCustomerId();
                    final EditProfileModel editProfileModel = new EditProfileModel(custId,sFName,sLName,sEmail,sMobileNo,sPassword,
                            sApartmentName,sDoorNo,sArea,sAddress,sAddress,sPinCode);
                    Call<EditProfileModel> callEditProfile = apiInterface.editMyProfile(editProfileModel);
                    callEditProfile.enqueue(new Callback<EditProfileModel>() {
                        @Override
                        public void onResponse(Call<EditProfileModel> call, Response<EditProfileModel> response) {
                            EditProfileModel resourceMyProfile = response.body();
                            if(resourceMyProfile.status.equals("success"))
                            {
                                Toast.makeText(getApplicationContext(),resourceMyProfile.message,Toast.LENGTH_SHORT).show();
                                Intent intentMyProf = new Intent(getBaseContext(),MyProfile_act.class);
                                startActivity(intentMyProf);

                            }
                            else if(resourceMyProfile.status.equals("failure"))
                            {
                                Toast.makeText(getApplicationContext(),resourceMyProfile.message,Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<EditProfileModel> call, Throwable t) {

                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        editProfile();
    }
}

