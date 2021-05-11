package com.bdxh.librarybase.base;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseResult<T> implements Parcelable {

    private int code;
    private String msg;
    private String token;
    private T data ;

    public boolean isOk() {
        return code == 1;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(code);
        parcel.writeString(msg);
        parcel.writeString(token);
        parcel.writeString(data.getClass().getName());
        parcel.writeParcelable((Parcelable) data,i);
    }

    protected BaseResult(Parcel in) {
        code = in.readInt();
        token = in.readString();
        msg = in.readString() ;
        String dataName = in.readString();
        try {
            data = in.readParcelable(Class.forName(dataName).getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<BaseResult> CREATOR = new Creator<BaseResult>() {
        @Override
        public BaseResult createFromParcel(Parcel in) {
            return new BaseResult(in);
        }

        @Override
        public BaseResult[] newArray(int size) {
            return new BaseResult[size];
        }
    };
}
