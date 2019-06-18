package com.app.ecommerce;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.app.ecommerce.retrofit.APIClient;
import com.app.ecommerce.retrofit.APIInterface;
import com.app.ecommerce.retrofit.ContactUsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUs extends AppCompatActivity {

    WebView webview;
    APIInterface apiInterface;
    String sUrl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);

        String prd_id = getIntent().getExtras().getString("contactus_id", "defaultKey");

        webview = findViewById(R.id.webView1);
        webview.getSettings().setJavaScriptEnabled(true);


        //----------------------------contact us json response-----------------------
        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (Utils.CheckInternetConnection(getApplicationContext())) {
            final ContactUsModel readContactUs = new ContactUsModel(prd_id);

            Call<ContactUsModel> call = apiInterface.getContactUs(readContactUs);
            call.enqueue(new Callback<ContactUsModel>() {
                @Override
                public void onResponse(Call<ContactUsModel> call, Response<ContactUsModel> response) {
                    ContactUsModel productResponse = response.body();
                    List<ContactUsModel.DatumContact> datumList = productResponse.result;
                    for (ContactUsModel.DatumContact imgs : datumList) {
                        if (response.isSuccessful()) {
                            sUrl = imgs.getDesc();
//------------------for load response to webview for redirecting to contact us html page---------------------------------------------------------------------
                            webview.loadUrl(sUrl);

                            webview.setWebViewClient(new WebViewClient() {

                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return true;
                                }
                            });

                        }
                    }


                }


                @Override
                public void onFailure(Call<ContactUsModel> call, Throwable t) {
                    call.cancel();
                }
            });


        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please check internet connection", Toast.LENGTH_SHORT).show();
        }



    }
}
