package com.example.yajun.dragtoplayout.net.download;

/**
 * Created by yajun on 2016/5/27.
 * 请求体进度回调接口，比如用于文件上传中
 */
public interface ProgressRequestListener {
    //这个是ui线程回调，可直接操作UI
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
