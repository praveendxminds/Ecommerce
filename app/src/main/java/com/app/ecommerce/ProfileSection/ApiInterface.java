package com.app.ecommerce.ProfileSection;


import com.app.ecommerce.MyOrder.MyOrders;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("index.php?route=api/login")
    Call<UserLogin> login(@Body UserLogin user);

    @POST("index.php?route=api/customer/register")
    Call<UserSignUp> postRegisterUser(@Body UserSignUp signupdata);

    @GET("index.php?route=api/custom/getApartments")
    Call<ApartmentList> getApartmentList();
}
