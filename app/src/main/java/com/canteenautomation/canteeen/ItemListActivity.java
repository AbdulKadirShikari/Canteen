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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.canteenautomation.canteeen.Adapter.ItemListAdapter;
import com.canteenautomation.canteeen.Model.BannnerModel;
import com.canteenautomation.canteeen.Model.ItemListModel;
import com.canteenautomation.canteeen.Model.SelectedModelItem;
import com.canteenautomation.canteeen.Services.Config;
import com.canteenautomation.canteeen.database.datahelper.UserDataHelper;
import com.canteenautomation.canteeen.database.model.UserModel;
import com.canteenautomation.canteeen.sohel.Const;
import com.canteenautomation.canteeen.sohel.Helper;
import com.canteenautomation.canteeen.sohel.JSONParser;
import com.canteenautomation.canteeen.sohel.S;
import com.canteenautomation.canteeen.sohel.SavedData;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ItemListModel> listmodels = new ArrayList<>();
    List<ItemListModel> listmodelsSelected = new ArrayList<>();
    ItemListAdapter activityAdapter;
    Toolbar mToolbar;
    int glCountity = 0;
    int k;
    ImageView mImageView;
    private String USER_ID, itemName, id, itemPrice, itemDescription, itemImage, ids;
    Button btn_add_to_card;
    private HashMap<String, String> Hash_file_maps;
    private List<BannnerModel> sliderArrayList = new ArrayList<>();
    SliderLayout sliderLayout;
    PagerIndicator pagerIndicator;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String action;
    private String notificationTitle;
    private String msg;
    Activity mActivity = ItemListActivity.this;


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final AlertDialog dialog = new AlertDialog.Builder(mActivity)
                    .setTitle("Canteen")
                    .setMessage("Do You want to close app?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            dialog.dismiss();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        UserModel userModel;
        userModel = UserDataHelper.getInstance().getList().get(0);
        USER_ID = userModel.getUserId();
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Item List");
        ;
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_left_arrow_key);
        mImageView = findViewById(R.id.notification);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.I(ItemListActivity.this, ItemHistory.class, null);
            }
        });
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        pagerIndicator = (PagerIndicator) findViewById(R.id.banner_slider_indicator);
        recyclerView = findViewById(R.id.recyclerView);
        btn_add_to_card = findViewById(R.id.btn_add_to_card);
        activityAdapter = new ItemListAdapter(this, listmodels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(activityAdapter);
        btn_add_to_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listmodelsSelected.clear();
                for (int i = 0; i < listmodels.size(); i++) {
                    if (listmodels.get(i).selected.equals("Selected")) {
                        k=listmodels.get(i).quantity;
                        Log.e("checkListITem::" + listmodels.get(i).itemName, " CheckQuanity::" + listmodels.get(i).quantity + " Selected::" + listmodels.get(i).selected);
                        listmodelsSelected.add(listmodels.get(i));
                    }
                }
                if (listmodelsSelected.size() > 0 && k > 0 ) {
                    SelectedModelItem selectedModelItem = new SelectedModelItem();
                    selectedModelItem.mItemListModels = listmodelsSelected;
                    Intent intent = new Intent(ItemListActivity.this, ItemSummary.class);
                    intent.putExtra("listArray", selectedModelItem);
                    startActivity(intent);
                } else {
                    Toast.makeText(ItemListActivity.this, "Please Select some item", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                S.E("Broadcast");
                if(!isFinishing()&&active){
                if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
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
                                        S.I(mActivity, ItemHistory.class, null);
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
        Item_List();
        Imagearray();
    }

    public void Imagearray() {
        S.E("Check By Ani");
        new JSONParser(ItemListActivity.this).parseVollyStringRequest(Const.URL.OFFER_BANNER, 1, S.getParams(), new Helper() {

            @Override
            public void backResponse(String response) {
                S.E("Check By Ani" + response);
                S.E("Check By Ani" + S.getParams());
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    if (jsonObject1.getString("status").equals("200")) {
                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
                        Hash_file_maps = new HashMap<String, String>();
                        Hash_file_maps.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            BannnerModel bannerModel = new BannnerModel();
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            String banner_id = jsonObject11.getString("id");
                            String bannerImage = jsonObject11.getString("bannerImg");
                            //String bannerCreatedDate = jsonObject11.getString("bannerCreatedDate");
                            bannerModel.setBanner_id(banner_id);
                            bannerModel.setBannerImage(bannerImage);
                            // bannerModel.setBannerCreatedDate(bannerCreatedDate);
                            sliderArrayList.add(bannerModel);
                            Hash_file_maps.put(String.valueOf(i), jsonObject11.getString("bannerImg"));
                        }
                        S.E("Hash_file_maps" + Hash_file_maps.size());
                        for (String name : Hash_file_maps.keySet()) {

                            DefaultSliderView textSliderView = new DefaultSliderView(ItemListActivity.this);
                            textSliderView
                                    .description(name)
                                    .image(Hash_file_maps.get(name))
                                    .setScaleType(BaseSliderView.ScaleType.Fit);
                            /*.setOnSliderClickListener(this);*/
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle()
                                    .putString("extra", name);
                            sliderLayout.addSlider(textSliderView);
                        }
                        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                        sliderLayout.setCustomAnimation(new DescriptionAnimation());
                        sliderLayout.setDuration(5000);
                        sliderLayout.setCustomIndicator(pagerIndicator);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    S.E("checkException Here" + e);
                }
            }
        });
    }


    private void Item_List() {
        {
           /* final Dialog progress = S.initProgressDialog(ItemListActivity.this);
            progress.show();*/
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.URL.MENU_ITEM, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    S.E("ItemListActivity" + response);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        if (jsonObject.getInt("status") == 200 && jsonObject.getString("message").equals("success")) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject userData = data.getJSONObject(i);
                                id = userData.getString("id");
                                S.E("itemid" + id);
                                itemName = userData.getString("itemName");
                                itemPrice = userData.getString("itemPrice");
                                itemDescription = userData.getString("itemDescription");
                                itemImage = userData.getString("itemImage");
                                ItemListModel model = new ItemListModel();
                                model.itemName = itemName;
                                model.itemDescription = itemDescription;
                                model.itemPrice = itemPrice;
                                model.id = id;
                                model.USER_ID = USER_ID;
                                model.imageurl = Const.URL.IMAGE_URL + itemImage;
                                listmodels.add(model);
                            }
                            activityAdapter.notifyDataSetChanged();

                        } else {
                            //progress.dismiss();
                            Toast.makeText(ItemListActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ItemListActivity", "onErrorResponse: " + error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", USER_ID);
                    S.E("ItemListActivity" + params);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(ItemListActivity.this);
            requestQueue.add(stringRequest);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.Action_logout) {
            UserDataHelper.getInstance().deleteAll();
            S.I_clear(ItemListActivity.this, LoginSignUpActivity.class, null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void getSelectedItem(final int i, int count) {

        final ItemListModel itemListModel = listmodels.get(i);
        glCountity = count;
        final Dialog dialog = new Dialog(ItemListActivity.this);
        dialog.setContentView(R.layout.dialog_add_item);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        Button btn_done = dialog.findViewById(R.id.btn_done);
        ImageView add_item = dialog.findViewById(R.id.add_item);
        ImageView cancel_btn_dialog = dialog.findViewById(R.id.cancel_btn_dialog);
        ImageView minus_item = dialog.findViewById(R.id.remove_item);
        final TextView no_of_item = dialog.findViewById(R.id.no_of_item);
        cancel_btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        no_of_item.setText("" + glCountity);

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (glCountity < 10) {
                    ++glCountity;
                    no_of_item.setText("" + glCountity);
                }
            }
        });
        minus_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (glCountity > 1) {
                    --glCountity;
                    no_of_item.setText("" + glCountity);
                }

            }
        });


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListModel.selected = "Selected";
                itemListModel.quantity = glCountity;
                listmodels.set(i, itemListModel);
                dialog.dismiss();
            }
        });

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



