package com.spiderdevelopers.rasna.extras;

/**
 * Created by Ajay parekh on 10-12-2017.
 */

public class Constatnts {
    public static final int FRAGMENT_REST_LIST = 1;
    public static final int FRAGMENT_MENU = 2;
    public static final int FRAGMENT_ITEM = 3;
    public static final int FRAGMENT_CART = 4;
    public static final int FRAGMENT_ADDRESS = 5;
    public static final int FRAGMENT_ORDER_SUMMERY = 6;
    public static final int FRAGMENT_PAYMENT = 7;
    public static final int FRAGMENT_ORDER_HISTORY = 8;
    public static final int FRAGMENT_PROFILE = 9;
    public static final int FRAGMENT_NOTIFICATION = 10;
    public static final int FRAGMENT_ITEM_CUSTOMIZATION = 11;


    public static final int API_LOGIN = 101;
    public static final int API_MENU = 102;
    public static final int API_PRODUCT = 103;
    public static final int API_PLACE_ORDER = 104;
    public static final int API_REGISTER = 105;
    public static final int API_ORDER_SUMMARY = 106;
    public static final int API_ORDER_LIST = 107;
    public static final int API_CUSTOMIZATION = 108;
    public static final int API_PROFILE_DETAIL = 109;
    public static final int API_PROFILE_UPDATE = 110;
    public static final int API_ADDRESS_LIST = 111;
    public static final int API_ADDRESS_ADD = 112;
    public static final int API_ADDRESS_UPDATE = 113;
    public static final int API_ADDRESS_DELETE = 114;
    public static final int API_ORDER_DETAIL = 115;




    public static final String APIKEY = "APIKEY";
    public static final String LOGIN_ID = "LOGIN_ID";
    public static final String NAME = "NAME";
    public static final String MOBILE = "MOBILE";
    public static final String EMAIL = "EMAIL";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String CITY_ID = "CITY_ID";
    public static final String AREA_ID = "AREA_ID";
    public static final String IMAGE = "Image";
    public static final String CART_PREF = "CART_PREF";
    public static String FCMToken="FCMToken";
    public static final String PASSWORD = "PASSWORD";


    public static String getOrderStatus(int status)
    {
        switch (status)
        {
            default:
                return "PLACED";
            case 1:
                return "PLACED";
            case 2:
                return "IN_PROGRACESS";
            case 3:
                return "IN_TRANSIT";
            case 4:
                return "DELIVERD";
            case 5:
                return "CANCELLED";
            case 6:
                return "PICKUP_READY";

        }
    }
}
