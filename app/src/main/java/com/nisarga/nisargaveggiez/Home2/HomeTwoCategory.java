package com.nisarga.nisargaveggiez.Home2;

/**
 * Created by sushmita on 06/04/19.
 */


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;


import com.nisarga.nisargaveggiez.BottomNavigationViewHelper;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.barcode.ScannerActivity;
import com.nisarga.nisargaveggiez.barcode.nfc;
import com.nisarga.nisargaveggiez.cart.cart;
import com.nisarga.nisargaveggiez.Delivery;
import com.nisarga.nisargaveggiez.drawer.DrawerHeader;
import com.nisarga.nisargaveggiez.drawer.DrawerMenuItem;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.ProductDetails_act;
import com.google.zxing.integration.android.IntentIntegrator;
import com.mindorks.placeholderview.PlaceHolderView;

import static android.Manifest.permission.CALL_PHONE;

public class HomeTwoCategory extends AppCompatActivity {

    Toolbar toolbar;
    View menuItemView;
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;
    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private static HomeTwoCategory instance;
    public Context mContext;
    AutoCompleteTextView searchEditText;
    public static BottomNavigationView bottomNavigationView;
    int crt_cnt = 0;
    View view_count;
    Integer name_session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_2_category);


        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerView = (PlaceHolderView) findViewById(R.id.drawerView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        instance = this;

        mContext = this.getApplicationContext();


//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent i = new Intent(HomeTwoCategory.this, WelcomeActivity.class);
//                startActivity(i);
//
//            }
//        });
//        t.start();


        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setupDrawer();


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("tab1"));
        tabLayout.addTab(tabLayout.newTab().setText("tab2"));
        tabLayout.addTab(tabLayout.newTab().setText("tab3"));
        tabLayout.addTab(tabLayout.newTab().setText("tab4"));



        // tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount()

        );


        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //---------------------------------------------------------------------------------------footer contents-----------------------------

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);


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

                                new IntentIntegrator(HomeTwoCategory.this).setCaptureActivity(ScannerActivity.class).initiateScan();
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


        //-----------------------------------------------------------------------------------------------------------------------------------

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void setupDrawer() {
        mDrawerView
                .addView(new DrawerHeader(this.getApplicationContext()))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PREFERENCE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_ABOUT))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_FRANCHISE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_CALL))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_EMAIL))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_CONTACT))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_RATE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_FEEDBACK))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_SHARE));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    public void closeDrawer() {
        mDrawer.closeDrawer(Gravity.LEFT);
    }


    public static HomeTwoCategory getInstance() {
        return instance;
    }
    //-----------------------------------------------------------------------Category menu settings------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);

        //getting the search view from the menu
     /*   MenuItem searchViewItem = menu.findItem(R.id.action_search);

        //getting search manager from systemservice
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        //getting the search view
        final SearchView searchViews = (SearchView) searchViewItem.getActionView();

        //you can put a hint for the search input field
        searchViews.setQueryHint("Search...");
        searchViews.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //by setting it true we are making it iconified
        //so the search input will show up after taping the search iconified
        //if you want to make it visible all the time make it false
        searchViews.setMaxWidth(Integer.MAX_VALUE);

        searchEditText = (AutoCompleteTextView) searchViews.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        searchEditText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


        searchEditText.setThreshold(1);//will start working from first character
        searchEditText.setTextColor(Color.WHITE);
        searchEditText.setOnItemClickListener(onItemClickListener);

        RemoteData remoteData = new RemoteData(this);
        remoteData.getStoreData();


        //here we will get the search query
        searchViews.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent accountIntent = new Intent(getBaseContext(), search.class);
                startActivity(accountIntent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.Delivery:
                Intent DeliveryIntent = new Intent(getBaseContext(), Delivery.class);
                DeliveryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(DeliveryIntent);
                break;
            /*case R.id.account:
                Intent myIntent = new Intent(mContext, profile.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
              *//*  Boolean login_st_session = sharedpreferences.getBoolean("status", false);
                if (login_st_session == true) {
                    Intent myIntent = new Intent(mContext, profile.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(myIntent);
                } else {
                    Intent myIntent = new Intent(mContext, login.class);
                    myIntent.putExtra("Login_INTENT", "account");
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(myIntent);
                }*//*

                break;
*/
            case R.id.notification:

                Intent notificationIntent = new Intent(getBaseContext(), MyNotifications.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(notificationIntent);

                break;


        }
        return true;
    }

//----------------------------------------------------------------------------------------------------------------------------


    private CharSequence menuIconWithText(Drawable r, String title) {

        r.setBounds(0, 0, r.getIntrinsicWidth(), r.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(r, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }


    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent prdIntent = new Intent(getBaseContext(), ProductDetails_act.class);
                    prdIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(prdIntent);
                    Log.d("Firebase reg i: ", String.valueOf(i));

                }
            };





}

