package com.bdxh.librarybase.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public abstract class MvvmBaseFragment<VD extends ViewDataBinding, M extends BaseViewModel> extends Fragment implements IBaseView {

    protected VD binding ;
    protected M model;
    protected int viewModelId;
    protected BaseActivity mActivity;
    private View layout;
    private boolean isNavigationViewInit = false; // 记录是否已经初始化过一次视图
    protected View lastView = null; // 记录上次创建的view

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

    protected abstract int getLayoutId();

    protected abstract void initView();


    /**
     * 初始化ViewModel的id
     */
    public abstract int initVariableId();


    /**
     *  界面初始化数据传递
     */
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
        viewModelId = initVariableId();
        model = getViewModel();
        if (model == null) {
            Class modelClass;
            //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type
            //然后将其转换ParameterizedType
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                // 返回表示此类型实际类型参数的 Type 对象的数组。简而言之就是获得超类的泛型参数的实际类型,获取第二个泛型
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            }else{
                //没有指定泛型参数,默认使用BaseViewModel
                modelClass = BaseViewModel.class ;
            }
            model = (M) createViewModel(this ,modelClass);
        }

//        binding.setVariable(viewModelId,model);
        /*
         * 让ViewModel拥有View的生命周期感应
         * viewModel implements IBaseViewModel接口
         * IBaseViewModelMVVM extends LifecycleObserver
         * 所以ViewModel是lifecycle生命周期的观察者,viewmode可以在不同的生命周期中处理不同的事情
         * viewModel可以感受到ui的生命周期状态;
         * BaseViewModel中实现了IBaseViewModel中的类似生命周期的观察
         */
        getLifecycle().addObserver(model);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding != null) {
            binding.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除ViewModel 生命周期绑定
        getLifecycle().removeObserver(model);
    }

    /**
     * 初始化ViewModel
     * @return 继承BaseViewModel的ViewModel
     */
    public M getViewModel() {
        return null;
    }

    public <T extends ViewModel> T createViewModel(Fragment context, Class<T> clazz){
        return ViewModelProviders.of(context).get(clazz);
    }

}
