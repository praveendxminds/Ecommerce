package com.nisarga.nisargaveggiez.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mindorks.placeholderview.PlaceHolderView;
import com.nisarga.nisargaveggiez.DeliveryInformation;
import com.nisarga.nisargaveggiez.R;
import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.RateAndReviewModel;
import com.nisarga.nisargaveggiez.retrofit.RateModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateUsReviews extends AppCompatActivity {


    SessionManager session;
    APIInterface apiInterface;
    Toolbar toolbar;
    private PlaceHolderView phvReviews;
    private TextView tvTotalRatings, tvRatings,tvUsrName;
    private EditText etComment;
    private LinearLayout llwriteReview;
    private CardView llDislike,llLike;
    private ImageView ivUnlikeGray,ivUnlikeGreen,ivLikeGray,ivLikeGreen;
    private Button btnSubmit,btnWrteReview;
    private String value1,usrName1,usrName2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_us_reviews);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvTotalRatings = findViewById(R.id.tvTotalRatings);
        tvRatings = findViewById(R.id.tvRatings);
        phvReviews = findViewById(R.id.phvReviews);
        ivUnlikeGray = findViewById(R.id.ivUnlikeGray);
        ivUnlikeGreen = findViewById(R.id.ivUnlikeGreen);
        ivLikeGray = findViewById(R.id.ivLikeGray);
        ivLikeGreen = findViewById(R.id.ivLikeGreen);
        llDislike = findViewById(R.id.llDislike);
        llLike = findViewById(R.id.llLike);
        llwriteReview = findViewById(R.id.llwriteReview);
        tvUsrName = findViewById(R.id.tvUsrName);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnWrteReview = findViewById(R.id.btnWrteReview);
        etComment = findViewById(R.id.etComment);
        session = new SessionManager(getApplicationContext());
        apiInterface = APIClient.getClient().create(APIInterface.class);
        getComments();
        usrName1 = session.getFirstName();
        usrName2 = session.getLastName();
        if(!usrName1.equals("null") && !usrName1.equals(null) && !usrName1.isEmpty())
        {
            if(!usrName2.equals("null") && !usrName2.equals(null) && !usrName2.isEmpty())
            {
                tvUsrName.setText(usrName1+" "+usrName2);
            }
            else
            {
                tvUsrName.setText(usrName1);
            }
        }
        else
        {
            tvUsrName.setText("User");
        }
        btnWrteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llwriteReview.setVisibility(View.VISIBLE);
                btnWrteReview.setEnabled(false);
                btnWrteReview.setClickable(false);
                btnWrteReview.setBackgroundColor(Color.parseColor("#3334773C"));
            }
        });
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
                            phvReviews.addView(new RateReviewItems(getApplicationContext(), rvDatum.firstname,
                                    rvDatum.lastname, rvDatum.feedback,rvDatum.date_added));
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
                            tvTotalRatings.setText(String.valueOf(resource.nuber_of_users_rating)+" "+"Ratings");
                        } else {
                            tvTotalRatings.setText("0");
                        }
                        //rating thumb
                        if (!(resource.rated).equals("null") &&
                                !(resource.rated).equals(null) && !(resource.rated).isEmpty()) {
                            if(resource.rated.equals("1"))
                            {
                                ivLikeGreen.setVisibility(View.VISIBLE);
                                ivLikeGray.setVisibility(View.GONE);
                                ivUnlikeGreen.setVisibility(View.GONE);
                                ivUnlikeGray.setVisibility(View.VISIBLE);

                                //write review button disable
                                llwriteReview.setVisibility(View.VISIBLE);
                                btnWrteReview.setEnabled(false);
                                btnWrteReview.setClickable(false);
                                btnWrteReview.setBackgroundColor(Color.parseColor("#3334773C"));

                                //enable submit button
                                btnSubmit.setEnabled(true);
                                btnSubmit.setClickable(true);
                                btnSubmit.setBackgroundResource(R.drawable.review_btn);
                                if(!(resource.feedback).equals("null") &&
                                        !(resource.feedback).equals(null) && !(resource.feedback).isEmpty())
                                {
                                    etComment.setText(String.valueOf(resource.feedback));
                                }
                                else
                                {
                                    etComment.setText("");
                                }
                               giveRatings();
                              submitRatings("1");
                            }
                            else if((resource.rated.equals("0")))
                            {
                                ivUnlikeGreen.setVisibility(View.VISIBLE);
                                ivUnlikeGray.setVisibility(View.GONE);
                                ivLikeGreen.setVisibility(View.GONE);
                                ivLikeGray.setVisibility(View.VISIBLE);

                                //disable write review button and make visible comment section
                                llwriteReview.setVisibility(View.VISIBLE);
                                btnWrteReview.setEnabled(false);
                                btnWrteReview.setClickable(false);
                                btnWrteReview.setBackgroundColor(Color.parseColor("#3334773C"));

                                //enable submit button
                                btnSubmit.setEnabled(true);
                                btnSubmit.setClickable(true);
                                btnSubmit.setBackgroundResource(R.drawable.review_btn);
                                if(!(resource.feedback).equals("null") &&
                                        !(resource.feedback).equals(null) && !(resource.feedback).isEmpty())
                                {
                                    etComment.setText(String.valueOf(resource.feedback));
                                }
                                else
                                {
                                    etComment.setText("");
                                }
                                giveRatings();
                                submitRatings("0");
                            }
                            else
                            {
                                ivUnlikeGreen.setVisibility(View.GONE);
                                ivUnlikeGray.setVisibility(View.VISIBLE);
                                ivLikeGreen.setVisibility(View.GONE);
                                ivLikeGray.setVisibility(View.VISIBLE);
                                //submit button disable
                                btnSubmit.setEnabled(false);
                                btnSubmit.setClickable(false);
                                btnSubmit.setBackgroundColor(Color.parseColor("#3334773C"));
                                giveRatings();
                            }

                        } else {
                            ivUnlikeGreen.setVisibility(View.GONE);
                            ivUnlikeGray.setVisibility(View.VISIBLE);
                            ivLikeGreen.setVisibility(View.GONE);
                            ivLikeGray.setVisibility(View.VISIBLE);
                            //submit button disable
                            btnSubmit.setEnabled(false);
                            btnSubmit.setClickable(false);
                            btnSubmit.setBackgroundColor(Color.parseColor("#3334773C"));
                            giveRatings();

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

    public void giveRatings()
    {

        ivUnlikeGray.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                ivUnlikeGreen.setVisibility(View.VISIBLE);
                ivUnlikeGray.setVisibility(View.GONE);
                ivLikeGreen.setVisibility(View.GONE);
                ivLikeGray.setVisibility(View.VISIBLE);

                //enable submit button
                btnSubmit.setEnabled(true);
                btnSubmit.setClickable(true);
                btnSubmit.setBackgroundResource(R.drawable.review_btn);

                value1 = "0";
                submitRatings(value1);
            }
        });

        ivLikeGray.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                ivLikeGreen.setVisibility(View.VISIBLE);
                ivLikeGray.setVisibility(View.GONE);
                ivUnlikeGreen.setVisibility(View.GONE);
                ivUnlikeGray.setVisibility(View.VISIBLE);

                //enable submit button
                btnSubmit.setEnabled(true);
                btnSubmit.setClickable(true);
                btnSubmit.setBackgroundResource(R.drawable.review_btn);
                value1 = "1";
                submitRatings(value1);
            }
        });
    }

    public void submitRatings(final String rate)
    {
        btnSubmit.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if(rate.equals("null") || rate.equals(null) || rate.isEmpty())
                {
                    Toast.makeText(RateUsReviews.this, "Please Rate Us...", Toast.LENGTH_SHORT).show();
                }
                else {
                    final RateModel ref = new RateModel(session.getCustomerId(), rate, etComment.getText().toString());
                    Call<RateModel> calledu = apiInterface.setrate(ref);
                    calledu.enqueue(new Callback<RateModel>() {
                        @Override
                        public void onResponse(Call<RateModel> calledu, Response<RateModel> response) {
                            final RateModel resource = response.body();
                            if (resource.status.equals("success")) {
                                Toast.makeText(RateUsReviews.this, resource.message, Toast.LENGTH_SHORT).show();
                                Intent intentHomePage = new Intent(RateUsReviews.this, HomePage.class);
                                intentHomePage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentHomePage);
                            } else {
                                Toast.makeText(RateUsReviews.this, resource.message, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RateModel> calledu, Throwable t) {
                            calledu.cancel();
                        }
                    });
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInfo = getMenuInflater();
        menuInfo.inflate(R.menu.instruction_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent intentSubmitBack = new Intent(RateUsReviews.this, HomePage.class);
                intentSubmitBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentSubmitBack);

                return true;

            case R.id.help_menu_item:
                Intent intentInfo = new Intent(getBaseContext(),DeliveryInformation.class);
                startActivity(intentInfo);
                break;
        }
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
