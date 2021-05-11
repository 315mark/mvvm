package com.bdxh.module_base.debug;

import com.bdxh.librarybase.base.BaseApplication;
import com.bdxh.module_base.config.ModuleConfig;

public class DebugApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        ModuleConfig.getInstance().init(this);
    }
}
