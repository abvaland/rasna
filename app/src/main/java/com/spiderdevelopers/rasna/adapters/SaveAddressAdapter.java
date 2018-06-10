package com.spiderdevelopers.rasna.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.activities.ManageAddressActivity;
import com.spiderdevelopers.rasna.res_models.AddressListRes;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 28-01-2018.
 */

public class SaveAddressAdapter extends RecyclerView.Adapter<SaveAddressAdapter.ViewHolder> {
    private final ArrayList<AddressListRes.Address> addressList;
    private final ManageAddressActivity activity;
    public SaveAddressAdapter(ArrayList<AddressListRes.Address> addressList, ManageAddressActivity manageAddressActivity) {
        this.addressList = addressList;
        this.activity=manageAddressActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_save_address,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
        TextView tvAddress,tvLabel;
        ImageView imgEdit;
        public ViewHolder(View itemView) {
            super(itemView);
            tvLabel=itemView.findViewById(R.id.tvLabel);
            tvAddress=itemView.findViewById(R.id.tvAddress);
            imgEdit=itemView.findViewById(R.id.imgEdit);

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.editAddress(getAdapterPosition());
                }
            });
        }
    }
}
