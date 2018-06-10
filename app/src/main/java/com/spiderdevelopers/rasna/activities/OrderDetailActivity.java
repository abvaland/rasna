package com.spiderdevelopers.rasna.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.adapters.OrderDetailItemAdapter;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.network.UserRepository;
import com.spiderdevelopers.rasna.res_models.BaseModel;
import com.spiderdevelopers.rasna.res_models.OrderDetailRes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderDetailActivity extends BaseActivity implements UserRepository.APIListener {
    private static final String TAG = "OrderDetailActivity";
    private int orderId=0;

    TextView tvOrderId,tvOrderDateTime,tvStatus,tvAddress,tvCGST,tvSGST,tvDeliveryCharges,tvDiscount,tvTotal;
    RecyclerView rvItems;
    LinearLayout llMain;
    private OrderDetailRes orderDetailRes;
    OrderDetailItemAdapter orderDetailItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        initObjects();

        setupToolbar();
        setTitle(getResources().getString(R.string.str_order_detail));
        enableBack();

        bindViews();

        if(!isOnline())
        {
            showNoNetworkDialog();
            return;
        }

        showLoading(findViewById(R.id.progressBar));
        userRepository.orderDetail(this,orderId);
    }

    private void bindViews() {
        llMain=findViewById(R.id.llMain);
        tvOrderId=findViewById(R.id.tvOrderId);
        tvOrderDateTime=findViewById(R.id.tvOrderDateTime);
        tvStatus=findViewById(R.id.tvStatus);
        tvAddress=findViewById(R.id.tvAddress);
        tvCGST=findViewById(R.id.tvCGST);
        tvSGST=findViewById(R.id.tvSGST);
        tvDeliveryCharges=findViewById(R.id.tvDeliveryCharges);
        tvDiscount=findViewById(R.id.tvDiscount);
        tvTotal=findViewById(R.id.tvTotal);
        rvItems=findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initObjects() {
        if(getIntent().hasExtra("order_id"))
        {
            orderId=getIntent().getIntExtra("order_id",0);
            Log.e(TAG,"order_id -> "+orderId);
        }
    }

    @Override
    public void onResponse(int apiId, BaseModel baseModel) {
        hideLoading(findViewById(R.id.progressBar));

         orderDetailRes= (OrderDetailRes) baseModel;
        if(orderDetailRes!=null)
        {
            bindData();
        }

    }

    private void bindData() {
        llMain.setVisibility(View.VISIBLE);

        tvOrderId.setText(getResources().getString(R.string.str_order_on)+" : "+orderDetailRes.id);


        try {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt=simpleDateFormat.parse(orderDetailRes.time_stamp);

            SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd MMM yyyy, hh:mm a");
            tvOrderDateTime.setText(simpleDateFormat1.format(dt));
        }catch (Exception e)
        {
            Log.e("OrderHistoryAdapter","exception -> "+e.toString());
        }
        tvStatus.setText(Constatnts.getOrderStatus(orderDetailRes.order_status));

        tvAddress.setText(orderDetailRes.address.house_number+","+orderDetailRes.address.street
                +","+orderDetailRes.address.area+","+orderDetailRes.address.landmark
                +","+orderDetailRes.address.city+","+orderDetailRes.address.pincode);




        tvDeliveryCharges.setText(" + "+getResources().getString(R.string.str_currency_symbol)+" "+orderDetailRes.delivery_charges);

        float sgst = 0;
        float delivery_charges = 0;
        float cgst = 0;
        float total = 0;
        try {
            total= Float.parseFloat(orderDetailRes.base_amount);
            cgst= Float.parseFloat(orderDetailRes.cgst);
            sgst= Float.parseFloat(orderDetailRes.sgst);
            delivery_charges= Float.parseFloat(orderDetailRes.delivery_charges);
        }
        catch (Exception e)
        {
            Log.e(TAG,"exception -> "+e.toString());
        }
        float diductCgst=(total*cgst)/100;
        float diductSgst=(total*sgst)/100;

        tvCGST.setText(" + "+getResources().getString(R.string.str_currency_symbol)+" "+diductCgst);
        tvSGST.setText(" + "+getResources().getString(R.string.str_currency_symbol)+" "+diductSgst);

        float totalPayable = total + (diductCgst + diductSgst + delivery_charges);

        float discount=0;
        try {
            discount= Float.parseFloat(orderDetailRes.discount_amount);
        }
        catch (Exception e)
        {
            discount=0;
        }
        tvDiscount.setText(" - "+getResources().getString(R.string.str_currency_symbol)+" "+discount);

        totalPayable=totalPayable-discount;

        tvTotal.setText(getResources().getString(R.string.str_currency_symbol)+" "+totalPayable);


        if(orderDetailRes.order_details!=null)
        {
            orderDetailItemAdapter=new OrderDetailItemAdapter(orderDetailRes.order_details);
            rvItems.setAdapter(orderDetailItemAdapter);
        }
    }
}
