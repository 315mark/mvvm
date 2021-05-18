package com.bdxh.module_order;

import com.bdxh.librarybase.base.BaseActivity;
import com.bdxh.module_order.bean.Order;
import com.bdxh.module_order.databinding.ModuleOrderActivityOrderBinding;
import com.bdxh.module_order.model.OrderViewModel;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

public class OrderActivity extends BaseActivity< OrderViewModel , ModuleOrderActivityOrderBinding > {

    private OrderAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.module_order_activity_order;
    }

    @Override
    protected void initView() {

        databind.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databind.recyclerView.setAdapter(mAdapter);
        model.liveData.observe(this, new Observer<PagedList<Order>>() {
            @Override
            public void onChanged(PagedList<Order> data) {
                mAdapter.submitList(data);
            }
        });

    }
}
