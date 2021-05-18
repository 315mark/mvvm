package com.bdxh.librarybase.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public abstract class BaseFragment<VD extends ViewDataBinding> extends Fragment implements IBaseView {

    protected VD binding ;
    protected AppCompatActivity mActivity;
    private View layout;
    private boolean isNavigationViewInit = false; // 记录是否已经初始化过一次视图
    protected View lastView = null; // 记录上次创建的view

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //界面初始化传递数据
        initBundle();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //fragment 的view 已经创建则不再创建
        if (lastView == null) {
            binding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
            binding.setLifecycleOwner(this);
            lastView = binding.getRoot();
        }
        return lastView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (isNavigationViewInit ) { //初始化的视图不在初始化
            super.onViewCreated(view, savedInstanceState);
            //私有初始化DataBinding 和ViewModel
            initViewModel();
            initView();
            initData();
            //页面事件监听方法 ,用于viewmodel到 view层的注册
            initViewObservable();
        }
        isNavigationViewInit = true ;
    }

    protected abstract void initViewModel();

    protected abstract int getLayoutId();

    protected abstract void initView();

    public <T extends View> T findViewById(@IdRes int id) {
        return layout.findViewById(id);
    }

    @Override
    public void initBundle() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void onReload() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding != null) {
            binding.unbind();
        }
    }
}
