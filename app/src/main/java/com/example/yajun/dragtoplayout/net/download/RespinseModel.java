package com.example.yajun.dragtoplayout.net.download;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by yajun on 2016/5/27.
 */
public class RespinseModel implements Serializable{

    private Call call;

    private Response response;

    public RespinseModel(Call call,Response response){
        this.call = call;
        this.response = response;
    }

    public Call getCall() {
        return call;
    }

    public void setCall(Call call) {
        this.call = call;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
