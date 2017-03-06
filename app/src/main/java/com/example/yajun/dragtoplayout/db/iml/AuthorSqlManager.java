package com.example.yajun.dragtoplayout.db.iml;

import com.example.yajun.dragtoplayout.base.App;
import com.example.yajun.dragtoplayout.db.DBUtil;

import java.util.List;

/**
 * Created by yajun on 2016/5/20.
 *
 */
public class AuthorSqlManager {

    private static DBUtil dbUtil;

    private static AuthorSqlManager instance;

    private AuthorSqlManager() {
        super();
    }

    public static AuthorSqlManager getInstance() {
        if (instance == null) {
            instance = new AuthorSqlManager();
        }
        dbUtil = App.getInstance().getDbUtil();
        return instance;
    }

    /**
     * 保存入数据库
     *
     * @param entity
     */
    public void save(Object entity) {
        dbUtil.save(entity);
        notifyMsgChanged("tongsh");
    }

    /**
     * 保存入数据库
     *
     * @param entities
     */
    public void save(List<? extends Object> entities) {
        if (entities != null) {
            for (Object t : entities) {
                save(t);
            }
        }
    }

    public static void registerMsgObserver(OnMessageChange observer) {
        dbUtil.registerObserver(observer);
    }

    public static void unregisterMsgObserver(OnMessageChange observer) {
        dbUtil.unregisterObserver(observer);
    }

    public static void notifyMsgChanged(String session) {
        dbUtil.notifyChanged(session);
    }
}
