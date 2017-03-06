/*
 * Copyright 2015 Eric Liu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.yajun.dragtoplayout.common;

import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Eric on 15/6/10.
 */
public class RequestParams {

    private final HashMap<String, String> params;

    private final HashMap<String, File> paramFiles;

    public RequestParams() {
        params = new HashMap<>();
        paramFiles = new HashMap<>();
    }

    public void put(String key, String value) {
        params.put(key, value);
    }

    public void put(String key, Object value) {
        params.put(key, value.toString());
    }

    public void put(String key, File value) {
        paramFiles.put(key, value);
    }

    public void remove(String key) {
        params.remove(key);
        paramFiles.remove(key);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (HashMap.Entry<String, String> entry : params.entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }

            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());
        }

        return stringBuilder.toString();
    }

    /**
     * 创建 GET url
     * @param url
     * @return
     */
    public String toQueryString(String url) {
        String queryString = toString();

        if (!TextUtils.isEmpty(queryString)) {
            url += url.contains("?") ? "&" : "?";
            url += queryString;
        }
        return url;
    }

    /**
     * 获取POT 表单
     * @return
     */
    public RequestBody toRequestBody() {
        if (paramFiles == null || paramFiles.size() == 0) {
            //表单提交，没有文件
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            for (String key : params.keySet()) {
                bodyBuilder.add(key, params.get(key));
            }
            return bodyBuilder.build();
        } else {
            //表单提交，有文件 /添加非文件参数
            MultipartBody.Builder multipartBodybuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            //拼接键值对
            if (!params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    multipartBodybuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }
            //拼接文件
            for (Map.Entry<String, File> fileEntry : paramFiles.entrySet()) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), fileEntry.getValue());
                multipartBodybuilder.addFormDataPart(fileEntry.getKey(), fileEntry.getValue().getName(), fileBody);
            }
            return multipartBodybuilder.build();
        }
    }

}
