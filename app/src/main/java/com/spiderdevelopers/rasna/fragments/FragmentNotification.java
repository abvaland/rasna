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
import com.spiderdevelopers.rasna.adapters.NotificationAdapter;

/**
 * Created by Ajay parekh on 21-12-2017.
 */

public class FragmentNotification extends Fragment {
    private View view;
    RecyclerView rvNotification;
    HomeActivity activity;
    NotificationAdapter notificationAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        bindViews();

        getNotifications();

        return view;
    }

    private void getNotifications() {
        notificationAdapter=new NotificationAdapter();
        rvNotification.setAdapter(notificationAdapter);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (HomeActivity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setTitle(getResources().getString(R.string.str_notification));
    }
    private void bindViews() {
        rvNotification=view.findViewById(R.id.rvNotification);
        rvNotification.setLayoutManager(new LinearLayoutManager(activity));
    }


}
