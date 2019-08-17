package com.nisarga.nisargaveggiez.Home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nisarga.nisargaveggiez.R;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryFilter extends AppCompatActivity {

    Context context;
    FrameLayout drawerContent;
    LinearLayout llSeekbarContainer,llSortBy,llSortByOptions;
    CrystalRangeSeekbar rangeSeekbar;
    private RangeSeekBar<Integer> seekBar;
    Toolbar toolbarFilter;
    private TextView tvSortByVal;
    private Spinner spnrSortBy;
    private TextView tvSeekBarMid, tv_seekbarMin, tv_seekbarMax;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_filter);

        drawerContent = findViewById(R.id.drawerContent);
        tvSortByVal = findViewById(R.id.tvSortByVal);
      //  spnrSortBy = findViewById(R.id.spnrSortBy);
        tvSeekBarMid = findViewById(R.id.tvSeekBarMid);
        tv_seekbarMax = findViewById(R.id.tv_seekbarMax);
        tv_seekbarMin = findViewById(R.id.tv_seekbarMin);
        toolbarFilter = findViewById(R.id.toolbarFilter);
        setSupportActionBar(toolbarFilter);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        llSortByOptions = findViewById(R.id.llSortByOptions);
        llSortBy = findViewById(R.id.llSortBy);
        llSeekbarContainer = findViewById(R.id.llSeekbarContainer);
        rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.rangeSeekbar1);
      /*  Bitmap bmLeftThumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.oval);
        Bitmap bmRightThumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.oval);
      rangeSeekbar.setLeftThumbBitmap(bmLeftThumb);
        rangeSeekbar.setRightThumbBitmap(bmRightThumb);
*/
      selectSortBy();
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tv_seekbarMin.setText(String.valueOf(minValue));
                tvSeekBarMid.setText(String.valueOf(maxValue));
            }
        });

        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });

    }
public void selectSortBy()
{
    llSortBy.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            llSortByOptions.setVisibility(View.VISIBLE);
        }
    });
}
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    } @Override
    protected void onResume() {
        super.onResume();
    }


}
