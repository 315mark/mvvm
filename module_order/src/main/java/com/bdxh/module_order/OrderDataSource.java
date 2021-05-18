package com.bdxh.module_order;

import android.app.Application;

import com.bdxh.module_order.bean.Order;
import com.uber.autodispose.AutoDisposeConverter;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class OrderDataSource extends DataSource.Factory<Integer, Order> {

    private Application application;

    public OrderDataSource( Application lifecycleOwner) {
        this.application = lifecycleOwner;
    }

    private MutableLiveData<OrderDataSourceFactory> liveData =new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, Order> create() {
        OrderDataSourceFactory mFactory = new OrderDataSourceFactory(application);
        liveData.postValue(mFactory);
        return mFactory;
    }
}
