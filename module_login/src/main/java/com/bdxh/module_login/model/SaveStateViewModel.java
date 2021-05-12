package com.bdxh.module_login.model;

import android.app.Application;
import com.bdxh.librarybase.base.BaseViewModel;
import com.jeremyliao.liveeventbus.utils.AppUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

//viewmodel 持久化保存数据  轻量级数据保存 数据大还得sp mmkv 数据库
public class SaveStateViewModel extends BaseViewModel {

    //持久化保存数据 界面销毁再打开直接调用销毁前的数据  点击虚拟返回键并不会保存数据
    private Integer aBack;

    private SavedStateHandle savedStateHandle;

    private static  final String KEY_A_NUMBER = "A_number";

    public SaveStateViewModel(@NonNull Application application) {
        super(application);
    }

    public SaveStateViewModel(SavedStateHandle savedStateHandle) {
        super(AppUtils.getApp());
        this.savedStateHandle = savedStateHandle;
    }

    public MutableLiveData<Integer> getCore(){
        if (!savedStateHandle.contains(KEY_A_NUMBER)) {
            savedStateHandle.set(KEY_A_NUMBER,0);
        }
        return savedStateHandle.getLiveData(KEY_A_NUMBER);
    }

    //增加 操作
    public void aTeamAdd(int p){
        aBack = savedStateHandle.get(KEY_A_NUMBER);
        getCore().setValue(getCore().getValue()+p);
    }

}
