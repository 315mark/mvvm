package com.bdxh.module_nav.ui.dashboard;

import com.bdxh.librarybase.base.MvvmBaseFragment;
import com.bdxh.module_nav.R;
import com.bdxh.module_nav.databinding.ModuleNavFragmentDashboardBinding;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

public class DashboardFragment extends MvvmBaseFragment<ModuleNavFragmentDashboardBinding,DashboardViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.module_nav_fragment_dashboard;
    }

    @Override
    protected void initView() {
        //这是代码赋值  布局赋值更方便
        model.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.textDashboard.setText(s);
            }
        });
    }

    @Override
    public int initVariableId() {
        return 0;
    }
}