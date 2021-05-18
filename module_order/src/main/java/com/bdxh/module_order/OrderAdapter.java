package com.bdxh.module_order;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bdxh.module_order.bean.Order;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends PagedListAdapter<Order, OrderAdapter.OrderViewHolder> {

    protected OrderAdapter() {
        super(mDiffCallback);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        // 只显示6行
        int parentHeight = parent.getHeight();
        ViewGroup.LayoutParams lp = itemView.getLayoutParams();
        lp.height = parentHeight / 6;
        itemView.setLayoutParams(lp);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.item.setText(String.valueOf(getItem(position)));
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        private final TextView item;

        private OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(android.R.id.text1);
        }
    }

    private static final DiffUtil.ItemCallback<Order> mDiffCallback = new DiffUtil.ItemCallback<Order>() {
        @Override
        public boolean areItemsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
            return oldItem.equals(newItem);
        }
    };

}
