package com.bdxh.module_main;

import android.view.MenuItem;
import com.bdxh.librarybase.base.BaseActivity;
import com.bdxh.module_base.service.IMineService;
import com.bdxh.module_base.service.IOrderService;
import com.bdxh.module_base.service.ITaskService;
import com.bdxh.module_main.databinding.ModuleMainActivityMainBinding;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.sankuai.waimai.router.Router;
import com.sankuai.waimai.router.annotation.RouterUri;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;

@RouterUri(path = RouterConstants.JUMP_MAIN)
public class MainActivity extends BaseActivity<ViewModelMain,ModuleMainActivityMainBinding>{

    private CustomViewPager viewPager;
    BottomNavigationView navView;
    private int currentPosition = 0;
    private List<Fragment> fragmentList ;

    @Override
    protected int getLayout() {
        return R.layout.module_main_activity_main;
    }

    @Override
    protected void initView(){
       // dataBinding
        viewPager = findViewById(R.id.view_page_main);
        navView = findViewById(R.id.btn_nav);
        viewPager.setCanScroll(false);
        viewPager.setOffscreenPageLimit(3);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.tab_task){
                    currentPosition =0;
                }else if ( itemId == R.id.tab_order){
                    currentPosition = 1;
                }else if ( itemId ==  R.id.tab_person){
                    currentPosition =2;
                }
                viewPager.setCurrentItem(currentPosition,false);
                return true;
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        initFragment();
        LiveEventBus.get(RouterConstants.JUMP_MAIN ,String.class)
                .observeSticky(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        ToastUtils.showShort(s);
                    }
                });
    }

    private void initFragment() {
        fragmentList =new ArrayList<>();
        //任务
        ITaskService taskService = Router.getService(ITaskService.class,"/task_fragment");
        // 订单
        IOrderService orderService = Router.getService(IOrderService.class,"/order_fragment");
        //我的
        IMineService mineService = Router.getService(IMineService.class,"/mine_fragment");

        if (taskService == null){
            //单独运行main组件,无法获取fragment组件实例,正常使用时应与需要加载的组件集成使用
            ToastUtils.showShort("单独运行main组件,无法获取fragment组件实例");
        }
        Fragment taskFragment = taskService.provideInstance();
        Fragment orderFragment = orderService.provideInstance();
        Fragment mineFragment = mineService.provideInstance();
        fragmentList.add(taskFragment);
        fragmentList.add(orderFragment);
        fragmentList.add(mineFragment);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
    }
}
