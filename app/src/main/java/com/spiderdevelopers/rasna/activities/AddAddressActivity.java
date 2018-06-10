package com.spiderdevelopers.rasna.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.gson.Gson;
import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.req_models.AddAddressReq;
import com.spiderdevelopers.rasna.res_models.AddressListRes;
import com.spiderdevelopers.rasna.res_models.BaseModel;

public class AddAddressActivity extends BaseActivity implements View.OnClickListener, UserRepository.APIListener {

   // RadioButton rbtHome,rbtOffice;
    EditText etHouseNumber,etStreet,etArea,etPincode,etCity,etLandMark,etLabel;
    Button btnSave,btnUpdate;
    private boolean isEdit;
    private AddressListRes.Address address;
    private Menu my_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        getIntentData();

        setupToolbar();
        setTitle(getResources().getString(R.string.str_add_Address));
        enableBack();

        bindViews();

        setupData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_address_menu,menu);
        my_menu=menu;

        if(isEdit)
        {
            my_menu.findItem(R.id.menu_delete).setVisible(true);
        }
        else
        {
            my_menu.findItem(R.id.menu_delete).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_delete)
        {
            if(address!=null)
            {
                showLoading(findViewById(R.id.progressBar));
                userRepository.deleteAddress(this,address.id);
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void setupData() {
        if(isEdit)
        {
            setTitle(getResources().getString(R.string.str_add_Address));
            etLabel.setText(address.label);
            etArea.setText(address.area);
            etCity.setText(address.city);
            etHouseNumber.setText(address.house_number);
            etPincode.setText(address.pincode);
            etStreet.setText(address.street);
            etLandMark.setText(address.landmark);
            btnUpdate.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.GONE);


        }
        else
        {
            btnUpdate.setVisibility(View.GONE);
            btnSave.setVisibility(View.VISIBLE);

        }
    }

    private void getIntentData() {
        Bundle bundle=getIntent().getExtras();

        if(bundle!=null)
        {
            isEdit=bundle.getBoolean("isEdit",false);
            if(isEdit)
                address=new Gson().fromJson(bundle.getString("address"), AddressListRes.Address.class);
        }
    }

    private void bindViews() {
        etLandMark=findViewById(R.id.etLandMark);
        etLabel=findViewById(R.id.etLabel);
        etHouseNumber=findViewById(R.id.etHouseNumber);
        etStreet=findViewById(R.id.etStreet);
        etArea=findViewById(R.id.etArea);
        etPincode=findViewById(R.id.etPincode);
        etCity=findViewById(R.id.etCity);
        btnSave=findViewById(R.id.btnSave);
        btnUpdate=findViewById(R.id.btnUpdate);

        btnSave.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnSave:
                if(!isOnline())
                {
                    showNoNetworkDialog();
                    return;
                }

                if(isValid())
                {
                    AddAddressReq addAddressReq=new AddAddressReq();
                    addAddressReq.customer_id=preferenceManager.getInt(Constatnts.LOGIN_ID)+"";
                    addAddressReq.area=etArea.getText().toString();
                    addAddressReq.city=etCity.getText().toString();
                    addAddressReq.house_number=etHouseNumber.getText().toString();
                    addAddressReq.label=etLabel.getText().toString();
                    addAddressReq.landmark=etLandMark.getText().toString();
                    addAddressReq.pincode=etPincode.getText().toString();
                    addAddressReq.street=etStreet.getText().toString();

                    showLoading(findViewById(R.id.progressBar));
                    userRepository.addAddress(this,addAddressReq);
                }
                break;

            case R.id.btnUpdate:
                if(!isOnline())
                {
                    showNoNetworkDialog();
                    return;
                }

                if(isValid() && address!=null)
                {
                    AddAddressReq addAddressReq=new AddAddressReq();
                    addAddressReq.customer_id=preferenceManager.getInt(Constatnts.LOGIN_ID)+"";
                    addAddressReq.area=etArea.getText().toString();
                    addAddressReq.city=etCity.getText().toString();
                    addAddressReq.house_number=etHouseNumber.getText().toString();
                    addAddressReq.label=etLabel.getText().toString();
                    addAddressReq.landmark=etLandMark.getText().toString();
                    addAddressReq.pincode=etPincode.getText().toString();
                    addAddressReq.street=etStreet.getText().toString();

                    showLoading(findViewById(R.id.progressBar));
                    userRepository.updateAddress(this,addAddressReq,address.id);
                }
                break;
        }
    }

    private boolean isValid() {

        if(etLabel.getText().toString().matches(""))
        {
            etLabel.setError(getResources().getString(R.string.str_require));
            etLabel.requestFocus();
            return false;
        }
        else if(etHouseNumber.getText().toString().matches(""))
        {
            etHouseNumber.setError(getResources().getString(R.string.str_require));
            etHouseNumber.requestFocus();
            return false;
        }
        else if(etStreet.getText().toString().matches(""))
        {
            etStreet.setError(getResources().getString(R.string.str_require));
            etStreet.requestFocus();
            return false;
        }
        else if(etArea.getText().toString().matches(""))
        {
            etArea.setError(getResources().getString(R.string.str_require));
            etArea.requestFocus();
            return false;
        }
        else if(etPincode.getText().toString().matches(""))
        {
            etPincode.setError(getResources().getString(R.string.str_require));
            etPincode.requestFocus();
            return false;
        }
        else if(etPincode.getText().length()!=6)
        {
            etPincode.setError("Invalid Pincode");
            etPincode.requestFocus();
            return false;
        }
        else if(etCity.getText().toString().matches(""))
        {
            etCity.setError(getResources().getString(R.string.str_require));
            etCity.requestFocus();
            return false;
        }
        else if(etLandMark.getText().toString().matches(""))
        {
            etLandMark.setError(getResources().getString(R.string.str_require));
            etLandMark.requestFocus();
            return false;
        }


        return true;
    }

    @Override
    public void onResponse(int apiId, BaseModel baseModel) {
        hideLoading(findViewById(R.id.progressBar));
        switch (apiId)
        {
            case Constatnts.API_ADDRESS_ADD:
                if(baseModel.status)
                {
                    showToast("Add Address Successfully");
                    finish();
                }
                else
                {
                    showToast("Error in add address");
                }
                break;
            case Constatnts.API_ADDRESS_UPDATE:
                if(baseModel.status)
                {
                    showToast("Update Address Successfully");
                    finish();
                }
                else
                {
                    showToast("Error in update address");
                }
                break;
            case Constatnts.API_ADDRESS_DELETE:
                if(baseModel.status)
                {
                    showToast("Delete Address Successfully");
                    finish();
                }
                else
                {
                    showToast("Error in delete address");
                }
                break;
        }
    }
}
