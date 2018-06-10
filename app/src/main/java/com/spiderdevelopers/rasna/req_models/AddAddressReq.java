package com.spiderdevelopers.rasna.req_models;

import com.google.gson.Gson;

/**
 * Created by Ajay parekh on 28-01-2018.
 */

public class AddAddressReq {
    public String id;
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
