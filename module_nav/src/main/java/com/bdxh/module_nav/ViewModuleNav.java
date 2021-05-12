package com.bdxh.module_nav;

import android.app.Application;

import com.bdxh.librarybase.base.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class ViewModuleNav extends BaseViewModel{

    private MutableLiveData<Integer> mData;

    public ViewModuleNav(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Integer> getmData() {
        if (mData == null) {
            mData = new MutableLiveData<>();
        }
        return mData;
    }
}
