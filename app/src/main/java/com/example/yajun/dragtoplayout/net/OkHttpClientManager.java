package com.example.yajun.dragtoplayout.net;

import android.content.Context;

import com.example.yajun.dragtoplayout.base.App;
import com.example.yajun.dragtoplayout.common.RequestParams;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yajun on 2016/5/25.
 */
public class OkHttpClientManager {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8"); //post上传json数据
    public static final MediaType TEXT = MediaType.parse("text/plain;charset=utf-8");//post上传字符串数据
    public static final MediaType STREAM = MediaType.parse("application/octet-stream");//post上传字节数组

    private Context context;
    private Gson mGson;
    private OkHttpClient okHttpClient;

    private static OkHttpClientManager mInstance;

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    public OkHttpClientManager() {
        this.context = App.getInstance();
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .proxy(Proxy.NO_PROXY);
        mGson = new Gson();
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return Response
     */
    private Response _getAsync(String url) throws IOException {
        final Request request = new Request.Builder()
                .tag(url)
                .url(url)
                .build();
        return okHttpClient.newCall(request).execute();
    }

    /**
     * 同步的Get请求
     *
     * @param url
     * @return 字符串
     */
    private String _getAsString(String url) throws IOException {
        Response response = _getAsync(url);
        if (response.isSuccessful()) {
            return response.body().string();
        }
        return null;
    }

    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return
     */
    private Response _post(String url, RequestParams params) throws IOException {
        Request request = new Request.Builder()
                .tag(url)
                .url(url)
                .post(params.toRequestBody())
                .build();
        return okHttpClient.newCall(request).execute();
    }

    /**
     * 同步的Post请求
     *
     * @param url
     * @param jsonStr post的参数
     * @return
     */
    private Response _post(String url, String jsonStr) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder()
                .tag(url)
                .url(url)
                .post(requestBody)
                .build();
        return okHttpClient.newCall(request).execute();
    }

    /**
     * 同步的Post请求
     *
     * @param url
     * @param jsonStr post的参数
     * @return 字符串
     */
    private String _postAsString(String url, String jsonStr) throws IOException {
        Response response = _post(url, jsonStr);
        if (response.isSuccessful()) {
            return response.body().string();
        }
        return null;
    }

    /**
     * 同步的Post请求
     *
     * @param url
     * @param params post的参数
     * @return 字符串
     */
    private String _postAsString(String url, RequestParams params) throws IOException {
        Response response = _post(url, params);
        if (response.isSuccessful()) {
            return response.body().string();
        }
        return null;
    }

//    public void execute(Response response) {
//        try {
//            int code = response.code();
//            String responseString = "without response body";
//            if (response.body() != null)
//                responseString = response.body().string();
//
//            if (response.isSuccessful())
//                mResponseHandler.sendSuccess(code, responseString);
//            else
//                mResponseHandler.sendFailure(code, responseString);
//        } catch (IOException e) {
//            String error = "unknown";
//            if (e.getMessage() != null) error = e.getMessage();
//
//            if (error.equals("Canceled"))
//                mResponseHandler.sendCancel();
//            else
//                mResponseHandler.sendFailure(0, error);
//        }
//    }

    public static Response doGet(String url) throws IOException {
        return getInstance()._getAsync(url);
    }

    public static String doGetString(String url) throws IOException {
        return getInstance()._getAsString(url);
    }

    public static Response doPost(String url, RequestParams params) throws IOException {
        return getInstance()._post(url, params);
    }

    public static Response doPost(String url, String jsonStr) throws IOException {
        return getInstance()._post(url, jsonStr);
    }

    public static String doPostString(String url, RequestParams params) throws IOException {
        return getInstance()._postAsString(url, params);
    }

    public static String doPostString(String url, String jsonStr) throws IOException {
        return getInstance()._postAsString(url, jsonStr);
    }

    /**
     * 异步下载文件
     *
     * @param url
     * @param destFileDir 本地文件存储的文件夹
     */
    private void _downloadAsyn(final String url, final String destFileDir) {//final ResultCallback callback
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    /**
     * 根据Tag取消请求
     */
    public void cancelTag(Object tag) {
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : okHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}
