package com.bdxh.module_login;

import android.view.View;
import com.bdxh.librarybase.base.BaseActivity;
import com.bdxh.module_login.databinding.ModuleLoginActivityLoginBinding;
import com.bdxh.module_login.model.ViewModelLogin;
import com.bdxh.module_login.model.bean.LoginResult;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.sankuai.waimai.router.annotation.RouterUri;
import androidx.lifecycle.Observer;

@RouterUri(path = RouterConstants.JUMP_MAIN)
public class LoginActivity extends BaseActivity<ViewModelLogin , ModuleLoginActivityLoginBinding> {

    private ViewModelLogin mViewModel;

    @Override
    protected int getLayout(){
        return R.layout.module_login_activity_login;
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    protected void initView(){
        mViewModel = model;

        databind.moduleLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.Login("userName","pwd");
                saveLoginParams(null);
            }
        });

        mViewModel.getLoginData().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                //监听登录接口返回数据 从而进行操作
                saveLoginParams(loginResult);
            }
        });
    }

    private void saveLoginParams(LoginResult user){
        //跳转main页面
        startUri(this, RouterConstants.JUMP_MAIN, null, null);
        LiveEventBus.get(RouterConstants.JUMP_MAIN).post("登录成功发送LiveEventBus消息");
        finish();
    }
}
