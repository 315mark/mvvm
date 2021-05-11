package com.bdxh.module_task.view;

import android.os.Handler;
import android.widget.TextView;

import com.bdxh.librarybase.base.BaseFragment;
import com.bdxh.module_base.service.ITaskService;
import com.bdxh.module_task.R;
import com.bdxh.module_task.model.ViewModelTask;
import com.bdxh.module_task.model.bean.TaskResult;
import com.sankuai.waimai.router.annotation.RouterProvider;
import com.sankuai.waimai.router.annotation.RouterService;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

@RouterService(interfaces = ITaskService.class , key = "/task_fragment" , singleton = true)
public class TaskFragment extends BaseFragment<ViewModelTask> implements ITaskService {

    private TextView txt;
    private ViewModelTask modelTask;

    @Override
    protected int getLayoutId() {
        return R.layout.module_task_fragment_task;
    }

    @Override
    protected void initView() {
        txt = findViewById(R.id.module_task_txt);
        modelTask = model;

        modelTask.getTaskData().observe(this, taskResult -> {
            if (taskResult == null) {
                return;
            }
            String liveDataStr = "LiveData更新数据";
            txt.setText(liveDataStr);
        });
    }

    @Override
    public void initData() {
        super.initData();
        new Handler().postDelayed(() -> {
            //仅作演示demo
            modelTask.getTaskInfo("查询任务...");
        },3000);
    }

    @Override
    public Fragment provideInstance(){
        return getInstance();
    }

    @RouterProvider
    public static TaskFragment getInstance() {
        return new TaskFragment();
    }

}
