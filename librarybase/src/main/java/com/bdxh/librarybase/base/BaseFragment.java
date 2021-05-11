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
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public abstract class BaseFragment<M extends BaseViewModel> extends Fragment implements IBaseView {

    protected M model;
    protected BaseActivity mActivity;
    private View layout;
    private boolean isNavigationViewInit = false; // 记录是否已经初始化过一次视图
    protected View lastView = null; // 记录上次创建的view

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initBundle();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        layout = view;
        initViewObservable();
        initViewModel();
        initBundle();
        initView();
        initData();
        return view;
    }

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

    private void initViewModel(){
        model = getViewModel();
        if (model == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            }else{
                //没有指定泛型参数,默认使用BaseViewModel
                modelClass = BaseViewModel.class ;
            }
            model = (M) createViewModel(this ,modelClass);
        }
        //让ViewModel 拥有View的生命周期感应
        getLifecycle().addObserver(model);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除ViewModel 生命周期绑定
        getLifecycle().removeObserver(model);
    }

    public M getViewModel() {
        return null;
    }

    public <T extends ViewModel> T createViewModel(BaseFragment context, Class<T> clazz){
        return ViewModelProviders.of(context).get(clazz);
    }
}
