package com.nisarga.nisargaveggiez.MyOrder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nisarga.nisargaveggiez.SessionManager;
import com.nisarga.nisargaveggiez.Utils;
import com.nisarga.nisargaveggiez.retrofit.APIClient;
import com.nisarga.nisargaveggiez.retrofit.APIInterface;
import com.nisarga.nisargaveggiez.retrofit.OrderDetailModel;
import com.bumptech.glide.Glide;

import com.nisarga.nisargaveggiez.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by praveen on 15/11/18.
 */


public class OrderDetial extends AppCompatActivity {

    Toolbar toolbar;

    SessionManager session;
    private ImageView pro_img;
    APIInterface apiInterface;
    RelativeLayout orderDetailContainer;
    TextView grant_total_val,amnt_pay,item_cnt,prc,del_charge;
    private String custId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        session = new SessionManager(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        orderDetailContainer = findViewById(R.id.orderDetailContainer);
        grant_total_val=findViewById(R.id.grant_total_val);
        amnt_pay=findViewById(R.id.amnt_pay);
        item_cnt=findViewById(R.id.item_cnt);
        prc=findViewById(R.id.prc);
        del_charge=findViewById(R.id.del_charge);

        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        pro_img = (ImageView) findViewById(R.id.prd_img);

        Glide.with(getApplication()).load("https://5.imimg.com/data5/FQ/QY/MY-56156518/cashew-nut-500x500.jpg").into(pro_img);

     /*   apiInterface = APIClient.getClient().create(APIInterface.class);

        if (Utils.CheckInternetConnection(getApplicationContext())) {
//-------------------------------------image slider view----------------------------------------------------------------------
            custId = session.getCustomerId();
            final OrderDetailModel get_order_list = new OrderDetailModel(custId,"1");
            Call<OrderDetailModel> call = apiInterface.getMyOrderDetail(get_order_list);
            call.enqueue(new Callback<OrderDetailModel>() {
                @Override
                public void onResponse(Call<OrderDetailModel> call, Response<OrderDetailModel> response) {

                    OrderDetailModel resource = response.body();

                    List<OrderDetailModel.OrderDetailDatum> datumList1 = resource.result;

                    for (OrderDetailModel.OrderDetailDatum orderDetail : datumList1) {

                        grant_total_val.setText(orderDetail.mdquantity);
                        item_cnt.setText(orderDetail.mdname);
                        prc.setText(orderDetail.mdprice);
                        del_charge.setText(orderDetail.mdtax);
                        amnt_pay.setText(orderDetail.mdtotal);

                        Log.e("-----OrdersList--", orderDetail.mdname+" "+orderDetail.mdmodel);


                    }
                }


                @Override
                public void onFailure(Call<OrderDetailModel> call, Throwable t) {
                    call.cancel();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "No Internet. Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
*/



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




}