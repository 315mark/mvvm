package com.bdxh.librarybase.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.jeremyliao.liveeventbus.utils.AppUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//
public class BaseApplication extends Application {

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
    }

    public static void setInstance(Application Instance) {
        mInstance = Instance;
        AppUtils.init(Instance);
        Instance.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
                BaseStackManager.getInstance().addActivity(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                BaseStackManager.getInstance().removeActivity(activity);
            }
        });
    }

    public static Application getInstance() {
        return mInstance;
    }
}
