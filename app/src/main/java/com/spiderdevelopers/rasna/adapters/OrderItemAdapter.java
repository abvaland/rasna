package com.spiderdevelopers.rasna.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.res_models.ProductData;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 20-12-2017.
 */

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder>{
    ArrayList<ProductData> cartProduct;
    Context context;
    public OrderItemAdapter(ArrayList<ProductData> cartProduct) {
       this.cartProduct=cartProduct;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductData productData = cartProduct.get(position);
        holder.tvItemName.setText(productData.name);

        try {
            float price= Float.parseFloat(productData.price);
            float total=price*productData.cart_count;
            holder.tvPrice.setText(context.getResources().getString(R.string.str_currency_symbol)+" "+total);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if(productData.customizations!=null)
        {
            float extras=0;
            for (int i=0;i<productData.customizations.size();i++)
            {
                try {
                    float price= Float.parseFloat(productData.customizations.get(i).price);
                    extras+=price;
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            if(extras>0)
            {
                float price= Float.parseFloat(productData.price);
                float total=(price+extras)*productData.cart_count;
                holder.tvPrice.setText(context.getResources().getString(R.string.str_currency_symbol)+" "+total);
            }
        }
    }

    @Override
    public int getItemCount() {
        return cartProduct.size();
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
