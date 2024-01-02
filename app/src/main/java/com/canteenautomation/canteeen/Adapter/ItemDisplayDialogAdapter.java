package com.canteenautomation.canteeen.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canteenautomation.canteeen.Model.ItemDisplayDialogModel;
import com.canteenautomation.canteeen.R;

import java.util.List;

public class ItemDisplayDialogAdapter extends RecyclerView.Adapter<ItemDisplayDialogAdapter.MyViewHolder> {
    public ItemDisplayDialogAdapter(Context context, List<ItemDisplayDialogModel> models) {
        mContext = context;
        mModels = models;
    }

    Context mContext;
   List<ItemDisplayDialogModel> mModels;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_item_history_detail, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ItemDisplayDialogModel itemDisplayDialogModel=mModels.get(i);
        myViewHolder.item_name.setText(itemDisplayDialogModel.item_name);
        myViewHolder.item_prize.setText(itemDisplayDialogModel.item_prize);
        myViewHolder.item_quantity.setText(itemDisplayDialogModel.item_quantity);

    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_name,item_prize,item_quantity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name=itemView.findViewById(R.id.item_name);
            item_prize=itemView.findViewById(R.id.item_prize);
            item_quantity=itemView.findViewById(R.id.item_quantity);
        }
    }
}
