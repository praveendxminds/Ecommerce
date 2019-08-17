package com.app.ecommerce.ProfileSection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.app.ecommerce.DeliveryInformation;
import com.app.ecommerce.R;
import com.app.ecommerce.notifications.MyNotifications;

public class RefersAndEarn_act extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.refer_earn_act);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater  menuNotfiInfo = getMenuInflater();
        menuNotfiInfo.inflate(R.menu.notifi_and_info_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menu_notifi:
                Intent intentNotifi = new Intent(RefersAndEarn_act.this,MyNotifications.class);
                startActivity(intentNotifi);
                break;

            case R.id.menu_info:
                Intent intentInfo = new Intent(RefersAndEarn_act.this,DeliveryInformation.class);
                startActivity(intentInfo);
                break;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
