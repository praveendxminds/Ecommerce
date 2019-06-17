package com.app.ecommerce.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by praveen on 24/11/18.
 */

public interface APIInterface {

    @GET("json/category.json")
    Call<MultipleResources> doGetListResources();


    @GET("json/category.json")
    Call<CategoriesHomeTwo> doGetListcategories();

    @GET("json/products.json")
    Call<ProductsHomeTwo> doGetListProducts();

    @GET("json/products.json")
    Call<ProductslHomeOne> doGetProducts();

    @GET("index.php?route=api/custom/products/")
    Call<ProductslHomePage> getHomePageProducts();

    @GET("json/products.json")
    Call<ImageScroll> doGetListImages();

    @POST("index.php?route=api/custom/productdetails")
    Call<ProductDetailsModel> getProductDetails(@Body ProductDetailsModel id);

    @FormUrlEncoded
    @POST("json/login.php")
    Call<User> doGetLogin(@Field("name") String name, @Field("password") String password);


    @FormUrlEncoded
    @POST("json/register.php")
    Call<RegisterUser> doGetRegister(@Field("fname") String fname, @Field("lname") String lname, @Field("email") String email, @Field("mobile") String mobile);

}