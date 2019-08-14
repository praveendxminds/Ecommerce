package com.app.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.WebPagesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryInformation extends AppCompatActivity {

    WebView webview;
    APIInterface apiInterface;
    String sHtmlStr;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_information);

        webview = findViewById(R.id.webViewDeliveryInfo);
        webview.getSettings().setJavaScriptEnabled(true);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //----------------------------contact us json response-----------------------
        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final WebPagesModel readContactUs = new WebPagesModel("5");

            Call<WebPagesModel> call = apiInterface.getContactUs(readContactUs);
            call.enqueue(new Callback<WebPagesModel>() {
                @Override
                public void onResponse(Call<WebPagesModel> call, Response<WebPagesModel> response) {
                    WebPagesModel productResponse = response.body();
                    List<WebPagesModel.DatumContact> datumList = productResponse.result;
                    for (WebPagesModel.DatumContact imgs : datumList) {
                        if (response.isSuccessful()) {
                            sHtmlStr = imgs.getDesc();
//------------------for load response to webview for redirecting to contact us html page---------------------------------------------------------------------
                            webview.loadData(sHtmlStr, "text/html", null);
                        }
                    }

                }

                @Override
                public void onFailure(Call<WebPagesModel> call, Throwable t) {
                    call.cancel();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

