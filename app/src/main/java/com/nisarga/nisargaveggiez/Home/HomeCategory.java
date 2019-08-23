package com.nisarga.nisargaveggiez.Home;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.bumptech.glide.Glide;
import com.nisarga.nisargaveggiez.ContactUs;
import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.MyOrder.MyOrders;
import com.nisarga.nisargaveggiez.PrivacyPolicy;
import com.nisarga.nisargaveggiez.ProfileSection.EditProfile_act;
import com.nisarga.nisargaveggiez.ProfileSection.GoogleFeedback_act;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfileModel;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfile_act;
import com.nisarga.nisargaveggiez.ProfileSection.NavEditImage;
import com.nisarga.nisargaveggiez.ProfileSection.Offers_act;
import com.nisarga.nisargaveggiez.ProfileSection.RateUs_act;
import com.nisarga.nisargaveggiez.ProfileSection.RefersAndEarn_act;
import com.nisarga.nisargaveggiez.ProfileSection.SignUpImageResponse;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.TermsConditions;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.Wishlist.WishListHolder;
import com.nisarga.nisargaveggiez.cart.cart;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.ProductListModel;
import com.nisarga.nisargaveggiez.retrofit.RateModel;
import com.nisarga.nisargaveggiez.wallet.MyWalletActivity;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mindorks.placeholderview.PlaceHolderView;

import java.io.File;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sushmita on 11/06/2019
 */

