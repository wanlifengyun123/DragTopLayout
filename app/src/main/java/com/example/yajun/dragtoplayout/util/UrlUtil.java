package com.example.yajun.dragtoplayout.util;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class UrlUtil {

	public static String[] CHANNEL_URL = new String[] {
		"http://news.qq.com/society_index.shtml"   ,   //社会    //<a href="http://news.qq.com/society_index.shtml"    target="_blank" id="navlinkSociety" bosszone="topnav5">  社会  </a>
		"http://news.qq.com/photo.shtml"           ,   //图片    //<a href="http://news.qq.com/photo.shtml"            target="_blank" id="navlinkPhoto" bosszone="topnav6">    图片  </a>
		"http://v.qq.com/news/index.html"          ,   //视频 true    //<a href="http://v.qq.com/news/index.html"           target="_blank" id="navlinkVideo" bosszone="topnav7">    视频  </a>
		"http://mil.qq.com/"                       ,   //军事    //<a href="http://mil.qq.com/"                        target="_blank" id="navlinkMil" bosszone="topnav8">      军事  </a>
		"http://cul.qq.com/"                       ,   //文化    //<a href="http://cul.qq.com/"                        target="_blank" id="navlinkWh" bosszone="topnav11">      文化  </a>
		"http://news.qq.com/newspedia/all.htm"     ,   //百科    //<a href="http://news.qq.com/newspedia/all.htm"      target="_blank" id="navlinkWiki" bosszone="topnav11">    百科  </a>
		"http://gongyi.qq.com/"                    ,   //公益    //<a href="http://gongyi.qq.com/"                     target="_blank" id="navlinkGy" bosszone="topnav12">      公益  </a>
		"http://city.qq.com/"                      ,   //城市    //<a href="http://city.qq.com/"                       target="_blank" id="navlinkCs" bosszone="topnav13">      城市  </a>
		"http://news.qq.com/zt2015/wxghz/index.htm",   //新闻哥  //<a href="http://news.qq.com/zt2015/wxghz/index.htm" target="_blank" id="navlinkXWG" bosszone="topnav14">     新闻哥</a>
		"http://history.news.qq.com/"              ,   //历史    //<a href="http://history.news.qq.com/"               target="_blank" id="navlinkHistory" bosszone="topnav10"> 历史  </a>
	};

	public static String[] CHANNEL_URL_2 = new String[]{
			"http://xw.qq.com/m/news/",     //新闻
			"http://xw.qq.com/m/shehui/",   //社会
			"http://xw.qq.com/m/mil/",      //军事
			"http://xw.qq.com/m/ent/",      //娱乐
			"http://xw.qq.com/m/tech/"      //科技
	};

	public static String[] CHANNEL_NAME = new String[]{
			"新闻",
			"社会",
			"军事",
			"娱乐",
			"科技",
	};

	static String[] ignoreKeys=new String[]{
			"浏览原图",
			"视频下载",
	};

	public static String parserContent(String content) {
		String reslut = "";
		//过滤正文之前的内容
		if(content.contains("<span class=\"imgMessage\">")){
			int index = content.lastIndexOf("<span class=\"imgMessage\">");
			content = content.substring(index, content.length());
		}
		
		content = "<html>"+content+"</html>";
		NodeFilter contentFilter = new TagNameFilter("html");
		try {
			Parser imgParser = new Parser();
			imgParser.setResource(content);
			NodeList imgList = imgParser.extractAllNodesThatMatch(contentFilter);
			
			reslut = imgList.asString();
			for (int i = 0; i < ignoreKeys.length; i++) {
				if (reslut.contains(ignoreKeys[i])) {
					int index = reslut.indexOf(ignoreKeys[i]);
					reslut = reslut.substring(index + 8, reslut.length());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reslut;
	}

	public static String parserImgUrl(String content) {
		String reslut = "";
		NodeFilter contentFilter = new AndFilter(
				new TagNameFilter("div"),
				new HasAttributeFilter("class","picbox"));
		try {
			Parser imgParser = new Parser();
			imgParser.setResource(content);
			NodeList imgList = imgParser.extractAllNodesThatMatch(contentFilter);
			String bodyString = imgList.toHtml();
			if (bodyString.contains("<img") && bodyString.contains("src=")) {
				String imgString = imgList.elementAt(0).toHtml();
				int imglinkstart = imgString.indexOf("src=\"");
				int imglinkend = imgString.indexOf(">");
				if (imgString.contains("\" alt=")) {
					imglinkend = imgString.indexOf("\" alt=");
				}
				reslut = imgString.substring(imglinkstart + 5, imglinkend);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reslut;
	}
	
	public static String dateToString(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.PRC);
		Date tDate = new Date(date);
		String dateString = tDate.toLocaleString();
		try {
			dateString=sdf.format(tDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateString;
	}
}
