package com.canteenautomation.canteeen.Services;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.canteenautomation.canteeen.database.datahelper.UserDataHelper;
import com.canteenautomation.canteeen.database.model.UserModel;
import com.canteenautomation.canteeen.sohel.Const;
import com.canteenautomation.canteeen.sohel.S;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OnTokenRefresh extends FirebaseInstanceIdService {
    String tokenString;
    private String USER_ID;


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        tokenString = FirebaseInstanceId.getInstance().getToken();
        Log.e("HELLO", "MyTokenServies::" + tokenString);
        UserModel userModel = UserDataHelper.getInstance().getList().get(0);
        USER_ID = userModel.getUserId();
        UpdateToken();
    }

    private void UpdateToken() {
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.UPDATE_FCM, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    S.E("MyTokenServies" + response);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        if (jsonObject.getInt("status") == 200 &&
                                jsonObject.getString("message").equals("success")) {
                            Toast.makeText(OnTokenRefresh.this, "Refresh", Toast.LENGTH_SHORT).show();


                        } else {
                            //  progress.dismiss();
                            Toast.makeText(OnTokenRefresh.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("MyTokenServies", "onErrorResponse: " + error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();
                    if(tokenString==null){
                        UserModel userModel = UserDataHelper.getInstance().getList().get(0);
                        tokenString = userModel.getUSERTOKEN();
                    }
                    params.put("fcm_id", tokenString);
                    params.put("user_id", USER_ID);

                    S.E("MyTokenServies" + params);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(OnTokenRefresh.this);
            requestQueue.add(stringRequest);

        }
    }
}
