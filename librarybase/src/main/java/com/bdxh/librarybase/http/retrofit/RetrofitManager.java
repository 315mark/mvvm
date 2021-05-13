package com.bdxh.librarybase.http.retrofit;

import android.content.Context;

import com.bdxh.librarybase.cookie.CookieJarImpl;
import com.bdxh.librarybase.cookie.store.PersistentCookieStore;
import com.bdxh.librarybase.fastjson.FastJsonConvertFactory;
import com.bdxh.librarybase.http.interceptor.BaseInterceptor;
import com.bdxh.librarybase.http.interceptor.CacheInterceptor;
import com.bdxh.librarybase.http.interceptor.ProgressInterceptor;
import com.bdxh.librarybase.utils.HttpsUtils;
import com.blankj.utilcode.BuildConfig;
import com.jeremyliao.liveeventbus.logger.Logger;
import com.jeremyliao.liveeventbus.utils.AppUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
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
        return createRetrofit(baseUrl,null);
    }

    private Retrofit createRetrofit(String baseUrl, Map<String, String> headers){
       return createRetrofit(baseUrl,headers, false,false);
    }

    private Retrofit createRetrofit(String baseUrl, Map<String, String> headers ,boolean isCache ,boolean isDownLoadApp){

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();  // https证书认证,封装了认证方法,可根据自己公司进行调整;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()

                .readTimeout(DEFAULT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_SECONDS,TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为15s
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS));

        if (!isCache) {
            Context mContext = AppUtils.getApp().getApplicationContext();
            builder.cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
               .addInterceptor(new BaseInterceptor(headers))   // 添加header的拦截器
               .addInterceptor(new CacheInterceptor(mContext)) //无网络状态智能读取缓存
               .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);   // https的证书校验
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor);
        }

        if (!isDownLoadApp){  // app下载进度的拦截器
            builder.addInterceptor(new ProgressInterceptor());
        }

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder.build())
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
