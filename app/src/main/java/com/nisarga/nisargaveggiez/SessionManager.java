package com.nisarga.nisargaveggiez;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.nisarga.nisargaveggiez.ProfileSection.Login_act;
import com.nisarga.nisargaveggiez.ProfileSection.SignUp_act;

public class SessionManager {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "SessionData";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_REF = "Isreferal";
    private static final String KEY_CUST_ID = "customer_id";
    private static final String KEY_DATE_ADDED = "date_added";
    private static final String KEY_TOKEN_LOGIN = "api_token";
    private static final String KEY_CUST_GRP_ID = "customer_group_id";
    private static final String KEY_CART = "cart";
    private static final String KEY_WISHLIST = "wishlist";
    private static final String KEY_ADDRESS_ID = "address_id";
    public static final String KEY_EMAIL_ID = "email";
    public static final String KEY_LNAME = "lastname";
    public static final String KEY_FNAME = "firstname";
    public static final String KEY_TELEPHONE = "telephone";

    public static final String KEY_EMAIL_ADDRESS = "email";
    public static final String KEY_FNAME_ADDRS = "firstname";
    public static final String KEY_LNAME_ADDRS = "lastname";
    public static final String KEY_PHONE_ADDRS = "mobile";
    public static final String KEY_APRT_NAME_ADDRS = "apartment_name";
    public static final String KEY_BLOCK_ADDRS = "block";
    public static final String KEY_FLOOR_ADDRS = "floor";
    public static final String KEY_DOOR_ADDRS = "door";
    public static final String KEY_AREA_ADDRS = "area";
    public static final String KEY_ADDRESS_ADDRS = "address";
    public static final String KEY_PINCODE_ADDRS = "pincode";
    public static final String KEY_CITY_ADDRS = "city";

    public static final String SHIP_FNAME = "first_name";
    public static final String SHIP_LNAME = "last_name";
    public static final String SHIP_PHONE = "phone";
    public static final String SHIP_APARTMENTDETAILS = "ship_apartment_details";
    public static final String SHIP_APARTMENT = "ship_apartment";
    public static final String SHIP_INSTRUCT = "ship_instructions";
    public static final String SHIP_DELIVERY_DAY = "ship_delivery_day";

    public static final String DELIVERY_DAY = "delivery_day";
    public static final String DELIVERY_WEEK = "delivery_week";
    public static final String TXN_ID = "txnid";


    public static final String KEY_TOKEN_ID = "fcm_id";
    public static final String KEY_TOKEN_SAVED = "fcm_saved";
    public static final String KEY_Cart_COUNT = "cartcount";
    public static final String KEY_Cancel = "cancel";
    public static final String KEY_Status = "status";
    public static final String KEY_PrdId = "product_id";

    public static final String KEY_TOTAL = "total";
    public static final String KEY_TOTAL_SAVING = "total_savings";


    public static final String KEY_IMAGE = "image";
    public static final String KEY_COMPANY = "company";
    public static final String KEY_ADDR_1 = "address_1";
    public static final String KEY_ADDR_2 = "address_2";
    public static final String KEY_CITY = "city";
    public static final String KEY_COUNTRY_ID = "country_id";
    public static final String KEY_ZONE_ID = "zone_id";
    public static final String KEY_POST_CODE = "postcode";
    public static final String KEY_FLOOR = "floor";
    public static final String KEY_DOOR = "door";
    public static final String KEY_BLOCK = "block";
    public static final String KEY_APRT_NAME = "apartment_name";
    public static final String KEY_CUSTOM_FIELD = "custom_field";
    public static final String KEY_ORDER_ADDRESS = "order_address";
    public static final String KEY_ORDER_SAVINGS = "order_svings";
    public static final String KEY_DELIVERY_CHARGES = "delivery_charges";
    public static final String KEY_ORDER_TOTAL = "total";

    public static final String KEY_JSON_COUNT = "json";


