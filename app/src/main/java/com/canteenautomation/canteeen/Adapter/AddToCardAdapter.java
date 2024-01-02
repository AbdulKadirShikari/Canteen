package com.canteenautomation.canteeen.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canteenautomation.canteeen.ItemSummary;
import com.canteenautomation.canteeen.Model.ItemListModel;
import com.canteenautomation.canteeen.R;
import com.canteenautomation.canteeen.sohel.S;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddToCardAdapter extends RecyclerView.Adapter<AddToCardAdapter.MyViewHolder> {
    Context mContext;
    List<ItemListModel> mModels;

    public AddToCardAdapter(Context context, List<ItemListModel> models) {
        mContext = context;
        mModels = models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_addtocard, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final ItemListModel addToCardModel =mModels.get(i);
        myViewHolder.summary_quantity.setText(""+addToCardModel.quantity);
        myViewHolder.Summary_textViewItemName.setText(addToCardModel.itemName);
        S.E("imageurl-----"+addToCardModel.imageurl);
      Picasso.with(mContext).load(addToCardModel.imageurl).into(myViewHolder.Summary_itemListImage);
        myViewHolder.cancel_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                S.E("log i==="+i+"======="+mModels.get(i));
             ((ItemSummary)mContext).modifyList(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Summary_textViewItemName,summary_quantity;
        ImageView Summary_itemListImage,cancel_item;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            summary_quantity=itemView.findViewById(R.id.summary_quantity);
            Summary_textViewItemName=itemView.findViewById(R.id.Summary_textViewItemName);
            Summary_itemListImage=itemView.findViewById(R.id.Summary_itemListImage);
            cancel_item=itemView.findViewById(R.id.cancel_item);
        }
    }
}
