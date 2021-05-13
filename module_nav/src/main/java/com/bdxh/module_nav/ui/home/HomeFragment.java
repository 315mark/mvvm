package com.bdxh.module_nav.ui.home;

import com.bdxh.librarybase.base.MvvmBaseFragment;
import com.bdxh.module_nav.R;
import com.bdxh.module_nav.databinding.ModuleNavFragmentHomeBinding;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

public class HomeFragment extends MvvmBaseFragment<ModuleNavFragmentHomeBinding,HomeViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.module_nav_fragment_home;
    }

    @Override
    protected void initView() {
        model.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.textHome.setText(s);
            }
        });
    }

    @Override
    public int initVariableId() {
        return 0;
    }
}