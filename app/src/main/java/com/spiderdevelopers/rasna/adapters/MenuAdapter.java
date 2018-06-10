package com.spiderdevelopers.rasna.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.res_models.MenuData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 17-12-2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    MenuItemClickListener listener;
    ArrayList<MenuData> categories;
    Context mContext;
    public MenuAdapter(MenuItemClickListener listener, ArrayList<MenuData> categories) {
        this.listener = listener;
        this.categories=categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_menu_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MenuData menuData = categories.get(position);
        Picasso.with(mContext).load(menuData.image_path).into(holder.imgMenu);
        holder.tvMenuName.setText(menuData.name);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMenu;
        TextView tvMenuName;
        public ViewHolder(View itemView) {
            super(itemView);
            imgMenu=itemView.findViewById(R.id.imgMenu);
            tvMenuName=itemView.findViewById(R.id.tvMenuName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onMenuClick(categories.get(getAdapterPosition()));
                }
            });
        }
    }
    public interface MenuItemClickListener
    {
        public void onMenuClick(MenuData menuData);
    }
}
