package com.example.moudle_room;

import android.os.AsyncTask;
import android.view.View;

import com.bdxh.librarybase.base.BaseActivity;
import com.bdxh.librarybase.base.BaseViewModel;
import com.blankj.utilcode.util.LogUtils;
import com.example.moudle_room.databinding.ModuleRoomPageActivityBinding;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

//DEMO 练习
public class RoomDataActivity extends BaseActivity<BaseViewModel, ModuleRoomPageActivityBinding> {

    StudentDao dao;
    StudentDataBase dataBase;
    StudentPageAdapter mAdapter;
    LiveData<PagedList<Student>> liveData;

    @Override
    protected int getLayout() {
        return R.layout.module_room_page_activity;
    }

    @Override
    protected void initView() {
        mAdapter = new StudentPageAdapter();
        databind.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databind.recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        databind.recyclerView.setAdapter(mAdapter);
        dao = StudentDataBase.getInstance(this).getStudentDao();
        liveData = new LivePagedListBuilder<>(dao.getAllStudents(),20).build();
        liveData.observe(this, new Observer<PagedList<Student>>() {
            @Override
            public void onChanged(PagedList<Student> students) {
                mAdapter.submitList(students);
                students.addWeakCallback(null, new PagedList.Callback() {

                    //
                    @Override
                    public void onChanged(int position, int count) {
                        LogUtils.d(" onChanged " + students);
                    }

                    @Override
                    public void onInserted(int position, int count) {

                    }

                    @Override
                    public void onRemoved(int position, int count) {

                    }
                });

            }
        });

        databind.buttonPopulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student[] students = new Student[1000];
                for (int i = 0; i < students.length; i++) {
                    Student student = new Student();
                    student.studentNumber =i;
                    students[i] =student ;
                }
                //做相应操作
                new InsertAsyncTask(dao).execute(students);
            }
        });

        databind.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ClearAsyncTask(dao).execute();
            }
        });
    }

    static class InsertAsyncTask extends AsyncTask<Student, Void, Void> {
        StudentDao studentDao;

        InsertAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.insertStudents(students);
            return null;
        }
    }

    static class ClearAsyncTask extends AsyncTask<Void, Void, Void> {
        StudentDao studentDao;

        ClearAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentDao.clearAll();
            return null;
        }
    }
}
