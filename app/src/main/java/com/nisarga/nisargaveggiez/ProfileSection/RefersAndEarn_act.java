package com.nisarga.nisargaveggiez.ProfileSection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.Home.SimilarProductsListItem;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.notifications.MyNotifications;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.ReferalModel;
import com.nisarga.nisargaveggiez.retrofit.SimilarProductsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefersAndEarn_act extends AppCompatActivity {

    Toolbar toolbar;
    APIInterface apiInterface;
    EditText edref;
    TextView tvshare;
    SessionManager session;

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

        apiInterface = APIClient.getClient().create(APIInterface.class);
        session = new SessionManager(getApplicationContext());


        edref = (EditText) findViewById(R.id.ref);
        tvshare = (TextView) findViewById(R.id.tvshare);

        final ReferalModel ref = new ReferalModel(Integer.parseInt(session.getCustomerId()));
        Call<ReferalModel> calledu = apiInterface.getReferal(ref);
        calledu.enqueue(new Callback<ReferalModel>() {
            @Override
            public void onResponse(Call<ReferalModel> calledu, Response<ReferalModel> response) {
                final ReferalModel resource = response.body();
                if (resource.status.equals("success"))
                {

                    List<ReferalModel.ReferalModelDatum> datumList = resource.result;
                    for (ReferalModel.ReferalModelDatum imgs : datumList) {
                        if (response.isSuccessful())
                        {
                            Log.d("refd", imgs.referal_code);
                            edref.setText(imgs.referal_code);
                        }
                    }

                }
                else if (resource.status.equals("error"))
                {

                }
            }

            @Override
            public void onFailure(Call<ReferalModel> calledu, Throwable t) {
                calledu.cancel();
            }
        });


        tvshare.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View voew)
            {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "https://play.google.com/store/apps/details?id=com.nisarga.nisargaveggiez&referrer="+edref.getText());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });


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
