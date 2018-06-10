package com.spiderdevelopers.rasna.res_models;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 27-01-2018.
 */

public class OrderListRes extends BaseModel {
    public ArrayList<Order> orders;

    public class Order {
        public int id;
        public String branch_id;
        public String customer_id;
        public String time_stamp;
        public String address_id;
        public String base_amount;
        public String coupan_code;
        public String discount_amount;
        public String delivery_charges;
        public String cgst;
        public String sgst;
        public int status;
        public String delivery_time_stamp;
        public String type;

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
