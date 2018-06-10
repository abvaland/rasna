package com.spiderdevelopers.rasna.network;

import android.util.Log;

import com.spiderdevelopers.rasna.extras.Constatnts;
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

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Ajay parekh on 08-01-2018.
 */

public class UserRepository {

    APIListener apiListener;
    public static String TAG="UserRepository";


    public void login(APIListener listener, LoginReq loginReq)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<LoginRes> call = api.login(loginReq);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: "+loginReq);

        call.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, retrofit2.Response<LoginRes> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_LOGIN,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_LOGIN);
                }
            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_LOGIN,t.getMessage());
            }
        });
    }

    public void register(APIListener listener, RegisterReq registerReq)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<BaseModel> call = api.register(registerReq);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: "+registerReq);

        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, retrofit2.Response<BaseModel> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_REGISTER,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_REGISTER);
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_REGISTER,t.getMessage());
            }
        });
    }

    public void getMenu(APIListener listener, int branchId)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<MenuResponse> call = api.getMenu(branchId);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: "+branchId);

        call.enqueue(new Callback<MenuResponse>() {
            @Override
            public void onResponse(Call<MenuResponse> call, retrofit2.Response<MenuResponse> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_MENU,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_MENU);
                }
            }

            @Override
            public void onFailure(Call<MenuResponse> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_MENU,t.getMessage());
            }
        });
    }

    public void getProducts(APIListener listener, int categoryId)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<ProductRes> call = api.getProduct(categoryId);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: "+categoryId);

        call.enqueue(new Callback<ProductRes>() {
            @Override
            public void onResponse(Call<ProductRes> call, retrofit2.Response<ProductRes> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_PRODUCT,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_PRODUCT);
                }
            }

            @Override
            public void onFailure(Call<ProductRes> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_PRODUCT,t.getMessage());
            }
        });
    }

    public void placeOrder(APIListener listener, OrderReq orderReq)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<OrderPlaceRes> call = api.placeOrder(orderReq);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: "+orderReq);

        call.enqueue(new Callback<OrderPlaceRes>() {
            @Override
            public void onResponse(Call<OrderPlaceRes> call, retrofit2.Response<OrderPlaceRes> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_PLACE_ORDER,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_PLACE_ORDER);
                }
            }

            @Override
            public void onFailure(Call<OrderPlaceRes> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_PLACE_ORDER,t.getMessage());
            }
        });
    }

    public void orderSummary(APIListener listener)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<OrderSummaryRes> call = api.orderSummery();

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: ");

        call.enqueue(new Callback<OrderSummaryRes>() {
            @Override
            public void onResponse(Call<OrderSummaryRes> call, retrofit2.Response<OrderSummaryRes> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_ORDER_SUMMARY,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_ORDER_SUMMARY);
                }
            }

            @Override
            public void onFailure(Call<OrderSummaryRes> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_ORDER_SUMMARY,t.getMessage());
            }
        });
    }

    public void orderList(APIListener listener,int userId)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<OrderListRes> call = api.orderList(userId);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: ");

        call.enqueue(new Callback<OrderListRes>() {
            @Override
            public void onResponse(Call<OrderListRes> call, retrofit2.Response<OrderListRes> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_ORDER_LIST,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_ORDER_LIST);
                }
            }

            @Override
            public void onFailure(Call<OrderListRes> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_ORDER_LIST,t.getMessage());
            }
        });
    }

    public void customizations(APIListener listener,int product_id)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<CustomizationsRes> call = api.customizations(product_id);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: ");

        call.enqueue(new Callback<CustomizationsRes>() {
            @Override
            public void onResponse(Call<CustomizationsRes> call, retrofit2.Response<CustomizationsRes> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_CUSTOMIZATION,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_CUSTOMIZATION);
                }
            }

            @Override
            public void onFailure(Call<CustomizationsRes> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_CUSTOMIZATION,t.getMessage());
            }
        });
    }

    public void profileDetail(APIListener listener,int user_id)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<ProfileDetailRes> call = api.profileDetail(user_id);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: ");

        call.enqueue(new Callback<ProfileDetailRes>() {
            @Override
            public void onResponse(Call<ProfileDetailRes> call, retrofit2.Response<ProfileDetailRes> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_PROFILE_DETAIL,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_PROFILE_DETAIL);
                }
            }

            @Override
            public void onFailure(Call<ProfileDetailRes> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_PROFILE_DETAIL,t.getMessage());
            }
        });
    }

    public void updateProfile(APIListener listener, User user,int user_id)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<BaseModel> call = api.updateProfile(user,user_id);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: "+user);

        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, retrofit2.Response<BaseModel> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_PROFILE_UPDATE,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_PROFILE_UPDATE);
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_PROFILE_UPDATE,t.getMessage());
            }
        });
    }

    public void addressList(APIListener listener,int user_id)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<AddressListRes> call = api.addressList(user_id);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: ");

        call.enqueue(new Callback<AddressListRes>() {
            @Override
            public void onResponse(Call<AddressListRes> call, retrofit2.Response<AddressListRes> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_ADDRESS_LIST,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_ADDRESS_LIST);
                }
            }

            @Override
            public void onFailure(Call<AddressListRes> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_ADDRESS_LIST,t.getMessage());
            }
        });
    }

    public void addAddress(APIListener listener, AddAddressReq request)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<BaseModel> call = api.addAddress(request);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: "+request);

        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, retrofit2.Response<BaseModel> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_ADDRESS_ADD,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_ADDRESS_ADD);
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_ADDRESS_ADD,t.getMessage());
            }
        });
    }


    public void updateAddress(APIListener listener, AddAddressReq request,int address_id)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<BaseModel> call = api.updateAddress(request,address_id);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: ");

        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, retrofit2.Response<BaseModel> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_ADDRESS_UPDATE,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_ADDRESS_UPDATE);
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_ADDRESS_UPDATE,t.getMessage());
            }
        });
    }

    public void deleteAddress(APIListener listener,int address_id)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<BaseModel> call = api.deleteAddress(address_id);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: ");

        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, retrofit2.Response<BaseModel> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_ADDRESS_DELETE,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_ADDRESS_DELETE);
                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_ADDRESS_DELETE,t.getMessage());
            }
        });
    }

    public void orderDetail(APIListener listener,int order_id)
    {
        apiListener=listener;

        RetroService api = RetroClient.getApiService(getHttpClient());
        Call<OrderDetailRes> call = api.orderDetails(order_id);

        Log.e(TAG,"URL :: "+call.request().url().toString());
        Log.e(TAG,"Send Data :: ");

        call.enqueue(new Callback<OrderDetailRes>() {
            @Override
            public void onResponse(Call<OrderDetailRes> call, retrofit2.Response<OrderDetailRes> response) {
                if(response.body()!=null)
                {
                    apiListener.onResponse(Constatnts.API_ORDER_DETAIL,response.body());
                }
                else
                {
                    apiListener.onResponseNull(Constatnts.API_ORDER_DETAIL);
                }
            }

            @Override
            public void onFailure(Call<OrderDetailRes> call, Throwable t) {
                apiListener.onFailure(Constatnts.API_ORDER_DETAIL,t.getMessage());
            }
        });
    }

    public OkHttpClient getHttpClient()
    {
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request.Builder builder = originalRequest.newBuilder().header("Authorization", "");

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();

        return client;
    }


    public interface APIListener
    {
        public void onResponse(int apiId, BaseModel baseModel);
        public void onResponseNull(int apiId);
        public void onFailure(int apiId, String message);
    }
}
