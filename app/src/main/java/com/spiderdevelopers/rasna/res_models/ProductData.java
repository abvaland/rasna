package com.spiderdevelopers.rasna.res_models;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Ajay parekh on 08-01-2018.
 */

public class ProductData {
    public int id;
   public String food_category_id;
   public String name;
   public String description;
   public String image_path;
   public String quantity;
   public String unit_id;
   public String taste_id;
   public String price;
   public String is_jain;
   public String is_avaliable;
   public int is_customizable;
   public  int cart_count=0;

   public ArrayList<CustomizationsRes.Customization> customizations;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
