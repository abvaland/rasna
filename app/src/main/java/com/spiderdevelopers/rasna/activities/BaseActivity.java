package com.spiderdevelopers.rasna.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.extras.PreferenceManager;
import com.spiderdevelopers.rasna.network.UserRepository;

/**
 * Created by Ajay parekh on 10-12-2017.
 */

public class BaseActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    Menu menu;
    UserRepository userRepository;

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager=PreferenceManager.getInstance(this);
        userRepository=new UserRepository();
    }
    public void showNoNetworkDialog()
    {
        showToast("Network unavailable.!");
    }
    public void gotoHomeScreen()
    {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
    public void gotoLoginScreen()
    {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void gotoSignUpScreen()
    {
        Intent intent=new Intent(this,SignupActivity.class);
        startActivity(intent);
    }


    public void setupToolbar()
    {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle("");
    }
    public void enableBack()
    {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void setTitle(String title)
    {
        ((TextView)findViewById(R.id.toolbar_title)).setText(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        this.menu=menu;
        return super.onCreateOptionsMenu(menu);
    }
    public void showItemMenu()
    {
        if(menu!=null)
        {
            menu.findItem(R.id.menu_filter).setVisible(true);
            menu.findItem(R.id.menu_search).setVisible(true);
        }
    }
    public void hideMenu()
    {
        if(menu!=null)
        {
            menu.findItem(R.id.menu_filter).setVisible(false);
            menu.findItem(R.id.menu_search).setVisible(false);
        }
    }

    public void showLoading(View view)
    {
        view.setVisibility(View.VISIBLE);
    }
    public void hideLoading(View view)
    {
        view.setVisibility(View.GONE);
    }


    public boolean isOnline()
    {
        try
        {
            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public  boolean hasPermission( String[] permissions) {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && permissions!=null)
        {
            for(String permission:permissions)
            {
                if(ActivityCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED)
                    return false;
            }
        }
        return true;
    }
    public void gotoActivity(Class className, Bundle bundle,boolean isClearStack)
    {
        Intent intent=new Intent(this,className);

        if(bundle!=null)
            intent.putExtras(bundle);

        if(isClearStack)
        {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);
    }
    public void showToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void onResponseNull(int apiId) {
        hideLoading(findViewById(R.id.progressBar));
        showToast("Server not Respond");

    }

    public void onFailure(int apiId, String message) {
        hideLoading(findViewById(R.id.progressBar));
        showToast(message);
    }
}
