package com.spiderdevelopers.rasna.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.res_models.BaseModel;
import com.spiderdevelopers.rasna.res_models.ProfileDetailRes;
import com.spiderdevelopers.rasna.res_models.User;

public class ProfileActivity extends BaseActivity implements UserRepository.APIListener {
    private static final String TAG = "ProfileActivity";
    EditText etFname,etLastName,etMobile,etAltMobile,etEmail;
    Button btnUpdate;
    private ProfileDetailRes profileDetailRes;
    LinearLayout llData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setupToolbar();
        setTitle(getResources().getString(R.string.str_profile));
        enableBack();


        bindViews();

        getProfile();
    }

    private void getProfile() {
        if(!isOnline())
        {
            showNoNetworkDialog();
            return;
        }

        showLoading(findViewById(R.id.progressBar));
        userRepository.profileDetail(this,preferenceManager.getInt(Constatnts.LOGIN_ID));
    }

    private void bindViews() {
        llData=findViewById(R.id.llData);
        etFname=findViewById(R.id.etFname);
        etLastName=findViewById(R.id.etLastName);
        etMobile=findViewById(R.id.etMobile);
        etAltMobile=findViewById(R.id.etAltMobile);
        etEmail=findViewById(R.id.etEmail);
        btnUpdate=findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
           onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void updateProfile() {

        if(!isOnline())
        {
            showNoNetworkDialog();
            return;
        }

        if(isValid() && profileDetailRes!=null)
        {
            User user=new User();
            user.alternate_number=etAltMobile.getText().toString();
            user.first_name=etFname.getText().toString();
            user.last_name=etLastName.getText().toString();
            user.mobile_number=etMobile.getText().toString();
            user.api_token=profileDetailRes.api_token;
            user.device_type=profileDetailRes.device_type;
            user.email=profileDetailRes.email;
            user.id=profileDetailRes.id;
            user.password=profileDetailRes.password;
            user.push_token=profileDetailRes.push_token;
            user.remember_token=profileDetailRes.remember_token;

            showLoading(findViewById(R.id.progressBar));
            userRepository.updateProfile(this,user,preferenceManager.getInt(Constatnts.LOGIN_ID));
        }
    }

    private boolean isValid() {
        if(etFname.getText().toString().matches(""))
        {
            etFname.setError(getResources().getString(R.string.str_require));
            etFname.requestFocus();
            return false;
        }
        else if(etLastName.getText().toString().matches(""))
        {
            etLastName.setError(getResources().getString(R.string.str_require));
            etLastName.requestFocus();
            return false;
        }
        else if(etMobile.getText().toString().matches(""))
        {
            etMobile.setError(getResources().getString(R.string.str_require));
            etMobile.requestFocus();
            return false;
        }
        else if(etEmail.getText().toString().matches(""))
        {
            etEmail.setError(getResources().getString(R.string.str_require));
            etEmail.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onResponse(int apiId, BaseModel baseModel) {
        hideLoading(findViewById(R.id.progressBar));
        switch (apiId)
        {
            case Constatnts.API_PROFILE_DETAIL:
                 profileDetailRes= (ProfileDetailRes) baseModel;
                Log.e(TAG,"profileDetailRes - > "+profileDetailRes);

                if(profileDetailRes!=null)
                {
                    bindData();
                }
                break;
            case Constatnts.API_PROFILE_UPDATE:
                Log.e(TAG,"response -> "+baseModel);
                if(baseModel.status)
                {
                    showToast("Profile update successfully");
                    finish();
                }
                else
                {
                    showToast("Error in profile update");
                }
                break;
        }
    }

    private void bindData() {
        llData.setVisibility(View.VISIBLE);
        etFname.setText(profileDetailRes.first_name);
        etLastName.setText(profileDetailRes.last_name);
        etMobile.setText(profileDetailRes.mobile_number);
        etAltMobile.setText(profileDetailRes.alternate_number);
        etEmail.setText(profileDetailRes.email);
    }
}
