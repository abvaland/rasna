package com.spiderdevelopers.rasna.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.fragments.FragmentItemCustom;
import com.spiderdevelopers.rasna.res_models.CustomizationsRes;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 28-01-2018.
 */

public class CustomizeAdapter extends RecyclerView.Adapter<CustomizeAdapter.ViewHolder> {
    private final ArrayList<CustomizationsRes.Customization> customizations;
    private final FragmentItemCustom fragmentItemCustom;
    Context context;
    public CustomizeAdapter(ArrayList<CustomizationsRes.Customization> customizations, FragmentItemCustom fragmentItemCustom) {
        this.customizations=customizations;
        this.fragmentItemCustom=fragmentItemCustom;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_customizable,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.checkbox.setText(customizations.get(position).item_name+"   + "+context.getResources().getString(R.string.str_currency_symbol)+customizations.get(position).price);
    }

    @Override
    public int getItemCount() {
        return customizations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;
        public ViewHolder(View itemView) {
            super(itemView);
            checkbox=itemView.findViewById(R.id.checkbox);

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                    {
                        customizations.get(getAdapterPosition()).isSelected=true;
                        fragmentItemCustom.calculateTotal();
                    }
                    else
                    {
                        customizations.get(getAdapterPosition()).isSelected=false;
                        fragmentItemCustom.calculateTotal();
                    }
                }
            });
        }
    }
}
