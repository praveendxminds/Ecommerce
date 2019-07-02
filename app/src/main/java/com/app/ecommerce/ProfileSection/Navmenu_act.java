package com.app.ecommerce.ProfileSection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ecommerce.NavMenuHeader;
import com.app.ecommerce.NavMenuItems;
import com.app.ecommerce.R;
import com.mindorks.placeholderview.PlaceHolderView;

public class Navmenu_act extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_menu_act);
        init();
    }

    Toolbar toolbar;
    ImageView ivProfile;
    TextView tvProPicEdit, tvLogout;
    PlaceHolderView lvMenuList;

    Integer[] icon = {R.drawable.ic_person, R.drawable.ic_person, R.drawable.ic_person,
            R.drawable.ic_person, R.drawable.ic_person, R.drawable.ic_person, R.drawable.ic_person,
            R.drawable.ic_person, R.drawable.ic_person, R.drawable.ic_person, R.drawable.ic_person,
            R.drawable.ic_person};

    String[] menu_list = new String[]{"Home", "My Orders", "My Address", "My Wallet", "Offers",
            "Refer & Earn", "Rate Us", "About & Contact Us", "FAQs", "Terms & Conditions",
            "Google Feedback", "Policy"};

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivProfile = findViewById(R.id.ivProfile);
        tvProPicEdit = findViewById(R.id.tvProPicEdit);
        lvMenuList = findViewById(R.id.lvMenuList);
        tvLogout = findViewById(R.id.tvLogout);

        lvMenuList.addView(new NavMenuHeader(getApplicationContext()))
        .addView(new NavMenuItems(this.getApplicationContext(),NavMenuItems.DRAWER_MENU_ITEM_HOME))
                .addView(new NavMenuItems(this.getApplicationContext(),NavMenuItems.DRAWER_MENU_ITEM_MY_ORDER))
                .addView(new NavMenuItems(this.getApplicationContext(),NavMenuItems.DRAWER_MENU_ITEM_MY_ADDRESS))
                .addView(new NavMenuItems(this.getApplicationContext(),NavMenuItems.DRAWER_MENU_ITEM_MY_WALLET))
                .addView(new NavMenuItems(this.getApplicationContext(),NavMenuItems.DRAWER_MENU_ITEM_OFFERS))
                .addView(new NavMenuItems(this.getApplicationContext(),NavMenuItems.DRAWER_MENU_ITEM_REFER_EARN))
                .addView(new NavMenuItems(this.getApplicationContext(),NavMenuItems.DRAWER_MENU_ITEM_RATE_US))
                .addView(new NavMenuItems(this.getApplicationContext(),NavMenuItems.DRAWER_MENU_ITEM_ABT_CONTACT))
                .addView(new NavMenuItems(this.getApplicationContext(),NavMenuItems.DRAWER_MENU_ITEM_FAQ))
                .addView(new NavMenuItems(this.getApplicationContext(),NavMenuItems.DRAWER_MENU_ITEM_TERMS))
                .addView(new NavMenuItems(this.getApplicationContext(),NavMenuItems.DRAWER_MENU_ITEM_GFEEDBACK))
                .addView(new NavMenuItems(this.getApplicationContext(),NavMenuItems.DRAWER_MENU_ITEM_POLICY));



     /*   MyListAdapter adapter = new MyListAdapter(this, icon, menu_list);
        lvMenuList.setAdapter(adapter);

        lvMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(Navmenu_act.this, HomePage.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(Navmenu_act.this, MyOrders.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(Navmenu_act.this, SignUp_act.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent(Navmenu_act.this, EditProfile_act.class);
                    startActivity(intent);
                } else if (position == 4) {
                    Intent intent = new Intent(Navmenu_act.this, Offers_act.class);
                    startActivity(intent);
                } else if (position == 5) {
                    Intent intent = new Intent(Navmenu_act.this, RefersAndEarn_act.class);
                    startActivity(intent);
                } else if (position == 6) {
                    Intent intent = new Intent(Navmenu_act.this, RateUs_act.class);
                    startActivity(intent);
                } else if (position == 7) {
                    Intent intent = new Intent(Navmenu_act.this, AboutAndContactUs_act.class);
                    startActivity(intent);
                } else if (position == 8) {
                    Intent intent = new Intent(Navmenu_act.this, Faqs_act.class);
                    startActivity(intent);
                } else if (position == 9) {
                    Intent intent = new Intent(Navmenu_act.this, TermsAndCondition_act.class);
                    startActivity(intent);
                } else if (position == 10) {
                    Intent intent = new Intent(Navmenu_act.this, GoogleFeedback_act.class);
                    startActivity(intent);
                } else if (position == 11) {
                    Intent intent = new Intent(Navmenu_act.this, Policy_act.class);
                    startActivity(intent);
                }
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                break;
        }
        return true;
    }
}
