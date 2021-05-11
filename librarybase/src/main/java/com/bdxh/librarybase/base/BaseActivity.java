package com.bdxh.librarybase.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import com.bdxh.librarybase.R;
import com.blankj.utilcode.util.LogUtils;
import com.sankuai.waimai.router.Router;
import com.sankuai.waimai.router.common.DefaultUriRequest;
import com.sankuai.waimai.router.components.UriSourceTools;
import com.sankuai.waimai.router.core.OnCompleteListener;
import com.sankuai.waimai.router.core.UriRequest;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import qiu.niorgai.StatusBarCompat;

public abstract class BaseActivity <VM extends BaseViewModel , DB extends ViewDataBinding> extends AppCompatActivity implements IBaseView{

    protected VM model;
    protected DB databind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewDataBind();
        setContentView(getLayout());
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.bg_status_bar),112);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViewModel();
        initBundle();
        initViewObservable();
        initView();
        initData();
    }

    private void initViewDataBind(){
        databind = DataBindingUtil.setContentView(this, getLayout());
        databind.executePendingBindings();
    }

    protected abstract int getLayout();

    protected abstract void initView();

    @Override
    public void initBundle() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(model);
    }

    private void initViewModel(){
        model = getViewModel();
        if (model ==null){
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            }else {
                modelClass = BaseViewModel.class;
            }
            model = (VM) createViewModel(this,modelClass);
        }
        getLifecycle().addObserver(model);
    }

    protected VM getViewModel(){
        return null ;
    }

    public <T extends ViewModel> T createViewModel(BaseActivity context, Class<T> clazz){
        return ViewModelProviders.of(context).get(clazz);
    }

    //第三步 初始化Router
    //第四步 配置跳转Activity
    //第五步 发起uri 跳转

    // 方式1，直接传context和URI
    public void jumpUri(Context context, String uri){
        Router.startUri(context,uri);
    }

    // 方式2，或构造一个UriRequest
    public void startUri(Context context, String uri){
        Router.startUri(new UriRequest(context,uri));
    }

    // 方式3，使用DefaultUriRequest，最常用
    public void startUri(Context context, String uri , String extraName, Parcelable parcelable){
        Router.startUri(new DefaultUriRequest(context,uri)
              .activityRequestCode(100)  // startActivityForResult使用的RequestCode
                // 设置跳转来源，默认为内部跳转，还可以是来自WebView、来自Push通知等。
                // 目标Activity可通过UriSourceTools区分跳转来源。
                .from(UriSourceTools.FROM_INTERNAL)
                .putExtra(extraName,parcelable)
                //设置跳转动画
                .overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim)
                //监听跳转完成事件
                .onComplete(new OnCompleteListener() {
                    @Override
                    public void onSuccess(@NonNull UriRequest request) {
                         LogUtils.e("跳转成功");
                    }

                    @Override
                    public void onError(@NonNull UriRequest request, int resultCode) {

                    }
                })

        );
    }
}
