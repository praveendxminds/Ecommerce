package com.app.ecommerce.Home;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.app.ecommerce.Delivery;
import com.app.ecommerce.ProfileSection.Navmenu_act;
import com.app.ecommerce.R;
import com.app.ecommerce.SessionManager;
import com.app.ecommerce.Splash;
import com.app.ecommerce.Utils;
import com.app.ecommerce.adapter.RemoteData;
import com.app.ecommerce.appIntro.WelcomeActivity;
import com.app.ecommerce.cart.cart;
import com.app.ecommerce.fcm.NotificationUtils;
import com.app.ecommerce.fcm.fcmConfig;
import com.app.ecommerce.notifications.MyNotifications;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.ProductslHomePage;
import com.app.ecommerce.search;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends AppCompatActivity {

    public Context mContext;
    private static HomePage instance;
    APIInterface apiInterface;
    SessionManager session;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    View view_count;

    public static BottomNavigationView bottomNavigationView;
    private Toolbar mToolbarHomePage;
    private PlaceHolderView list_items_homePage;
    ProgressBar progressBarHomePage;
    LinearLayout llProfileIcon;
    AutoCompleteTextView searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        session = new SessionManager(getApplicationContext());


        Integer cnt = session.getCartCount();
        Log.d("cartcnt", String.valueOf(cnt));

        mToolbarHomePage = (Toolbar) findViewById(R.id.toolbarHomePage);
        setSupportActionBar(mToolbarHomePage);
        getSupportActionBar().setTitle(null);
        instance = this;
        progressBarHomePage = (ProgressBar) findViewById(R.id.loadingHomePage);
        llProfileIcon = (LinearLayout) findViewById(R.id.llProfileIcon);
        list_items_homePage = (PlaceHolderView) findViewById(R.id.list_items_homePage);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationHomePage);
        // BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        mContext = this.getApplicationContext();

        AndroidNetworking.initialize(getApplicationContext());


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(HomePage.this, Splash.class);
                startActivity(i);
            }
        });
        t.start();

        final ArrayList<String> imageArray = new ArrayList<String>();
        final ArrayList<String> headArray = new ArrayList<String>();

        if (Utils.CheckInternetConnection(getApplicationContext())) {

            //-------------------------------------image slider view------------------------------------------------
            final ProductslHomePage productslHomePage = new ProductslHomePage("7");
            Call<ProductslHomePage> call = apiInterface.getHomePageProducts(productslHomePage);
            call.enqueue(new Callback<ProductslHomePage>() {
                @Override
                public void onResponse(Call<ProductslHomePage> call, Response<ProductslHomePage> response) {
                    ProductslHomePage resource = response.body();
                    List<ProductslHomePage.BannerList> datumList = resource.banner;
                    for (ProductslHomePage.BannerList imageslider1 : datumList) {
                        progressBarHomePage.setVisibility(View.INVISIBLE);
                        imageArray.add(imageslider1.image);
                        headArray.add(imageslider1.title);
                    }
                    list_items_homePage.addView(new HomePageImageSlider(mContext, headArray, imageArray));

                    //-----------------------------------------Recommended List-------------------------------------

                    List<ProductslHomePage.RecommendedList> imageRecomendProducts = resource.recommended;
                    List<ProductslHomePage.RecommendedList> newImageRecommendProducts = new ArrayList<>();
                    for (int i = 0; i < (imageRecomendProducts.size() > 10 ? 10 : imageRecomendProducts.size()); i++) {
                        newImageRecommendProducts.add(imageRecomendProducts.get(i));
                    }
                    list_items_homePage.addView(new HomePageRecommended(getApplicationContext(), newImageRecommendProducts));

                    //-----------------------------------------deal of day ------------------------------------------

                    List<ProductslHomePage.DealOfDayList> imageListDeal = resource.dealoftheday;
                    List<ProductslHomePage.DealOfDayList> newImageListDeal = new ArrayList<>();
                    for (int i = 0; i < (imageListDeal.size() > 10 ? 10 : imageListDeal.size()); i++) {
                        newImageListDeal.add(imageListDeal.get(i));
                    }
                    list_items_homePage.addView(new HomePageDealofDayList(getApplicationContext(), newImageListDeal));
                }

                @Override
                public void onFailure(Call<ProductslHomePage> call, Throwable t) {
                    call.cancel();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }


        llProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, Navmenu_act.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                break;

                            case R.id.navigation_wishlist:
                                break;

                            case R.id.navigation_wallet:
                                break;

                            case R.id.navigation_profile:
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
                }
            }
        };

        displayFirebaseRegId();
    }

    public static HomePage getInstance() {
        return instance;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);


        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchViews = (SearchView) searchViewItem.getActionView();
        searchViews.setQueryHint("Search...");
        searchViews.setBackgroundColor(getResources().getColor(R.color.white));

        int actionBarHeight = mToolbarHomePage.getLayoutParams().height;
        int actionBarwidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        Log.d("ccccc", String.valueOf(actionBarwidth));
        Log.d("aaaaaa", String.valueOf(actionBarHeight));
        LinearLayout.LayoutParams tvLay = new LinearLayout.LayoutParams((int)(actionBarwidth/1.8),
                (int)(actionBarHeight/1.9));
        searchViews.setBackground(ContextCompat.getDrawable(this, R.drawable.search_border));
        searchViews.setLayoutParams(tvLay);


        searchViews.setIconifiedByDefault(false);
        searchViews.setFocusable(false);
        searchViews.setIconified(false);
     //   searchViews.setIconifiedByDefault(false);

//        ImageView closeBtn = (ImageView) searchViews.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
//        closeBtn.setVisibility(View.GONE);
//        closeBtn.setEnabled(false);
//        closeBtn.setImageDrawable(null);


        ImageView searchMagIcon = (ImageView) searchViews.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchMagIcon.setImageResource(R.drawable.ic_search_black);


        searchViews.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
      //  searchViews.setMaxWidth(600);

        searchEditText = (AutoCompleteTextView) searchViews.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.black));





        searchEditText.setThreshold(1);//will start working from first character
        searchEditText.setTextColor(Color.BLACK);
        searchEditText.setOnItemClickListener(onItemClickListener);
        searchEditText.clearFocus();

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

        return true;
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent prdIntent = new Intent(getBaseContext(), ProductDetailHome.class);
            prdIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(prdIntent);
            Log.d("Firebase reg i: ", String.valueOf(i));
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                Intent notificationIntent = new Intent(getBaseContext(), MyNotifications.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(notificationIntent);
                break;

            case R.id.delivery:
                Intent DeliveryIntent = new Intent(getBaseContext(), cart.class);
                DeliveryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(DeliveryIntent);
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

                Intent accountIntent = new Intent(getBaseContext(), ProductDetailHome.class);
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
                        Toast.makeText(HomePage.this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();

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
}