    public static final String KEY_CART_ID = "cart_id";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createTokenSession(String tkn) {
        editor.putString(KEY_TOKEN_ID, tkn);
        editor.commit();
    }

    public String getKeyTokenId() {
        return pref.getString(KEY_TOKEN_ID, null);
    }

    public void createTokenStatus() {
        editor.putBoolean(KEY_TOKEN_SAVED, true);
        editor.commit();
    }

    public boolean getTokenStatus() {
        return pref.getBoolean(KEY_TOKEN_SAVED, false);
    }

    public void cartcount(Integer cnt) {
        editor.putInt(KEY_Cart_COUNT, cnt);
        editor.commit();
    }

    public Integer getCartCount() {
        return pref.getInt(KEY_Cart_COUNT, 0);
    }

    public void storeCancelId(String cancel_id) {
        editor.putString(KEY_Cancel, cancel_id);
        editor.commit();
    }

    public String getCancelId() {
        return pref.getString(KEY_Cancel, null);
    }

    public void setTxnId(String txnId)
    {
        editor.putString(TXN_ID, txnId);
        editor.commit();
    }
    public String getTxnId() {
        return pref.getString(TXN_ID, null);
    }
    public void clearTxnId() {
        editor.clear();
        editor.commit();
    }

    public void storeStatusOrder(String status) {
        editor.putString(KEY_Status, status);
        editor.commit();
    }

    public String getStatusOrder() {
        return pref.getString(KEY_Status, null);
    }

