package com.bdxh.librarybase.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public interface ItemType {

   int getItemCount();

   int getLayoutId();

   RecyclerView.ViewHolder getViewHolder(View holder);

   void bindViewHolder(RecyclerView.ViewHolder holder , int index);

}
