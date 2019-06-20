package com.app.ecommerce.ProfileSection;

import io.intercom.retrofit2.Call;
import io.intercom.retrofit2.http.Body;
import io.intercom.retrofit2.http.POST;

public interface ApiInterface {

    @POST("log")
    Call<UserLogin> login(@Body UserLogin user);
}
