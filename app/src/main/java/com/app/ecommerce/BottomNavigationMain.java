package com.app.ecommerce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.ecommerce.Wishlist.WishListHolder;
import com.app.ecommerce.barcode.ScannerActivity;
import com.app.ecommerce.barcode.nfc;
import com.app.ecommerce.cart.cart;
import com.google.zxing.integration.android.IntentIntegrator;

import static android.Manifest.permission.CALL_PHONE;

public class BottomNavigationMain extends AppCompatActivity {
    public static BottomNavigationView bottomNavigationView;
    Activity activity=BottomNavigationMain.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
    //getBottomNavigation(activity);
    }
    public void getBottomNavigation(final Activity activity)
    {

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

                                new IntentIntegrator(activity).setCaptureActivity(ScannerActivity.class).initiateScan();
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

    }
}
