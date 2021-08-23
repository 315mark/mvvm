package com.bdxh.librarybase.base;

import android.app.Application;
import android.view.View;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.ObservableConverter;

public class BaseViewModel extends AndroidViewModel implements IBaseViewModel{

    // AndroidViewModel 是ViewModel 子类 可以直接调用getApplication（）访问应用的全局资源
    // getApplication().getResources().getString()
    // paging 使用recyclerview需要在重写此方法中进行初始化
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    public <T> AutoDisposeConverter<T> bindLifecycle() {
        return AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(getApplication()));
    }
}
