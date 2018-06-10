package com.spiderdevelopers.rasna.res_models;

import com.google.gson.Gson;

/**
 * Created by Ajay parekh on 08-01-2018.
 */

public class OrderPlaceRes extends BaseModel{
    public boolean placed;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
