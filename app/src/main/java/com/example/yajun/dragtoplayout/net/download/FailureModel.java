package com.example.yajun.dragtoplayout.net.download;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;

/**
 * Created by yajun on 2016/5/27.
 */
public class FailureModel implements Serializable{

    private Call call;

    private IOException exception;

    public FailureModel(Call call,IOException exception){
        this.call = call;
        this.exception = exception;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public IOException getException() {
        return exception;
    }

    public void setException(IOException exception) {
        this.exception = exception;
    }
}
