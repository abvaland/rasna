package com.spiderdevelopers.rasna.res_models;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 28-01-2018.
 */

public class AddressListRes extends BaseModel {
    public ArrayList<Address> addresses;

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
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
