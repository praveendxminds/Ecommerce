package com.app.ecommerce;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.WebPagesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicy extends AppCompatActivity {

    WebView webview;
    APIInterface apiInterface;
    String sHtmlStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);

        webview = findViewById(R.id.webViewPrivacyPolicy);
        webview.getSettings().setJavaScriptEnabled(true);


        //----------------------------contact us json response-----------------------
        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final WebPagesModel readContactUs = new WebPagesModel("3");

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
}
