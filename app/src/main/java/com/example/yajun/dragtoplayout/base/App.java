/*
 * Copyright 2015 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.yajun.dragtoplayout.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.yajun.dragtoplayout.db.DBUtil;
import com.example.yajun.dragtoplayout.net.OkHttpUtil;


/**
 * {@link Application} used to initialize Analytics. Code initialized in
 * Application classes is rare since this code will be run any time a ContentProvider, Activity,
 * or Service is used by the user or system. Analytics, dependency injection, and multi-dex
 * frameworks are in this very small set of use cases.
 */
public class App extends Application {
    
    public static App instance;

    private DBUtil dbUtil;

    public synchronized static App getInstance(){
        if(instance == null){
            new Throwable("App is not init");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }

    public DBUtil getDbUtil() {
        if(dbUtil == null){
            dbUtil = DBUtil.create(this,new DBUtil.DbUpdateListener(){

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//                    db.beginTransaction();
//                    db.execSQL("alter table author add bytes varchar(1000)");
//                    db.endTransaction();
                }
            });
        }
        return dbUtil;
    }

}
