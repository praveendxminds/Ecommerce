package com.app.ecommerce.ProfileSection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.ecommerce.R;

public class EditProfile_act extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_act);
        init();
    }

    ImageView ivProfile;
    TextView tvBack, tvProPicEdit;
    EditText etName, etEmail, etMobileNo;
    BottomNavigationView bottom_navigation;

    private void init() {
        ivProfile = findViewById(R.id.ivProfile);

        tvBack = findViewById(R.id.tvBack);
        tvProPicEdit = findViewById(R.id.tvProPicEdit);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMobileNo = findViewById(R.id.etMobileNo);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        break;

                    case R.id.navigation_wishlist:
                        break;

                    case R.id.navigation_wallet:
                        break;

                    case R.id.navigation_profile:
                        break;
                }

                return false;
            }
        });
    }
}
