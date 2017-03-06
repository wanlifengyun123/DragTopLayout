package com.example.yajun.dragtoplayout.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * simpleBeanAdapter
 */
public abstract class SimpleBeanAdapter<T> extends BaseAdapter {
	public Activity activity;
	public Fragment fragment;


	public List<T> data;
	public Resources res;

	public SimpleBeanAdapter(Activity activity) {
		this.activity = activity;
		this.res = activity.getResources();
	}

	public SimpleBeanAdapter(Fragment fragment) {
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

	public void startActivity(Intent intent){
		if(fragment!=null){
			fragment.startActivity(intent);
		}else{
			activity.startActivity(intent);
		}
	}
	public void startActivity(Intent intent,int requestCode){
		if(fragment!=null){
			fragment.startActivityForResult(intent, requestCode);
		}else{
			activity.startActivityForResult(intent, requestCode);
		}
	}

}
