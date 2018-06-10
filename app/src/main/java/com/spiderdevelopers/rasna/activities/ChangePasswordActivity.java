package com.spiderdevelopers.rasna.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.res_models.BaseModel;
import com.spiderdevelopers.rasna.res_models.User;

public class ChangePasswordActivity extends BaseActivity implements UserRepository.APIListener {

    private static final String TAG = "ChangePasswordActivity";
    EditText etOldPass,etPass,etConfirmPass;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setupToolbar();
        setTitle(getResources().getString(R.string.str_change_pass));
        enableBack();

        bindViews();
    }

    private void bindViews() {
        etOldPass=findViewById(R.id.etOldPass);
        etPass=findViewById(R.id.etPass);
        etConfirmPass=findViewById(R.id.etConfirmPass);
        btnUpdate=findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        if(isValid())
        {
            Log.e(TAG,"new Password -> "+etPass.getText().toString());
            User user=new User();
            user.password=etPass.getText().toString();

            showLoading(findViewById(R.id.progressBar));
            userRepository.updateProfile(this,user,preferenceManager.getInt(Constatnts.LOGIN_ID));

        }
    }

    private boolean isValid() {

        String pass=preferenceManager.getString(Constatnts.PASSWORD);

        if(etOldPass.getText().toString().matches(""))
        {
            etOldPass.setError(getResources().getString(R.string.str_require));
            etOldPass.requestFocus();
            return false;
        }
        if(!etOldPass.getText().toString().matches(pass))
        {
            etOldPass.setError("Old "+getResources().getString(R.string.wrn_password_not_match));
            etOldPass.requestFocus();
            return false;
        }

        else if(etPass.getText().toString().matches(""))
        {
            etPass.setError(getResources().getString(R.string.str_require));
            etPass.requestFocus();
            return false;
        }
        else if(etConfirmPass.getText().toString().matches(""))
        {
            etConfirmPass.setError(getResources().getString(R.string.str_require));
            etConfirmPass.requestFocus();
            return false;
        }
        else if(!etPass.getText().toString().matches(etConfirmPass.getText().toString()))
        {
            etPass.setError(getResources().getString(R.string.wrn_password_not_match));
            etPass.requestFocus();

            return false;
        }
        return true;
    }

    @Override
    public void onResponse(int apiId, BaseModel baseModel) {
        hideLoading(findViewById(R.id.progressBar));
        if(baseModel.status)
        {
            showToast("Password update successfully");

            preferenceManager.setString(Constatnts.PASSWORD,etPass.getText().toString());
            finish();
        }
        else
        {
            showToast("Update in password update");
        }
    }
}
