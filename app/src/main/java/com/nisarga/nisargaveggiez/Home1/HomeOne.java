package com.nisarga.nisargaveggiez.Home1;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.appIntro.WelcomeActivity;
import com.nisarga.nisargaveggiez.barcode.ScannerActivity;
import com.nisarga.nisargaveggiez.barcode.nfc;
import com.nisarga.nisargaveggiez.cart.cart;
import com.nisarga.nisargaveggiez.Delivery;
import com.nisarga.nisargaveggiez.drawer.DrawerHeader;
import com.nisarga.nisargaveggiez.drawer.DrawerMenuItem;
import com.nisarga.nisargaveggiez.fcm.NotificationUtils;
import com.nisarga.nisargaveggiez.fcm.fcmConfig;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.ProductDetails_act;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.ProductslHomeOne;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CALL_PHONE;

public class HomeOne extends AppCompatActivity {

    private PlaceHolderView mDrawerViewHomeOne;
    private DrawerLayout mDrawerHomeOne;
    private Toolbar mToolbarHomeOne;
    private PlaceHolderView list_items_home_one;
    public Context mContext;
    private ViewPager vpagerHomeOne;
    private static HomeOne instance;
    APIInterface apiInterface;
    private String url;
    ProgressBar progressBarHomeOne;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    AutoCompleteTextView searchEditText;
    public static String MyPREFERENCES = "sessiondata";
    SharedPreferences sharedpreferences;


    int crt_cnt = 0;
    View view_count;
    Integer name_session;
    android.view.View menuItemView;
    public static BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_one);

        AndroidNetworking.initialize(getApplicationContext());


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(HomeOne.this, WelcomeActivity.class);
                startActivity(i);

            }
        });
        t.start();


        crt_cnt = 0;

        mDrawerHomeOne = (DrawerLayout) findViewById(R.id.drawerLayoutHomeOne);
        mDrawerViewHomeOne = (PlaceHolderView) findViewById(R.id.drawerViewHomeOne);
        mToolbarHomeOne = (Toolbar) findViewById(R.id.toolbarHomeOne);
        progressBarHomeOne = (ProgressBar) findViewById(R.id.loadingHomeOne);
        //-----------------------------------------------------------AdapterFlipper------------------------------------------------------

        setSupportActionBar(mToolbarHomeOne);
        getSupportActionBar().setTitle(null);
        instance = this;


        list_items_home_one = (PlaceHolderView) findViewById(R.id.list_items_home_one);

        mContext = this.getApplicationContext();
        url = "https://www.groceryfactory.in/media/em_minislideshow/1517843523_0_banner-1.jpg";
        //list_items.setLayoutManager(new GridLayoutManager(this, 2));//for view HomeTwoCategory list in grid view
// Create a grid layout with two columns
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        // Create a custom SpanSizeLookup where the first item spans both columns
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });

        list_items_home_one.setLayoutManager(layoutManager);


        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        final ArrayList<String> imageArray = new ArrayList<String>();
        final ArrayList<String> headArray = new ArrayList<String>();
        Log.w("myApp", android_id);
        apiInterface = APIClient.getClient().create(APIInterface.class);


        if (Utils.CheckInternetConnection(getApplicationContext())) {
//-------------------------------------image slider view----------------------------------------------------------------------
            Call<ProductslHomeOne> call = apiInterface.doGetProducts();
            call.enqueue(new Callback<ProductslHomeOne>() {
                @Override
                public void onResponse(Call<ProductslHomeOne> call, Response<ProductslHomeOne> response) {

                    ProductslHomeOne resource = response.body();

                    List<ProductslHomeOne.imageslider> datumList = resource.resultdata;
                    for (ProductslHomeOne.imageslider imageslider1 : datumList) {

                        //  list_items.addView(new HomeTwoImageSlider(getApplicationContext(), imageslider1.title, imageslider1.slimg));

                        progressBarHomeOne.setVisibility(View.INVISIBLE);
                        Log.e("-----imgslider--", imageslider1.slimg);
                        imageArray.add(imageslider1.slimg);
                        Log.e("-----imgtitle--", imageslider1.title);
                        headArray.add(imageslider1.title);

                    }
                    list_items_home_one.addView(new HomeOneImageSlider(mContext, headArray, imageArray));


                    List<ProductslHomeOne.products> datumList1 = resource.data;

                    for (ProductslHomeOne.products product : datumList1) {

                        list_items_home_one.addView(new HomeOneProductItem(mContext, product.title, product.url, product.price, product.qty));

                        Log.e("-----imgurl--", product.url);
                    }
                }
                @Override
                public void onFailure(Call<ProductslHomeOne> call, Throwable t) {
                    call.cancel();
                }
            });
            //--------------------------------------product list-----------------------------------------------------


        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

        setupDrawer();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationHomeOne);
//        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);


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

                                new IntentIntegrator(HomeOne.this).setCaptureActivity(ScannerActivity.class).initiateScan();
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


        /*  FCM */

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(fcmConfig.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(fcmConfig.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(fcmConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //  txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();


        /*  FCM */

    }


    private void setupDrawer() {
        mDrawerViewHomeOne
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

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerHomeOne, mToolbarHomeOne, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerHomeOne.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    public void closeDrawer() {
        mDrawerHomeOne.closeDrawer(Gravity.LEFT);
    }


    public static HomeOne getInstance() {
        return instance;
    }


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
        });*/


        return true;
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.Delivery:
                Intent DeliveryIntent = new Intent(getBaseContext(), Delivery.class);
                DeliveryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(DeliveryIntent);
                break;
           /* case R.id.account:
                Intent myIntent = new Intent(mContext, profile.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(myIntent);
               *//* Boolean login_st_session = sharedpreferences.getBoolean("status", false);
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

                break;*/

            case R.id.notification:

                Intent notificationIntent = new Intent(getBaseContext(), MyNotifications.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(notificationIntent);

                break;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //show dialogue with result
                showResultDialogue(result.getContents());

                Intent accountIntent = new Intent(getBaseContext(), ProductDetails_act.class);
                startActivity(accountIntent);

            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //method to construct dialogue with scan results
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
                        Toast.makeText(HomeOne.this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();

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

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(fcmConfig.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

//        Log.d( "Firebase reg id: ", regId);

        //if (!TextUtils.isEmpty(regId))
        //  txtRegId.setText("Firebase Reg Id: " + regId);
        // else
        //   txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDrawerViewHomeOne.refresh();

        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        name_session = sharedpreferences.getInt("count", 0);


        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        view_count = bottomNavigationMenuView.getChildAt(4);
        new QBadgeView(mContext).bindTarget(view_count).setBadgeTextColor(mContext.getResources()
                .getColor(R.color.white)).setGravityOffset(15, -2, true)
                .setBadgeNumber(name_session).setBadgeBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));


        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(fcmConfig.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(fcmConfig.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {


        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    //-----------------------------------------------------------------------Add-to-Cart----------------------------------------------------


}
