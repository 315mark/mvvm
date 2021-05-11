package com.bdxh.librarybase.adapter;

import android.os.Bundle;
import android.view.View;
import com.bdxh.librarybase.R;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class DiffUtilActivity extends AppCompatActivity {

    // Demo  Activity 使用Diff
    private RecyclerView mRecyclerView ;
    private DiffUtilAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = new RecyclerView(this);

        initRv();
    }

    private void initRv() {
        mAdapter = new DiffUtilAdapter(R.layout.support_simple_spinner_dropdown_item,getDiffUtilEntities());
        mRecyclerView.setAdapter(mAdapter);

        //添加头布局
        View view = getLayoutInflater().inflate(R.layout.brvah_quick_view_load_more, mRecyclerView,false);
        mAdapter.addHeaderView(view);

        //必须设置Diff Callback
        mAdapter.setDiffCallback(new AdapterDiffCallBack());
    }

    //假数据
    public static List<DiffUtilEntity> getDiffUtilEntities() {
        List<DiffUtilEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            list.add(new DiffUtilEntity(
                    i,
                    "Item " + i,
                    "This item " + i + " content",
                    "06-12")
            );
        }
        return list;
    }

    //
    private void initClick() {
        mRecyclerView.setOnClickListener(v -> changeData());

        mRecyclerView.setOnClickListener(v -> {
            // change item 0
            mAdapter.getData().set(0, new DiffUtilEntity(
                    1,
                    "😊😊Item " + 0,
                    "Item " + 0 + " content have change (notifyItemChanged)",
                    "06-12"));
            mAdapter.notifyItemChanged(mAdapter.getHeaderLayoutCount(), DiffUtilAdapter.ITEM_0_PAYLOAD);
        });
    }

    //
    private void changeData(){
        List<DiffUtilEntity> newData = getNewList();
        mAdapter.setDiffNewData(newData);
    }

    //变更的假数据
    private List<DiffUtilEntity> getNewList(){
        List<DiffUtilEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            /*
            Simulate deletion of data No. 1 and No. 3
            模拟删除1号和3号数据
             */
            if (i == 1 || i == 3) {
                continue;
            }

            /*
            Simulate modification title of data No. 0
            模拟修改0号数据的title
             */
            if (i == 0) {
                list.add(new DiffUtilEntity(
                        i,
                        "😊Item " + i,
                        "This item " + i + " content",
                        "06-12")
                );
                continue;
            }

            /*
            Simulate modification content of data No. 4
            模拟修改4号数据的content发生变化
             */
            if (i == 4) {
                list.add(new DiffUtilEntity(
                        i,
                        "Item " + i,
                        "Oh~~~~~~, Item " + i + " content have change",
                        "06-12")
                );
                continue;
            }

            list.add(new DiffUtilEntity(
                    i,
                    "Item " + i,
                    "This item " + i + " content",
                    "06-12")
            );
        }
        return list;
    }

}
