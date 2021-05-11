package com.bdxh.module_login.repository;

import android.app.Application;

import com.bdxh.librarybase.RetrofitManager;
import com.bdxh.librarybase.base.BaseViewModel;

import androidx.annotation.NonNull;

public class LoginDataSource extends BaseViewModel {

    private static final String BASE_URL = "http://172.16.102.55:8080/";
    private static final String TEST_Url = "TEST_URL";
    private static final String TEMP_Url = "TEMP_URL";
    private static final String LOCAL_Url = "LOCAL_URL";

    public LoginDataSource(@NonNull Application application) {
        super(application);
    }

    public static LoginApiService getLoginApi(){
        return RetrofitManager.getInstance().getApiService(LoginApiService.class,BASE_URL);
    }

}
