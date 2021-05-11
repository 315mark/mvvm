package com.bdxh.module_base.service;

import androidx.fragment.app.Fragment;

public interface IOrderService {
    String taskname = "IOrderService 作用于跨组件调起Fragment";

    Fragment provideInstance();
}
