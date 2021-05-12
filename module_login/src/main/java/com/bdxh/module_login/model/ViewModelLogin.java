package com.bdxh.module_login.model;

import android.app.Application;

import com.bdxh.librarybase.base.BaseResult;
import com.bdxh.librarybase.utils.RxUtils;
import com.bdxh.module_login.model.bean.LoginResult;
import com.bdxh.module_login.repository.ILoginDataSource;
import com.bdxh.module_login.repository.LoginDataSource;
import com.blankj.utilcode.util.LogUtils;
import com.jeremyliao.liveeventbus.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewModelLogin extends LoginDataSource implements ILoginDataSource {

    private MutableLiveData<LoginResult> data;

    public ViewModelLogin(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<LoginResult> getLoginData(){
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }

    @Override
    public void Login(String userName, String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put("username" , userName);
        map.put("pwd" , pwd);
        getLoginApi().login(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxUtils.<BaseResult<LoginResult>>observableToMain())
                .compose(RxUtils.<BaseResult<LoginResult>>exceptionTransformer())
                .as(this.<BaseResult<LoginResult>>bindLifecycle())
                .subscribe(new Consumer<BaseResult<LoginResult>>() {
                    @Override
                    public void accept(BaseResult<LoginResult> result) throws Exception {
                        data.setValue(result.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(throwable.getMessage());
                    }
                });
    }
}
