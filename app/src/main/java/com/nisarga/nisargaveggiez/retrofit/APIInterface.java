package com.nisarga.nisargaveggiez.retrofit;

import com.nisarga.nisargaveggiez.Home.CartCount;
import com.nisarga.nisargaveggiez.Home.UpdateToCartModel;
import com.nisarga.nisargaveggiez.ProfileSection.ApartmentList;
import com.nisarga.nisargaveggiez.ProfileSection.FilterCategoryModel;
import com.nisarga.nisargaveggiez.ProfileSection.ForgetPasswordModel;
import com.nisarga.nisargaveggiez.ProfileSection.MyProfileModel;
import com.nisarga.nisargaveggiez.ProfileSection.NavEditImage;
import com.nisarga.nisargaveggiez.ProfileSection.QuantityList;
import com.nisarga.nisargaveggiez.ProfileSection.ResetPasswordModel;
import com.nisarga.nisargaveggiez.ProfileSection.SignUpImageResponse;
import com.nisarga.nisargaveggiez.ProfileSection.UserLogin;
import com.nisarga.nisargaveggiez.ProfileSection.UserSignUp;
import com.nisarga.nisargaveggiez.ProfileSection.VerifyOTP;
import com.nisarga.nisargaveggiez.billing.AddOrder;
import com.nisarga.nisargaveggiez.fcm.TokenFCM;
import com.nisarga.nisargaveggiez.notifications.NotificationListModel;
import com.nisarga.nisargaveggiez.wallet.Usewallet;
import com.nisarga.nisargaveggiez.wallet.Walletpayment;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<SimilarProductsModel> getSimilarProducts(@Body SimilarProductsModel product_id);

    @POST("index.php?route=api/custom/pages")
    Call<WebPagesModel> getContactUs(@Body WebPagesModel id);

    @POST("index.php?route=account/wishlist/wishListProducts")
    Call<GetWishList> getWishList(@Body GetWishList id);

    @POST("index.php?route=account/wishlist/removeWishList")
    Call<RemoveWishListItem> removeWishListItem(@Body RemoveWishListItem id);

    @POST("index.php?route=account/wishlist/insertWishList")
    Call<InsertWishListItems> addtoWishList(@Body InsertWishListItems id);

    @POST("index.php")
    Call<ShippingAddrModel> addShippingAddress(@Query("route") String route, @Query("api_token") String api_token, @Body ShippingAddrModel id);

    @POST("index.php?route=api/order/cusOrder")
    Call<MyOrderList> getMyOrdersList(@Body MyOrderList customer_id);

    @POST("index.php?route=api/custom/CancelOrder")
    Call<CancelOrderModel> cancelOrder(@Body CancelOrderModel customer_id);

    @POST("index.php?route=api/order/cusSingleOrder")
    Call<OrderDetailModel> getMyOrderDetail(@Body OrderDetailModel id);

    @POST("index.php?route=api/order/MyorderProductList")
    Call<ReorderItemsModel> showReorderItems(@Body ReorderItemsModel id);

    @POST("index.php")
    Call<ReOrder> reorderItems(@Query("route") String route, @Query("api_token") String api_token, @Body ReOrder id);


    @POST("index.php?route=api/customer/walletadd")
    Call<AddMoneytoWalletModel> addMoney(@Body AddMoneytoWalletModel id);

    @POST("index.php?route=api/customer/getwalletbalance")
    Call<WalletBlncModel> getWalletBlnc(@Body WalletBlncModel id);

    @POST("index.php?route=api/customer/getloyaltypoints")
    Call<LoyalityPointsModel> getLoyalityPoints(@Body LoyalityPointsModel id);

    @POST("index.php?route=api/customer/redeemLoyaltyPoints")
    Call<ReedemLoyalityPoints> redeemPoints(@Body ReedemLoyalityPoints id);

    @POST("index.php?route=api/customer/transactionHistory")
    Call<TxnHistoryModel> getHistory(@Body TxnHistoryModel id);

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
    Call<CartListModel> getCartList(@Query("route") String route, @Query("api_token") String api_token,
                                    @Body CartListModel model);

    @POST("index.php")
    Call<CustomerDetails> addcustdetails(@Query("route") String route, @Query("api_token") String api_token,
                                         @Body CustomerDetails customer_id);

    @POST("index.php?route=account/wishlist/insertWishListToCart")
    Call<MoveToCartModel> moveToCart(@Body MoveToCartModel id);

    @GET("index.php?route=api/custom/getApartments")
    Call<ApartmentList> getApartmentList();

    @POST("index.php?route=api/login")
    Call<UserLogin> login(@Body UserLogin user);

    @POST("index.php?route=api/customer/register")
    Call<UserSignUp> postRegisterUser(@Body UserSignUp signupdata);

    @Multipart
    @POST("index.php?route=api/uploadprofile/fileupload")
    Call<SignUpImageResponse> signupImageUpload(@Part MultipartBody.Part file);


    @POST("index.php?route=api/uploadprofile/editNavImage")
    Call<NavEditImage> nav_edit_image(@Body NavEditImage image);

    @POST("index.php?route=api/myprofile/FilterProduct")
    Call<FilterCategoryModel> filter_products(@Body FilterCategoryModel filter);

    @POST("index.php?route=api/customer/fatchRefaralCode")
    Call<ReferalModel> getReferal(@Body ReferalModel id);

    @POST("index.php?route=api/customer/earnReferral")
    Call<EarnReferal> earnReferal(@Body EarnReferal id);

    @POST("index.php?route=api/forgetpassword/forgotpassword")
    Call<ForgetPasswordModel> forget_password(@Body ForgetPasswordModel forgetPasswordModel);

    @POST("index.php?route=api/forgetpassword/verifyotp")
    Call<VerifyOTP> verify_otp(@Body VerifyOTP verifyOTP);

    @POST("index.php?route=api/custom/NotifyToken")
    Call<TokenFCM> fcmtoken(@Body TokenFCM id);

    @POST("index.php?route=api/rateus/giveRateUs")
    Call<RateModel> setrate(@Body RateModel id);

    @POST("index.php")
    Call<CartCount> getCartCount(@Query("route") String route, @Query("api_token") String api_token);

    @POST("index.php?route=api/custom/productOptions/")
    Call<QuantityList> quantity_list(@Body QuantityList quantityList);

    @POST("index.php")
    Call<AddToCartModel> callAddToCart(@Query("route") String route, @Query("api_token") String api_token,
                                       @Body AddToCartModel model);

    @POST("index.php")
    Call<AddCartNullSpinner> callNullSpinner(@Query("route") String route, @Query("api_token") String api_token,
                                             @Body AddCartNullSpinner model);

    @POST("index.php")
    Call<UpdateToCartModel> updateAddToCart(@Query("route") String route, @Query("api_token") String api_token,
                                            @Body UpdateToCartModel model);


    @POST("index.php")
    Call<AddOrder> AddOrder(@Query("route") String route, @Query("api_token") String api_token,
                            @Body AddOrder model);

    @POST("index.php")
    Call<ReOrder> AddOrder(@Query("route") String route, @Query("api_token") String api_token,
                           @Body ReOrder model);

    @POST("index.php?route=api/orderreview/giveOrderreview")
    Call<OrderFeedback> orderfeedback(@Body OrderFeedback feed);


    @POST("index.php?route=api/custom/notificationList")
    Call<NotificationListModel> getnotificationlist(@Body NotificationListModel notify);

    @POST("index.php?route=api/forgetpassword/savechangedpassword")
    Call<ResetPasswordModel> reset_password(@Body ResetPasswordModel resetPasswordModel);

    @POST("index.php?route=api/payment/useWallet")
    Call<Usewallet> esewallet(@Body Usewallet usewallet);


    @POST("index.php?route=api/payment/onlinePayment")
    Call<Walletpayment> onlinepayment(@Body Walletpayment usewallet);


}