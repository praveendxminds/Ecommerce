/*
package com.nisarga.nisargaveggiez.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.ProfileSection.FilterCategoryModel;
import com.nisarga.nisargaveggiez.R;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFilter extends AppCompatActivity {

    APIInterface apiInterface;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_filter);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressdialog = new ProgressDialog(CategoryFilter.this);
        progressdialog.setMessage("Please Wait....");
        init();
    }

    TextView tvSortByText, tvSeekBarMin, tvSeekBarMax;
    LinearLayout llSortByOption;
    CheckBox cbPopularity, cbLowToHigh, cbHighToLow, cbNewestFirst;
    CrystalRangeSeekbar rangeSeekbar;
    Button btnClearFilter, btnApplyFilter;
    ImageView ivDropDown, ivDropUp;

    String sPopularity, sLowToHigh, sHighToLow, sNewestFirst, minPrice, maxPrice;
    int sFilterPopularity = 0;
    int sFilterLowToHigh = 0;
    int sFilterHighToLow = 0;
    int sFilterNewestFirst = 0;

    private void init() {
        tvSortByText = findViewById(R.id.tvSortByText);
        tvSeekBarMin = findViewById(R.id.tvSeekBarMin);
        tvSeekBarMax = findViewById(R.id.tvSeekBarMax);

        ivDropDown = findViewById(R.id.ivDropDown);
        ivDropUp = findViewById(R.id.ivDropUp);
        llSortByOption = findViewById(R.id.llSortByOption);

        cbPopularity = findViewById(R.id.cbPopularity);
        cbLowToHigh = findViewById(R.id.cbLowToHigh);
        cbHighToLow = findViewById(R.id.cbHighToLow);
        cbNewestFirst = findViewById(R.id.cbNewestFirst);

        rangeSeekbar = findViewById(R.id.rangeSeekbar);

        btnClearFilter = findViewById(R.id.btnClearFilter);
        btnApplyFilter = findViewById(R.id.btnApplyFilter);

        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minPrice = String.valueOf(minValue);
                maxPrice = String.valueOf(maxValue);
                tvSeekBarMin.setText(minPrice);
                tvSeekBarMax.setText(maxPrice);
            }
        });

        ivDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSortByOption.setVisibility(View.VISIBLE);
                ivDropDown.setVisibility(View.GONE);
                ivDropUp.setVisibility(View.VISIBLE);
            }
        });

        ivDropUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSortByOption.setVisibility(View.GONE);
                ivDropUp.setVisibility(View.GONE);
                ivDropDown.setVisibility(View.VISIBLE);
            }
        });

        cbPopularity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    sPopularity = cbPopularity.getText().toString();
                    sFilterPopularity = 1;
                    // tvSortByText.setText(sPopularity + ", " + sLowToHigh + ", " + sHighToLow + ", " + sNewestFirst);
                }
            }
        });

        cbLowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    sLowToHigh = cbLowToHigh.getText().toString();
                    sFilterLowToHigh = 1;
                    //  tvSortByText.setText(sPopularity + ", " + sLowToHigh + ", " + sHighToLow + ", " + sNewestFirst);
                }
            }
        });

        cbHighToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    sHighToLow = cbHighToLow.getText().toString();
                    sFilterHighToLow = 1;
                    //tvSortByText.setText(sPopularity + ", " + sLowToHigh + ", " + sHighToLow + ", " + sNewestFirst);
                }
            }
        });

        cbNewestFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    sNewestFirst = cbNewestFirst.getText().toString();
                    sFilterNewestFirst = 1;
                    // tvSortByText.setText(sPopularity + ", " + sLowToHigh + ", " + sHighToLow + ", " + sNewestFirst);
                }
            }
        });

        btnClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.CheckInternetConnection(getApplicationContext())) {
                    saveFilterData(sFilterPopularity, sFilterLowToHigh, sFilterHighToLow, sFilterNewestFirst, minPrice, maxPrice);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveFilterData(int sFilterPopularity, int sFilterLowToHigh, int sFilterHighToLow, int sFilterNewestFirst,
                                String minPrice, String maxPrice) {

        final FilterCategoryModel model = new FilterCategoryModel(sFilterPopularity, sFilterLowToHigh, sFilterHighToLow,
                sFilterNewestFirst, minPrice, maxPrice);

        Call<FilterCategoryModel> callEditProfile = apiInterface.filter_products(model);
        callEditProfile.enqueue(new Callback<FilterCategoryModel>() {
            @Override
            public void onResponse(Call<FilterCategoryModel> call, Response<FilterCategoryModel> response) {
                FilterCategoryModel resourceMyProfile = response.body();
                if (resourceMyProfile.status.equals("success")) {
                    Intent intent = new Intent(CategoryFilter.this, HomeCategory.class);
                    startActivity(intent);
                } else if (resourceMyProfile.status.equals("failure")) {
                    Toast.makeText(CategoryFilter.this, resourceMyProfile.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterCategoryModel> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
*/
