package com.spiderdevelopers.rasna.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.activities.AddAddressActivity;
import com.spiderdevelopers.rasna.activities.HomeActivity;
import com.spiderdevelopers.rasna.activities.ManageAddressActivity;
import com.spiderdevelopers.rasna.adapters.AddressAdapter;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.extras.PreferenceManager;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.res_models.AddressListRes;
import com.spiderdevelopers.rasna.res_models.BaseModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 20-12-2017.
 */

public class Fragment_Address extends Fragment implements View.OnClickListener, UserRepository.APIListener {
    private View view;
    RecyclerView rvAddress;
    HomeActivity activity;
    AddressAdapter addressAdapter;
    TextView tvNext;
    public static int selectedAddress=-1;
    UserRepository userRepository;
    PreferenceManager preferenceManager;
    private ArrayList<AddressListRes.Address> addressList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_delivery_address,container,false);

        userRepository=new UserRepository();
        preferenceManager=PreferenceManager.getInstance(activity);

        selectedAddress=-1;

        bindViews();



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setTitle(getResources().getString(R.string.str_select_address));

        getDiveryAddress();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (HomeActivity) context;
    }

    private void bindViews() {
        tvNext=view.findViewById(R.id.tvNext);
        rvAddress=view.findViewById(R.id.rvAddress);
        rvAddress.setLayoutManager(new LinearLayoutManager(activity));
        tvNext.setOnClickListener(this);
    }

    private void getDiveryAddress() {
        if(!activity.isOnline())
        {
            activity.showNoNetworkDialog();
            return;
        }

        activity.showLoading(view.findViewById(R.id.progressBar));
        userRepository.addressList(this,preferenceManager.getInt(Constatnts.LOGIN_ID));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tvNext:
                if(selectedAddress>-1)
                {
                    preferenceManager.setInt("address_id",addressList.get(selectedAddress).id);
                    activity.changeFragment(Constatnts.FRAGMENT_ORDER_SUMMERY,"",true,false,null);
                }

                else
                    activity.showToast("Please select address");
                break;

        }
    }

    @Override
    public void onResponse(int apiId, BaseModel baseModel) {
        activity.hideLoading(view.findViewById(R.id.progressBar));

        AddressListRes addressListRes= (AddressListRes) baseModel;

        if(addressListRes!=null)
        {
            addressList=addressListRes.addresses;
            if(addressList.size()>0)
            {
                addressAdapter=new AddressAdapter(addressList);
                rvAddress.setAdapter(addressAdapter);
            }
            else
            {
                Bundle bundle=new Bundle();
                bundle.putBoolean("isEdit",false);
                activity.gotoActivity(AddAddressActivity.class,bundle,false);
            }
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
