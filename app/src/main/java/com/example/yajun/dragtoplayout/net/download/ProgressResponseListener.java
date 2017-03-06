package com.example.yajun.dragtoplayout.net.download;

/**
 * Created by yajun on 2016/5/27.
 * 响应体进度回调接口，比如用于文件下载中
 */
public interface  ProgressResponseListener {
    //这个是非ui线程回调，不可直接操作UI
    void onResponseProgress(long bytesRead, long contentLength, boolean done);

}
