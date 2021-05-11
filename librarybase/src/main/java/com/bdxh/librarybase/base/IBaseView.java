package com.bdxh.librarybase.base;

//view 初始化
public interface IBaseView {
    //界面参数传递
    void initBundle();

    //初始化数据
    void initData();

    //初始化观察者
    void initViewObservable();

    //空数据点击网络加载
    void onReload();

}
