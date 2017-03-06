package com.example.yajun.dragtoplayout.common.switchpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/10/30.
 */
public class PagerDetailAdapter extends FragmentStatePagerAdapter {

    private List<String> data;

    public PagerDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PagerDetailFragment.getInstance(data.get(position));
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<String> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }
}
