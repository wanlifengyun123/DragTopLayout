package com.example.yajun.dragtoplayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/10.
 *
 * base adapter for listview
 *
 * must implement getLayoutId/display and viewholder
 *
 */
public abstract class ListBaseAdapter<T> extends BaseAdapter {

    protected Context context;
    protected LayoutInflater inflater;

    List<T> data ;

    public ListBaseAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * NOTIC
     * setData# setAdataper 配对使用,防止因为数据源的改变而notifiydatachanged无效
     * 或者在初始中调用setAdapter之前使用
     * */
    public void setData(List<T> data){
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder baseViewHolder = null;
        if (convertView == null){
            convertView = inflater.inflate(getLayoutId(), null);
            baseViewHolder = getViewHolder(convertView);
            convertView.setTag(baseViewHolder);
        }else{
            baseViewHolder = (BaseViewHolder) convertView.getTag();
        }
        display(position, baseViewHolder);
        return convertView;
    }

    public abstract int getLayoutId();
    public abstract void display(int position, BaseViewHolder viewHolder);
    public abstract BaseViewHolder getViewHolder(View view);

    public abstract class BaseViewHolder{
        public BaseViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}

