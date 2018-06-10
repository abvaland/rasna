package com.spiderdevelopers.rasna.req_models;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 08-01-2018.
 */

public class OrderReq {
   public String customer_id;
   public int branch_id;
   public int address_id;
   public int type;
   public ArrayList<OrderItem> order_items;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
