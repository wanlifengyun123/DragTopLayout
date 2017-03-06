package com.example.yajun.dragtoplayout.net;

import android.text.TextUtils;
import android.widget.ProgressBar;

import com.example.yajun.dragtoplayout.common.RequestParams;
import com.example.yajun.dragtoplayout.net.download.ProgressHelper;
import com.example.yajun.dragtoplayout.net.download.ProgressRequestBody;
import com.example.yajun.dragtoplayout.net.download.ProgressRequestListener;
import com.example.yajun.dragtoplayout.net.download.ProgressResponseListener;
import com.example.yajun.dragtoplayout.net.download.UIProgressRequestListener;
import com.example.yajun.dragtoplayout.net.download.UIProgressResponseListener;
import com.example.yajun.dragtoplayout.util.LogUtils;
import com.example.yajun.dragtoplayout.util.NetWorkUtil;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okio.ByteString;

/**
 * Created by yajun on 2016/5/26.
 */
public class OkHttpUtil {

    public static String USER_AGENT = "AsyncOkHttp";

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType STREAM = MediaType.parse("application/octet-stream");

    private OkHttpClient mOkHttpClient;
    private static Object lock = new Object();

    private  static OkHttpUtil instance;

    public static OkHttpUtil getInstance() {
        if(instance == null){
            synchronized (lock){
                instance = new OkHttpUtil();
            }
        }
        return instance;
    }

