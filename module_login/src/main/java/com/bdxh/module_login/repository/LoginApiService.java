package com.bdxh.module_login.repository;

import com.bdxh.librarybase.base.BaseResult;
import com.bdxh.module_login.model.bean.LoginResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApiService {

    @POST("query/user")
    Observable<BaseResult<LoginResult>> login(@Body Map<String, String> params);

}
