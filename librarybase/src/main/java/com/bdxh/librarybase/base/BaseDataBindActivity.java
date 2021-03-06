package com.bdxh.librarybase.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.bdxh.librarybase.R;
import com.bdxh.librarybase.widget.EmptyViewHelper;
import com.blankj.utilcode.util.LogUtils;
import com.sankuai.waimai.router.Router;
import com.sankuai.waimai.router.common.DefaultUriRequest;
import com.sankuai.waimai.router.components.UriSourceTools;
import com.sankuai.waimai.router.core.OnCompleteListener;
import com.sankuai.waimai.router.core.UriRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import qiu.niorgai.StatusBarCompat;

public abstract class BaseDataBindActivity<Bind extends ViewDataBinding> extends AppCompatActivity implements IBaseView{

    protected Bind databind;
    private EmptyViewHelper emptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewDataBind();
        setContentView(getLayout());
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.bg_status_bar), 112);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initBundle();
        initViewObservable();
        initView();
        initData();
    }

    private void initViewDataBind() {
        databind = DataBindingUtil.setContentView(this, getLayout());
        databind.executePendingBindings();
        databind.setLifecycleOwner(this);
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
    public void onReload() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();  //super.XX(); ?????? ????????????????????? ???????????????????????????  git config --system http.sslcainfo "C:\ruanjian\Git\bin\curl-ca-bundle.crt"
        if (databind != null){
            databind.unbind();
        }
    }

    public void showEmptyView(View view){
        if (emptyView == null) {
            emptyView = new EmptyViewHelper(this);
            emptyView.setReloadCallBack(this);
        }
        emptyView.loadNormallLayout(view);
    }

    //????????? ?????????Router
    //????????? ????????????Activity
    //????????? ??????uri ??????

    // ??????1????????????context???URI
    public void jumpUri(Context context, String uri) {
        Router.startUri(context, uri);
    }

    // ??????2??????????????????UriRequest
    public void startUri(Context context, String uri) {
        Router.startUri(new UriRequest(context, uri));
    }

    // ??????3?????????DefaultUriRequest????????????
    public void startUri(Context context, String uri, String extraName, Parcelable parcelable) {
        Router.startUri(new DefaultUriRequest(context, uri)
                .activityRequestCode(100)  // startActivityForResult?????????RequestCode
                // ???????????????????????????????????????????????????????????????WebView?????????Push????????????
                // ??????Activity?????????UriSourceTools?????????????????????
                .from(UriSourceTools.FROM_INTERNAL)
                .putExtra(extraName, parcelable)
                //??????????????????
                .overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim)
                //????????????????????????
                .onComplete(new OnCompleteListener() {
                    @Override
                    public void onSuccess(@NonNull UriRequest request) {
                        LogUtils.e("????????????");
                    }

                    @Override
                    public void onError(@NonNull UriRequest request, int resultCode) {

                    }
                })
        );
    }
}
