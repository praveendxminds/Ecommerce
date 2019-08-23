package com.nisarga.nisargaveggiez;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.WebPagesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUs extends AppCompatActivity {

    WebView webview;
    APIInterface apiInterface;
    String sHtmlStr;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);

        webview = findViewById(R.id.webViewContactUs);
        webview.getSettings().setJavaScriptEnabled(true);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //----------------------------contact us json response-----------------------
        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final WebPagesModel readContactUs = new WebPagesModel("7");

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
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}
