package com.bdxh.module_order.model;

import android.app.Application;
import com.bdxh.librarybase.base.BaseViewModel;
import com.bdxh.librarybase.http.retrofit.RetrofitManager;
import com.bdxh.module_order.OrderDataSource;
import com.bdxh.module_order.bean.Order;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class OrderViewModel extends BaseViewModel {

    public LiveData<PagedList<Order>> liveData;
    private Order mOrder;

    public OrderViewModel(@NonNull Application application){
        super(application);
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)  // 用于设置控件占位
                .setPageSize(10)              // 设置每页的大小
                .setPrefetchDistance(3)       // 设置当距离底部还有多少条数据时开始加载下一页数据
                .setInitialLoadSizeHint(30)   // 设置首次加载数据数量 必须是 pagesize 的倍数  默认3倍
                .setMaxSize(65536* 10)        // 设置 PageList 所能承受最大值 超出将出现异常 pagesize的倍数
                .build();

        liveData = new LivePagedListBuilder<>(new OrderDataSource(getApplication()), config).build();
    }


    String BaseUrl = "https://api.douban.com/v2/" ;

    private ApiService getOrderInfo(){
        return RetrofitManager.getInstance().getApiService(ApiService.class, BaseUrl);
    }

    private interface ApiService{
        @GET("movie/in_theaters")
        Observable<Order> OrderInfo(@Query("start") int since, @Query("count") int perPage);
    }

}
