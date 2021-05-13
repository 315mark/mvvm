package com.bdxh.module_nav.ui.notifications;

import android.app.Application;

import com.bdxh.librarybase.base.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends BaseViewModel {

    private MutableLiveData<String> mText;

    public NotificationsViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}