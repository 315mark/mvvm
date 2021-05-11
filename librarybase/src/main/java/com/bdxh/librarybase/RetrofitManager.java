package com.bdxh.librarybase;

import com.bdxh.librarybase.fastjson.FastJsonConvertFactory;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final int DEFAULT_SECONDS = 8;

    private RetrofitManager(){
    }

    private static class RetrofitManagerInstance{
        private static final RetrofitManager manager = new RetrofitManager();
    }

    public static RetrofitManager getInstance(){
        return RetrofitManagerInstance.manager;
    }

    private Retrofit createRetrofit(String baseUrl){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .connectTimeout(DEFAULT_SECONDS,TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConvertFactory.create())
                .build();
    }

    //根据各模块业务接口  获取不同的请求接口对象 单工程直接放一个Class文件中统一管理
    public <T> T getApiService(Class<T> clazz,String baseUrl){
        return createRetrofit(baseUrl).create(clazz);
    }
}
