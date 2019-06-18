package com.app.ecommerce.Home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.app.ecommerce.R;
import com.app.ecommerce.Utils;
import com.app.ecommerce.barcode.ScannerActivity;
import com.app.ecommerce.barcode.nfc;
import com.app.ecommerce.cart.cart;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.ProductslHomePage;
import com.google.zxing.integration.android.IntentIntegrator;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CALL_PHONE;
import static com.app.ecommerce.Utils.CheckInternetConnection;

/**
 * Created by sushmita on 11/06/2019
 */


public class HomeCategory extends AppCompatActivity {

    Toolbar toolbar;
    private PlaceHolderView mCartView;
    private ImageButton btn_sortHomeCateg, btn_filterHomeCateg;
    APIInterface apiInterface;
    public Context mContext;
    private static HomeCategory instance;
    public static BottomNavigationView bottomNavigationView;
    View view_count;

    public boolean state = true;
    Integer name_session;

    UseSharedPreferences useSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_category);
        toolbar = (Toolbar) findViewById(R.id.toolbarHomeCateg);
        setSupportActionBar(toolbar);

        useSharedPreferences = new UseSharedPreferences(getApplication());

        AndroidNetworking.initialize(getApplicationContext());
        mContext = this.getApplicationContext();
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mCartView = (PlaceHolderView) findViewById(R.id.recycler_order);
        btn_filterHomeCateg = findViewById(R.id.btn_filterHomeCateg);
        btn_sortHomeCateg = findViewById(R.id.btn_sortHomeCateg);
        List<ProductslHomePage.DealOfDayList> mList = new ArrayList();

        //--------------------------2 columns on placeholderview---------------------------------------
      /*  GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        // Create a custom SpanSizeLookup where the first item spans both columns
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 0)
                    return 2;
                else
                    return 2;
            }
        });

        mCartView.setLayoutManager(layoutManager);*/

        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mCartView.setLayoutManager(layoutManager);
//------------------------------------------------------------------------------------------------------
        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (Utils.CheckInternetConnection(getApplicationContext())) {

            Call<ProductslHomePage> call = apiInterface.getHomePageProducts();
            call.enqueue(new Callback<ProductslHomePage>() {
                @Override
                public void onResponse(Call<ProductslHomePage> call, Response<ProductslHomePage> response) {

                    ProductslHomePage resource = response.body();
                    List<ProductslHomePage.DealOfDayList> datumList = resource.data;

                    for (ProductslHomePage.DealOfDayList imgs : datumList) {
                        if (response.isSuccessful()) {

                            mCartView.addView(new HomeCategoryItemGridView(mContext, imgs.prd_id, imgs.image, imgs.name, imgs.price));

                        }

                    }


                    mCartView.sort(new Comparator<Object>() {
                        @Override
                        public int compare(Object item1, Object item2) {
                            if (item1 instanceof HomeCategoryItem && item2 instanceof HomeCategoryItem) {
                                HomeCategoryItem view1 = (HomeCategoryItem) item1;
                                HomeCategoryItem view2 = (HomeCategoryItem) item2;
                                return view1.getTitle().compareTo(view2.getTitle());
                            }
                            return 0;
                        }
                    });

                    mCartView.refresh();


                }

                @Override
                public void onFailure(Call<ProductslHomePage> call, Throwable t) {
                    call.cancel();
                }
            });


        } else {
            Toast.makeText(mContext, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        // BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);


        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                //selectedFragment = ItemOneFragment.newInstance();
                                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();


                                break;
                            case R.id.navigation_scan:
                                //selectedFragment = ItemTwoFragment.newInstance();

                                new IntentIntegrator(HomeCategory.this).setCaptureActivity(ScannerActivity.class).initiateScan();
                                break;
                            case R.id.navigation_tap:
                                //selectedFragment = ItemThreeFragment.newInstance();

                                Intent nfcIntent = new Intent(getBaseContext(), nfc.class);
                                startActivity(nfcIntent);


                                break;
                            case R.id.navigation_call:
                                Intent i = new Intent(Intent.ACTION_CALL);
                                i.setData(Uri.parse("tel:18001231947"));
                                if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                    startActivity(i);
                                } else {
                                    requestPermissions(new String[]{CALL_PHONE}, 1);
                                }
                                break;
                            case R.id.navigation_cart:
                                Intent myIntent = new Intent(getBaseContext(), cart.class);
                                startActivity(myIntent);

                                break;
                        }
                        return true;
                    }
                });
//-------------------------------sort and filter option--------------------------------------------

        btn_sortHomeCateg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (state == false) {
                    //sort
                    mCartView.sort(new Comparator<Object>() {
                        @Override
                        public int compare(Object item1, Object item2) {
                            if (item1 instanceof HomeCategoryItemGridView && item2 instanceof HomeCategoryItemGridView) {
                                HomeCategoryItemGridView view1 = (HomeCategoryItemGridView) item1;
                                HomeCategoryItemGridView view2 = (HomeCategoryItemGridView) item2;
                                Log.e("----sorted------", view1.getTitle() + "  " + view2.getTitle());
                                v.setBackgroundResource(R.drawable.settings);
                                //changing image after clicking...
                               // btn_sortHomeCateg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.settings, 0, 0, 0);
                                return view1.getTitle().compareTo(view2.getTitle());
                            }
                            return 0;
                        }
                    });

                    mCartView.refresh();
                    state = true;
                } else {
                    //reverse
                    mCartView.sort(new Comparator<Object>() {
                        @Override
                        public int compare(Object item1, Object item2) {
                            if (item1 instanceof HomeCategoryItemGridView && item2 instanceof HomeCategoryItemGridView) {
                                HomeCategoryItemGridView view1 = (HomeCategoryItemGridView) item1;
                                HomeCategoryItemGridView view2 = (HomeCategoryItemGridView) item2;
                                 v.setBackgroundResource(R.drawable.phone);
                                //changing image after clicking
                               // btn_sortHomeCateg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.phone, 0, 0, 0);
                                Log.e("----reversed------", view1.getTitle() + "  " + view2.getTitle());
                                return view2.getTitle().compareToIgnoreCase(view1.getTitle());
                            }
                            return 0;
                        }
                    });
                    mCartView.refresh();
                    state = false;
                }
            }
        });

    }

    //--------------------------------------toolbarmenu------------------------------------------------------------------
//---------------------------------------for scanner popup-------------------------------------------------------
    public void showResultDialogue(final String result) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Scan Result")
                .setMessage("Scanned result is " + result)
                .setPositiveButton("Copy result", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Result", result);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(HomeCategory.this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .show();
    }

    //----------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onResume() {
        super.onResume();
        name_session = useSharedPreferences.getCountValue();

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        view_count = bottomNavigationMenuView.getChildAt(4);
        new QBadgeView(mContext).bindTarget(view_count).setBadgeTextColor(mContext.getResources().getColor(R.color.white))
                .setGravityOffset(15, -2, true).setBadgeNumber(name_session).setBadgeBackgroundColor
                (mContext.getResources().getColor(R.color.colorPrimaryDark));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}