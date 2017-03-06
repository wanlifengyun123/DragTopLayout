package com.example.yajun.dragtoplayout.common;

/**
 * Created by yajun on 2016/5/26.
 */
public class ResponseResult<T> {

    public static interface ResponseCode{
        int SUCCESS = 1;
        int FAILT = 2;
        int ERROR = 3;
    }

    private int code;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
