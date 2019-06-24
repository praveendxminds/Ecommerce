package com.app.ecommerce.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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

    @POST("index.php?route=api/custom/homepage")
    Call<ProductslHomePage> getHomePageProducts(@Body ProductslHomePage id);

    @GET("json/products.json")
    Call<ImageScroll> doGetListImages();

    @POST("index.php?route=api/custom/productdetails")
    Call<ProductDetailsModel> getProductDetails(@Body ProductDetailsModel id);

    @POST("index.php?route=api/custom/pages")
    Call<WebPagesModel> getContactUs(@Body WebPagesModel id);

    @POST("index.php?route=account/wishlist/wishListProducts")
    Call<GetWishList> getWishList(@Body GetWishList id);

    @POST("index.php?route=account/wishlist/removeWishList")
    Call<RemoveWishListItem> removeWishListItem(@Body RemoveWishListItem id);

    @POST("index.php?route=account/wishlist/insertWishList")
    Call<InsertWishListItems> addtoWishList(@Body InsertWishListItems id);

    @POST("index.php?route=api/order/cusOrder")
    Call<MyOrderList> getMyOrdersList(@Body MyOrderList id);

    @FormUrlEncoded
    @POST("json/login.php")
    Call<User> doGetLogin(@Field("name") String name, @Field("password") String password);


    @FormUrlEncoded
    @POST("json/register.php")
    Call<RegisterUser> doGetRegister(@Field("fname") String fname, @Field("lname") String lname, @Field("email") String email, @Field("mobile") String mobile);

}