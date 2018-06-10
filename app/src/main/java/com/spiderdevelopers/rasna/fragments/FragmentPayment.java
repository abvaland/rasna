package com.spiderdevelopers.rasna.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.activities.HomeActivity;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.extras.PreferenceManager;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.req_models.OrderItem;
import com.spiderdevelopers.rasna.req_models.OrderReq;
import com.spiderdevelopers.rasna.res_models.BaseModel;
import com.spiderdevelopers.rasna.res_models.OrderPlaceRes;
import com.spiderdevelopers.rasna.res_models.ProductData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajay parekh on 20-12-2017.
 */

public class FragmentPayment extends BaseFragment implements View.OnClickListener, UserRepository.APIListener {
    private static final String TAG = "FragmentPayment";
    private View view;
    HomeActivity activity;
    TextView tvCheckout;
    private PreferenceManager preferenceManager;
    UserRepository userRepository;
    private int delivery_type=1;
    RadioButton rbtCOD,rbtPaytm,rbtDebit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_payment,container,false);

        getDeliveryType();

        bindViews();

        preferenceManager=PreferenceManager.getInstance(activity);
        userRepository=new UserRepository();

        tvCheckout=view.findViewById(R.id.tvCheckout);
        tvCheckout.setOnClickListener(this);
        return view;
    }

    private void bindViews() {
        rbtCOD=view.findViewById(R.id.rbtCOD);
        rbtPaytm=view.findViewById(R.id.rbtPaytm);
        rbtDebit=view.findViewById(R.id.rbtDebit);
    }

    private void getDeliveryType() {
        Bundle bundle=getArguments();
        if(bundle!=null)
        {
            delivery_type=bundle.getInt("delivery_type",1);
            Log.e(TAG,"delivery_type -> "+delivery_type);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (HomeActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setTitle(getResources().getString(R.string.str_payment));
    }

    @Override
    public void onClick(View view) {

        if(!isValid())
        {
            return;
        }


        if(preferenceManager.contains(Constatnts.CART_PREF)) {
            Log.e(TAG,"data->"+preferenceManager.getString(Constatnts.CART_PREF));
            ArrayList<ProductData> cartProduct = new Gson().fromJson(preferenceManager.getString(Constatnts.CART_PREF), new TypeToken<List<ProductData>>() {
            }.getType());
            Log.e(TAG,"cartProduct->"+cartProduct);

            OrderReq orderReq=new OrderReq();
            orderReq.branch_id=preferenceManager.getInt("branch_id");
            orderReq.address_id=preferenceManager.getInt("address_id");
            orderReq.customer_id=preferenceManager.getInt(Constatnts.LOGIN_ID)+"";

            ArrayList<OrderItem> listOrderItems=new ArrayList<>();
            for (int i=0;i<cartProduct.size();i++)
            {
                OrderItem orderItem=new OrderItem();
                orderItem.id=cartProduct.get(i).id;
                orderItem.price=cartProduct.get(i).price;
                orderItem.quantity=cartProduct.get(i).cart_count;
                listOrderItems.add(orderItem);
            }

            orderReq.order_items=listOrderItems;
            orderReq.type=delivery_type;
            if(activity.isOnline())
            {
                showLoading(this.view.findViewById(R.id.progressBar));
                userRepository.placeOrder(this,orderReq);
            }
        }
    }

    private boolean isValid() {

        if(rbtCOD.isChecked() || rbtDebit.isChecked() || rbtPaytm.isChecked())
        {
            return true;
        }
        activity.showToast("Please select payment method");
        return false;
    }

    @Override
    public void onResponse(int apiId, BaseModel baseModel) {
        hideLoading(view.findViewById(R.id.progressBar));
        switch (apiId)
        {
            case Constatnts.API_PLACE_ORDER:
                OrderPlaceRes orderPlaceRes= (OrderPlaceRes) baseModel;
                Log.e(TAG,"orderPlaceRes->"+orderPlaceRes);

                if(orderPlaceRes.placed)
                {
                    activity.showToast("Order Successfully Placed");
                    activity.changeFragment(Constatnts.FRAGMENT_REST_LIST,"",false,false,null);

                    preferenceManager.remove(Constatnts.CART_PREF);
                }
                else
                {
                    activity.showToast("Error");
                }
                break;
        }
    }
}
