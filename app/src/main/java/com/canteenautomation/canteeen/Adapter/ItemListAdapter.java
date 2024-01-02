package com.canteenautomation.canteeen.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.canteenautomation.canteeen.ItemListActivity;
import com.canteenautomation.canteeen.ItemSummary;
import com.canteenautomation.canteeen.Model.ItemListModel;
import com.canteenautomation.canteeen.R;
import com.canteenautomation.canteeen.sohel.S;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {
    Context mContext;
    List<ItemListModel> mModels;
    String fooditemID=" ";
    int j = 0;

    public ItemListAdapter(Context context, List<ItemListModel> models) {
        mContext = context;
        mModels = models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate
                (R.layout.food_item_discription, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        final ItemListModel model = mModels.get(i);
        myViewHolder.textViewItemPrice.setText(model.itemPrice);
        myViewHolder.textViewItemName.setText(model.itemName);
        Picasso.with(mContext).load(model.imageurl).into(myViewHolder.itemListImage);
        myViewHolder.add_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.E("mModels.get(i).quantity---"+mModels.get(i).quantity);
                ((ItemListActivity)mContext).getSelectedItem(i,mModels.get(i).quantity);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName, textViewItemPrice, no_of_item;
        ImageView itemListImage;
        Button add_item_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemPrice = itemView.findViewById(R.id.textViewItemPrice);
            itemListImage = itemView.findViewById(R.id.itemListImage);
            add_item_btn = itemView.findViewById(R.id.add_item_btn);
        }
    }
}
