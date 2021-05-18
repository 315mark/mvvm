package com.example.moudle_room.holder;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class BaseBindingViewHolder<B> extends RecyclerView.ViewHolder {

    private B binding;

    public BaseBindingViewHolder(View itemView) {
        super(itemView);
    }

    public B getBinding() {
        return binding;
    }

    public void setBinding(B binding) {
        this.binding = binding;
    }
}