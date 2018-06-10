package com.spiderdevelopers.rasna.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.res_models.OrderDetailRes;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 11-02-2018.
 */

public class OrderDetailItemAdapter extends RecyclerView.Adapter<OrderDetailItemAdapter.ViewHolder> {
    private final ArrayList<OrderDetailRes.OrderDetails> order_details;
    Context context;

    public OrderDetailItemAdapter(ArrayList<OrderDetailRes.OrderDetails> order_details) {
        this.order_details=order_details;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderDetailRes.OrderDetails item = order_details.get(position);

        holder.tvItemName.setText(item.menu_item.name);

        try {
            float price= Float.parseFloat(item.menu_item.price);
            float total=price*item.quantity;
            holder.tvPrice.setText(context.getResources().getString(R.string.str_currency_symbol)+" "+total);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if(item.order_customizations!=null)
        {
            float extras=0;
            for (int i=0;i<item.order_customizations.size();i++)
            {
                try {
                    float price= Float.parseFloat(item.order_customizations.get(i).price);
                    extras+=price;
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            if(extras>0)
            {
                float price= Float.parseFloat(item.menu_item.price);
                float total=(price+extras)*item.quantity;
                holder.tvPrice.setText(context.getResources().getString(R.string.str_currency_symbol)+" "+total);
            }
        }
    }

    @Override
    public int getItemCount() {
        return order_details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName,tvPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvItemName=itemView.findViewById(R.id.tvItemName);

        }
    }
}
