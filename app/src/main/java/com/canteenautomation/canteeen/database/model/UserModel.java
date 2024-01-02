package com.canteenautomation.canteeen.database.model;

import android.database.sqlite.SQLiteDatabase;


public class UserModel {
    public static final String TABLE_NAME = "UserModel";
    public static final String KEY_ID = "_id";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USEREMAIL = "userEmail";
    public static final String KEY_USERNAME = "userName";
    public static final String KEY_USERPHONE = "userPhone";
    public static final String KEY_USERTOKEN = "USERTOKEN";
  //  public static final String KEY_USERType = "userType";
    public static void creteTable(SQLiteDatabase db) {
        String CREATE_CLIENTTABLE = "create table " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_USER_ID + " text, " +
                KEY_USERNAME + " text, " +
                KEY_USEREMAIL + " text, " +
                KEY_USERTOKEN + " text, " +
                KEY_USERPHONE + " text " +
                ")";
        db.execSQL(CREATE_CLIENTTABLE);
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
    String userId;
    String userName;
    String userEmail;
    String userPhone;

    public String getUSERTOKEN() {
        return USERTOKEN;
    }

    public void setUSERTOKEN(String USERTOKEN) {
        this.USERTOKEN = USERTOKEN;
    }

    String USERTOKEN;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

}
