package com.spiderdevelopers.rasna.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.spiderdevelopers.rasna.extras.Constatnts;
import com.spiderdevelopers.rasna.extras.PreferenceManager;
import com.spiderdevelopers.rasna.fragments.FragmentCart;
import com.spiderdevelopers.rasna.res_models.ProductData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 17-12-2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    Context context;
    ArrayList<ProductData> productItem;
    PreferenceManager preferenceManager;
    FragmentCart fragmentCart;
    public CartAdapter(Context context, ArrayList<ProductData> productItem, FragmentCart fragmentCart) {
        this.context = context;
        this.productItem = productItem;
        preferenceManager=PreferenceManager.getInstance(context);
        this.fragmentCart=fragmentCart;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card_item,parent,false);
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

        holder.tvCount.setText(productData.cart_count+"");


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
                holder.tvPrice.setText(context.getResources().getString(R.string.str_currency_symbol)
                        +" "+productData.price+" + "+context.getResources().getString(R.string.str_currency_symbol)+extras);
            }
        }

    }

    @Override
    public int getItemCount() {
        return productItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btnMinus,btnPlus;
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
            btnMinus=itemView.findViewById(R.id.btnMinus);
            btnPlus=itemView.findViewById(R.id.btnPlus);
            tvCount=itemView.findViewById(R.id.tvCount);

            btnPlus.setOnClickListener(this);
            btnMinus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {

                case R.id.btnMinus:
                    productItem.get(getAdapterPosition()).cart_count--;

                    removeProduct(productItem.get(getAdapterPosition()));

                    if(productItem.get(getAdapterPosition()).cart_count==0)
                    {
                        productItem.remove(getAdapterPosition());
                    }
                    notifyItemChanged(getAdapterPosition());

                    fragmentCart.calculateTotal(productItem);
                    break;
                case R.id.btnPlus:
                    productItem.get(getAdapterPosition()).cart_count++;
                    addProduct(productItem.get(getAdapterPosition()));
                    notifyItemChanged(getAdapterPosition());

                    fragmentCart.calculateTotal(productItem);
                    break;


            }
        }
        private void removeProduct(ProductData productData) {
            if(preferenceManager.contains(Constatnts.CART_PREF))
            {
                ArrayList<ProductData> cartProduct=new Gson().fromJson(preferenceManager.getString(Constatnts.CART_PREF),new TypeToken<ArrayList<ProductData>>(){}.getType());

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

                preferenceManager.setString(Constatnts.CART_PREF,new Gson().toJson(cartProduct));
            }
            else
            {
                ArrayList<ProductData> cartProduct=new ArrayList<>();
                cartProduct.add(productData);
                preferenceManager.setString(Constatnts.CART_PREF,new Gson().toJson(cartProduct));
            }
        }
    }
}
