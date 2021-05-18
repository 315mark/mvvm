package com.bdxh.librarybase.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public abstract class BaseActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends BaseDataBindActivity<DB> {

    protected VM model;

    @Override
    public void initBundle() {
        super.initBundle();
        initViewModel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(model);
    }

    private void initViewModel() {
        model = getViewModel();
        if (model == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                modelClass = BaseViewModel.class;
            }
            model = (VM) createViewModel(this, modelClass);
        }
        getLifecycle().addObserver(model);

    }


    protected VM getViewModel() {
        return null;
    }

    //普通ViewModel
    public <T extends ViewModel> T createViewModel(BaseActivity context, Class<T> clazz) {
        return ViewModelProviders.of(context).get(clazz);
    }

    //数据持久化保存
    public <T extends ViewModel> T saveStateViewModel(BaseActivity context, Class<T> clazz) {
//        return new ViewModelProvider(this,new SavedStateViewModelFactory(getApplication(),this)).get(clazz);
        return ViewModelProviders.of(context,new SavedStateViewModelFactory(getApplication(),this)).get(clazz);
    }

}
