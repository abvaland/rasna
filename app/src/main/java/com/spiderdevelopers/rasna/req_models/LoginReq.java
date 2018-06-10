package com.spiderdevelopers.rasna.req_models;

import com.google.gson.Gson;

/**
 * Created by Ajay parekh on 08-01-2018.
 */

public class LoginReq {
    public String username;
    public String password;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
