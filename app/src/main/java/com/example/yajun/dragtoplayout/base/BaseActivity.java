package com.example.yajun.dragtoplayout.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.yajun.dragtoplayout.common.StatusBarCompat;
import com.example.yajun.dragtoplayout.task.ITaskHandle;
import com.example.yajun.dragtoplayout.task.Tasker;
import com.example.yajun.dragtoplayout.util.ActivityManager;
import com.example.yajun.dragtoplayout.util.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by yajun on 2016/5/24.
 *
 */
public abstract class BaseActivity extends AppCompatActivity implements ITaskHandle{

    private static final String TAG = BaseActivity.class.getSimpleName();

    private final Handler handler = new Handler();
    private boolean isDestroyed;

    private Tasker _tasker;

    public Tasker getTasker() {
        if (_tasker != null) {
            return _tasker;
        }
        return _tasker = new Tasker(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initBundle(savedInstanceState, getIntent());
        ActivityManager.getInstance().addActivity(this);// 加入管理栈
        LogUtils.i("UI--->> onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        toggleTranslucent(false, "#303F9F");
        ButterKnife.bind(this);
        initToolBar();
        init(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    public void handleAction(Message msg){

    }

    protected void initToolBar(){

    }

    protected void initBundle(Bundle savedInstanceState,Intent intent){

    }

    private ProgressDialog mProgressDialog;

    public void showDialog(){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("正在加载数据...");
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    public void hideDialog(){
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.i("UI--->> onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.i("UI--->> onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i("UI--->> onStop()");
    }

    /**
     * 获取handler
     *
     * @return
     */
    public Handler getHandler() {
        return handler;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    public void onDetachedFromWindow() {
        if (_tasker != null) {
            _tasker.cancelTask();
        }
        super.onDetachedFromWindow();
    }

    protected void toggleTranslucent(boolean isTranslucent,String colorStr) {
        if (isTranslucent) {
            StatusBarCompat.translucentStatusBar(this);
        } else {
            StatusBarCompat.setStatusBarColor(this, Color.parseColor(colorStr));
        }
    }

    @Override
    protected void onDestroy() {
        LogUtils.i("UI--->> onDestroy()");
        isDestroyed = true;
        ActivityManager.getInstance().removeActivity(this);// 移除管理栈
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
