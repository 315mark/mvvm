package com.bdxh.module_task.model.bean;

import android.os.Parcel;
import com.bdxh.librarybase.base.BaseEntity;

public class TaskResult extends BaseEntity {

    private String flag;
    private String userId;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public TaskResult(String flag, String userId) {
        this.flag = flag;
        this.userId = userId;
    }

    public TaskResult(Parcel in, String flag, String userId) {
        super(in);
        this.flag = flag;
        this.userId = userId;
    }

}
