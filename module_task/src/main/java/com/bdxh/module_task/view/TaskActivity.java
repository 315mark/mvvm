package com.bdxh.module_task.view;

import android.Manifest;
import android.app.Activity;

import com.bdxh.librarybase.base.BaseActivity;
import com.bdxh.module_task.R;

import androidx.activity.result.ActivityResult;

public class TaskActivity extends BaseActivity {

    String[] mPermissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected int getLayout() {
        return R.layout.module_task_activity_task;
    }

    @Override
    protected void initView(){
  /*  //跳转Activity 传参
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            parserActivityData(result);
            Intent intent = result.getData();
        });

        // 跳转去其他界面  也可配置 Bundle 传递数据 用法不变
        Intent intent = new Intent(this, TextActivity.class);
        launcher.launch(intent);

        // GetContent 用于选择内容并获取返回结果  TakeVideo，用于拍摄视频并获取返回结果
        // PickContact，用于选择联系人并获取返回结果 TakePicture，用于拍照并获取返回结果  用于共享文件中的数据
        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {

        });

        mGetContent.launch("image/*");

        // 请求权限
        ActivityResultLauncher<String> permission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
          if (result) {
        //              "同意" ;
          }
        //              "拒绝" ;
        });
        //申请访问通讯录权限
        permission.launch(Manifest.permission.READ_CONTACTS);

        //多组权限
        ActivityResultLauncher<String[]> permissions = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {

        });

        permissions.launch(mPermissions);*/

    }

    private void parserActivityData(ActivityResult result) {
        switch (result.getResultCode()) {

            case Activity.RESULT_OK:
                String magic = result.getData().getStringExtra("magic");
//            Activity.RESULT_OK -> {
//                val msg = result.data?.getStringExtra("magic") ?: "没返回intent"
//                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
//            }
//            else -> {
//                Log.i("magic", "返回数据失败")
//            }
        }
    }
}
