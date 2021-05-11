package com.bdxh.module_login.model.bean;

import android.os.Parcel;
import com.bdxh.librarybase.base.BaseEntity;

public class LoginResult extends BaseEntity {

    private String flag;
    private String userId;

    public LoginResult(String flag, String userId) {
        this.flag = flag;
        this.userId = userId;
    }

    public LoginResult(Parcel in, String flag, String userId) {
        super(in);
        this.flag = flag;
        this.userId = userId;
    }

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

    @Override
    public String toString() {
        return "LoginResult{" +
                "flag='" + flag + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
