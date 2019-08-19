package com.nisarga.nisargaveggiez.retrofit;

import com.nisarga.nisargaveggiez.ProfileSection.ApartmentList;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfileModel;
import com.nisarga.nisargaveggiez.ProfileSection.SignUpImageResponse;
import com.nisarga.nisargaveggiez.ProfileSection.UserLogin;
import com.nisarga.nisargaveggiez.ProfileSection.UserSignUp;

import io.intercom.retrofit2.http.Multipart;
import io.intercom.retrofit2.http.Part;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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


    @GET("index.php?route=api/productcategory/categoryinproduct")
    Call<ShopByCategModel> getShopByCateg();

    @POST("index.php?route=api/custom/products/")
    Call<ProductListModel> getProductsList(@Body ProductListModel id);

    @POST("index.php?route=api/custom/productdetails/")
    Call<ProductDetailsModel> getProductDetails(@Body ProductDetailsModel id);

    @POST("index.php?route=api/order/similarProductList")
    Call<SimilarProductsModel> getSimilarProducts(@Body SimilarProductsModel prd_id);

    @POST("index.php?route=api/custom/pages")
    Call<WebPagesModel> getContactUs(@Body WebPagesModel id);

    @POST("index.php?route=account/wishlist/wishListProducts")
    Call<GetWishList> getWishList(@Body GetWishList id);

    @POST("index.php?route=account/wishlist/removeWishList")
    Call<RemoveWishListItem> removeWishListItem(@Body RemoveWishListItem id);

    @POST("index.php?route=account/wishlist/insertWishList")
    Call<InsertWishListItems> addtoWishList(@Body InsertWishListItems id);

    @POST("index.php?route=api/order/cusOrder")
    Call<MyOrderList> getMyOrdersList(@Body MyOrderList customer_id);

    @POST("index.php?route=api/order/cusSingleOrder")
    Call<OrderDetailModel> getMyOrderDetail(@Body OrderDetailModel id);

    /*@POST("index.php?route=api/order/MyorderProductList&")
    Call<ReorderItemsModel> showReorderItems(@Query("api_token") String token, @Body ReorderItemsModel body);
*/
    @POST("index.php?route=api/order/MyorderProductList")
    Call<ReorderItemsModel> showReorderItems(@Body ReorderItemsModel id);

    @FormUrlEncoded
    @POST("json/login.php")
    Call<User> doGetLogin(@Field("name") String name, @Field("password") String password);


    @FormUrlEncoded
    @POST("json/register.php")
    Call<RegisterUser> doGetRegister(@Field("fname") String fname, @Field("lname") String lname, @Field("email") String email, @Field("mobile") String mobile);


    @POST("index.php?route=api/myprofile/Myprofile")
    Call<MyProfileModel> showMyProfile(@Body MyProfileModel myprofile);

    @POST("index.php?route=api/customer/cusedit")
    Call<EditProfileModel> editMyProfile(@Body EditProfileModel editProfile);

   /* @POST("index.php?route=api/cart/remove")
    Call<RemoveCartItems> removeCartItems(@Body RemoveCartItems key);*/

   @POST("index.php")
   Call<CartListModel> getCartList(@Query("route") String route,@Query("api_token") String api_token);


   @POST("index.php?route=account/wishlist/insertWishListToCart")
    Call<MoveToCartModel> moveToCart(@Body MoveToCartModel id);

   @POST("index.php?route=api/cart/add&api_token=c6bc796996424a77c3ceab40e1")
    Call<AddToCartModel> callAddToCart(@Body AddToCartModel id);


    @GET("index.php?route=api/custom/getApartments")
    Call<ApartmentList> getApartmentList();

    @POST("index.php?route=api/login")
    Call<UserLogin> login(@Body UserLogin user);

    @POST("index.php?route=api/customer/register")
    Call<UserSignUp> postRegisterUser(@Body UserSignUp signupdata);

    @Multipart
    @POST("imageupload")
    Call<SignUpImageResponse> signupImageUpload(@Part MultipartBody.Part file1);

}