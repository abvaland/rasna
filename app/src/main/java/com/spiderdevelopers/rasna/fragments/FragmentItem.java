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

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.activities.HomeActivity;
import com.spiderdevelopers.rasna.adapters.ItemAdapter;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.res_models.BaseModel;
import com.spiderdevelopers.rasna.res_models.MenuResponse;
import com.spiderdevelopers.rasna.res_models.ProductRes;

/**
 * Created by Ajay parekh on 17-12-2017.
 */

public class FragmentItem extends BaseFragment implements UserRepository.APIListener {
    private static final String TAG = "FragmentItem";
    private View view;
    RecyclerView rvItem;
    ItemAdapter itemAdapter;
    HomeActivity activity;
    UserRepository userRepository;
    private int categoryId=0;
    private String categoryName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_item,container,false);

        userRepository=new UserRepository();

        init();

        bindViews();

        getItems();

        return view;
    }

    private void init() {
        if(getArguments()!=null)
        {
            Bundle bundle=getArguments();
            if(bundle.containsKey("cat_id"))
            {
                categoryId=bundle.getInt("cat_id");
                categoryName=bundle.getString("cat_name");
            }
        }
    }

    private void getItems() {
       if(!activity.isOnline())
       {
           activity.showNoNetworkDialog();
           return;
       }

        showLoading(view.findViewById(R.id.progressBar));
        userRepository.getProducts(this,categoryId);
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.showItemMenu();
        activity.setTitle(categoryName);
    }

    @Override
    public void onPause() {
        super.onPause();
        activity.hideMenu();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (HomeActivity) context;
    }

    private void bindViews() {
        rvItem=view.findViewById(R.id.rvItem);
        rvItem.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void onResponse(int apiId, BaseModel baseModel) {
        hideLoading(view.findViewById(R.id.progressBar));
        switch (apiId)
        {
            case Constatnts.API_PRODUCT:
                ProductRes productRes= (ProductRes) baseModel;
                Log.e(TAG,"menuRes->"+productRes);

                if(productRes.menu_items!=null)
                {
                    itemAdapter=new ItemAdapter(activity,productRes.menu_items);
                    rvItem.setAdapter(itemAdapter);
                }
                break;
        }
    }

    @Override
    public void onResponseNull(int apiId) {
        super.onResponseNull(apiId);
    }

    @Override
    public void onFailure(int apiId, String message) {
        super.onFailure(apiId,message);
    }
}
