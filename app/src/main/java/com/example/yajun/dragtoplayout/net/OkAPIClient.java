package com.example.yajun.dragtoplayout.net;

import com.example.yajun.dragtoplayout.bean.News;
import com.example.yajun.dragtoplayout.bean.NewsBanner;
import com.example.yajun.dragtoplayout.bean.NewsItem;
import com.example.yajun.dragtoplayout.bean.NewsModule;
import com.example.yajun.dragtoplayout.util.UrlUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.OnErrorFailedException;
import rx.functions.Func1;

/**
 * Created by rakuishi on 15/05/02.
 */
public class OkAPIClient {

    public static final String TAG = OkAPIClient.class.getSimpleName();

    private static final OkAPIClient instance = new OkAPIClient();
    private OkHttpClient mOkHttpClient;
    private Serializer mSerializer;
    private Gson mGson;

    private OkAPIClient() {
        mOkHttpClient = new OkHttpClient();
        mSerializer = new Persister();
        mGson = new Gson();
        mOkHttpClient.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .proxy(Proxy.NO_PROXY);
    }

    public static OkAPIClient getInstance() {
        return OkAPIClient.instance;
    }

    public Observable<Response> getResponse(final Request request) {
        return Observable.create(new Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try {
                    Response response = mOkHttpClient.newCall(request).execute();
                    subscriber.onNext(response);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<NewsModule> requestFeed(int position) {
        Request request = new Request.Builder()
                .header("Cache-Control", "public, max-age=" + 60)
                .url(UrlUtil.CHANNEL_URL_2[position])
                .get()
                .build();
        return getResponse(request)
                .map(convertResponseToObject(UrlUtil.CHANNEL_URL_2[position]));
    }

    public Observable<NewsItem> requestDetail(String url) {
        Request request = new Request.Builder()
                .header("Cache-Control", "public, max-age=" + 60)
                .url(url)
                .get()
                .build();
        return getResponse(request)
                .map(convertResponseToObject());
    }

//    public Observable<List<Repo>> requestRepos() {
//        Request request = new Request.Builder()
//                .url("https://api.github.com/users/rakuishi/repos")
//                .get()
//                .build();
//        return getResponse(request)
//                .map(convertJSONResponseToObject(mGson, new TypeToken<List<Repo>>() {
//                }));
//    }
//
//    public Observable<List<Gist>> requestGists() {
//        Request request = new Request.Builder()
//                .url("https://api.github.com/users/rakuishi/gists")
//                .get()
//                .build();
//        return getResponse(request)
//                .map(convertJSONResponseToObject(mGson, new TypeToken<List<Gist>>() {
//                }));
//    }

    private static <T> Func1<Response, T> convertXMLResponseToObject(final Serializer serializer, final Class<T> clazz) {
        return new Func1<Response, T>() {
            @Override
            public T call(Response response) {
                try {
                    return serializer.read(clazz, response.body().string());
                } catch (Exception e) {
                    throw new OnErrorFailedException(e);
                }
            }
        };
    }

    private static <T> Func1<Response, T> convertJSONResponseToObject(final Gson gson, final TypeToken<T> typeToken) {
        return new Func1<Response, T>() {
            @Override
            public T call(Response response) {
                try {
                    JsonElement element = gson.fromJson(response.body().string(), JsonElement.class);
                    return gson.fromJson(element, typeToken.getType());
                } catch (Exception e) {
                    throw new OnErrorFailedException(e);
                }
            }
        };
    }

    private static Func1<Response, NewsModule> convertResponseToObject(final String url) {
        return new Func1<Response, NewsModule>() {
            @Override
            public NewsModule call(Response response) {
                try {
                    NewsModule module = new NewsModule();
                    String result = response.body().string();
                    module.bannerList = NewsBanner.getNewsBannerList(result);
                    module.newsList = News.getNewsList(result);
                    return module;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            }
        };
    }

    private static Func1<Response, NewsItem> convertResponseToObject() {
        return new Func1<Response, NewsItem>() {
            @Override
            public NewsItem call(Response response) {
                try {
                    String result = response.body().string();
                    return NewsItem.getNewsItem(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            }
        };
    }

}
