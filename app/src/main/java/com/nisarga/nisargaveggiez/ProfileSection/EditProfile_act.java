package com.nisarga.nisargaveggiez.ProfileSection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.EditProfileModel;

import java.util.ArrayList;
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
    CircleImageView ivProfile;
    TextView tvNearBy, tvApartmentName;
    EditText etFName, etLName, etEmail, etMobileNo, etOldPass, etNewPass, etConfirmPass, etDoorNo, etCity,
            etAddress, etPinCode;
    Button btnUpdate;
    ListView lvApartmentList;
    DrawerLayout mDrawer;
    LinearLayout llCheckBox;
    CheckBox cbNearByApartment;

    String sFName, sLName, sEmail, sMobileNo, sPassword, sNewPass, sConfirmPass, sApartmentName,
            sDoorNo, sCity, sAddress, sPinCode, sProfilePic;

    String apartment_pincode[], apartment_address[], apartment_id[], apartment_city[], apartment_landmark[];
    String strApartmentId, strAddress, strLandmark, strPincode, strCity;
    String strNearBy = "0";

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
        etFName.setEnabled(false);
        etLName = findViewById(R.id.etLName);
        etLName.setEnabled(false);
        etEmail = findViewById(R.id.etEmail);
        etMobileNo = findViewById(R.id.etMobileNo);
        etOldPass = findViewById(R.id.etOldPass);
        etNewPass = findViewById(R.id.etNewPass);
        etConfirmPass = findViewById(R.id.etConfirmPass);
        tvApartmentName = findViewById(R.id.tvApartmentName);
        etDoorNo = findViewById(R.id.etDoorNo);
        etCity = findViewById(R.id.etCity);
        etAddress = findViewById(R.id.etAddress);
        etPinCode = findViewById(R.id.etPinCode);

        tvNearBy = findViewById(R.id.tvNearBy);
        btnUpdate = findViewById(R.id.btnUpdate);

        lvApartmentList = findViewById(R.id.lvApartmentList);
        mDrawer = findViewById(R.id.mDrawer);
        llCheckBox = findViewById(R.id.llCheckBox);
        cbNearByApartment = findViewById(R.id.cbNearByApartment);

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

        cbNearByApartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    strNearBy = "1";
                    etAddress.getText().clear();
                } else {
                    etAddress.setText(strAddress);
                }
            }
        });

        tvApartmentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(Gravity.RIGHT);
                llCheckBox.setVisibility(View.VISIBLE);
                tvApartmentName.setVisibility(View.GONE);
                final ArrayList<String> apartment_list = new ArrayList<>();

                if (Utils.CheckInternetConnection(getApplicationContext())) {
                    Call<ApartmentList> callheight = apiInterface.getApartmentList();
                    callheight.enqueue(new Callback<ApartmentList>() {
                        @Override
                        public void onResponse(Call<ApartmentList> callheight, Response<ApartmentList> response) {
                            ApartmentList eduresource = response.body();
                            List<ApartmentList.ApartmentListDatum> datumList = eduresource.data;
                            apartment_id = new String[datumList.size()];
                            apartment_address = new String[datumList.size()];
                            apartment_pincode = new String[datumList.size()];
                            apartment_city = new String[datumList.size()];
                            apartment_landmark = new String[datumList.size()];
                            int i = 0;
                            for (ApartmentList.ApartmentListDatum datum : datumList) {
                                apartment_list.add(datum.name);
                                apartment_id[i] = datum.apartment_id;
                                apartment_address[i] = datum.address;
                                apartment_pincode[i] = datum.pincode;
                                apartment_city[i] = datum.city;
                                apartment_landmark[i] = datum.landmark;
                                i++;
                            }

                            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(EditProfile_act.this,
                                    R.layout.apartment_spinner_item, apartment_list);
                            lvApartmentList.setAdapter(itemsAdapter);
                            lvApartmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String apartmentName = (String) lvApartmentList.getItemAtPosition(position);
                                    String apartmentId = apartment_id[position];
                                    String apartmentAddress = apartment_address[position];
                                    String apartmentPinCode = apartment_pincode[position];
                                    String apartmentCity = apartment_city[position];
                                    String apartmentLandmark = apartment_landmark[position];
                                    strApartmentId = apartmentId;
                                    sApartmentName = apartmentName;
                                    strAddress = apartmentAddress;
                                    strLandmark = apartmentLandmark;
                                    strPincode = apartmentPinCode;
                                    strCity = apartmentCity;

                                    tvApartmentName.setText(sApartmentName);
                                    etAddress.setText(strAddress + " , " + strLandmark);
                                    etCity.setText(strCity);
                                    etPinCode.setText(strPincode);

                                    mDrawer.closeDrawer(Gravity.RIGHT);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<ApartmentList> callheight, Throwable t) {
                            callheight.cancel();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                }
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
                            Glide.with(EditProfile_act.this).load(mpmResult.image).fitCenter().dontAnimate()
                                    .into(ivProfile);
                            etFName.setText(mpmResult.firstname);
                            etLName.setText(mpmResult.lastname);
                            etEmail.setText(mpmResult.email);
                            etMobileNo.setText(mpmResult.telephone);
                            tvApartmentName.setText(mpmResult.apartment);
                            etDoorNo.setText(mpmResult.door);
                            etCity.setText(mpmResult.city);
                            etAddress.setText(mpmResult.address_1);
                            etPinCode.setText(mpmResult.postcode);

                            sProfilePic = mpmResult.image;
                            sFName = mpmResult.firstname;
                            sLName = mpmResult.lastname;
                            sEmail = mpmResult.email;
                            sMobileNo = mpmResult.telephone;
                            sApartmentName = mpmResult.apartment;
                            sDoorNo = mpmResult.door;
                            sCity = mpmResult.city;
                            sAddress = mpmResult.address_1;
                            sPinCode = mpmResult.postcode;

                            if (mpmResult.nearby.equals("0")) {
                                tvNearBy.setVisibility(View.GONE);
                            } else {
                                tvNearBy.setVisibility(View.VISIBLE);
                            }
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
                sEmail = etEmail.getText().toString();
                sMobileNo = etMobileNo.getText().toString();
                sPassword = etOldPass.getText().toString();
                sNewPass = etNewPass.getText().toString();
                sConfirmPass = etConfirmPass.getText().toString();
                if ((sConfirmPass.trim()).equals(sNewPass.trim())) {
                    sNewPass = sConfirmPass;
                } else {
                    etConfirmPass.requestFocus();
                    etConfirmPass.setError("Password mismatch");
                }

                sDoorNo = etDoorNo.getText().toString();
                sCity = etCity.getText().toString();
                sAddress = etAddress.getText().toString();
                sPinCode = etPinCode.getText().toString();

                if (Utils.CheckInternetConnection(getApplicationContext())) {
                    if (!TextUtils.isEmpty(session.getCustomerId())) {
                        saveEditData(sFName, sLName, sEmail, sMobileNo, sPassword, sConfirmPass, sApartmentName,
                                sDoorNo, sAddress, sCity, sPinCode, strNearBy, strApartmentId);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveEditData(String sFName, String sLName, String sEmail, String sMobileNo, String sPassword,
                              String sConfPassword, String sApartmentName, String sDoorNo, String sAddress,
                              String sCity, String sPinCode, String sNearBy, String sApartmentId) {

        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final EditProfileModel editProfileModel = new EditProfileModel(session.getCustomerId(), sFName,
                sLName, sEmail, sMobileNo, sPassword, sConfPassword, sApartmentName, sDoorNo, sAddress,
                sCity, sPinCode, sNearBy, sApartmentId);

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
}

