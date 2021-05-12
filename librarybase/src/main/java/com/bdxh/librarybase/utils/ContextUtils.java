package com.bdxh.librarybase.utils;

import android.content.Context;

public class ContextUtils {

    private static Context mContext ;

    public ContextUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Context context){
        mContext = context.getApplicationContext();
    }


    public static Context getContext() {
        if (mContext != null) {
            return mContext;
        }
        throw new NullPointerException("should be initialized in application");
    }
}
