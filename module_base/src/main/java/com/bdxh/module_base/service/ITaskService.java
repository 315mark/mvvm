package com.bdxh.module_base.service;

import androidx.fragment.app.Fragment;

public interface ITaskService {

    String taskname = "ITaskService 作用于跨组件调起Fragment";

    Fragment provideInstance();
}
