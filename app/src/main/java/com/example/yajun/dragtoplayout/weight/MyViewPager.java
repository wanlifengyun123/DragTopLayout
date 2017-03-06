package com.example.yajun.dragtoplayout.weight;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 亚军 on 2016/5/3.
 * menu 事件拦截 自动轮播
 */
public class MyViewPager extends ViewPager {

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            stopPlay();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            startPlay();
        }
        return super.dispatchTouchEvent(event);
    }

    private Runnable viewPagerLoopRunnable = new Runnable() {
        @Override
        public void run() {
            if (getAdapter() == null || getAdapter().getCount() < 2) {
                return;
            }
            int i = getCurrentItem();
            if (i == getAdapter().getCount() - 1) {
                i = 0;
            } else {
                i++;
            }
            setCurrentItem(i, true);
            postDelayed(viewPagerLoopRunnable, 3000);
        }
    };

    /**
     * 自动轮播.
     */
    public void startPlay() {
        if (this.getAdapter().getCount() <= 1) {
            return;
        }
        stopPlay();
        this.postDelayed(viewPagerLoopRunnable, 3000);
    }


    public void stopPlay() {
        if (viewPagerLoopRunnable != null) {
            this.removeCallbacks(viewPagerLoopRunnable);
        }
    }

}
