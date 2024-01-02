package com.canteenautomation.canteeen.Model;

import java.io.Serializable;

public class ItemListModel implements Serializable {
    public String itemName;
    public String itemPrice;
    public String imageurl;
    public String itemDescription;
    public String id;
    public String USER_ID;
    public String selected="notSelected";

    public int quantity=0;
}
