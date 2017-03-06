package com.example.yajun.dragtoplayout.db.iml;

import java.util.ArrayList;

/**
 * Created by yajun on 2016/5/20.
 */
public class AbstractObservable<T> {

    protected final ArrayList<T> mObservers = new ArrayList<T>();

    /**
     * 注册观察者
     * @param observer
     */
    public void registerObserver(T observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized(mObservers) {
            if (mObservers.contains(observer)) {
                throw new IllegalStateException("ECObservable " + observer + " is already registered.");
            }
            mObservers.add(observer);
        }
    }

    /**
     * 移除观察
     * @param observer
     */
    public void unregisterObserver(T observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized(mObservers) {
            int index = mObservers.indexOf(observer);
            if (index == -1) {
//                throw new IllegalStateException("ECObservable " + observer + " was not registered.");
                return;
            }
            mObservers.remove(index);
        }
    }

    /**
     * 移除所有观察着
     */
    public void unregisterAll() {
        synchronized(mObservers) {
            mObservers.clear();
        }
    }
}
