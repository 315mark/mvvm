package com.bdxh.module_base.config;

import android.app.Application;
import com.bdxh.librarybase.base.BaseApplication;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.sankuai.waimai.router.Router;
import com.sankuai.waimai.router.common.DefaultRootUriHandler;
import com.tencent.mmkv.MMKV;

public class BaseModule implements IBaseModule {
    protected MMKV mmkv;

    @Override
    public void init(Application application) {
        InitWMRouter();

        mmkv = MMKV.defaultMMKV();
        
        initLiveEventBus();
    }

    private void initLiveEventBus() {

        LiveEventBus.config()
                .autoClear(false)
                .lifecycleObserverAlwaysActive(true);
    }

    private void InitWMRouter() {
        DefaultRootUriHandler rootHandler = new DefaultRootUriHandler(BaseApplication.getInstance());
        Router.init(rootHandler);
    }
}
