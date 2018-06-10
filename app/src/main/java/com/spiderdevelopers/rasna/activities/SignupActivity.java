package com.spiderdevelopers.rasna.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.req_models.RegisterReq;
import com.spiderdevelopers.rasna.res_models.BaseModel;

public class SignupActivity extends BaseActivity implements View.OnClickListener, UserRepository.APIListener {

    TextView tvAlreadyUser;
    Button btnSignUp;
    EditText etConfirmPass,etPass,etEmail,etMobile,etLastName,etFname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        tvAlreadyUser=findViewById(R.id.tvAlreadyUser);
        btnSignUp=findViewById(R.id.btnSignUp);
        etConfirmPass=findViewById(R.id.etConfirmPass);
        etPass=findViewById(R.id.etPass);
        etEmail=findViewById(R.id.etEmail);
        etMobile=findViewById(R.id.etMobile);
        etLastName=findViewById(R.id.etLastName);
        etFname=findViewById(R.id.etFname);


        tvAlreadyUser.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tvAlreadyUser:
                gotoLoginScreen();
                finish();
                break;
            case R.id.btnSignUp:
                if(!isOnline())
                {
                    showToast(getString(R.string.wrn_network_error));
                    return;
                }
                if(isValid())
                {
                    RegisterReq registerReq=new RegisterReq();
                    registerReq.first_name=etFname.getText().toString();
                    registerReq.last_name=etLastName.getText().toString();
                    registerReq.email_id=etEmail.getText().toString();
                    registerReq.password=etPass.getText().toString();
                    registerReq.mobile_no=etMobile.getText().toString();

                    showLoading(findViewById(R.id.progressBar));
                    userRepository.register(this,registerReq);
                }
                break;
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
        if (baseModel.status)
        {
            showToast("Register Success");
            gotoLoginScreen();
            finish();
        }
        else
        {
            showToast(baseModel.error);
        }
    }
}
