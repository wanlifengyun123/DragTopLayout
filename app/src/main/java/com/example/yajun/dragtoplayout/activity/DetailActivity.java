package com.example.yajun.dragtoplayout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yajun.dragtoplayout.R;
import com.example.yajun.dragtoplayout.adapter.DetailItemAdapter;
import com.example.yajun.dragtoplayout.base.BaseActivity;
import com.example.yajun.dragtoplayout.bean.NItem;
import com.example.yajun.dragtoplayout.bean.News;
import com.example.yajun.dragtoplayout.bean.NewsBanner;
import com.example.yajun.dragtoplayout.bean.NewsItem;
import com.example.yajun.dragtoplayout.bean.NewsModule;
import com.example.yajun.dragtoplayout.fragment.Fragment1;
import com.example.yajun.dragtoplayout.net.OkAPIClient;
import com.example.yajun.dragtoplayout.util.ToastUtil;
import com.example.yajun.dragtoplayout.util.UrlUtil;
import com.example.yajun.dragtoplayout.weight.ComListView;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class DetailActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.headerbar)
    LinearLayout headerbar;

    @Bind(R.id.tv_detail_title)
    TextView tvTitle;
    @Bind(R.id.tv_detail_date)
    TextView tvContext;
    @Bind(R.id.listView)
    ComListView listView;

    private CompositeSubscription mSubscription = new CompositeSubscription();
    private News news;

    DetailItemAdapter adapter;

    List<NItem> data;

    @Override
    protected void initBundle(Bundle savedInstanceState, Intent intent) {
        Bundle bundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        news = (News) bundle.getSerializable("news");
        if(savedInstanceState != null){
            data = (List<NItem>) bundle.getSerializable("ITEM_NEWS");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("news",news);
        outState.putSerializable("ITEM_NEWS", (Serializable) data);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (toolbar != null) {
            toolbar.setTitle("Google I/O Test");
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        adapter = new DetailItemAdapter(this);
        listView.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        showDialog();
        mSubscription.add(OkAPIClient.getInstance().requestDetail(news.hrefStr)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsItem>() {
                    @Override
                    public void onCompleted() {
                        hideDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        ToastUtil.show("Error:" + e.getMessage());
                    }

                    @Override
                    public void onNext(NewsItem newsModule) {
                        if(newsModule != null){
                            tvTitle.setText(newsModule.title);
                            tvContext.setText(newsModule.date + " " + newsModule.source);
                        }
                        data = newsModule.nItems;
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    }

                }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_search:
                        return true;
                }
                return false;
            }
        });
        return true;
    }
}
