package com.spiderdevelopers.rasna.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.activities.ChangePasswordActivity;
import com.spiderdevelopers.rasna.activities.HomeActivity;
import com.spiderdevelopers.rasna.activities.LoginActivity;
import com.spiderdevelopers.rasna.activities.ManageAddressActivity;
import com.spiderdevelopers.rasna.activities.ProfileActivity;
import com.spiderdevelopers.rasna.extras.PreferenceManager;

/**
 * Created by Ajay parekh on 21-12-2017.
 */

public class FragmentProfile extends Fragment implements View.OnClickListener {
    private View view;
    HomeActivity activity;
    CardView cartChangePass,cardBasicInfo,cardManageAddress,cardLogout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_profile,container,false);
        bindViews();
        return view;
    }

    private void bindViews() {
        cartChangePass=view.findViewById(R.id.cartChangePass);
        cardBasicInfo=view.findViewById(R.id.cardBasicInfo);
        cardManageAddress=view.findViewById(R.id.cardManageAddress);
        cardLogout=view.findViewById(R.id.cardLogout);

        cartChangePass.setOnClickListener(this);
        cardBasicInfo.setOnClickListener(this);
        cardManageAddress.setOnClickListener(this);
        cardLogout.setOnClickListener(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (HomeActivity) context;
    }
    @Override
    public void onResume() {
        super.onResume();
        activity.setTitle(getResources().getString(R.string.str_profile));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.cartChangePass:
                activity.gotoActivity(ChangePasswordActivity.class,null,false);
                break;

            case R.id.cardBasicInfo:
                activity.gotoActivity(ProfileActivity.class,null,false);
                break;
            case R.id.cardManageAddress:
                activity.gotoActivity(ManageAddressActivity.class,null,false);
                break;
            case R.id.cardLogout:
                activity.showToast("Successfully logged out");
                PreferenceManager.getInstance(activity).clearData();
                activity.gotoActivity(LoginActivity.class,null,true);
                activity.finish();
                break;

        }

    }
}
