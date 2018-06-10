package com.spiderdevelopers.rasna.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.adapters.AddressAdapter;
import com.spiderdevelopers.rasna.adapters.SaveAddressAdapter;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.res_models.AddressListRes;
import com.spiderdevelopers.rasna.res_models.BaseModel;

import java.util.ArrayList;

public class ManageAddressActivity extends BaseActivity implements UserRepository.APIListener {

    private static final String TAG = "ManageAddressActivity";
    SaveAddressAdapter saveAddressAdapter;
    private ArrayList<AddressListRes.Address> addressList;
    RecyclerView rvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);

        setupToolbar();
        setTitle(getResources().getString(R.string.str_manage_address));
        enableBack();

        bindViews();
    }

    private void bindViews() {
        rvAddress=findViewById(R.id.rvAddress);
        rvAddress.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adddress_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.menu_add)
        {
            Bundle bundle=new Bundle();
            bundle.putBoolean("isEdit",false);
            gotoActivity(AddAddressActivity.class,bundle,false);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!isOnline())
        {
            showNoNetworkDialog();
            return;
        }

        showLoading(findViewById(R.id.progressBar));
        userRepository.addressList(this,preferenceManager.getInt(Constatnts.LOGIN_ID));
    }

    @Override
    public void onResponse(int apiId, BaseModel baseModel) {
        hideLoading(findViewById(R.id.progressBar));

        AddressListRes addressListRes= (AddressListRes) baseModel;
        Log.e(TAG,"addressListRes"+addressListRes);
        if(addressListRes!=null)
        {
            addressList=addressListRes.addresses;
            if(addressList.size()>0)
            {
                saveAddressAdapter=new SaveAddressAdapter(addressList,this);
                rvAddress.setAdapter(saveAddressAdapter);
            }
            else
            {
                rvAddress.setAdapter(null);
                showToast("Address not available");
            }
        }
    }
    public void editAddress(int position)
    {
        Bundle bundle=new Bundle();
        bundle.putBoolean("isEdit",true);
        bundle.putString("address",new Gson().toJson(addressList.get(position)));
        gotoActivity(AddAddressActivity.class,bundle,false);
    }
}
