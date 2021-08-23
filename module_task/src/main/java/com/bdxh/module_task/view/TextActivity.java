package com.bdxh.module_task.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bdxh.module_task.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class TextActivity extends AppCompatActivity {

    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_task_activity_task);
        intent = getIntent();
        intent.putExtra("magic","传参");
        //关闭后传回数据到 MainActivity
        setResult(Activity.RESULT_OK,intent);
        finish();

        //获取不属于共享空间的文件 MediaStore使用无效 只能通过SAF
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                int resultCode = result.getResultCode();
                if (resultCode == 100) {
                    Uri uri = result.getData().getData();
                    openUriForRead(uri);
                }
            }
        });
    }

    // Android 11(R)
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void createFileR(View view){
        // Android R /data/data/com.android.providers.media/databases/external.db 专门管理各种文件数据库的
        // 有一张表，files 专门管理所有文件的数据表（只有当你选择分区存储的时候，才这么玩,包含 文件，图片，视频
        // Android R 操作文件的方式，这种方式在Android 10 想要兼容，必须设置 requestLegacyExternalStorage="false"
        // MediaStore.Files 这个是放置，所有的文件夹（包括Documents、Movies、Picture等）
        // Android 11 操作文件就是操作数据库的，获取到一个路径
        Uri uri = MediaStore.Files.getContentUri("external");
        //创建一个ContentValue 对象 用来插入数据库(给存储文件的数据)
        ContentValues contentValues = new ContentValues();
        //直接创建 .txt 要存储的路径 , 要创建文件的上一级存储目录
        String path = Environment.DIRECTORY_DOWNLOADS + "test"; //这是文件夹
        //给路径的字段设置键值对
        contentValues.put(MediaStore.Downloads.RELATIVE_PATH,path);
        //设置文件的名字,如果数据库已经有了这个文件,可能存储不成功
        contentValues.put(MediaStore.Downloads.DISPLAY_NAME,"test.txt");
        //可以不用设置
        contentValues.put(MediaStore.Downloads.TITLE,"test");
        //第一个参数是,操作那个数据库,因为操作的数据库是 external ,所以直接传递上面的 uri
        //插入一条数据 ,然后把生成的文件路径返回
        Uri insert = getContentResolver().insert(uri,contentValues);
        // Android 11 中,所有文件和文件夹从名字上是无法区分的 ,除非这个文件里面有内容,才能成为文件
        String content = "Android 11 数据测试";
        OutputStream outputStream = null ;
        try {
            outputStream = getContentResolver().openOutputStream(insert);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            bos.write(content.getBytes());
            bos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // Android 9
    private void createFile(View view) {
        String pathname = "/sdcard/test.txt";
        File file = new File(pathname);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
        
    //创建图片 对图片增删改查
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void createImg() {
        String imgName = "mv.jpg";
        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.ImageColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES);
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME,imgName);
        //插入外置卡路径
        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
        // 所有文件的创建都需要写数据 ,不写数据就不会生成文件
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background);
        try {
            OutputStream os = contentResolver.openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Uri queryUri;
    public void queryImg(){
        // 获取存放图片的uri
        Uri externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Images.Media.DISPLAY_NAME + "=?";
        String[] arg = new String[]{"mv.jpg"};
        //第二个参数是指定查询的列,如果为null ,那就是查所有
       Cursor cursor = getContentResolver().query(externalUri, null, selection, arg, null);
        if (cursor != null && cursor.moveToFirst()) {
            // 这是获取图片存储的绝对路径
            // int indexOrThrow = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            // String data = cursor.getString(indexOrThrow);

            //我要获取这张图片的uri  先拿到隔着个图片的id
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
            // 将查询的id 转化成对应的uri
            queryUri = ContentUris.withAppendedId(externalUri, id);
            cursor.close();
        }
    }

    public void deleteImg(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            int delete = getContentResolver().delete(queryUri,null);
            if (delete > 0) {
                Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updateImg(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME,"test.jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            int update = getContentResolver().update(queryUri,null,null);
            if (update > 0) {
                Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 动态检查权限
    public static boolean checkPermission(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {

        }
        return false;
    }

    public void changeImg(){
        String fileName = System.currentTimeMillis() +".jpg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //系统裁剪无法访问私有目录 只能保存在公有目录  Android 10 内存分配
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH,Environment.DIRECTORY_PICTURES +  "/Crop");
            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        }else{
         // android 10以下
        }
    }

    //当文件保存在共享文件目录 这样就不用适配 同时不需要申请权限
    private void saveImg(){
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(getExternalMediaDirs()[0].getAbsolutePath() + File.separator + fileName);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        @SuppressLint("ServiceCast") TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELECOM_SERVICE);
    }


    //获取不属于共享空间的文件 MediaStore使用无效 只能通过SAF
    private void openUriForRead1(Uri uri){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //指定选择文本类型的文件
        intent.setType("text/plain");
        this.startActivityForResult(intent,100);
    }


    private void openUriForRead(Uri uri) {
        if (uri == null)
            return;
        try {
            //获取输入流
            InputStream inputStream = getContentResolver().openInputStream(uri);
            byte[] readContent = new byte[1024];
            int len = 0;
            do {
                //读文件
                len = inputStream.read(readContent);
                if (len != -1) {
                    Log.d("test", "read content:" + new String(readContent).substring(0, len));
                }
            } while (len != -1);
            inputStream.close();
        } catch (Exception e) {
            Log.d("test", e.getLocalizedMessage());
        }
    }

    private void openUriForWrite(Uri uri) {
        if (uri == null) {
            return;
        }

        try {
            //从uri构造输出流
            OutputStream outputStream = getContentResolver().openOutputStream(uri);
            //待写入的内容
            String content = "hello world I'm from SAF\n";
            //写入文件
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            Log.d("test", e.getLocalizedMessage());
        }
    }

}
