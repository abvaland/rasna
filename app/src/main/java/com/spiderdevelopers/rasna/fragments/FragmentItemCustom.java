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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.activities.HomeActivity;
import com.spiderdevelopers.rasna.adapters.CustomizeAdapter;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.extras.PreferenceManager;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.res_models.BaseModel;
import com.spiderdevelopers.rasna.res_models.CustomizationsRes;
import com.spiderdevelopers.rasna.res_models.ProductData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 23-12-2017.
 */

public class FragmentItemCustom extends Fragment implements UserRepository.APIListener {

    private static final String TAG = "FragmentItemCustom";
    private View view;
    HomeActivity activity;
    ImageView imgProduct;
    TextView tvName,tvTotal,tvPayable,tvCheckout;
    RecyclerView rvCustom;
    private ProductData productData;
    UserRepository userRepository;
    CustomizeAdapter customizeAdapter;
    private CustomizationsRes customizationsRes;
    LinearLayout llBottom;
    private PreferenceManager preferenceManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_item_cutomization,container,false);
        preferenceManager=PreferenceManager.getInstance(activity);
        getProduct();

        bindViews();

        getCustomization();

        return view;
    }

    private void getCustomization() {
        if(!activity.isOnline())
        {
            activity.showNoNetworkDialog();
        }

        if(productData!=null)
        {
            activity.showLoading(view.findViewById(R.id.progressBar));
            userRepository.customizations(this,productData.id);
        }

    }

    private void getProduct() {
        Bundle bundle=getArguments();

        if(bundle!=null)
        {
            productData=new Gson().fromJson(bundle.getString("product"), ProductData.class);
            Log.e(TAG,"productData -> "+new Gson().toJson(productData));
        }
    }

    private void bindViews() {
        userRepository=new UserRepository();
        llBottom=view.findViewById(R.id.llBottom);
        imgProduct=view.findViewById(R.id.imgProduct);
        tvName=view.findViewById(R.id.tvName);
        tvTotal=view.findViewById(R.id.tvTotal);
        tvPayable=view.findViewById(R.id.tvPayable);
        tvCheckout=view.findViewById(R.id.tvCheckout);
        rvCustom=view.findViewById(R.id.rvCustom);
        rvCustom.setLayoutManager(new LinearLayoutManager(activity));

        tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customizationsRes!=null)
                {
                    addCustomization();
                    addProduct();

                    activity.onBackPressed();
                }
            }
        });
    }

    private void addCustomization() {
        ArrayList<CustomizationsRes.Customization> alCostom=new ArrayList<>();
        for (int i=0;i<customizationsRes.customizations.size();i++) {
            if (customizationsRes.customizations.get(i).isSelected) {
                alCostom.add(customizationsRes.customizations.get(i));
            }
        }

        if(alCostom.size()>0)
        {
            productData.customizations=alCostom;
        }
    }

    private void addProduct() {
        productData.cart_count++;
        if(preferenceManager.contains(Constatnts.CART_PREF))
        {
            ArrayList<ProductData> cartProduct=new Gson().fromJson(preferenceManager.getString(Constatnts.CART_PREF),new TypeToken<ArrayList<ProductData>>(){}.getType());
            Log.e(TAG,"previous cart list ->"+cartProduct);

            boolean isAvailable=false;
            for (int i=0;i<cartProduct.size();i++)
            {
                if(cartProduct.get(i).id==productData.id)
                {
                    cartProduct.get(i).cart_count++;
                    isAvailable=true;
                }
            }

            if(!isAvailable)
                cartProduct.add(productData);

            Log.e(TAG,"After added cart list ->"+cartProduct);
            preferenceManager.setString(Constatnts.CART_PREF,new Gson().toJson(cartProduct));
        }
        else
        {
            ArrayList<ProductData> cartProduct=new ArrayList<>();
            cartProduct.add(productData);
            Log.e(TAG,"added cart list ->"+cartProduct);
            preferenceManager.setString(Constatnts.CART_PREF,new Gson().toJson(cartProduct));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setTitle(getResources().getString(R.string.str_item_customization));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (HomeActivity) context;
    }

    @Override
    public void onResponse(int apiId, BaseModel baseModel) {
        activity.hideLoading(view.findViewById(R.id.progressBar));

         customizationsRes= (CustomizationsRes) baseModel;

        if(customizationsRes!=null)
        {
            bindData();
        }
    }

    private void bindData() {
        if(customizationsRes.customizations.size()>0)
        {
            customizeAdapter=new CustomizeAdapter(customizationsRes.customizations,this);
            rvCustom.setAdapter(customizeAdapter);
        }

        Picasso.with(activity).load(productData.image_path).into(imgProduct);
        tvName.setText(productData.name);
        tvTotal.setText("Total "+getResources().getString(R.string.str_currency_symbol)+productData.price);
        tvPayable.setText(" = "+getResources().getString(R.string.str_currency_symbol)+productData.price);

    }

    public void calculateTotal()
    {
        float total;
        String strTotal;
        String symbol=getResources().getString(R.string.str_currency_symbol);
        try {
            total= Float.parseFloat(productData.price);
            strTotal="Total "+symbol+" "+total;

            for (int i=0;i<customizationsRes.customizations.size();i++)
            {
                if(customizationsRes.customizations.get(i).isSelected)
                {
                    float price= Float.parseFloat(customizationsRes.customizations.get(i).price);
                    total+=price;
                    strTotal=strTotal+" + "+ symbol+price;
                }
            }

            tvTotal.setText(strTotal);
            tvPayable.setText(" = "+symbol+total);

        }catch (Exception e)
        {
            e.printStackTrace();
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
