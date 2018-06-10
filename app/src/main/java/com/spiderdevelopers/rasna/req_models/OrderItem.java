package com.spiderdevelopers.rasna.req_models;

import com.google.gson.Gson;

/**
 * Created by Ajay parekh on 08-01-2018.
 */

public class OrderItem {
    public int id;
    public int quantity;
    public String price;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