    public void createLoginSession(String customer_id, String customer_group_id, String firstname, String lastname,
                                   String email, String cart, String wishlist, String address_id,
                                   String date_added, String image, String telephone, String company, String address_1,
                                   String address_2, String city, String country_id, String zone_id, String postcode, String floor,
                                   String door, String block, String apartment_name, String api_token) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_CUST_ID, customer_id);
        editor.putString(KEY_CUST_GRP_ID, customer_group_id);
        editor.putString(KEY_FNAME, firstname);
        editor.putString(KEY_LNAME, lastname);
        editor.putString(KEY_EMAIL_ID, email);
        editor.putString(KEY_CART, cart);
        editor.putString(KEY_WISHLIST, wishlist);
        editor.putString(KEY_ADDRESS_ID, address_id);
        editor.putString(KEY_DATE_ADDED, date_added);
        editor.putString(KEY_IMAGE, image);
        editor.putString(KEY_TELEPHONE, telephone);
        editor.putString(KEY_COMPANY, company);
        editor.putString(KEY_ADDR_1, address_1);
        editor.putString(KEY_ADDR_2, address_2);
        editor.putString(KEY_CITY, city);
        editor.putString(KEY_COUNTRY_ID, country_id);
        editor.putString(KEY_ZONE_ID, zone_id);
        editor.putString(KEY_POST_CODE, postcode);
        editor.putString(KEY_FLOOR, floor);
        editor.putString(KEY_DOOR, door);
        editor.putString(KEY_BLOCK, block);
        editor.putString(KEY_APRT_NAME, apartment_name);
        editor.putString(KEY_TOKEN_LOGIN, api_token);
        editor.commit();
    }


    public String getCustomerId() {
        return pref.getString(KEY_CUST_ID, null);
    }

    public String getFirstName() {
        return pref.getString(KEY_FNAME, null);
    }

    public String getLastName() {
        return pref.getString(KEY_LNAME, null);
    }

    public String getPhoneNumber() {
        return pref.getString(KEY_TELEPHONE, null);
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL_ID, null);
    }

    public String getCart() {
        return pref.getString(KEY_CART, null);
    }

    public String getWishlist() {
        return pref.getString(KEY_WISHLIST, null);
    }

    public String getAddressId() {
        return pref.getString(KEY_ADDRESS_ID, null);
    }

    public String getDateAdded() {
        return pref.getString(KEY_DATE_ADDED, null);
    }

    public String getProfileImage() {
        return pref.getString(KEY_IMAGE, null);
    }

    public String getTelephone() {
        return pref.getString(KEY_TELEPHONE, null);
    }

    public String getCompany() {
        return pref.getString(KEY_COMPANY, null);
    }

    public String getAddrFirst() {
        return pref.getString(KEY_ADDR_1, null);
    }

    public String getAddrSecond() {
        return pref.getString(KEY_ADDR_2, null);
    }

    public String getCity() {
        return pref.getString(KEY_CITY, null);
    }

    public String getCountryId() {
        return pref.getString(KEY_COUNTRY_ID, null);
    }

    public String getZoneId() {
        return pref.getString(KEY_ZONE_ID, null);
    }

    public String getPincode() {
        return pref.getString(KEY_POST_CODE, null);
    }

    public String getFloor() {
        return pref.getString(KEY_FLOOR, null);
    }

    public String getDoorNo() {
        return pref.getString(KEY_DOOR, null);
    }

    public String getBlockNo() {
        return pref.getString(KEY_BLOCK, null);
    }

    public String getApartment() {
        return pref.getString(KEY_APRT_NAME, null);
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN_LOGIN, null);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void saveNewAddress(String first_name, String last_name, String email, String phone,
                               String apartment, String block, String door, String floor,
                               String area, String city, String address, String pincode) {

        editor.putString(KEY_FNAME_ADDRS, first_name);
        editor.putString(KEY_LNAME_ADDRS, last_name);
        editor.putString(KEY_PHONE_ADDRS, phone);
        editor.putString(KEY_EMAIL_ADDRESS, email);
        editor.putString(KEY_APRT_NAME_ADDRS, apartment);
        editor.putString(KEY_BLOCK_ADDRS, block);
        editor.putString(KEY_DOOR_ADDRS, door);
        editor.putString(KEY_FLOOR_ADDRS, floor);
        editor.putString(KEY_AREA_ADDRS, area);
        editor.putString(KEY_CITY_ADDRS, city);
        editor.putString(KEY_ADDRESS_ADDRS, address);
        editor.putString(KEY_PINCODE_ADDRS, pincode);
        editor.commit();
    }

    public String showFirstName() {
        return pref.getString(KEY_FNAME_ADDRS, null);
    }

    public String showLastName() {
        return pref.getString(KEY_LNAME_ADDRS, null);
    }

    public String showPhoneNumber() {
        return pref.getString(KEY_PHONE_ADDRS, null);
    }

    public String showEmailId() {
        return pref.getString(KEY_EMAIL_ADDRESS, null);
    }

    public void saveTotal(String total, String totalSaving) {
        editor.putString(KEY_TOTAL, total);
        editor.putString(KEY_TOTAL_SAVING, totalSaving);
        editor.commit();
    }

    public String getTotalAmount() {
        return pref.getString(KEY_TOTAL, null);
    }

    public String getTotalSavingAmount() {
        return pref.getString(KEY_TOTAL_SAVING, null);
    }


    public String getApartmentName() {
        return pref.getString(KEY_APRT_NAME_ADDRS, null);
    }

    public String getBlockNumber() {
        return pref.getString(KEY_BLOCK_ADDRS, null);
    }

    public String getDoorNumber() {
        return pref.getString(KEY_DOOR_ADDRS, null);
    }

    public String getFloorNumber() {
        return pref.getString(KEY_FLOOR_ADDRS, null);
    }

    public String getUsrArea() {
        return pref.getString(KEY_AREA_ADDRS, null);
    }

    public String getUsrCity() {
        return pref.getString(KEY_CITY_ADDRS, null);
    }

    public String getUsrAddress() {
        return pref.getString(KEY_ADDRESS_ADDRS, null);
    }

    public String getUsrPincode() {
        return pref.getString(KEY_PINCODE_ADDRS, null);
    }


    public void saveShippingDetails(String first_name, String last_name, String phone, String instruct, String delivery_day,
                                    String apartment_details, String apartment) {

        editor.putString(SHIP_FNAME, first_name);
        editor.putString(SHIP_LNAME, last_name);
        editor.putString(SHIP_PHONE, phone);
        editor.putString(SHIP_APARTMENT, apartment);//modified apartment name
        editor.putString(SHIP_APARTMENTDETAILS, apartment_details);//modified apartment details
        editor.putString(SHIP_INSTRUCT, instruct);//modified apartment details
        editor.putString(SHIP_DELIVERY_DAY, delivery_day);//modified apartment details
        editor.commit();
    }

    public String getShipFirstName() {
        return pref.getString(SHIP_FNAME, null);
    }

    public String getShipLastName() {
        return pref.getString(SHIP_LNAME, null);
    }

    public String getShipPhone() {
        return pref.getString(SHIP_PHONE, null);
    }

    public String getShipApartmentName() {
        return pref.getString(SHIP_APARTMENT, null);
    }

    public String getShipApartmentDetails() {
        return pref.getString(SHIP_APARTMENTDETAILS, null);
    }


    public String getShipDeliveryDay() {
        return pref.getString(SHIP_DELIVERY_DAY, null);
    }


    public void saveShippingDetails(String first_name, String last_name, String address_1,
                                    String city, String countryId, String zoneId, String apartment, String area,
                                    String pincode, String instruct) {

        editor.putString(KEY_FNAME, first_name);
        editor.putString(KEY_LNAME, last_name);
        editor.putString(KEY_ADDR_1, address_1);
        editor.putString(KEY_CITY, city);
        editor.putString(KEY_COUNTRY_ID, countryId);
        editor.putString(KEY_ZONE_ID, zoneId);
        editor.putString(KEY_COMPANY, apartment);//modified apartment name
        editor.putString(KEY_ADDR_2, area);//modified apartment name
        editor.putString(KEY_POST_CODE, pincode);//modified apartment details
        editor.putString(KEY_CUSTOM_FIELD, instruct);//modified apartment details

        editor.commit();
    }

    public String getShipInstruct() {
        return pref.getString(KEY_CUSTOM_FIELD, null);
    }

    public boolean getReferalStatus() {
        return pref.getBoolean(IS_REF, false);
    }

    public void setReferalStatus() {
        editor.putBoolean(IS_REF, true);
        editor.commit();
    }

    public String getDeliverydate() {
        return pref.getString(DELIVERY_DAY, null);
    }

    public void setDeliverydate(String dte) {
        editor.putString(DELIVERY_DAY, dte);
        editor.commit();
    }

    public String getDeliveryweek() {
        return pref.getString(DELIVERY_WEEK, null);
    }

    public void setDeliveryweek(String wk) {
        editor.putString(DELIVERY_WEEK, wk);
        editor.commit();
    }

    public void addCartId(String cart_id) {
        editor.putString(KEY_CART_ID, cart_id);
        editor.commit();
    }

    public String getCartId()
    {
        return pref.getString(KEY_CART_ID, null);
    }

    public void addorder(String ord_addr,String ord_savings,String ord_charges,String ord_total)
    {
        editor.putString(KEY_ORDER_ADDRESS, ord_addr);
        editor.putString(KEY_ORDER_SAVINGS, ord_savings);
        editor.putString(KEY_DELIVERY_CHARGES, ord_charges);
        editor.putString(KEY_ORDER_TOTAL, ord_total);
        editor.commit();
    }

    public String addorder_address()
    {
        return pref.getString(KEY_ORDER_ADDRESS, null);
    }

    public String addorder_savings()
    {
        return pref.getString(KEY_ORDER_SAVINGS, null);
    }

    public String addorder_delvery_charges()
    {
        return pref.getString(KEY_DELIVERY_CHARGES, null);
    }

    public String addorder_total()
    {
        return pref.getString(KEY_ORDER_TOTAL, null);
    }


}
