package com.bdxh.librarybase.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class AdapterDiffCallBack extends DiffUtil.ItemCallback<DiffUtilEntity> {


    @Override
    public boolean areItemsTheSame(@NonNull DiffUtilEntity oldItem, @NonNull DiffUtilEntity newItem) {

        // 返回两个item是否相同
        // 例如：此处两个item的数据实体是User类，所以以id作为两个item是否相同的依据
        // 即此处返回两个user的id是否相同
        // return mOldList[oldItemPosition].id === mNewList[newItemPosition].id

        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull DiffUtilEntity oldItem, @NonNull DiffUtilEntity newItem) {

        // 是同一个item时 即areItemsTheSame返回true ，仍需判断两个item的内容是否相同
        // 此处以User的age作为两个item内容是否相同的依据
        // 即返回两个user的age是否相同
        // return mOldList[oldItemPosition].content.equals(mNewList[newItemPosition].content)

        return oldItem.getContent().equals(newItem.getContent()) &&
               oldItem.getDate().equals(newItem.getDate()) &&
               oldItem.getTitle().equals(newItem.getTitle()) ;
    }

    /**
     * 可选实现
     * 如果需要精确修改某一个view中的内容，请实现此方法。
     * 如果不实现此方法，或者返回null，将会直接刷新整个item。
     *
     * @param oldItem Old data
     * @param newItem New data
     * @return Payload info. if return null, the entire item will be refreshed.
     */
    @Override
    public Object getChangePayload(@NonNull DiffUtilEntity oldItem, @NonNull DiffUtilEntity newItem) {
        return null;
    }
}
