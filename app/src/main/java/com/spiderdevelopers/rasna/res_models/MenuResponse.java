package com.spiderdevelopers.rasna.res_models;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 08-01-2018.
 */

public class MenuResponse extends BaseModel {
    public ArrayList<MenuData> categories;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
