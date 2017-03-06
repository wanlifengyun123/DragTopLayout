package com.example.yajun.dragtoplayout.bean;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yajun on 2016/6/7.
 */
public class News implements Serializable {

    public int id;
    public int type;
    public String title;
    public String content;
    public String thumb;
    public String hrefStr;
    public String count;
    public String flag;

    public static List<News> getNewsList(String str) throws IOException {

        List<News> list = new ArrayList<News>();
        // 获取文档对象
//        Document doc = Jsoup.parseBodyFragment(str);
//        Document document = Jsoup.connect(str).timeout(5000).get();
        Document doc = Jsoup.parse(str);

        Elements newsList = doc.getElementsByClass("list");
        for (int i = 0; i < newsList.size(); i++) {
            Element element = newsList.get(i);
            Elements elementsByTag = element.getElementsByTag("li");
            for (int j = 0; j < elementsByTag.size(); j++) {
                Element elementLi = elementsByTag.get(j);
                News news = new News();
                if (TextUtils.isEmpty(elementLi.getElementsByClass("photo").text())) {
                    news.type = 1;
                } else {
                    news.type = 2;
                }
                news.id = j;
                news.title = elementLi.select("h2").text();
                news.content = elementLi.select("p").text();
                news.thumb = elementLi.getElementsByClass("thumb").get(0).getElementsByTag("img").attr("src");
                news.hrefStr = elementLi.select("a").attr("href");
                news.count = elementLi.getElementsByClass("count").get(0).text();
                String stringCount = elementLi.select("i").toString();
                if(stringCount.contains("flag")){
                    if(stringCount.contains("flag0")){//网页
                        news.flag = "网页";
                    }else if(stringCount.contains("flag3")){//视频
                        news.flag = "视频";
                    }else if(stringCount.contains("flag4")){//专题
                        news.flag = "专题";
                    }else if(stringCount.contains("flag")){//独家
                        news.flag = "独家";
                    }else {//其他
                        news.flag = "其他";
                    }
                }
                list.add(news);
            }
        }
//        for (News news : list) {
//            Log.d("ddd", "banner :" + news.toString());
//        }
        return list;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", thumb='" + thumb + '\'' +
                ", hrefStr='" + hrefStr + '\'' +
                ", count='" + count + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