    public OkHttpUtil() {
        synchronized (OkHttpUtil.class) {
            if (mOkHttpClient == null) {
                mOkHttpClient = new OkHttpClient.Builder()
                        .build();
//                        .proxy(Proxy.NO_PROXY)
//                        .addInterceptor(new LoggingInterceptor())//拦截器 参数输出
//                        .addNetworkInterceptor(new NetworkInterceptor())
//                        .connectTimeout(10, TimeUnit.SECONDS)
//                        .writeTimeout(10, TimeUnit.SECONDS)
//                        .readTimeout(30, TimeUnit.SECONDS)
            }
        }
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 该不会开启异步线程。
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return getInstance().getOkHttpClient().newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
     * @param request
     */
    public static void enqueue(Request request){
        getInstance().getOkHttpClient().newCall(request).enqueue(null);
    }

    /**
     * Create a Request.Builder.
     *
     * @param url the URL of HTTP request.
     * @return
     */
    public Request.Builder createRequestBuilder(String url) {
        Request.Builder builder = new Request.Builder().url(url).tag(url);
        return builder;
    }

    /**
     * GET请求
     * @param url RequestParams.toQueryString(url)
     * @return
     * @throws IOException
     */
    public Response doGet(String url) throws IOException {
        Request.Builder builder = createRequestBuilder(url);
        return execute(builder.build());
    }

    /**
     * Post方式提交String字符串
     * @param url
     * @param postBody
     * @return
     * @throws IOException
     */
    public Response doPost(String url,String postBody) throws IOException {
        Request.Builder builder = createRequestBuilder(url);
        if(isJson(postBody)){
            builder.post(RequestBody.create(MEDIA_TYPE_JSON, postBody));
        }else {
            builder.post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody));
        }
        return execute(builder.build());
    }

    /**
     * Post方式提交流
     * @param url
     * @param byteString
     * @return
     * @throws IOException
     */
    public Response doPost(String url, ByteString byteString) throws IOException {
        Request.Builder builder = createRequestBuilder(url);
        builder.post(RequestBody.create(MEDIA_TYPE_MARKDOWN, byteString));
        return execute(builder.build());
    }

    /**
     * Post方式提交表单和文件
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public Response doPost(String url,RequestParams params) throws IOException {
        Request.Builder builder = createRequestBuilder(url);
        builder.post(params.toRequestBody());
        return execute(builder.build());
    }

    public Response downLoadFile(final String url,RequestParams params, final String destFileDir) throws IOException {
        OkHttpClient cone = ProgressHelper.addProgressResponseListener(mOkHttpClient, new ProgressResponseListener() {
            @Override
            public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
                LogUtils.i("File DownLoad info: ");
                LogUtils.i("File DownLoad info 当前读取字节长度 :" + bytesRead);
                LogUtils.i("File DownLoad info 总字节长度 : " + contentLength);
                LogUtils.i("File DownLoad info 是否下载完成 ：" + (done ? "完成" : "未完成"));
            }
        });
        return doPost(url, params);
    }

    /**
     * Post方式文件下载
     * @param url
     * @param params
     * @param destFileDir
     * @param uiProgressResponseListener
     * @param responseCallback
     */
    public void downLoadFile(final String url,
                              RequestParams params,
                              final String destFileDir,
                              final UIProgressResponseListener uiProgressResponseListener,
                              Callback responseCallback) {
        OkHttpClient cone = ProgressHelper.addProgressResponseListener(mOkHttpClient, new ProgressResponseListener() {
            @Override
            public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
                LogUtils.i("File DownLoad info: ");
                LogUtils.i("File DownLoad info 当前读取字节长度 :" + bytesRead);
                LogUtils.i("File DownLoad info 总字节长度 : " + contentLength);
                LogUtils.i("File DownLoad info 是否下载完成 ：" + (done ? "完成" : "未完成"));
                LogUtils.i("File DownLoad info 下载进度" + (100 * bytesRead) / contentLength + " % done ");
            }
        });
        Request.Builder builder = createRequestBuilder(url);
        builder.post(params.toRequestBody());
        mOkHttpClient.newCall(builder.build()).enqueue(responseCallback);
    }

    /**
     * Post方式文件上传
     * @param url
     * @param params
     * @param uiProgressRequestListener
     * @param downloadProgeress
     * @param responseCallback
     */
    public void upLoadFile(final String url,
                              RequestParams params,
                              final UIProgressRequestListener uiProgressRequestListener,
                              final ProgressBar downloadProgeress,
                              Callback responseCallback) {
        ProgressRequestBody progressRequestBody = ProgressHelper.addProgressRequestListener(params.toRequestBody(), new ProgressRequestListener() {

            @Override
            public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                LogUtils.i("bytesRead:" + bytesWritten);
                LogUtils.i("contentLength:" + contentLength);
                LogUtils.i("done:" + done);
                if (contentLength != -1) {
                    //长度未知的情况下回返回-1
                    LogUtils.i((100 * bytesWritten) / contentLength + "% done");
                }
                LogUtils.e("================================");
                //ui层回调
                downloadProgeress.setProgress((int) ((100 * bytesWritten) / contentLength));
            }
        });
        Request.Builder builder = createRequestBuilder(url);
        builder.post(progressRequestBody);
        mOkHttpClient.newCall(builder.build()).enqueue(responseCallback);
    }

    static class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            LogUtils.i(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            LogUtils.i(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }

    static class NetworkInterceptor implements Interceptor {
        @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (NetWorkUtil.isNetworkConnected()) {
                int maxAge = 60;
                request = request.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28;
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return chain.proceed(request);
        }
    }

    static class HttpAuthenticator implements Authenticator {

        @Override
        public Request authenticate(Route route, Response response) throws IOException {
            LogUtils.i("Authenticating for response: " + response);
            LogUtils.i("Challenges: " + response.challenges());
            String credential = Credentials.basic("jesse", "password1");
            return response.request().newBuilder()
                    .header("Authorization", credential)
                    .build();
        }
    }

    /**
     * 是否是json字符串
     * @param json
     * @return
     */
    public static boolean isJson(String json) {
        if (TextUtils.isEmpty(json)) return false;

        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

    /**
     * 根据Tag取消请求
     */
    public static void cancelTag(Object tag) {
        for (Call call : getInstance().getOkHttpClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getInstance().getOkHttpClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }
}

