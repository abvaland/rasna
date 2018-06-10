package com.spiderdevelopers.rasna.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.activities.HomeActivity;
import com.spiderdevelopers.rasna.adapters.OrderItemAdapter;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.extras.PreferenceManager;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.res_models.BaseModel;
import com.spiderdevelopers.rasna.res_models.OrderSummaryRes;
import com.spiderdevelopers.rasna.res_models.ProductData;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 20-12-2017.
 */

public class FragmentOrderSummery extends Fragment implements View.OnClickListener, UserRepository.APIListener {

    private static final String TAG = "FragmentOrderSummery";
    private View view;
    RecyclerView rvOrderList;
    HomeActivity activity;
    OrderItemAdapter orderItemAdapter;
    TextView tvPayment;
    private PreferenceManager preferenceManager;
    private TextView tvTotal;
    private float total=0;
    RadioButton rbtHome,rbtPickup;
    UserRepository userRepository;
    int delivery_type=1;
    private OrderSummaryRes orderSummaryRes;
    ScrollView scrollData;

    TextView tvDeliveryCharges,tvSGST,tvCGST;
    LinearLayout llDeliveryCharges;
    float totalPayable=0;
    float cgst=0;
    float sgst=0;
    float delivery_charges=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_order_summery,container,false);
        preferenceManager=PreferenceManager.getInstance(activity);

        initObjects();

        bindViews();

        getCharges();

        return view;
    }

    private void initObjects() {
        userRepository=new UserRepository();
    }

    private void getCharges() {

        if(!activity.isOnline())
        {
            activity.showNoNetworkDialog();
            return;
        }

        activity.showLoading(view.findViewById(R.id.progressBar));
        userRepository.orderSummary(this);

    }

    private void getOrderItems() {
        if(preferenceManager.contains(Constatnts.CART_PREF)) {
            ArrayList<ProductData> cartProduct = new Gson().fromJson(preferenceManager.getString(Constatnts.CART_PREF), new TypeToken<ArrayList<ProductData>>() {
            }.getType());
            orderItemAdapter=new OrderItemAdapter(cartProduct);
            rvOrderList.setAdapter(orderItemAdapter);

            for (int i=0;i<cartProduct.size();i++)
            {
                float price= Float.parseFloat(cartProduct.get(i).price);

                if(cartProduct.get(i).customizations!=null)
                {
                    Log.e(TAG,"customization -> "+new Gson().toJson(cartProduct.get(i).customizations));
                    float extras=0;
                    for (int j=0;j<cartProduct.get(i).customizations.size();j++)
                    {
                        try {
                            float price2= Float.parseFloat(cartProduct.get(i).customizations.get(j).price);
                            extras+=price2 ;
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    Log.e(TAG,"extras -> "+extras);
                    total+=(price+extras)*cartProduct.get(i).cart_count;
                }
                else
                {
                    total+=price*cartProduct.get(i).cart_count;
                }
            }

            Log.e(TAG,"Total amount ->"+total);


            tvDeliveryCharges.setText(" + "+getResources().getString(R.string.str_currency_symbol)+" "+orderSummaryRes.getDELIVERY_CHARGES());

            try {
                cgst= Float.parseFloat(orderSummaryRes.getCGST());
                sgst= Float.parseFloat(orderSummaryRes.getSGST());
                delivery_charges= Float.parseFloat(orderSummaryRes.getDELIVERY_CHARGES());
            }
            catch (Exception e)
            {
                Log.e(TAG,"exception -> "+e.toString());
            }
            float diductCgst=(total*cgst)/100;
            float diductSgst=(total*sgst)/100;

            tvCGST.setText(" + "+getResources().getString(R.string.str_currency_symbol)+" "+diductCgst+"("+orderSummaryRes.getCGST()+"%)");
            tvSGST.setText(" + "+getResources().getString(R.string.str_currency_symbol)+" "+diductSgst+"("+orderSummaryRes.getSGST()+"%)");

            totalPayable=total+(diductCgst+diductSgst+delivery_charges);

            tvTotal.setText(getResources().getString(R.string.str_currency_symbol)+" "+totalPayable);
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        activity.setTitle(getResources().getString(R.string.str_order_summery));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (HomeActivity) context;
    }

    private void bindViews() {
        llDeliveryCharges=view.findViewById(R.id.llDeliveryCharges);
        tvCGST=view.findViewById(R.id.tvCGST);
        tvSGST=view.findViewById(R.id.tvSGST);
        tvDeliveryCharges=view.findViewById(R.id.tvDeliveryCharges);
        scrollData=view.findViewById(R.id.scrollData);
        rbtPickup=view.findViewById(R.id.rbtPickup);
        rbtHome=view.findViewById(R.id.rbtHome);
        tvTotal=view.findViewById(R.id.tvTotal);
        tvPayment=view.findViewById(R.id.tvPayment);
        rvOrderList=view.findViewById(R.id.rvOrderList);
        rvOrderList.setLayoutManager(new LinearLayoutManager(activity));
        tvPayment.setOnClickListener(this);

        rbtHome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    delivery_type=1;
                    Log.e(TAG,"delivery type -> "+delivery_type);
                    llDeliveryCharges.setVisibility(View.VISIBLE);

                    totalPayable=totalPayable+delivery_charges;

                    tvTotal.setText(getResources().getString(R.string.str_currency_symbol)+" "+totalPayable);
                }
            }
        });
        rbtPickup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    delivery_type=2;
                    Log.e(TAG,"delivery type -> "+delivery_type);
                    llDeliveryCharges.setVisibility(View.GONE);


                    totalPayable=totalPayable-delivery_charges;

                    tvTotal.setText(getResources().getString(R.string.str_currency_symbol)+" "+totalPayable);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tvPayment:
                    if(orderSummaryRes!=null)
                    {
                        Bundle bundle=new Bundle();
                        bundle.putInt("delivery_type",delivery_type);
                        activity.changeFragment(Constatnts.FRAGMENT_PAYMENT,"",true,false,bundle);
                    }

                break;
        }
    }

    @Override
    public void onResponse(int apiId, BaseModel baseModel) {
        activity.hideLoading(view.findViewById(R.id.progressBar));
        orderSummaryRes= (OrderSummaryRes) baseModel;
        Log.e(TAG,"orderSummaryRes->"+orderSummaryRes);
        if(orderSummaryRes!=null)
        {
            scrollData.setVisibility(View.VISIBLE);
            getOrderItems();
        }

    }

    @Override
    public void onResponseNull(int apiId) {
        activity.hideLoading(view.findViewById(R.id.progressBar));
        activity.onResponseNull(apiId);
    }

    @Override
    public void onFailure(int apiId, String message) {
        activity.hideLoading(view.findViewById(R.id.progressBar));
        activity.onFailure(apiId,message);
    }
}
