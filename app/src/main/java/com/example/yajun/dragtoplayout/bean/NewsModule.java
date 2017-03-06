package com.example.yajun.dragtoplayout.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yajun on 2016/6/7.
 *
 */
public class NewsModule implements Serializable{

    public int type;
    public String name;
    public List<NewsBanner> bannerList;
    public List<News> newsList;

}
