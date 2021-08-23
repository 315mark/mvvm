package com.example.moudle_room;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.moudle_room.databinding.ModuleRoomCellBinding;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class StudentPageAdapter extends BaseListAdapter<Student, StudentPageAdapter.StudentViewHolder> {

    private List<Student> mData;

    protected StudentPageAdapter() {
        super(diffCallback);
        this.mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ModuleRoomCellBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.module_room_cell, parent, false);
        return new StudentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        ModuleRoomCellBinding binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setModel(mData.get(position));
        binding.executePendingBindings();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        ModuleRoomCellBinding binding;

        StudentViewHolder(ModuleRoomCellBinding binding) {
           super(binding.getRoot());
           this.binding = binding;
        }
    }

    private static final DiffUtil.ItemCallback<Student> diffCallback = new DiffUtil.ItemCallback<Student>() {
         @Override
         public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
             return oldItem.id == newItem.id;
         }

         @Override
         public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
             return oldItem.studentNumber == newItem.studentNumber;
         }
     };
}
