package com.spiderdevelopers.rasna.res_models;

import com.google.gson.Gson;

/**
 * Created by Ajay parekh on 08-01-2018.
 */

public class LoginRes extends BaseModel {

    public User user;
    public String access_token;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
