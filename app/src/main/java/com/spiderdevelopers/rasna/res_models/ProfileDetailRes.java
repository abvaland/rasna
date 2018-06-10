package com.spiderdevelopers.rasna.res_models;

import com.google.gson.Gson;

/**
 * Created by Ajay parekh on 28-01-2018.
 */

public class ProfileDetailRes extends BaseModel {
    public int id;
    public String first_name;
    public String mobile_number;
    public String last_name;
    public String alternate_number;
    public String push_token;
    public String device_type;
    public String email;
    public String password;
    public String remember_token;
    public String api_token;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