public class HomeCategory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    APIInterface apiInterface;
    SessionManager session;
    ProgressDialog progressdialog;

    public static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_WRITE_PERMISSION = 786;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_category);

        progressdialog = new ProgressDialog(HomeCategory.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());

        init();
        initApi();
    }

    DrawerLayout drawerHomeCategory;
    Toolbar toolbar;
    TextView tvTotalProduct;
    ImageButton ivbtnListView, ivbtnFilter;
    PlaceHolderView phvCategoryList;
    ProgressBar pbLoading;
    BottomNavigationView bottom_navigation;
    NavigationView navLeftMenu;
    View headerView;
    LinearLayout llLeftMenuLogout;

    //----Toolbar----
    CircleImageView ivToolbarProfile;
    AutoCompleteTextView searchEditText;
    LinearLayout llProfileIcon;

    LinearLayout llProfileDesc, llEditProfile;
    CircleImageView ivProfilePic;
    TextView tvName, tvEmail, tvMobileNo;

    String strSearchKey, cart_count;
    public boolean chngView = true;
    private String imagepath = null;
    String strProfilePic = "null";

    TextView CartItemCount;

    private void init() {
        drawerHomeCategory = (DrawerLayout) findViewById(R.id.drawerHomeCategory);
        toolbar = (Toolbar) findViewById(R.id.toolbarHomeCategory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        tvTotalProduct = findViewById(R.id.tvTotalProduct);
        ivbtnListView = findViewById(R.id.ivbtnListView);
        ivbtnFilter = findViewById(R.id.ivbtnFilter);
        phvCategoryList = findViewById(R.id.phvCategoryList);

        pbLoading = findViewById(R.id.pbLoading);
        pbLoading.setVisibility(View.VISIBLE);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        llLeftMenuLogout = findViewById(R.id.llLeftMenuLogout);

        ivToolbarProfile = findViewById(R.id.ivToolbarProfile);
        llProfileIcon = findViewById(R.id.llProfileIcon);

        navLeftMenu = findViewById(R.id.navLeftMenu);
        headerView = navLeftMenu.getHeaderView(0);
        navLeftMenu.setNavigationItemSelectedListener(this);
        setNavMenuItemThemeColors(R.color.light_black_2, R.color.green);
        tvName = headerView.findViewById(R.id.tvName);
        tvEmail = headerView.findViewById(R.id.tvEmail);
        tvMobileNo = headerView.findViewById(R.id.tvMobileNo);
        llProfileDesc = headerView.findViewById(R.id.llProfileDesc);
        ivProfilePic = headerView.findViewById(R.id.ivProfilePic);
        llEditProfile = headerView.findViewById(R.id.llEditProfile);

        llProfileDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditProfile = new Intent(getBaseContext(), MyProfile_act.class);
                startActivity(intentEditProfile);
            }
        });

        llEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditProfile = new Intent(getBaseContext(), EditProfile_act.class);
                startActivity(intentEditProfile);
            }
        });

        llProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerHomeCategory.openDrawer(Gravity.LEFT);
            }
        });

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFile();
            }
        });

        showGridView();
        ivbtnListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chngView == false) {
                    showGridView();
                    ivbtnListView.setBackgroundResource(R.drawable.listview);
                    chngView = true;
                } else {
                    showListView();
                    ivbtnListView.setBackgroundResource(R.drawable.gridview);
                    chngView = false;
                }
            }
        });

        ivbtnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFilter = new Intent(getBaseContext(), CategoryFilter.class);
                startActivity(intentFilter);
            }
        });

        bottom_navigation.setOnNavigationItemSelectedListener
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
        bottom_navigation.setItemIconSize(40);
    }

    private void initApi() {
        //------------------- My profile view section----------------
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final MyProfileModel myProfileModel = new MyProfileModel(session.getCustomerId());
            Call<MyProfileModel> call = apiInterface.showMyProfile(myProfileModel);
            call.enqueue(new Callback<MyProfileModel>() {
                @Override
                public void onResponse(Call<MyProfileModel> call, Response<MyProfileModel> response) {
                    MyProfileModel resourceMyProfile = response.body();
                    if (resourceMyProfile.status.equals("success")) {
                        Toast.makeText(getApplicationContext(), resourceMyProfile.message, Toast.LENGTH_SHORT).show();
                        List<MyProfileModel.Datum> mpmDatum = resourceMyProfile.resultdata;
                        for (MyProfileModel.Datum mpmResult : mpmDatum) {
                            if (String.valueOf(mpmResult.image) == "null") {
                                ivProfilePic.setImageResource(R.drawable.camera);
                            } else {
                                Glide.with(HomeCategory.this).load(mpmResult.image).fitCenter().dontAnimate()
                                        .into(ivProfilePic);
                            }
                            tvName.setText(mpmResult.firstname + " " + mpmResult.lastname);
                            tvEmail.setText(mpmResult.email);
                            tvMobileNo.setText(mpmResult.telephone);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), resourceMyProfile.message, Toast.LENGTH_SHORT).show();
                    }
                    pbLoading.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<MyProfileModel> call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

        //------- Cart Count Section Api --------
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            Call<CartCount> call = apiInterface.getCartCount("api/cart/cartcount", session.getToken());
            call.enqueue(new Callback<CartCount>() {
                @Override
                public void onResponse(Call<CartCount> call, Response<CartCount> response) {
                    CartCount cartCount = response.body();
                    if (cartCount.status.equals("success")) {
                        cart_count = cartCount.data;
                    } else {
                        Toast.makeText(getApplicationContext(), cartCount.message, Toast.LENGTH_SHORT).show();
                    }

                    pbLoading.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<CartCount> call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void showListView() {
        phvCategoryList.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        if (Utils.CheckInternetConnection(getApplicationContext())) {

            final ProductListModel prdListModel = new ProductListModel("1");
            Call<ProductListModel> call = apiInterface.getProductsList(prdListModel);
            call.enqueue(new Callback<ProductListModel>() {
                @Override
                public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {
                    ProductListModel resource = response.body();
                    if (resource.status.equals("success")) {
                        if (String.valueOf(resource.profile_pic) == "null") {
                            ivToolbarProfile.setImageResource(R.drawable.camera);
                        } else {
                            Glide.with(HomeCategory.this).load(resource.profile_pic).fitCenter().dontAnimate()
                                    .into(ivToolbarProfile);
                        }
                        List<ProductListModel.ProductListDatum> datumList = resource.result;
                        for (ProductListModel.ProductListDatum imgs : datumList) {
                            phvCategoryList.addView(new HomeCategoryItem(HomeCategory.this, imgs.prd_id, imgs.image,
                                    imgs.name, imgs.price, imgs.discount_price, imgs.qty));
                        }
                    }

                    pbLoading.setVisibility(View.INVISIBLE);

                    phvCategoryList.sort(new Comparator<Object>() {
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

                    phvCategoryList.refresh();
                }

                @Override
                public void onFailure(Call<ProductListModel> call, Throwable t) {
                    call.cancel();
                }
            });


        } else {
            Toast.makeText(HomeCategory.this, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void showGridView() {
        phvCategoryList.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final ProductListModel prdListModel1 = new ProductListModel("1");
            Call<ProductListModel> call = apiInterface.getProductsList(prdListModel1);
            call.enqueue(new Callback<ProductListModel>() {
                @Override
                public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {
                    ProductListModel resource = response.body();
                    if (resource.status.equals("success")) {
                        if (String.valueOf(resource.profile_pic) == "null") {
                            ivToolbarProfile.setImageResource(R.drawable.camera);
                        } else {
                            Glide.with(HomeCategory.this).load(resource.profile_pic).fitCenter().dontAnimate()
                                    .into(ivToolbarProfile);
                        }
                        List<ProductListModel.ProductListDatum> datumList = resource.result;
                        for (ProductListModel.ProductListDatum imgs : datumList) {
                            phvCategoryList.addView(new HomeCategoryItemGridView(HomeCategory.this, imgs.prd_id,
                                    imgs.image, imgs.name, imgs.price, imgs.discount_price, imgs.qty));
                        }
                    }

                    pbLoading.setVisibility(View.INVISIBLE);

                    phvCategoryList.sort(new Comparator<Object>() {
                        @Override
                        public int compare(Object item1, Object item2) {
                            if (item1 instanceof HomeCategoryItem && item2 instanceof HomeCategoryItem) {
                                HomeCategoryItemGridView view1 = (HomeCategoryItemGridView) item1;
                                HomeCategoryItemGridView view2 = (HomeCategoryItemGridView) item2;
                                return view1.getTitle().compareTo(view2.getTitle());
                            }
                            return 0;
                        }
                    });

                    phvCategoryList.refresh();
                }

                @Override
                public void onFailure(Call<ProductListModel> call, Throwable t) {
                    call.cancel();
                }
            });


        } else {
            Toast.makeText(HomeCategory.this, "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_search, menu);

        MenuItem cart_menuItem = menu.findItem(R.id.cartmenu);
        FrameLayout rootView = (FrameLayout) cart_menuItem.getActionView();
        CartItemCount = (TextView) rootView.findViewById(R.id.cart_badge);
        CartItemCount.setText(cart_count);

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
        strSearchKey = searchEditText.getText().toString();
        searchEditText.setThreshold(1);//will start working from first character
        searchEditText.setTextColor(Color.parseColor("#824A4A4A"));
        searchEditText.setBackgroundColor(Color.TRANSPARENT);
        searchEditText.setOnItemClickListener(onItemClickListener);
        searchMagIcon.setImageDrawable(null);//remove search icon
        searchEditText.clearFocus();

//        RemoteData remoteData = new RemoteData(this);
//        remoteData.getStoreData(strSearchKey);
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

    public void setNavMenuItemThemeColors(int color, int icolor) {
        int navDefaultTextColor = Color.parseColor("#AB4A4A4A");
        int navDefaultIconColor = Color.parseColor("#FFFBD249");
        int navActiveIconColor = Color.parseColor("#FF34773C");

        //Defining ColorStateList for menu item Text
        ColorStateList navMenuTextList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{-android.R.attr.state_checked}

                },
                new int[]{
                        navDefaultTextColor,
                        navDefaultTextColor
                }
        );

        //Defining ColorStateList for menu item Icon
        ColorStateList navMenuIconList = new ColorStateList(
                new int[][]{

                        new int[]{android.R.attr.state_checked},
                        new int[]{-android.R.attr.state_checked}
                },
                new int[]{
                        navActiveIconColor,
                        navDefaultIconColor
                }
        );

        navLeftMenu.setItemTextColor(navMenuTextList);
        navLeftMenu.setItemIconTintList(navMenuIconList);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.menuleft_home) {
            menuItem.setEnabled(true);
            Intent intentHome = new Intent(HomeCategory.this, HomePage.class);
            startActivity(intentHome);
        } else if (id == R.id.menuleft_myorders) {
            Intent intentMyOrder = new Intent(HomeCategory.this, MyOrders.class);
            startActivity(intentMyOrder);
        } else if (id == R.id.menuleft_mywallet) {
            Intent intentMyWallet = new Intent(HomeCategory.this, MyWalletActivity.class);
            startActivity(intentMyWallet);
        } else if (id == R.id.menuleft_referearn) {
            Intent intentMyReferEarn = new Intent(HomeCategory.this, RefersAndEarn_act.class);
            startActivity(intentMyReferEarn);
        } else if (id == R.id.menuleft_rateus) {
            LayoutInflater li = LayoutInflater.from(HomeCategory.this);
            android.view.View promptsView = li.inflate(R.layout.rate_us_act, null);
            android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(HomeCategory.this,
                    R.style.AlertDialogStyle);
            alertDialogBuilder.setView(promptsView);

            // set the custom dialog components - text, image and button
            ImageView ivClose = (ImageView) promptsView.findViewById(R.id.ivClose);
            ImageView ivUnlike = (ImageView) promptsView.findViewById(R.id.ivUnlike);
            ImageView ivLike = (ImageView) promptsView.findViewById(R.id.ivLike);
            Button btnSubmit = (Button) promptsView.findViewById(R.id.btnSubmit);

            final android.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            ivLike.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    final RateModel ref = new RateModel(session.getCustomerId(), "1");
                    Call<RateModel> calledu = apiInterface.setrate(ref);
                    calledu.enqueue(new Callback<RateModel>() {
                        @Override
                        public void onResponse(Call<RateModel> calledu, Response<RateModel> response) {
                            final RateModel resource = response.body();
                            if (resource.status.equals("success")) {
                                Toast.makeText(HomeCategory.this, resource.message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HomeCategory.this, resource.message, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RateModel> calledu, Throwable t) {
                            calledu.cancel();
                        }
                    });
                }
            });

            ivUnlike.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    final RateModel ref = new RateModel(session.getCustomerId(), "0");
                    Call<RateModel> calledu = apiInterface.setrate(ref);
                    calledu.enqueue(new Callback<RateModel>() {
                        @Override
                        public void onResponse(Call<RateModel> calledu, Response<RateModel> response) {
                            final RateModel resource = response.body();
                            if (resource.status.equals("success")) {
                                Toast.makeText(HomeCategory.this, resource.message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HomeCategory.this, resource.message, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RateModel> calledu, Throwable t) {
                            calledu.cancel();
                        }
                    });
                }
            });

            ivClose.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    alertDialog.cancel();
                }
            });
            btnSubmit.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    alertDialog.cancel();
                }
            });

        } else if (id == R.id.menuleft_aboutcontact) {
            Intent intentAbtContact = new Intent(HomeCategory.this, ContactUs.class);
            startActivity(intentAbtContact);
        } else if (id == R.id.menuleft_faqs) {
            Intent intentFaqs = new Intent(HomeCategory.this, DeliveryInformation.class);
            startActivity(intentFaqs);
        } else if (id == R.id.menuleft_terms) {
            Intent intentTerms = new Intent(HomeCategory.this, TermsConditions.class);
            startActivity(intentTerms);
        } else if (id == R.id.menuleft_gfeedback) {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        } else if (id == R.id.menuleft_policy) {
            Intent intentPolicy = new Intent(HomeCategory.this, PrivacyPolicy.class);
            startActivity(intentPolicy);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drwLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void pickFile() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_PERMISSION);
            return;
        }
        openGallery();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            imagepath = getPath(filePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            ivProfilePic.setImageBitmap(bitmap);
            imagePath(imagepath);
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void imagePath(String imagepath) {
        try {
            progressdialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        File file0 = new File(imagepath);
        RequestBody requestFile0 = RequestBody.create(MediaType.parse("image"), file0);

        MultipartBody.Part img1 = MultipartBody.Part.createFormData("file", file0.getName(),
                requestFile0);

        Call<SignUpImageResponse> call = apiInterface.signupImageUpload(img1);
        call.enqueue(new Callback<SignUpImageResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignUpImageResponse> call, @NonNull Response<SignUpImageResponse> response) {
                SignUpImageResponse responseModel = response.body();
                if (responseModel.status.equals("success")) {
                    strProfilePic = responseModel.profile_url;
                    if (Utils.CheckInternetConnection(getApplicationContext())) {
                        navImageUpload(strProfilePic);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpImageResponse> call, @NonNull Throwable t) {
                Log.d("val", "Exception");
            }
        });
    }

    private void navImageUpload(String strProfilePic) {
        final NavEditImage navEditImage = new NavEditImage(session.getCustomerId(), strProfilePic);

        Call<NavEditImage> call = apiInterface.nav_edit_image(navEditImage);
        call.enqueue(new Callback<NavEditImage>() {
            @Override
            public void onResponse(Call<NavEditImage> call, Response<NavEditImage> response) {
                NavEditImage responsedata = response.body();
                if (responsedata.status.equals("success")) {
                    Toast.makeText(getApplicationContext(), responsedata.message, Toast.LENGTH_SHORT).show();
                } else if (responsedata.status.equals("failure")) {
                    Toast.makeText(getApplicationContext(), responsedata.message, Toast.LENGTH_SHORT).show();
                }
                progressdialog.dismiss();
            }

            @Override
            public void onFailure(Call<NavEditImage> call, Throwable t) {
                call.cancel();

            }
        });
    }

}