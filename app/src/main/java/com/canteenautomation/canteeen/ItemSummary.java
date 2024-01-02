package com.canteenautomation.canteeen;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.canteenautomation.canteeen.Adapter.AddToCardAdapter;
import com.canteenautomation.canteeen.Model.ItemListModel;
import com.canteenautomation.canteeen.Model.SelectedModelItem;
import com.canteenautomation.canteeen.Services.Config;
import com.canteenautomation.canteeen.database.datahelper.UserDataHelper;
import com.canteenautomation.canteeen.database.model.UserModel;
import com.canteenautomation.canteeen.sohel.Const;
import com.canteenautomation.canteeen.sohel.S;
import com.canteenautomation.canteeen.sohel.SavedData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSummary extends AppCompatActivity {
    TextView summary_totalprize;
    Button place_order_btn, modifyOrder;
    RecyclerView mRecyclerView;
    AddToCardAdapter mAdapter;
    List<ItemListModel> listmodels = new ArrayList<>();
    List<ItemListModel> list = new ArrayList<>();
    Toolbar mToolbar;
    public int itemPrice;
    public String Item_id = " ", ItemQuntity = " ";
    SelectedModelItem selectedModelItem;
    public int totalPrize = 0;
    private String USER_ID;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String action;
    private String notificationTitle;
    private String msg;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_summary);
        UserModel userModel;
        userModel = UserDataHelper.getInstance().getList().get(0);
        USER_ID = userModel.getUserId();
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Summary");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectedModelItem = (SelectedModelItem) getIntent().getSerializableExtra("listArray");
        Log.e("SelectedSizde", "" + selectedModelItem.mItemListModels.size());
        list = selectedModelItem.mItemListModels;

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_key);
        place_order_btn = findViewById(R.id.place_order_btn);
        modifyOrder = findViewById(R.id.modifyOrder);
        summary_totalprize = findViewById(R.id.summary_totalprize);
        mRecyclerView = findViewById(R.id.item_summary_recycler_view);
        mAdapter = new AddToCardAdapter(this, listmodels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
       /* mRegistrationBroadcastReceiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                S.E("Broadcast");
                if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    SavedData.saveNotificationRequest("no");
                    String message = intent.getStringExtra("message");

                    S.E("PUSH_NOTIFICATIONmsg"+message);
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
                        final AlertDialog dialog = new AlertDialog.Builder(ItemSummary.this).setTitle(notificationTitle)
                                .setMessage(msg)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                       // S.I_clear(ItemSummary.this,ItemHistory.class,null);
                                        dialog.dismiss();
                                    }
                                })

                                .show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));*/
        ListItem();
    }

    private void ListItem() {
        for (int i = 0; i < selectedModelItem.mItemListModels.size(); i++) {
            ItemListModel item = list.get(i);
            itemPrice = Integer.parseInt(item.itemPrice);
            totalPrize = totalPrize + itemPrice * item.quantity;
            ItemListModel addToCardModel = new ItemListModel();
            addToCardModel.quantity = item.quantity;
            addToCardModel.itemName = item.itemName;
            addToCardModel.imageurl = item.imageurl;
            addToCardModel.itemPrice = item.itemPrice;
            addToCardModel.id = item.id;
            listmodels.add(addToCardModel);
            //  S.E("imageurl" + itemPrice);
        }
        summary_totalprize.setText("Rs " + totalPrize + " /-");

        mAdapter.notifyDataSetChanged();
        modifyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        S.E("totalPrize else::" + totalPrize);
        place_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog = new AlertDialog.Builder(ItemSummary.this).setTitle("Warning")
                        .setMessage("Are you sure want to palce this Order?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                FinalOrder();
                                dialog.dismiss();

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                S.I(ItemSummary.this, ItemListActivity.class, null);
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void FinalOrder() {
        for (int j = 0; j < listmodels.size(); j++) {
            ItemListModel item = listmodels.get(j);
            if (Item_id.equals(" ")) {
                Item_id = item.id;
                ItemQuntity = "" + item.quantity;
            } else {
                Item_id = Item_id + "," + item.id;
                ItemQuntity = ItemQuntity + "," + item.quantity;
                S.E("else---Item_id::" + Item_id + "::---ItemQuntity::" + ItemQuntity);
            }
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.PLACE_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ItemSummary ", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 200 && jsonObject.getString("message").equals("success")) {
                        Toast.makeText(ItemSummary.this, "Thank You For Order", Toast.LENGTH_SHORT).show();
                        S.I_clear(ItemSummary.this, ItemListActivity.class, null);
                    } else {
                        Toast.makeText(ItemSummary.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ItemSummary ", "onErrorResponse: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", USER_ID);
                params.put("item_id", Item_id);
                params.put("quantity", ItemQuntity);
                Log.e("ItemSummary", "getParams: " + params);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ItemSummary.this);
        requestQueue.add(stringRequest);


    }


    public void modifyList(int i) {
        listmodels.remove(i);
        totalPrize = 0;

        for (int j = 0; j < listmodels.size(); j++) {

            ItemListModel item = listmodels.get(j);
            itemPrice = Integer.parseInt(item.itemPrice);
            S.E("itemPrice::" + itemPrice + " ItemQuantity::" + item.quantity);
            S.E("itemPrice*item.quantity::" + itemPrice * item.quantity);
            totalPrize = totalPrize + itemPrice * item.quantity;
            S.E("totalPrize::" + totalPrize);
        }
        if (listmodels.size() == 0) {
            Toast.makeText(this, "Please Select some item", Toast.LENGTH_SHORT).show();
            S.I_clear(ItemSummary.this, ItemListActivity.class, null);
        }
        summary_totalprize.setText("Rs " + totalPrize + " /-");
        mAdapter.notifyDataSetChanged();

    }
}
