package com.nisarga.nisargaveggiez.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.placeholderview.PlaceHolderView;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.RateAndReviewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateUsReviews extends AppCompatActivity {


    SessionManager session;
    APIInterface apiInterface;
    private PlaceHolderView phvReviews;
    private TextView tvTotalRatings, tvRatings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_us_reviews);

        tvTotalRatings = findViewById(R.id.tvTotalRatings);
        tvRatings = findViewById(R.id.tvRatings);
        phvReviews = findViewById(R.id.phvReviews);
        session = new SessionManager(getApplicationContext());
        apiInterface = APIClient.getClient().create(APIInterface.class);
        getComments();
    }

    public void getComments() {
        if (Utils.CheckInternetConnection(getApplicationContext())) {
//-------------------------------------image slider view----------------------------------------------------------------------
            final RateAndReviewModel get_order_list = new RateAndReviewModel(session.getCustomerId());
            Call<RateAndReviewModel> call = apiInterface.getComments(get_order_list);
            call.enqueue(new Callback<RateAndReviewModel>() {
                @Override
                public void onResponse(Call<RateAndReviewModel> call, Response<RateAndReviewModel> response) {

                    RateAndReviewModel resource = response.body();

                    List<RateAndReviewModel.ReviewDatum> datumList1 = resource.result;
                    if ((resource.status).equals("success")) {

                        for (RateAndReviewModel.ReviewDatum rvDatum : datumList1) {

                            phvReviews.addView(new RateReviewItems(getApplicationContext(), rvDatum.firstname, rvDatum.lastname, rvDatum.feedback));
                        }
                        //Rating Percent
                        if (!(resource.like_rate_in_percent).equals("null") &&
                                !(resource.like_rate_in_percent).equals(null) && !(resource.like_rate_in_percent).isEmpty()) {
                            tvRatings.setText((resource.like_rate_in_percent));
                        } else {
                            tvRatings.setText("0");
                        }
                        //Total Rating in digit
                        if (resource.nuber_of_users_rating != 0) {
                            tvTotalRatings.setText(String.valueOf(resource.nuber_of_users_rating));
                        } else {
                            tvTotalRatings.setText("0");
                        }

                    } else if (resource.status.equals("failure")) {

                        phvReviews.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onFailure(Call<RateAndReviewModel> call, Throwable t) {
                    call.cancel();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }
}
