package com.bdxh.module_main;

import android.app.Application;

import com.bdxh.librarybase.base.BaseViewModel;
import com.bdxh.librarybase.http.download.DownLoadSubscriber;
import com.bdxh.librarybase.http.download.ProgressCallBack;
import com.bdxh.librarybase.http.retrofit.RetrofitManager;
import com.bdxh.librarybase.utils.RxUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public class ViewModelMain extends BaseViewModel implements IDownLoadSource {

    private MutableLiveData<String> mDownLoad;

    public ViewModelMain(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getData(){
        if (mDownLoad == null){
            mDownLoad = new MutableLiveData<>();
        }
        return mDownLoad;
    }

    @Override
    public void downLoadApp(final ProgressCallBack callBack) {
           getDownLoadApp().download(BASE_URL)
                   .compose(RxUtils.<ResponseBody>observableToMain())
                   .doOnNext(new Consumer<ResponseBody>() {
                       @Override
                       public void accept(ResponseBody responseBody) throws Exception {
                           callBack.saveFile(responseBody);
                       }
                   })
                   .as(this.<ResponseBody>bindLifecycle())

                   .subscribe(new DownLoadSubscriber<ResponseBody>(callBack));
    }

    private static final String BASE_URL = "http://172.16.102.55:8080/";

    private ApiService getDownLoadApp(){
       return RetrofitManager.getInstance().getApiService(ApiService.class,BASE_URL);
    }
    private interface ApiService {
        @Streaming
        @GET
        Observable<ResponseBody> download(@Url String url);
    }
}
