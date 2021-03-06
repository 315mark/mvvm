package com.bdxh.module_task.view;

import android.os.Handler;
import com.bdxh.librarybase.base.MvvmBaseFragment;
import com.bdxh.module_base.service.ITaskService;
import com.bdxh.module_task.BR;
import com.bdxh.module_task.R;
import com.bdxh.module_task.databinding.ModuleTaskFragmentTaskBinding;
import com.bdxh.module_task.model.ViewModelTask;
import com.sankuai.waimai.router.annotation.RouterProvider;
import com.sankuai.waimai.router.annotation.RouterService;
import androidx.fragment.app.Fragment;

@RouterService(interfaces = ITaskService.class , key = "/task_fragment" , singleton = true)
public class TaskFragment extends MvvmBaseFragment<ModuleTaskFragmentTaskBinding,ViewModelTask> implements ITaskService {

    private ViewModelTask modelTask;

    @Override
    protected int getLayoutId() {
        return R.layout.module_task_fragment_task;
    }

    @Override
    protected void initView() {
        modelTask = model;

        modelTask.getTaskData().observe(this, taskResult -> {
            if (taskResult == null) {
                return;
            }
            String liveDataStr = "LiveData更新数据";
            binding.moduleTaskTxt.setText(liveDataStr);
        });
    }

    //
    @Override
    public int initVariableId() {
        return BR._all;
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
