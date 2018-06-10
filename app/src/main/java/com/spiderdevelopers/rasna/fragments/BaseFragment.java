package com.spiderdevelopers.rasna.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

import com.spiderdevelopers.rasna.res_models.BaseModel;

/**
 * Created by Ajay parekh on 08-01-2018.
 */

public class BaseFragment extends Fragment{
    public void showLoading(View view)
    {
        view.setVisibility(View.VISIBLE);
    }
    public void hideLoading(View view)
    {
        view.setVisibility(View.GONE);
    }


    public void onResponseNull(int apiId) {

    }

    public void onFailure(int apiId, String message) {

    }
}
