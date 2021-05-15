package com.bdxh.module_nav.ui.notifications;

import com.bdxh.librarybase.base.MvvmBaseFragment;
import com.bdxh.module_nav.BR;
import com.bdxh.module_nav.R;
import com.bdxh.module_nav.databinding.ModuleNavFragmentNotificationsBinding;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

public class NotificationsFragment extends MvvmBaseFragment<ModuleNavFragmentNotificationsBinding,NotificationsViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.module_nav_fragment_notifications;
    }

    @Override
    protected void initView() {

    }

    @Override
    public int initVariableId() {
        return BR._all;
    }
}