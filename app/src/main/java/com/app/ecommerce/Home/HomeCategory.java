package com.app.ecommerce.Home;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.app.ecommerce.ContactUs;
import com.app.ecommerce.Delivery;
import com.app.ecommerce.MyOrder.MyOrders;
import com.app.ecommerce.PrivacyPolicy;
import com.app.ecommerce.ProfileSection.EditProfile_act;
import com.app.ecommerce.ProfileSection.Faqs_act;
import com.app.ecommerce.ProfileSection.GoogleFeedback_act;
import com.app.ecommerce.ProfileSection.Login_act;
import com.app.ecommerce.ProfileSection.MyListAdapter;
import com.app.ecommerce.ProfileSection.MyProfile_act;
import com.app.ecommerce.ProfileSection.Offers_act;
import com.app.ecommerce.ProfileSection.RateUs_act;
import com.app.ecommerce.ProfileSection.RefersAndEarn_act;
import com.app.ecommerce.R;
import com.app.ecommerce.SessionManager;
import com.app.ecommerce.TermsConditions;
import com.app.ecommerce.Utils;
import com.app.ecommerce.Wishlist.WishListHolder;
import com.app.ecommerce.adapter.RemoteData;
import com.app.ecommerce.barcode.ScannerActivity;
import com.app.ecommerce.barcode.nfc;
import com.app.ecommerce.cart.cart;
import com.app.ecommerce.notifications.MyNotifications;
import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.ProductListModel;
import com.app.ecommerce.retrofit.ProductslHomePage;
import com.app.ecommerce.search;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipeViewBuilder;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

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

    APIInterface apiInterface;
    public Context mContext;

    Toolbar toolbar;
    private PlaceHolderView mCartView;
    private ImageButton btn_listViewHomeCateg, btn_filterHomeCateg;
    public static BottomNavigationView bottomNavigationView;
    View view_count;
    private LinearLayout llProfileIconCateg, llProfileDesc;
    private ImageButton imgBtnProfileCateg;
    AutoCompleteTextView searchEditText;
    private TextView tv_totalPrds;
    private ProgressBar progressBarHomeCateg;
    private ListView lvMenuListCateg;
    private ImageView ivEditProfileCateg;
    private ImageView ivProfilePic, ivEditProfile;
    private TextView tvName, tvEmail, tvMobileNo;
    private Button btnEditProfilePic;


    DrawerLayout drawerLayout;
    public boolean chngView = true;
    public boolean state = true;
    Integer name_session;
    SessionManager session;
    public static TextView textCartItemCount;
    UseSharedPreferences useSharedPreferences;
    Integer[] icon = {R.drawable.round_home_24px, R.drawable.my_order_yellow, R.drawable.location_yellow,
            R.drawable.walletyellow, R.drawable.offers_yellow, R.drawable.refer_earn_yellow, R.drawable.rateus_yellow,
            R.drawable.abt_contact_yellow, R.drawable.faqs_yellow, R.drawable.terms_yellow, R.drawable.google_feedback_yellow,
            R.drawable.privacy_policy_yellow, R.drawable.logout_yellow};

    String[] menu_list = new String[]{"Home", "My Orders", "My Address", "My Wallet", "Offers",
            "Refer & Earn", "Rate Us", "About & Contact Us", "FAQs", "Terms & Conditions",
            "Google Feedback", "Policy", "Logout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_category);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        mContext = this.getApplicationContext();

        session = new SessionManager(getApplicationContext());


        Integer cnt = session.getCartCount();
        Log.d("cartcnt", String.valueOf(cnt));
        useSharedPreferences = new UseSharedPreferences(getApplication());
        AndroidNetworking.initialize(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbarHomeCateg);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        progressBarHomeCateg = (ProgressBar) findViewById(R.id.loadingHomeCateg);
        llProfileIconCateg = findViewById(R.id.llProfileIconCateg);
        imgBtnProfileCateg = findViewById(R.id.imgBtnProfileCateg);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerHomeCateg);
        mCartView = (PlaceHolderView) findViewById(R.id.recycler_order);
        btn_filterHomeCateg = findViewById(R.id.btn_filterHomeCateg);
        btn_listViewHomeCateg = findViewById(R.id.btn_listViewHomeCateg);
        tv_totalPrds = findViewById(R.id.tv_totalPrds);

        lvMenuListCateg = findViewById(R.id.lvMenuList);

        ActionBar mActionBar = getActionBar();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        ivEditProfile = findViewById(R.id.ivEditProfile);
        llProfileDesc = findViewById(R.id.llProfileDesc);

        btnEditProfilePic = findViewById(R.id.btnEditProfilePic);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobileNo = findViewById(R.id.tvMobileNo);
        llProfileDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditProfile = new Intent(getBaseContext(), MyProfile_act.class);
                startActivity(intentEditProfile);
            }
        });

        ivEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditProfile = new Intent(getBaseContext(), EditProfile_act.class);
                startActivity(intentEditProfile);
            }
        });
        imgBtnProfileCateg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        showGridView();
        btn_listViewHomeCateg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chngView == false) {
                    showGridView();
                    btn_listViewHomeCateg.setBackgroundResource(R.drawable.listview);
                    chngView = true;
                } else {
                    showListView();
                    btn_listViewHomeCateg.setBackgroundResource(R.drawable.gridview);
                    chngView = false;
                }
            }
        });

        btn_filterHomeCateg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFilter = new Intent(getBaseContext(), CategoryFilter.class);
                startActivity(intentFilter);
            }
        });
        //profile navigation menu items----------------------
        MyListAdapter adapter = new MyListAdapter(this, icon, menu_list);
        lvMenuListCateg.setAdapter(adapter);
        lvMenuListCateg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(HomeCategory.this, HomePage.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(HomeCategory.this, MyOrders.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(HomeCategory.this, Login_act.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(HomeCategory.this, EditProfile_act.class);
                    startActivity(intent);
                } else if (position == 4) {
                    Intent intent = new Intent(HomeCategory.this, Offers_act.class);
                    startActivity(intent);
                } else if (position == 5) {
                    Intent intent = new Intent(HomeCategory.this, RefersAndEarn_act.class);
                    startActivity(intent);
                } else if (position == 6) {
                    Intent intent = new Intent(HomeCategory.this, RateUs_act.class);
                    startActivity(intent);
                } else if (position == 7) {
                    Intent intent = new Intent(HomeCategory.this, ContactUs.class);
                    startActivity(intent);
                } else if (position == 8) {
                    Intent intent = new Intent(HomeCategory.this, Faqs_act.class);
                    startActivity(intent);
                } else if (position == 9) {
                    Intent intent = new Intent(HomeCategory.this, TermsConditions.class);
                    startActivity(intent);
                } else if (position == 10) {
                    Intent intent = new Intent(HomeCategory.this, GoogleFeedback_act.class);
                    startActivity(intent);
                } else if (position == 11) {
                    Intent intent = new Intent(HomeCategory.this, PrivacyPolicy.class);
                    startActivity(intent);
                } else if (position == 12) {
                    Intent intent = new Intent(HomeCategory.this, PrivacyPolicy.class);
                    startActivity(intent);
                }
            }
        });
        //---------------------------------------------------
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                Intent intentHome = new Intent(getBaseContext(), HomePage.class);
                                startActivity(intentHome);
                                break;

                            case R.id.navigation_categories:
                                Intent intentCateg = new Intent(getBaseContext(), CategoriesBottomNav.class);
                                startActivity(intentCateg);
                                break;

                            case R.id.navigation_wishlist:
                                Intent intentWishlist = new Intent(getBaseContext(), WishListHolder.class);
                                intentWishlist.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentWishlist);
                                break;

                            case R.id.navigation_wallet:
                                break;

                        }
                        return true;
                    }
                });
        bottomNavigationView.setItemIconSize(38);
    }

    public void showListView() {
        mCartView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            Call<ProductListModel> call = apiInterface.getProductsList();
            call.enqueue(new Callback<ProductListModel>() {
                @Override
                public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {
                    ProductListModel resource = response.body();
                    List<ProductListModel.ProductListDatum> datumList = resource.result;

                    for (ProductListModel.ProductListDatum imgs : datumList) {
                        if (response.isSuccessful()) {
                            progressBarHomeCateg.setVisibility(View.INVISIBLE);
                            mCartView.addView(new HomeCategoryItem(mContext, textCartItemCount, imgs.prd_id, imgs.image,
                                    imgs.name, imgs.price, imgs.qty));
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
                public void onFailure(Call<ProductListModel> call, Throwable t) {
                    call.cancel();
                }
            });


        } else {
            Toast.makeText(mContext, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void showGridView() {
        mCartView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            Call<ProductListModel> call = apiInterface.getProductsList();
            call.enqueue(new Callback<ProductListModel>() {
                @Override
                public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {
                    ProductListModel resource = response.body();
                    List<ProductListModel.ProductListDatum> datumList = resource.result;

                    for (ProductListModel.ProductListDatum imgs : datumList) {
                        if (response.isSuccessful()) {
                            progressBarHomeCateg.setVisibility(View.INVISIBLE);
                            mCartView.addView(new HomeCategoryItemGridView(mContext, textCartItemCount, imgs.prd_id, imgs.image,
                                    imgs.name, imgs.price, imgs.qty));
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
                public void onFailure(Call<ProductListModel> call, Throwable t) {
                    call.cancel();
                }
            });


        } else {
            Toast.makeText(mContext, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    //---------------------------------------for scanner popup--------------------------------
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
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Result", result);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(HomeCategory.this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        name_session = useSharedPreferences.getCountValue();

        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        view_count = bottomNavigationMenuView.getChildAt(4);
      /*  new QBadgeView(mContext).bindTarget(view_count).setBadgeTextColor(mContext.getResources().getColor(R.color.white))
                .setGravityOffset(15, -2, true).setBadgeNumber(name_session).setBadgeBackgroundColor
                (mContext.getResources().getColor(R.color.colorPrimaryDark));*/
    }

    /* @Override
     public boolean onSupportNavigateUp() {
         onBackPressed();
         return true;
     }
 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_search, menu);

        MenuItem cart_menuItem = menu.findItem(R.id.cartmenu);
        FrameLayout rootView = (FrameLayout) cart_menuItem.getActionView();
        textCartItemCount = (TextView) rootView.findViewById(R.id.cart_badge);

        Integer cnt = session.getCartCount();
        textCartItemCount.setText(String.valueOf(cnt));

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent DeliveryIntent = new Intent(getBaseContext(), cart.class);
                DeliveryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(DeliveryIntent);

            }
        });

        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final android.support.v7.widget.SearchView searchViews = (android.support.v7.widget.SearchView) searchViewItem.getActionView();
        //searchViews.setQueryHint("Search...");
        searchViews.setBackgroundColor(getResources().getColor(R.color.white));

        int actionBarHeight = toolbar.getLayoutParams().height;
        int actionBarwidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        Log.d("ccccc", String.valueOf(actionBarwidth));
        Log.d("aaaaaa", String.valueOf(actionBarHeight));
        LinearLayout.LayoutParams tvLay = new LinearLayout.LayoutParams((int) (actionBarwidth / 1.65),
                (int) (actionBarHeight / 1.7));
        searchViews.setBackground(ContextCompat.getDrawable(this, R.drawable.search_border));
        searchViews.setLayoutParams(tvLay);

        searchViews.setIconifiedByDefault(false);//make default request focus disable
        searchViews.setFocusable(false);
        searchViews.setIconified(false);

        final ImageView searchMagIcon = (ImageView) searchViews.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchMagIcon.setImageResource(R.drawable.ic_search_black_24dp);
        searchMagIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        searchMagIcon.setPadding(0, 0, 0, 0);
        //searchViews.setPadding(-16, 0, 0, 0);//removing extraa space and align icon to leftmost of searchview
        searchViews.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchEditText = (AutoCompleteTextView) searchViews.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setPadding(2, 2, 2, 2);
        searchEditText.setHint(null);//removing search hint from search layout
        searchEditText.setThreshold(1);//will start working from first character
        searchEditText.setTextColor(Color.parseColor("#824A4A4A"));
        searchEditText.setBackgroundColor(Color.TRANSPARENT);
        searchEditText.setOnItemClickListener(onItemClickListener);
        searchMagIcon.setImageDrawable(null);//remove search icon
        searchEditText.clearFocus();

        RemoteData remoteData = new RemoteData(this);
        remoteData.getStoreData();
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

            case R.id.cart:
                Intent DeliveryIntent = new Intent(getBaseContext(), cart.class);
                DeliveryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(DeliveryIntent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}