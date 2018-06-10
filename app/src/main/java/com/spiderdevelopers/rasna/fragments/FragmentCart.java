package com.spiderdevelopers.rasna.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.activities.HomeActivity;
import com.spiderdevelopers.rasna.adapters.CartAdapter;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.extras.PreferenceManager;
import com.spiderdevelopers.rasna.res_models.ProductData;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 17-12-2017.
 */

public class FragmentCart extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "FragmentCart";
    private View view;
    RecyclerView rvCart;
    HomeActivity activity;
    CartAdapter cartAdapter;
    TextView tvCheckout;
    PreferenceManager preferenceManager;
    private float total=0;
    private TextView tvTotal;
    LinearLayout llBottom;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_cart,container,false);

        preferenceManager=PreferenceManager.getInstance(activity);

        bindViews();

        getCartData();

        return view;
    }

    private void getCartData() {
        if(preferenceManager.contains(Constatnts.CART_PREF)) {
            ArrayList<ProductData> cartProduct = new Gson().fromJson(preferenceManager.getString(Constatnts.CART_PREF), new TypeToken<ArrayList<ProductData>>() {
            }.getType());
            cartAdapter=new CartAdapter(activity,cartProduct,this);
            rvCart.setAdapter(cartAdapter);

             calculateTotal(cartProduct);
        }
        else
        {
            llBottom.setVisibility(View.GONE);
            activity.showToast("Your Cart is empty");
        }
    }
    public void calculateTotal(ArrayList<ProductData> cartProduct)
    {
        total=0;
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
        tvTotal.setText(getResources().getString(R.string.str_currency_symbol)+" "+total);

        if(cartProduct.size()>0)
        {
            llBottom.setVisibility(View.VISIBLE);
        }
        else
        {
            activity.showToast("Your Cart is empty");
            llBottom.setVisibility(View.GONE);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (HomeActivity) context;
    }

    private void bindViews() {
        llBottom=view.findViewById(R.id.llBottom);
        tvTotal=view.findViewById(R.id.tvTotal);
        rvCart=view.findViewById(R.id.rvCart);
        tvCheckout=view.findViewById(R.id.tvCheckout);
        rvCart.setLayoutManager(new LinearLayoutManager(getActivity()));

        tvCheckout.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setTitle(getResources().getString(R.string.str_cart));
    }

    @Override
    public void onClick(View view) {
        if(total>0)
        {
            activity.changeFragment(Constatnts.FRAGMENT_ADDRESS,"",true,false,null);
        }

    }
}