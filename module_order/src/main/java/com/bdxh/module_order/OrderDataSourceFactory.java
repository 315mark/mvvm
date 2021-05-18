package com.bdxh.module_order;

import android.app.Application;

import com.bdxh.librarybase.http.retrofit.RetrofitManager;
import com.bdxh.librarybase.utils.RxUtils;
import com.bdxh.module_order.bean.Order;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.paging.PageKeyedDataSource;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *  paging 分页使用
 */
public class OrderDataSourceFactory extends PageKeyedDataSource<Integer, Order> {

    public  static  final  int FIRST_PAGE = 1;
    public  static  final  int PER_PAGE = 8;
    public  static  final  String SITE = "stackoverflow";

    private Application application;

    public OrderDataSourceFactory(Application context) {
        application = context;
    }

    String BaseUrl = "https://api.douban.com/v2/" ;

    private ApiService getOrderInfo(){
        return RetrofitManager.getInstance().getApiService(ApiService.class, BaseUrl);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Order> callback) {
        getOrderInfo().OrderInfo(FIRST_PAGE,PER_PAGE)
                .compose(RxUtils.observableToMain())
                .compose(RxUtils.exceptionTransformer())
                .as( AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) application)))
                .subscribe(new Consumer<List<Order>>() {
                    @Override
                    public void accept(List<Order> order) throws Exception {
                        callback.onResult(order,null , FIRST_PAGE+1);
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Order> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Order> callback) {
        getOrderInfo().OrderInfo(params.key,PER_PAGE)
                .compose(RxUtils.observableToMain())
                .compose(RxUtils.exceptionTransformer())
                .as( AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) application)))
                .subscribe(new Consumer<List<Order>>() {
                    @Override
                    public void accept(List<Order> order) throws Exception {
//                        Integer nextKey = order.hasMore?params.key+1:null; //判断是否还有 hasMore 返回的字段
                        Integer nextKey = null;
                        if (params.key * PER_PAGE < order.get(0).total) {
                            nextKey = params.key + 1;
                        }
                        callback.onResult(order, nextKey);
//                        callback.onResult(order,nextKey);
                    }
                });
    }

    private interface ApiService{
        @GET("movie/in_theaters")
        Observable<List<Order>> OrderInfo(@Query("start") int since, @Query("count") int perPage);
    }


}
