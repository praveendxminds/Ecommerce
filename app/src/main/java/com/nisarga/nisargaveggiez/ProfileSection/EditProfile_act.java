package com.nisarga.nisargaveggiez.ProfileSection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nisarga.nisargaveggiez.EditProfile;
import com.nisarga.nisargaveggiez.Home.HomePage;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.EditProfileModel;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile_act extends AppCompatActivity {

    APIInterface apiInterface;
    SessionManager session;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_act);

        progressdialog = new ProgressDialog(EditProfile_act.this);
        progressdialog.setMessage("Please Wait....");

        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());
        init();
    }

    Toolbar toolbar;
    ImageView ivOldHidePass, ivOldShowPass, ivNewHidePass, ivNewShowPass, ivConfHidePass, ivConfShowPass;
    ImageView ivProfile;
    TextView tvNearBy;
    EditText etFName, etLName, etEmail, etMobileNo, etOldPass, etNewPass, etConfirmPass, etApartmentName,
            etDoorNo, etArea, etAddress, etPinCode;
    Button btnUpdate;

    String sFName, sLName, sEmail, sMobileNo, sPassword, sNewPass, sConfirmPass, sApartmentName,
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

        tvNearBy = findViewById(R.id.tvNearBy);
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

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //------------------------------------- My profile view section------------------------------------------------
            final MyProfileModel myProfileModel = new MyProfileModel(session.getCustomerId());
            Call<MyProfileModel> call = apiInterface.showMyProfile(myProfileModel);
            call.enqueue(new Callback<MyProfileModel>() {
                @Override
                public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {
                    MyProfileModel resourceMyProfile = response.body();
                    if (resourceMyProfile.status.equals("success")) {
                        List<MyProfileModel.Datum> mpmDatum = resourceMyProfile.resultdata;
                        for (MyProfileModel.Datum mpmResult : mpmDatum) {
                            if (String.valueOf(mpmResult.image) == "null") {
                                ivProfile.setImageResource(R.drawable.camera);
                            } else {
                                Glide.with(EditProfile_act.this).load(mpmResult.image).fitCenter().dontAnimate()
                                        .into(ivProfile);
                            }
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
                    progressdialog.dismiss();
                }

                @Override
                public void onFailure(Call<MyProfileModel> call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sFName = etFName.getText().toString();
                sLName = etLName.getText().toString();
                sEmail = etEmail.getText().toString();
                sMobileNo = etMobileNo.getText().toString();
                sNewPass = etNewPass.getText().toString();
                sConfirmPass = etConfirmPass.getText().toString();
                if ((sConfirmPass.trim()).equals(sNewPass.trim())) {
                    sPassword = sConfirmPass;
                } else {
                    etConfirmPass.requestFocus();
                    etConfirmPass.setError("Password mismatch");
                }
                sApartmentName = etApartmentName.getText().toString();
                sDoorNo = etDoorNo.getText().toString();
                sArea = etArea.getText().toString();
                sAddress = etAddress.getText().toString();
                sPinCode = etPinCode.getText().toString();

                if (Utils.CheckInternetConnection(getApplicationContext())) {
                    if (!TextUtils.isEmpty(session.getCustomerId())) {
                        saveEditData(sFName, sLName, sEmail, sMobileNo, sApartmentName, sPinCode, sAddress, sConfirmPass,
                                sDoorNo, sArea);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveEditData(String sFName, String sLName, String sEmail, String sMobileNo, String sApartmentName,
                              String sPinCode, String sAddress, String sConfirmPass, String sDoorNo, String sArea) {

        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final EditProfileModel editProfileModel = new EditProfileModel(session.getCustomerId(), sFName,
                sLName, sEmail, sMobileNo, sApartmentName, sPinCode, sAddress, sConfirmPass, sDoorNo,
                sArea);

        Call<EditProfileModel> callEditProfile = apiInterface.editMyProfile(editProfileModel);
        callEditProfile.enqueue(new Callback<EditProfileModel>() {
            @Override
            public void onResponse(Call<EditProfileModel> call, Response<EditProfileModel> response) {
                EditProfileModel resourceMyProfile = response.body();
                if (resourceMyProfile.status.equals("success")) {
                    Toast.makeText(getApplicationContext(), resourceMyProfile.message, Toast.LENGTH_SHORT).show();
                    Intent intentMyProf = new Intent(getBaseContext(), MyProfile_act.class);
                    startActivity(intentMyProf);
                } else if (resourceMyProfile.status.equals("failure")) {
                    Toast.makeText(getApplicationContext(), resourceMyProfile.message, Toast.LENGTH_SHORT).show();
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(Call<EditProfileModel> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            if (!TextUtils.isEmpty(session.getCustomerId())) {
                saveEditData(sFName, sLName, sEmail, sMobileNo, sApartmentName, sPinCode, sAddress, sConfirmPass,
                        sDoorNo, sArea);
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}

