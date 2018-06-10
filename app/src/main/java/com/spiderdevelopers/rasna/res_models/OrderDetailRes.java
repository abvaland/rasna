package com.spiderdevelopers.rasna.res_models;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 11-02-2018.
 */

public class OrderDetailRes extends BaseModel {
    public int id;
    public String branch_id;
    public String time_stamp;
    public String base_amount;
    public String discount_amount;
    public String delivery_charges;
    public String cgst;
    public String sgst;
    public int order_status;
    public int delivery_time_stamp;

    public ArrayList<OrderDetails> order_details;

    public class OrderDetails {
        public int id;
        public int quantity;

        public MenuItemData menu_item;

        public ArrayList<Customization> order_customizations;


        public class Customization {
            public int id;
            public String item_name;
            public String price;
            public boolean isSelected;

            @Override
            public String toString() {
                return new Gson().toJson(this);
            }
        }

        public class MenuItemData {
            public String name;
            public String price;
        }
    }

    public Address address;

    public class Address {
        public int id;
        public String customer_id;
        public String label;
        public String house_number;
        public String street;
        public String area;
        public String pincode;
        public String city;
        public String landmark;

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }
}
