package com.spiderdevelopers.rasna.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.activities.OrderDetailActivity;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.res_models.OrderListRes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ajay parekh on 21-12-2017.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    Context context;
    private final ArrayList<OrderListRes.Order> orders;
    public OrderHistoryAdapter(ArrayList<OrderListRes.Order> orders) {
        this.orders=orders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvOrderNo.setText(orders.get(position).id+"");

        try {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dt=simpleDateFormat.parse(orders.get(position).time_stamp);

                SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd MMM yyyy, hh:mm a");
                holder.tvOrderDateTime.setText(simpleDateFormat1.format(dt));
        }catch (Exception e)
        {
            Log.e("OrderHistoryAdapter","exception -> "+e.toString());
        }

        holder.tvTotal.setText(context.getResources().getString(R.string.str_currency_symbol)+" "+orders.get(position).base_amount);
        holder.tvStatus.setText(Constatnts.getOrderStatus(orders.get(position).status));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderNo,tvOrderDateTime,tvTotal,tvStatus;
        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderNo=itemView.findViewById(R.id.tvOrderNo);
            tvOrderDateTime=itemView.findViewById(R.id.tvOrderDateTime);
            tvTotal=itemView.findViewById(R.id.tvTotal);
            tvStatus=itemView.findViewById(R.id.tvStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, OrderDetailActivity.class);
                    intent.putExtra("order_id",orders.get(getAdapterPosition()).id);
                    context.startActivity(intent);
                }
            });
        }
    }
}
