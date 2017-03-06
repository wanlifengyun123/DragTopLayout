package com.example.yajun.dragtoplayout.common;

import android.util.Log;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InterruptedIOException;

/**
 * 网络请求通用结果集对象
 */
public class ResultEntity<T> {

    private static final String TAG = "ResultEntity.LOG";

    /**
     * 返回成功
     */
    public static final int RESULT_SUCCESS =1;

    public static final String HTTP_EXCEPTION_MSG = "网络连接异常,错误代码为:";


    /**
     * 网络连接设置错误 code
     */
    public static final String ECODE_HTTP_EXCEPTION = "500";

    /**
     * 网络连接设置错误 code
     */
    public static final String ECODE_HTTP_EXCEPTION_HOST_CONNECT = "501";

    /**
     *  网络连接超时 code
     */
    public static final String ECODE_HTTP_EXCEPTION_TIMEOUT  = "502";
    /**
     *  网络返回json格式非法
     */
    public static final String ECODE_JSON_ILLEGAL= "503";


    /**
     * result : {"authCode":452362}
     * resultMSG : 请求成功
     * resultCode : 1
     */

    public T result;

    public String resultMSG;

    public int resultCode;

    public void setResult(T result) {
        this.result = result;
    }

    public void setResultMSG(String resultMSG) {
        this.resultMSG = resultMSG;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public T getResult() {
        return result;
    }

    public String getResultMSG() {
        return resultMSG;
    }

    public int getResultCode() {
        return resultCode;
    }

    public boolean isSuccess(){
        return this.resultCode == RESULT_SUCCESS;
    }

    public ResultEntity(Exception e){
        Log.i(TAG, e.toString());
       if(e instanceof IOException){
           String code = ECODE_HTTP_EXCEPTION;
           if(e instanceof InterruptedIOException){
               if(e.getCause() != null){
                   if(e.getCause().equals("timeout")){
                       code = ECODE_HTTP_EXCEPTION_TIMEOUT;
                   }else{
                   }
               }
           }
           this.setResultMSG(HTTP_EXCEPTION_MSG + code);
        }else if(e instanceof JsonSyntaxException){
            String code = ECODE_JSON_ILLEGAL;
            this.setResultMSG(HTTP_EXCEPTION_MSG+code);
            this.setResultCode(0);
        }else{
            this.setResultMSG(e.getMessage());
            this.setResultCode(0);
        }
    }

}
