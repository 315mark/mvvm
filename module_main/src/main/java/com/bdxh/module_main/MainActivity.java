package com.bdxh.module_main;

import android.view.MenuItem;

import com.bdxh.librarybase.base.BaseActivity;
import com.bdxh.librarybase.http.download.ProgressCallBack;
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
import okhttp3.ResponseBody;

@RouterUri(path = RouterConstants.JUMP_MAIN)
public class MainActivity extends BaseActivity<ViewModelMain, ModuleMainActivityMainBinding> {

    private int currentPosition = 0;
    private List<Fragment> fragmentList;
    //这里是BottomNavigationView 和 ViewPager 配合使用

    String destFileDir = getApplication().getCacheDir().getPath();
    String destFileName = System.currentTimeMillis() + ".apk";

    @Override
    protected int getLayout() {
        return R.layout.module_main_activity_main;
    }

    @Override
    protected void initView() {
        // dataBinding
        databind.viewPageMain.setCanScroll(false);
        databind.viewPageMain.setOffscreenPageLimit(3);
        databind.btnNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.tab_task) {
                    currentPosition = 0;
                } else if (itemId == R.id.tab_order) {
                    currentPosition = 1;
                } else if (itemId == R.id.tab_person) {
                    currentPosition = 2;
                }
                databind.viewPageMain.setCurrentItem(currentPosition, false);
                return true;
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        initFragment();
        LiveEventBus.get(RouterConstants.JUMP_MAIN, String.class)
                .observeSticky(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        ToastUtils.showShort(s);
                    }
                });

        model.downLoadApp(new ProgressCallBack<ResponseBody>(this, destFileDir, destFileName) {
            @Override
            public void onSuccess(ResponseBody responseBody) {
            }

            @Override
            public void progress(long progress, long total) {
                LiveEventBus.get("DOWN_LOAD_PROGRESS").post(progress);
            }

            @Override
            public void onError(Throwable e) {

            }
        });


    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        //任务
        ITaskService taskService = Router.getService(ITaskService.class, "/task_fragment");
        // 订单
        IOrderService orderService = Router.getService(IOrderService.class, "/order_fragment");
        //我的
        IMineService mineService = Router.getService(IMineService.class, "/mine_fragment");

        if (taskService == null) {
            //单独运行main组件,无法获取fragment组件实例,正常使用时应与需要加载的组件集成使用
            ToastUtils.showShort("单独运行main组件,无法获取fragment组件实例");
        }

        Fragment taskFragment = taskService.provideInstance();
        Fragment orderFragment = orderService.provideInstance();
        Fragment mineFragment = mineService.provideInstance();
        fragmentList.add(taskFragment);
        fragmentList.add(orderFragment);
        fragmentList.add(mineFragment);
        databind.viewPageMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
