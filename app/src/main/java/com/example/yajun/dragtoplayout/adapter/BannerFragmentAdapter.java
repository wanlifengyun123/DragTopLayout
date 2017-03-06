package com.example.yajun.dragtoplayout.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.example.yajun.dragtoplayout.bean.NewsBanner;
import com.example.yajun.dragtoplayout.fragment.BannerFragment;

import java.util.List;

/**
 * Created by 亚军 on 2016/4/27.
 *
 */
public class BannerFragmentAdapter extends FragmentPagerAdapter {

    private List<NewsBanner> data;
    private FragmentManager fragmentManager;

    public BannerFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return BannerFragment.getInstance(data.get(position));
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<NewsBanner> data) {
        //清空缓存
        if(fragmentManager.getFragments() != null){
            FragmentTransaction ft = fragmentManager.beginTransaction();
            for (Fragment fragment : fragmentManager.getFragments()) {
                ft.remove(fragment);
            }
            ft.commit();
            fragmentManager.executePendingTransactions();
        }
        this.data = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
