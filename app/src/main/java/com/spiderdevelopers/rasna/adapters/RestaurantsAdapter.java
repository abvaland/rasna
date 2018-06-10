package com.spiderdevelopers.rasna.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spiderdevelopers.rasna.R;

/**
 * Created by Ajay parekh on 10-12-2017.
 */

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    RestaurantItemListener listener;

    public RestaurantsAdapter(RestaurantItemListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_rest_item,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position==0)
        {
            //holder.tvRestName.setText("Rasna One");
            holder.img.setImageResource(R.drawable.rasna_1);
        }
        else
        {
            //holder.tvRestName.setText("Rasna Two");
            holder.img.setImageResource(R.drawable.rasna_2);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRestName;
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            tvRestName=itemView.findViewById(R.id.tvRestName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRestaurantClick(getAdapterPosition());
                }
            });
        }
    }
    public interface RestaurantItemListener
    {
        public void onRestaurantClick(int position);
    }
}
