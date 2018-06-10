package com.spiderdevelopers.rasna.res_models;

import com.google.gson.Gson;

/**
 * Created by Ajay parekh on 27-01-2018.
 */

public class OrderSummaryRes extends BaseModel {

    public String CGST;
    public String SGST;
    public String DELIVERY_CHARGES;

    public String getCGST() {
        return CGST;
    }

    public void setCGST(String CGST) {
        this.CGST = CGST;
    }

    public String getSGST() {
        return SGST;
    }

    public void setSGST(String SGST) {
        this.SGST = SGST;
    }

    public String getDELIVERY_CHARGES() {
        return DELIVERY_CHARGES;
    }

    public void setDELIVERY_CHARGES(String DELIVERY_CHARGES) {
        this.DELIVERY_CHARGES = DELIVERY_CHARGES;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
