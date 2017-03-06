package com.example.yajun.dragtoplayout.bean;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yajun on 2016/6/7.
 */
public class NewsBanner implements Serializable{

    public int id;
    public String title;
    public String image;//图片连接
    public String inner;//点击连接

    public static List<NewsBanner> getNewsBannerList(String str){

        List<NewsBanner> list = new ArrayList<NewsBanner>();
        // 获取文档对象
        Document doc = Jsoup.parseBodyFragment(str);
//        <div class="gallery">
//        <a href="http://xw.qq.com/news/20160607019598/NEW2016060701959805" class="galleryinner">
//        <div class="galleryimage">
//        <img src="http://inews.gtimg.com/newsapp_ls/0/339527184_640330/0">
//        </div>
//        <h2 class="gallerytitle">血汗工厂：为IS缝制服的难民童工</h2>
//        </a>
//        </div>
        Elements bannerList = doc.getElementsByClass("gallery");
        for (int i = 0; i < bannerList.size(); i++) {
            Element element = bannerList.get(i);
            NewsBanner banner = new NewsBanner();
            banner.id = i;
            banner.title = element.getElementsByClass("gallerytitle").text();
            banner.image = element.getElementsByTag("img").get(0).attr("src");
            banner.inner = element.getElementsByClass("galleryinner").attr("href");
            list.add(banner);
        }
//        for (NewsBanner banner : list) {
//            Log.d("ddd","banner :" + banner.toString());
//        }
        return list;
    }

    @Override
    public String toString() {
        return "NewsBanner{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", inner='" + inner + '\'' +
                '}';
    }
}
