package com.bdxh.librarybase.utils;

import com.bdxh.librarybase.base.BaseResult;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

    /**
     * 线程调度器
     */

    public static <T> ObservableTransformer<T, T> observableToMain() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> observableBothToIo() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    public static <T> ObservableTransformer<T, T> exceptionTransformer() {
        return observable -> observable
//                        .map(new HandleFuc<T>())  //这里可以取出BaseResponse中的Result
                .onErrorResumeNext(new HttpResponseFunc());
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }
    }

    private static class HandleFuc<T> implements Function<BaseResult<T>, T> {
        @Override
        public T apply(BaseResult<T> response) {
            if (!response.isOk())
                throw new RuntimeException(!"".equals(response.getCode() + "" + response.getMsg()) ? response.getMsg() : "");
            return response.getData();
        }
    }

    /* Observable.Transformer*/
    public static <T> ObservableTransformer<BaseResult<T>, T> compatResult() {

        return upstream -> (ObservableSource<T>) upstream.flatMap((Function<BaseResult<T>, ObservableSource<T>>) tBaseBean -> {
            if (tBaseBean.isOk()) {
                return Observable.create(emitter -> {
                    try {
                        emitter.onNext(tBaseBean.getData());
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                });
            } else {
                return Observable.error(new ResponseException(tBaseBean.getCode(), tBaseBean.getMsg()));
            }
        }).compose(observableToMain());
    }

}
