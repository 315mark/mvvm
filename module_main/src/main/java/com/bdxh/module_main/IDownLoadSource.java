package com.bdxh.module_main;

import com.bdxh.librarybase.http.download.ProgressCallBack;

import androidx.lifecycle.LifecycleOwner;

public interface IDownLoadSource {
    void downLoadApp(ProgressCallBack callBack);
}
