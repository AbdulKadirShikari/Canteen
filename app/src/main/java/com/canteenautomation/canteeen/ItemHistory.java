package com.canteenautomation.canteeen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.canteenautomation.canteeen.Adapter.ItemDisplayDialogAdapter;
import com.canteenautomation.canteeen.Adapter.ItemHistoryAdapter;
import com.canteenautomation.canteeen.Model.ItemDisplayDialogModel;
import com.canteenautomation.canteeen.Model.ItemHistoryModel;
import com.canteenautomation.canteeen.Services.Config;
import com.canteenautomation.canteeen.database.datahelper.UserDataHelper;
import com.canteenautomation.canteeen.database.model.UserModel;
import com.canteenautomation.canteeen.sohel.Const;
import com.canteenautomation.canteeen.sohel.S;
import com.canteenautomation.canteeen.sohel.SavedData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemHistory extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ItemHistoryModel> listByIDmodels = new ArrayList<>();
    ItemHistoryAdapter activityAdapter;
    private Toolbar mToolbar;
    private String Order_id,quantity,date,prize,confirm_order;
    private String USER_ID,itemName,itemPrice;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String action;
    private String notificationTitle;
    private String msg;
    Activity mActivity =ItemHistory.this;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            S.I_clear(ItemHistory.this,ItemListActivity.class,null);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_history);
        mToolbar = findViewById(R.id.toolbar);
        UserModel userModel;
        userModel = UserDataHelper.getInstance().getList().get(0);
        USER_ID = userModel.getUserId();
        mToolbar.setTitle("Order History");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_key);
        recyclerView = findViewById(R.id.history_recycler_view);
        activityAdapter = new ItemHistoryAdapter(this,listByIDmodels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(activityAdapter);
        mRegistrationBroadcastReceiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                S.E("Broadcast");
                if (!isFinishing()&&active) {
                    if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                        SavedData.saveNotificationRequest("no");
                        String message = intent.getStringExtra("message");
                        S.E("PUSH_NOTIFICATIONmsg" + message);
                        try {
                            SavedData.saveNotificationRequest("no");
                            JSONObject jsonObject = new JSONObject(message);
                            JSONObject data = jsonObject.getJSONObject("data");
                            action = data.getString("action");
                            notificationTitle = data.getString("title");
                            JSONObject masge = data.getJSONObject("message");
                            msg = masge.getString("msg");
                            S.E("msg::" + msg);
                            S.E("action::" + action);
                            S.E("notificationTitle::" + notificationTitle);

                            AlertDialog dialog = new AlertDialog.Builder(mActivity)
                                    .setTitle(notificationTitle)
                                    .setMessage(msg)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            listByIDmodels.clear();
                                            ItemHistoryList();
                                            dialog.dismiss();
                                        }
                                    }).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));


        ItemHistoryList();
    }

    private void ItemHistoryList() {
        {
           /* final Dialog progress = S.initProgressDialog(ItemListActivity.this);
            progress.show();*/
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.ORDER_HISTORY, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    S.E("ItemHistory" + response);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        if (jsonObject.getInt("status") == 200 && jsonObject.getString("message").equals("success")) {
                            JSONArray data = jsonObject.getJSONArray("data");

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject userData = data.getJSONObject(i);
                                Order_id=userData.getString("id");
                                date=userData.getString("createDate");
                                prize=userData.getString("total_price");
                                confirm_order=userData.getString("confirm_order");
                                S.E("id::"+Order_id);
                                S.E("prize::"+prize);
                                S.E("confirm_order::"+confirm_order);
                                ItemHistoryModel itemHistoryModel=new ItemHistoryModel();
                                itemHistoryModel.order_id="Order Number-: "+Order_id;
                                itemHistoryModel.date=date;
                                itemHistoryModel.prize="Rs."+prize+"/-";
                                JSONArray items=userData.getJSONArray("items");
                                S.E("items_____Array"+items);
                                List<ItemDisplayDialogModel> models=new ArrayList<>();
                                for(int j=0;j<items.length();j++)
                                {
                                    JSONObject itemdata=items.getJSONObject(j);
                                    itemName =itemdata.getString("itemName");
                                    quantity =itemdata.getString("quantity");
                                    itemPrice =itemdata.getString("itemPrice");
                                    ItemDisplayDialogModel dialogModel=new ItemDisplayDialogModel();
                                    dialogModel.item_quantity="Quantity-: "+quantity;
                                    dialogModel.item_name=itemName;
                                    dialogModel.item_prize="Rs "+itemPrice+"/-";
                                    S.E("itemName::"+itemName+"::quantity::"+quantity
                                            +"::itemPrice::"+itemPrice);
                                    models.add(dialogModel);
                                }
                                itemHistoryModel.mModels=models;
                                if(confirm_order.equals("0")){
                                    itemHistoryModel.response="Pending";
                                }else {
                                    itemHistoryModel.response="Accepted";
                                }

                                listByIDmodels.add(itemHistoryModel);

                            }
                            activityAdapter.notifyDataSetChanged();
                        } else {
                            //progress.dismiss();
                            Toast.makeText(ItemHistory.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ItemHistory", "onErrorResponse: " + error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", USER_ID);

                    S.E("ItemHistory param" + params);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(ItemHistory.this);
            requestQueue.add(stringRequest);

        }
    }

    public void SetDiloag(int i) {
        ItemHistoryModel itemHistoryModel =listByIDmodels.get(i);
       List<ItemDisplayDialogModel> displayDialogModels =itemHistoryModel.mModels;
        final Dialog item_detail = new Dialog(ItemHistory.this);
        item_detail.setContentView(R.layout.dialog_item_history);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(item_detail.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        item_detail.getWindow().setAttributes(lp);
        item_detail.show();
        ItemDisplayDialogAdapter adapter;
        ImageView cancel_btn=item_detail.findViewById(R.id.cancel_btn);
        RecyclerView recyclerView=item_detail.findViewById(R.id.item_by_id_recyclerview);
        adapter = new ItemDisplayDialogAdapter(this, displayDialogModels);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_detail.dismiss();
            }
        });
        adapter.notifyDataSetChanged();
    }
    static boolean active = false;

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }
}
