package com.spiderdevelopers.rasna.req_models;

import com.google.gson.Gson;

/**
 * Created by Ajay parekh on 10-01-2018.
 */

public class RegisterReq {
    public String first_name;
    public String last_name;
    public String mobile_no;
    public String email_id;
    public String password;

    @Override
    public String toString() {
     return new Gson().toJson(this);
    }
}
