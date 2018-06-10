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

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.activities.HomeActivity;
import com.spiderdevelopers.rasna.adapters.OrderHistoryAdapter;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.extras.PreferenceManager;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.res_models.BaseModel;
import com.spiderdevelopers.rasna.res_models.OrderListRes;

/**
 * Created by Ajay parekh on 21-12-2017.
 */

public class FragmentOrderHistory extends Fragment implements UserRepository.APIListener {
    private View view;
    RecyclerView rvOrders;
    HomeActivity activity;
    OrderHistoryAdapter orderHistoryAdapter;
    UserRepository userRepository;
    PreferenceManager preferenceManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_history, container, false);

        bindViews();

        getOrderHistory();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        activity.setTitle(getResources().getString(R.string.str_orders));
    }

    private void getOrderHistory() {
        if(!activity.isOnline())
        {
            activity.showNoNetworkDialog();
            return;
        }

        activity.showLoading(view.findViewById(R.id.progressBar));
        userRepository.orderList(this,preferenceManager.getInt(Constatnts.LOGIN_ID));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (HomeActivity) context;
    }
    private void bindViews() {
        rvOrders=view.findViewById(R.id.rvorders);
        rvOrders.setLayoutManager(new LinearLayoutManager(activity));
        userRepository=new UserRepository();
        preferenceManager=PreferenceManager.getInstance(activity);
    }

    @Override
    public void onResponse(int apiId, BaseModel baseModel) {
        activity.hideLoading(view.findViewById(R.id.progressBar));
        OrderListRes orderListRes= (OrderListRes) baseModel;
        if(orderListRes!=null)
        {
            orderHistoryAdapter=new OrderHistoryAdapter(orderListRes.orders);
            rvOrders.setAdapter(orderHistoryAdapter);
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
