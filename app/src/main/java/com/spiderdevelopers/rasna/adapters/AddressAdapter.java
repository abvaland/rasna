package com.spiderdevelopers.rasna.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.fragments.Fragment_Address;
import com.spiderdevelopers.rasna.res_models.AddressListRes;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 20-12-2017.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{

    private final ArrayList<AddressListRes.Address> addressList;
    public AddressAdapter(ArrayList<AddressListRes.Address> addressList) {
        this.addressList=addressList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_address,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(Fragment_Address.selectedAddress!=position)
        {
            holder.rbtLabel.setChecked(false);
        }
        else
        {
            holder.rbtLabel.setChecked(true);
        }
        AddressListRes.Address address = addressList.get(position);
        holder.tvAddress.setText(address.house_number+","+address.street+","+address.area+","+address.landmark
                +","+address.city+","+address.pincode);
        holder.tvLabel.setText(address.label);
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton rbtLabel;
        TextView tvAddress,tvLabel;
        public ViewHolder(View itemView) {
            super(itemView);
            rbtLabel=itemView.findViewById(R.id.rbtLabel);
            tvAddress=itemView.findViewById(R.id.tvAddress);
            tvLabel=itemView.findViewById(R.id.tvLabel);

           /* rbtLabel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                    {
                        Fragment_Address.selectedAddress=getAdapterPosition();
                        notifyDataSetChanged();
                    }
                }
            });*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment_Address.selectedAddress=getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
