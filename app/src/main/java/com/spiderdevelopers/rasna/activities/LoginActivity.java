package com.spiderdevelopers.rasna.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.req_models.LoginReq;
import com.spiderdevelopers.rasna.res_models.BaseModel;
import com.spiderdevelopers.rasna.res_models.LoginRes;

import static android.opengl.ETC1.isValid;

public class LoginActivity extends BaseActivity implements View.OnClickListener, UserRepository.APIListener {

    private static final String TAG = "LoginActivity";
    Button btnLogin;
    TextView tvNewUser;
    EditText etUserName,etPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin=findViewById(R.id.btnLogin);
        tvNewUser=findViewById(R.id.tvNewUser);
        etUserName=findViewById(R.id.etUserName);
        etPass=findViewById(R.id.etPass);
        btnLogin.setOnClickListener(this);
        tvNewUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnLogin:
                if(!isOnline())
                {
                    showToast(getResources().getString(R.string.wrn_network_error));
                    return;
                }
                if(isValid()) {
                    LoginReq loginReq = new LoginReq();
                    loginReq.username = etUserName.getText().toString();
                    loginReq.password = etPass.getText().toString();

                    showLoading(findViewById(R.id.progressBar));
                    userRepository.login(this,loginReq);
                }

                break;
            case R.id.tvNewUser:
                gotoSignUpScreen();
                fileList();
                break;
        }
    }

    private boolean isValid() {
        if(etUserName.getText().toString().matches(""))
        {
            etUserName.setError(getResources().getString(R.string.str_require));
            return false;
        }
        if(etPass.getText().toString().matches(""))
        {
            etPass.setError(getResources().getString(R.string.str_require));
            return false;
        }

        return true;
    }

    @Override
    public void onResponse(int apiId, BaseModel baseModel) {
        hideLoading(findViewById(R.id.progressBar));
        switch (apiId)
        {
            case Constatnts.API_LOGIN:
                LoginRes loginRes= (LoginRes) baseModel;
                Log.e(TAG,"loginRes->"+loginRes);

                if(loginRes.user!=null)
                {
                    preferenceManager.setInt(Constatnts.LOGIN_ID,loginRes.user.id);
                    preferenceManager.setString(Constatnts.PASSWORD,loginRes.user.password);
                    gotoActivity(HomeActivity.class,null,true);
                    finish();
                }
                else
                {
                    showToast(loginRes.error);
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
