package com.spiderdevelopers.rasna.res_models;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 28-01-2018.
 */

public class CustomizationsRes extends BaseModel {
   public ArrayList<Customization> customizations;

    public class Customization {
        public int id;
        public String item_name;
        public String price;
        public boolean isSelected;

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
