package com.example.yajun.dragtoplayout.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yajun.dragtoplayout.task.ITaskHandle;
import com.example.yajun.dragtoplayout.task.Tasker;

import butterknife.ButterKnife;

/**
 * Created by yajun on 2016/5/24.
 *
 */
public abstract class BaseFragment extends Fragment implements ITaskHandle{

    public BaseActivity activity;
    private Tasker _tasker;

    public Tasker getTasker() {
        if (_tasker != null) {
            return _tasker;
        }
        return _tasker = new Tasker(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initBundle(savedInstanceState, getArguments());
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, fragmentView);
        init(savedInstanceState);
        return fragmentView;
    }

    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    public void handleAction(Message msg){

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onUserVisible();
        }
    }

    /**
     * 当fragment对用户可见时，会调用该方法，可在该方法中懒加载网络数据
     */
    protected void onUserVisible(){

    }

    protected void initBundle(Bundle savedInstanceState, Bundle arguments) {

    }

    private ProgressDialog mProgressDialog;

    public void showDialog(){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(activity);
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
    public void onDetach() {
        if (_tasker != null) {
            _tasker.cancelTask();
        }
        super.onDetach();
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
