package com.spiderdevelopers.rasna.res_models;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 08-01-2018.
 */

public class ProductRes extends BaseModel{
    public ArrayList<ProductData> menu_items;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
