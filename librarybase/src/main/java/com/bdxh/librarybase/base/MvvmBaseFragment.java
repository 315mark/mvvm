package com.bdxh.librarybase.base;

import com.jeremyliao.liveeventbus.utils.AppUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public abstract class MvvmBaseFragment<VD extends ViewDataBinding, M extends BaseViewModel> extends BaseFragment<VD> {

    protected M model;
    protected int viewModelId;

    /**
     * 初始化ViewModel的id
     */
    protected abstract int initVariableId();

    protected void initViewModel(){
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
            model = (M) createViewModel(modelClass);
        }


        //   binding.setVariable(viewModelId,model);
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

    public <T extends ViewModel> T createViewModel( Class<T> clazz){
        return ViewModelProviders.of(this).get(clazz);
    }


    //持久化保存
    public <T extends ViewModel> T saveStateViewModel(Class<T> clazz){
        return ViewModelProviders.of(this,new SavedStateViewModelFactory(AppUtils.getApp(),this)).get(clazz);
    }

}
