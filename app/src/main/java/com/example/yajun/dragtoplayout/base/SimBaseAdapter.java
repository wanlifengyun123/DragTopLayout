package com.example.yajun.dragtoplayout.base;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by yajun on 2016/5/18.
 */
public class SimBaseAdapter<T> extends BaseAdapter{

    public Activity activity;
    public Fragment fragment;


    public List<T> data;
    public Resources res;

    public SimBaseAdapter(Activity activity) {
        this.activity = activity;
        this.res = activity.getResources();
    }

    public SimBaseAdapter(Fragment fragment) {
        this.fragment = fragment;
        this.activity = fragment.getActivity();
        this.res = activity.getResources();
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void setData(List<T> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }


    public List<T> getData() {
        return data;
    }

    public void addData(List<T> data) {
        this.data.addAll(data);
        this.notifyDataSetChanged();
    }


    public void refreshDatas(List<T> data) {
        this.data = data;
        this.notifyDataSetInvalidated();
    }

}
