package com.canteenautomation.canteeen.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canteenautomation.canteeen.ItemHistory;
import com.canteenautomation.canteeen.Model.ItemHistoryModel;
import com.canteenautomation.canteeen.Model.ItemListModel;
import com.canteenautomation.canteeen.R;

import java.util.List;

public class ItemHistoryAdapter extends RecyclerView.Adapter<ItemHistoryAdapter.MyViewHolder> {
    public ItemHistoryAdapter(Context context, List<ItemHistoryModel> models) {
        mContext = context;
        mModels = models;
    }

    Context mContext;
    List<ItemHistoryModel> mModels;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final ItemHistoryModel itemHistoryModel = mModels.get(i);
        myViewHolder.Order_id.setText(itemHistoryModel.order_id);
        myViewHolder.date.setText(itemHistoryModel.date);
        myViewHolder.prize.setText(itemHistoryModel.prize);
        myViewHolder.response.setText(itemHistoryModel.response);
        myViewHolder.item_history_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ItemHistory)mContext).SetDiloag(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Order_id, date, prize, response;
        LinearLayout item_history_linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Order_id = itemView.findViewById(R.id.order_id);
            date = itemView.findViewById(R.id.date);
            prize = itemView.findViewById(R.id.prize);
            response = itemView.findViewById(R.id.response);
            item_history_linearLayout = itemView.findViewById(R.id.item_history_linearLayout);

        }
    }
}
