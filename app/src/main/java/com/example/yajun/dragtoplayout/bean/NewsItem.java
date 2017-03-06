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
public class NewsItem implements Serializable {


    public String title;
    public String date;
    public String source;
    public List<NItem> nItems;

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", source='" + source + '\'' +
                ", nItems=" + nItems +
                '}';
    }

    public static NewsItem getNewsItem(String string){
        NewsItem newsItem = new NewsItem();
        List<NItem> data = new ArrayList<>();
        Document doc = Jsoup.parseBodyFragment(string);

        Element primary = doc.getElementsByClass("primary").get(0);
        newsItem.title = primary.getElementsByClass("title").text();
        newsItem.date = primary.getElementsByClass("time").text();
        newsItem.source = primary.getElementsByClass("author").text();
        Elements content = primary.getElementsByClass("content").get(0).children();
        for (int i = 0; i < content.size(); i++) {
            Element contentImg = content.get(i);
            NItem nItem = new NItem();
            if(contentImg.tagName().equals("div")){
                nItem.type = "img";
                nItem.imgSrc = contentImg.getElementsByTag("img").attr("src");
                data.add(nItem);
            }else if(contentImg.tagName().equals("p")){
                nItem.type = "p";
                nItem.imgTxt = contentImg.select("p").text();
                data.add(nItem);
            }
        }
        newsItem.nItems = data;
        for (NItem nItem : newsItem.nItems) {
            Log.d("ddd","nItem:" + nItem.toString());
        }
        return newsItem;
    }

}
