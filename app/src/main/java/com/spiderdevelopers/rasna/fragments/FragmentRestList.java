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

import com.spiderdevelopers.rasna.activities.HomeActivity;
import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.adapters.RestaurantsAdapter;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.extras.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by Ajay parekh on 10-12-2017.
 */

public class FragmentRestList extends Fragment implements RestaurantsAdapter.RestaurantItemListener {
    RecyclerView rvRestList;
    private View view;
    HomeActivity activity;
    RestaurantsAdapter restaurantsAdapter;
    BannerSlider bannerSlider;
    PreferenceManager preferenceManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      view=inflater.inflate(R.layout.fragment_rest_list,container,false);

      preferenceManager=PreferenceManager.getInstance(activity);

      bindViews();

      getRestaurantsList();

      return view;
    }

    private void getRestaurantsList() {
        restaurantsAdapter=new RestaurantsAdapter(this);
        rvRestList.setAdapter(restaurantsAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (HomeActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setTitle(getResources().getString(R.string.home));
    }

    private void bindViews() {
        rvRestList=view.findViewById(R.id.rvRestList);
        rvRestList.setLayoutManager(new LinearLayoutManager(activity));

        bannerSlider = (BannerSlider) view.findViewById(R.id.banner_slider1);
        List<Banner> banners=new ArrayList<>();

        //banners.add(new RemoteBanner("Put banner image url here ..."));

        banners.add(new DrawableBanner(R.drawable.login_screen_bg));
        banners.add(new DrawableBanner(R.drawable.signup_screen_bg));
        banners.add(new DrawableBanner(R.drawable.login_screen_bg));
        banners.add(new DrawableBanner(R.drawable.signup_screen_bg));

        bannerSlider.setBanners(banners);
    }

    @Override
    public void onRestaurantClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putInt("id",position+1);
        preferenceManager.setInt("branch_id",position+1);
        activity.changeFragment(Constatnts.FRAGMENT_MENU,"Menu",true,false,bundle);
    }
}
