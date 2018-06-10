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
import com.spiderdevelopers.rasna.adapters.MenuAdapter;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.res_models.BaseModel;
import com.spiderdevelopers.rasna.res_models.MenuData;
import com.spiderdevelopers.rasna.res_models.MenuResponse;

/**
 * Created by Ajay parekh on 17-12-2017.
 */

public class FragmentMenu extends BaseFragment implements MenuAdapter.MenuItemClickListener, UserRepository.APIListener {
    private static final String TAG = "FragmentMenu";
    private View view;
    RecyclerView rvMenu;
    MenuAdapter menuAdapter;
    HomeActivity activity;
    UserRepository userRepository;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_menu,container,false);

        userRepository=new UserRepository();

        bindViews();

        getMenuList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setTitle(getResources().getString(R.string.str_menu));
    }

    private void getMenuList() {
       if(!activity.isOnline())
       {
           activity.showNoNetworkDialog();
           return;
       }

       try {
           int id = getArguments().getInt("id");
           if(id>0)
           {
               showLoading(view.findViewById(R.id.progressBar));
               userRepository.getMenu(this,id);
           }
           else
           {
               showLoading(view.findViewById(R.id.progressBar));
               userRepository.getMenu(this,1);
           }
       }
       catch (Exception e)
       {
           showLoading(view.findViewById(R.id.progressBar));
           userRepository.getMenu(this,1);
       }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (HomeActivity) context;
    }

    private void bindViews() {
        rvMenu=view.findViewById(R.id.rvMenu);
        rvMenu.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void onMenuClick(MenuData menuData) {
        Bundle bundle=new Bundle();
        bundle.putInt("cat_id",menuData.id);
        bundle.putString("cat_name",menuData.name);
        activity.changeFragment(Constatnts.FRAGMENT_ITEM,"Pizza",true,false,bundle);
    }

    @Override
    public void onResponse(int apiId, BaseModel baseModel) {
        hideLoading(view.findViewById(R.id.progressBar));
        switch (apiId)
        {
            case Constatnts.API_MENU:
                MenuResponse menuResponse= (MenuResponse) baseModel;
                Log.e(TAG,"menuRes->"+menuResponse);

                menuAdapter=new MenuAdapter(this,menuResponse.categories);
                rvMenu.setAdapter(menuAdapter);
                break;
        }
    }
}
