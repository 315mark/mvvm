package com.bdxh.myapplication;

import android.app.Application;
import android.util.Log;

import com.bdxh.librarybase.base.BaseApplication;
import com.bdxh.module_base.config.ModuleConfig;
import com.tencent.smtt.sdk.QbSdk;

import java.lang.reflect.Modifier;

// 打开登陆页面
public class APPAplication extends BaseApplication {

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        //组件化初始化
        ModuleConfig.getInstance().init(this);

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
		
		/*QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
			
			@Override
			public void onViewInitFinished(boolean arg0) {
				// TODO Auto-generated method stub
				//x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
				Log.d("app", " onViewInitFinished is " + arg0);
			}
			
			@Override
			public void onCoreInitFinished() {
				// TODO Auto-generated method stub
			}
		};
		//x5内核初始化接口
		QbSdk.initX5Environment(getApplicationContext(),  cb);*/
    }

}
