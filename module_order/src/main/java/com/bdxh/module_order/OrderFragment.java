package com.bdxh.module_order;

import com.bdxh.librarybase.base.BaseFragment;
import com.bdxh.module_base.service.IOrderService;
import com.sankuai.waimai.router.annotation.RouterProvider;
import com.sankuai.waimai.router.annotation.RouterService;
import androidx.fragment.app.Fragment;

@RouterService(interfaces = IOrderService.class ,key = "/order_fragment" , singleton = true)
public class OrderFragment extends BaseFragment implements IOrderService{
    @Override
    protected int getLayoutId() {
        return R.layout.module_order_fragment_order;
    }

    @Override
    protected void initView() {

    }

    @Override
    public Fragment provideInstance(){
        return getInstance();
    }

    //使用注解声明该方法是一个provide
    @RouterProvider
    public static OrderFragment getInstance(){
        return new OrderFragment();
    }
}
