package com.spiderdevelopers.rasna.network;

import com.spiderdevelopers.rasna.req_models.AddAddressReq;
import com.spiderdevelopers.rasna.req_models.LoginReq;
import com.spiderdevelopers.rasna.req_models.OrderReq;
import com.spiderdevelopers.rasna.req_models.RegisterReq;
import com.spiderdevelopers.rasna.res_models.AddressListRes;
import com.spiderdevelopers.rasna.res_models.BaseModel;
import com.spiderdevelopers.rasna.res_models.CustomizationsRes;
import com.spiderdevelopers.rasna.res_models.LoginRes;
import com.spiderdevelopers.rasna.res_models.MenuResponse;
import com.spiderdevelopers.rasna.res_models.OrderDetailRes;
import com.spiderdevelopers.rasna.res_models.OrderListRes;
import com.spiderdevelopers.rasna.res_models.OrderPlaceRes;
import com.spiderdevelopers.rasna.res_models.OrderSummaryRes;
import com.spiderdevelopers.rasna.res_models.ProductRes;
import com.spiderdevelopers.rasna.res_models.ProfileDetailRes;
import com.spiderdevelopers.rasna.res_models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetroService {

    // Api for login
    @POST("login")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<LoginRes> login(@Body() LoginReq loginReq);

    // Api for register
    @POST("register")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<BaseModel> register(@Body() RegisterReq registerReq);


    @GET("{branch_id}/categories")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<MenuResponse> getMenu(@Path("branch_id") int branchId);

    @GET("{cat_id}/menu_items")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<ProductRes> getProduct(@Path("cat_id") int catId);

    @POST("orders")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<OrderPlaceRes> placeOrder(@Body()OrderReq orderReq);

    @GET("order_summary")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<OrderSummaryRes> orderSummery();

    @GET("orders/{user_id}")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<OrderListRes> orderList(@Path("user_id") int user_id);

    @GET("customizations/{product_id}")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<CustomizationsRes> customizations(@Path("product_id") int product_id);

    @GET("profiles/{user_id}")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<ProfileDetailRes> profileDetail(@Path("user_id") int user_id);

    @PATCH("profiles/{user_id}")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<BaseModel> updateProfile(@Body() User user,@Path("user_id") int user_id);

    @GET("{user_id}/addresses")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<AddressListRes> addressList(@Path("user_id") int user_id);

    @POST("addresses")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<BaseModel> addAddress(@Body()AddAddressReq addAddressReq);

    @PATCH("addresses/{addresses_id}")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<BaseModel> updateAddress(@Body()AddAddressReq addAddressReq,@Path("addresses_id") int addresses_id);

    @DELETE("addresses/{address_id}")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<BaseModel> deleteAddress(@Path("address_id") int address_id);

    @GET("orders/{order_id}/details")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<OrderDetailRes> orderDetails(@Path("order_id") int order_id);



}
