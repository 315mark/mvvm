package com.bdxh.librarybase.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.util.List;

public class DiffUtilAdapter extends BaseQuickAdapter<DiffUtilEntity, BaseViewHolder> {
    public static final int ITEM_0_PAYLOAD = 901;

    public DiffUtilAdapter(int layoutResId ,List<DiffUtilEntity> list) {
        super(layoutResId , list);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiffUtilEntity diffUtilEntity) {
        //赋值
    }

    /**
     * 当有 payload info 时，只会执行此方法
     * @param helper   A fully initialized helper.
     * @param item     The item that needs to be displayed.
     * @param payloads payload info.
     */
    @Override
    protected void convert(BaseViewHolder helper, DiffUtilEntity item, List<?> payloads) {
        for (Object p : payloads) {
            int payload = (int) p;
            if (payload == ITEM_0_PAYLOAD){
                //赋值
//                helper.setText(R.id.tweetName, item.getTitle())
//                        .setText(R.id.tweetText, item.getContent());
            }
        }
    }

    // 设置Diff Callback  Activity中调用下面方法
    // 只需要设置一次就行了！建议初始化Adapter的时候就设置好。
    // mAdapter.setDiffCallback(new DiffDemoCallback());

    // 获取新数据
    // List<DiffUtilEntity> newData = getNewList();
    // 设置diff数据（默认就为异步Diff，不需要担心卡顿）
    // mAdapter.setDiffNewData(newData);

    // 第二次改变数据
    // mAdapter.setDiffNewData(newData2);
}
