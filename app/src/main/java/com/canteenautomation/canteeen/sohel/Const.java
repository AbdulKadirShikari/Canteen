package com.canteenautomation.canteeen.sohel;



public class Const {

    public interface URL {
        String HOST_URL = "http://placementsadda.com/Canteen/";
        String SIGNUP_URL = HOST_URL + "SignUp";
        String LOGIN_URL = HOST_URL + "Login";
        String MENU_ITEM = HOST_URL + "getAllMenuItem";
        String IMAGE_URL = HOST_URL + "uploads/itemImage/";
        String PLACE_ORDER = HOST_URL + "orderInsert";
        String ORDER_HISTORY = HOST_URL + "getOrderHistory";
        String OFFER_BANNER = HOST_URL + "getBanner";
        String UPDATE_FCM=HOST_URL +"updateFcm";
    }
}