package com.spiderdevelopers.rasna.activities;

import android.os.Handler;
import android.os.Bundle;

import com.spiderdevelopers.rasna.R;

public class SplashActivity extends BaseActivity {

    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                gotoNextScreen();
            }
        };

        handler.postDelayed(runnable,2000);
    }

    private void gotoNextScreen() {
        if(preferenceManager.isLoggedIn())
        {
            gotoHomeScreen();
        }
        else
        {
            gotoLoginScreen();
        }
        finish();

    }
}
