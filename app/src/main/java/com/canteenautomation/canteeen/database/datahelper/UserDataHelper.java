package com.canteenautomation.canteeen.database.datahelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.canteenautomation.canteeen.database.DataManager;
import com.canteenautomation.canteeen.database.model.UserModel;
import com.canteenautomation.canteeen.sohel.S;

import java.util.ArrayList;

/**
 * Created by CRUD-PC on 10/7/2016.
 */
public class UserDataHelper {
    private static UserDataHelper instance;
    private SQLiteDatabase db;
    private DataManager dm;
    Context cx;

    public UserDataHelper(Context cx) {
        instance = this;
        this.cx = cx;
        dm = new DataManager(cx, DataManager.DATABASE_NAME, null, DataManager.DATABASE_VERSION);
    }

    public static UserDataHelper getInstance() {
        return instance;
    }

    public void open() {
        db = dm.getWritableDatabase();
    }

    public void close() {
      //  db.close();
    }

    public void read() {
        db = dm.getReadableDatabase();
    }

    public void delete(int companyId) {
        open();
        db.delete(UserModel.TABLE_NAME, UserModel.KEY_ID + " = '" + companyId + "'", null);
        close();
    }

    public void deleteAll() {
        open();
        db.delete(UserModel.TABLE_NAME, null, null);
        close();
    }

    private boolean isExist(UserModel userModel) {
        read();
        Cursor cur = db.rawQuery("select * from " + UserModel.TABLE_NAME + " where " + UserModel.KEY_ID + "='" + userModel.getUserId() + "'", null);
        if (cur.moveToFirst()) {
            return true;
        }
        return false;
    }
 /* + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +


    KEY_education + " text, " +
    KEY_timing + " text, " +
    KEY_aboutYourself + " text, " +
    KEY_createdDate + " text " +*/
    public  void insertData(UserModel userModel) {
        open();
        ContentValues values = new ContentValues();
        values.put(UserModel.KEY_USER_ID, userModel.getUserId());
        values.put(UserModel.KEY_USERNAME, userModel.getUserName());
        values.put(UserModel.KEY_USEREMAIL, userModel.getUserEmail());
        values.put(UserModel.KEY_USERTOKEN, userModel.getUSERTOKEN());

        values.put(UserModel.KEY_USERPHONE, userModel.getUserPhone());


        if (!isExist(userModel)) {
            S.E("insert successfully");
            db.insert(UserModel.TABLE_NAME, null, values);
        } else {
            S.E("update successfully" + userModel.getUserId());
            db.update(UserModel.TABLE_NAME, values, UserModel.KEY_ID + "=" + userModel.getUserId(), null);
        }
        close();
    }

    public ArrayList<UserModel> getList() {
        ArrayList<UserModel> userItem = new ArrayList<UserModel>();
        read();
       // Cursor cursor = db.rawQuery("select * from UserModel", null);

        Cursor cursor = db.rawQuery("select * from "+UserModel.TABLE_NAME, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            do {
                UserModel taxiModel = new UserModel();
                taxiModel.setUserId(cursor.getString(cursor.getColumnIndex(UserModel.KEY_USER_ID)));
                taxiModel.setUserName(cursor.getString(cursor.getColumnIndex(UserModel.KEY_USERNAME)));
                taxiModel.setUserEmail(cursor.getString(cursor.getColumnIndex(UserModel.KEY_USEREMAIL)));
               // taxiModel.setPassword(cursor.getString(cursor.getColumnIndex(UserModel.KEY_USERPASSWORD)));
                taxiModel.setUserPhone(cursor.getString(cursor.getColumnIndex(UserModel.KEY_USERPHONE)));
                taxiModel.setUSERTOKEN(cursor.getString(cursor.getColumnIndex(UserModel.KEY_USERTOKEN)));
              //  taxiModel.setUserType(cursor.getString(cursor.getColumnIndex(UserModel.KEY_USERType)));
                userItem.add(taxiModel);
            } while ((cursor.moveToPrevious()));
            cursor.close();
        }
        close();
        return userItem;
    }
}