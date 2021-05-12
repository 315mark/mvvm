package com.bdxh.module_task.model;

import android.app.Application;
import com.bdxh.librarybase.base.BaseResult;
import com.bdxh.librarybase.utils.RxUtils;
import com.bdxh.module_task.model.bean.TaskResult;
import com.bdxh.module_task.repository.ITaskDataSource;
import com.bdxh.module_task.repository.TaskDataSource;
import com.blankj.utilcode.util.LogUtils;
import java.util.HashMap;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Consumer;

public class ViewModelTask extends TaskDataSource implements ITaskDataSource {

    private MutableLiveData<TaskResult> taskData;

    public ViewModelTask(@NonNull Application application){
        super(application);
    }

    public MutableLiveData<TaskResult> getTaskData() {
        if (taskData == null) {
            taskData =new MutableLiveData<>();
        }
        return taskData;
    }

    @Override
    public void getTaskInfo(String search) {
        Map<String, Object> map = new HashMap<>();
        map.put("searchStr",search);
        getTaskApi().getTask(map)
                .compose(RxUtils.observableToMain())  //这个只是对上面线程切换的封装
                .compose(RxUtils.exceptionTransformer())  // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .as(bindLifecycle())  //绑定生命周期
                .subscribe(new Consumer<BaseResult<TaskResult>>() {
                    @Override
                    public void accept(BaseResult<TaskResult> result) throws Exception {
                        taskData.setValue(result.getData());
                    }
                }, throwable -> LogUtils.d(throwable.getMessage()));
    }
}