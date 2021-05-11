package com.bdxh.module_base.config;

import android.app.Application;

import androidx.annotation.Nullable;

public class ModuleConfig {

    private  static final ModuleConfig mInstance = new ModuleConfig();

    public static ModuleConfig getInstance() {
        return mInstance;
    }

    private ModuleConfig(){

    }

    public void init(@Nullable Application application){
        for (String moduleInitName: ModuleConstant.initModuleNames) {
            try {
                Class<?> clazz = Class.forName(moduleInitName);
                IBaseModule iBaseModule = (IBaseModule) clazz.newInstance();
                iBaseModule.init(application);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
