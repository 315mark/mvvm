package com.bdxh.module_base.config;

import android.app.Application;

public interface IBaseModule {

    //初始化组件 此处接口可传入priority 优先级值 1-9

    void init(Application application);
}
