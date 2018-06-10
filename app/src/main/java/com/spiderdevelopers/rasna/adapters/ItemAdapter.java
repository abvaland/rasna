package com.spiderdevelopers.rasna.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spiderdevelopers.rasna.R;
import com.spiderdevelopers.rasna.activities.HomeActivity;
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.extras.PreferenceManager;
import com.spiderdevelopers.rasna.fragments.FragmentItem;
import com.spiderdevelopers.rasna.res_models.ProductData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 17-12-2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private static final String TAG = "ItemAdapter";
    Context context;
    ArrayList<ProductData> productItem;
    PreferenceManager preferenceManager;
    public ItemAdapter(Context context, ArrayList<ProductData> menu_items) {
        this.context = context;
        this.productItem=menu_items;
        preferenceManager=PreferenceManager.getInstance(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductData productData = productItem.get(position);

        Picasso.with(context).load(productData.image_path).into(holder.imgProduct);
        holder.tvName.setText(productData.name);

        if(productData.is_jain.matches("1"))
            holder.tvJain.setVisibility(View.VISIBLE);
        else
            holder.tvJain.setVisibility(View.GONE);

        holder.tvPrice.setText(context.getResources().getString(R.string.str_currency_symbol)+" "+productData.price);


        if(productData.cart_count>0)
        {
           holder.llCartCount.setVisibility(View.VISIBLE);
           holder.btnAdd.setVisibility(View.GONE);
           holder.tvCount.setText(productData.cart_count+"");
        }
        else
        {
            holder.llCartCount.setVisibility(View.GONE);
            holder.btnAdd.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public int getItemCount() {
        return productItem.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btnMinus,btnPlus,btnAdd;
        TextView tvCount;
        LinearLayout llCartCount;
        ImageView imgProduct;
        TextView tvName,tvSpicy,tvJain,tvPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvJain=itemView.findViewById(R.id.tvJain);
            tvSpicy=itemView.findViewById(R.id.tvSpicy);
            tvName=itemView.findViewById(R.id.tvName);
            imgProduct=itemView.findViewById(R.id.imgProduct);
            llCartCount=itemView.findViewById(R.id.llCartCount);
            btnAdd=itemView.findViewById(R.id.btnAdd);
            btnMinus=itemView.findViewById(R.id.btnMinus);
            btnPlus=itemView.findViewById(R.id.btnPlus);
            tvCount=itemView.findViewById(R.id.tvCount);

            btnAdd.setOnClickListener(this);
            btnPlus.setOnClickListener(this);
            btnMinus.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.btnAdd:
                    if(productItem.get(getAdapterPosition()).is_customizable==1)
                    {
                        Bundle bundle=new Bundle();
                        bundle.putString("product",new Gson().toJson(productItem.get(getAdapterPosition())));
                        ((HomeActivity)context).changeFragment(Constatnts.FRAGMENT_ITEM_CUSTOMIZATION,"",true,false,bundle);
                    }
                    else
                    {
                        productItem.get(getAdapterPosition()).cart_count++;
                        notifyItemChanged(getAdapterPosition());

                        addProduct(productItem.get(getAdapterPosition()));
                    }

                    break;
                case R.id.btnMinus:
                    productItem.get(getAdapterPosition()).cart_count--;
                    notifyItemChanged(getAdapterPosition());
                    removeProduct(productItem.get(getAdapterPosition()));
                    break;
                case R.id.btnPlus:
                    productItem.get(getAdapterPosition()).cart_count++;
                    notifyItemChanged(getAdapterPosition());
                    addProduct(productItem.get(getAdapterPosition()));
                        break;


            }
        }

        private void removeProduct(ProductData productData) {
            if(preferenceManager.contains(Constatnts.CART_PREF))
            {
                ArrayList<ProductData> cartProduct=new Gson().fromJson(preferenceManager.getString(Constatnts.CART_PREF),new TypeToken<ArrayList<ProductData>>(){}.getType());
                Log.e(TAG,"previous cart list ->"+cartProduct);
                for (int i=0;i<cartProduct.size();i++)
                {
                    if(cartProduct.get(i).id==productData.id)
                    {
                        cartProduct.get(i).cart_count--;
                        if(cartProduct.get(i).cart_count==0)
                        {
                            cartProduct.remove(i);
                        }
                    }
                }
                preferenceManager.setString(Constatnts.CART_PREF,new Gson().toJson(cartProduct));
            }
        }

        private void addProduct(ProductData productData) {
            if(preferenceManager.contains(Constatnts.CART_PREF))
            {
                ArrayList<ProductData> cartProduct=new Gson().fromJson(preferenceManager.getString(Constatnts.CART_PREF),new TypeToken<ArrayList<ProductData>>(){}.getType());
                Log.e(TAG,"previous cart list ->"+cartProduct);

                boolean isAvailable=false;
                for (int i=0;i<cartProduct.size();i++)
                {
                    if(cartProduct.get(i).id==productData.id)
                    {
                        cartProduct.get(i).cart_count++;
                        isAvailable=true;
                    }
                }

                if(!isAvailable)
                    cartProduct.add(productData);

                Log.e(TAG,"After added cart list ->"+cartProduct);
                preferenceManager.setString(Constatnts.CART_PREF,new Gson().toJson(cartProduct));
            }
            else
            {
                ArrayList<ProductData> cartProduct=new ArrayList<>();
                cartProduct.add(productData);
                Log.e(TAG,"added cart list ->"+cartProduct);
                preferenceManager.setString(Constatnts.CART_PREF,new Gson().toJson(cartProduct));
            }
        }
    }
}
