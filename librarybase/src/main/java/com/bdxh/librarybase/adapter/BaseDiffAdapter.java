package com.bdxh.librarybase.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BaseDiffAdapter extends ListAdapter<DiffUtilEntity, BaseDiffAdapter.DiffViewHolder> {

    protected BaseDiffAdapter(){
        super(new AdapterDiffCallBack());
    }

    @NonNull
    @Override
    public DiffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DiffViewHolder holder, int position){

    }

    public class DiffViewHolder extends RecyclerView.ViewHolder {

        public DiffViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
