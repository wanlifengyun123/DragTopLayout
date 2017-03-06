package com.example.yajun.dragtoplayout.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yajun.dragtoplayout.R;
import com.example.yajun.dragtoplayout.activity.DetailActivity;
import com.example.yajun.dragtoplayout.adapter.BannerFragmentAdapter;
import com.example.yajun.dragtoplayout.adapter.FragmentItemAdapter;
import com.example.yajun.dragtoplayout.base.BaseFragment;
import com.example.yajun.dragtoplayout.bean.NewsModule;
import com.example.yajun.dragtoplayout.common.WebViewActivity;
import com.example.yajun.dragtoplayout.net.OkAPIClient;
import com.example.yajun.dragtoplayout.util.ToastUtil;
import com.example.yajun.dragtoplayout.weight.swiperefreshlayout.SwipyRefreshLayout;
import com.example.yajun.dragtoplayout.weight.swiperefreshlayout.SwipyRefreshLayoutDirection;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yajun on 2016/5/24.
 */
public class Fragment1 extends BaseFragment {

    private static final String BK_CURRENT_ID = "BK_CURRENT_ID";
    private static final String BK_CURRENT_URL = "BK_CURRENT_URL";

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.swipe_refresh_layout)
    SwipyRefreshLayout refreshLayout;
    @Bind(R.id.view_empty_tv)
    TextView viewEmptyTv;

    private int currentId;
    private FragmentItemAdapter adapter;
    private BannerFragmentAdapter bannerFragmentAdapter;

    private NewsModule newsModule;

    private CompositeSubscription mSubscription = new CompositeSubscription();

    public static Fragment1 getInstance(int position) {
        Fragment1 fragment1 = new Fragment1();
        Bundle bundle = new Bundle();
        bundle.putInt(BK_CURRENT_ID, position);
        fragment1.setArguments(bundle);
        return fragment1;
    }

    @Override
    protected void initBundle(Bundle savedInstanceState, Bundle arguments) {
        Bundle bundle = savedInstanceState != null ? savedInstanceState : getArguments();
        currentId = bundle.getInt(BK_CURRENT_ID);
        if (savedInstanceState != null) {
            newsModule = (NewsModule) bundle.getSerializable("newsModule");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(BK_CURRENT_ID, currentId);
        outState.putSerializable("newsModule", newsModule);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.layout_viewpager, null);
        ViewPager viewPager = (ViewPager) headerView.findViewById(R.id.viewpager);
        bannerFragmentAdapter = new BannerFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(bannerFragmentAdapter);
        listView.addHeaderView(headerView);

        adapter = new FragmentItemAdapter(getActivity());
        listView.setAdapter(adapter);

        refreshLayout.setRefreshing(false);
        refreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                refreshLayout.setRefreshing(false);
                if(direction == SwipyRefreshLayoutDirection.TOP){

                }
            }
        });

        if (newsModule == null) {
            loadData();
        } else {
            bannerFragmentAdapter.setData(newsModule.bannerList);
            adapter.setData(newsModule.newsList);
        }
    }

    @Override
    protected void onUserVisible() {

    }

    private void loadData() {
        showDialog();
        mSubscription.add(OkAPIClient.getInstance().requestFeed(currentId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsModule>() {
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
                    public void onNext(NewsModule newsModule) {
                        bannerFragmentAdapter.setData(newsModule.bannerList);
                        adapter.setData(newsModule.newsList);
                    }

                }));
    }

    @OnItemClick(R.id.listView)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(getActivity(), WebViewActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("inner",adapter.getData().get(position).hrefStr);
//        intent.putExtras(bundle);
//        startActivity(intent);
        ToastUtil.show("position ： " + position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", adapter.getData().get(position - 1));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        mSubscription.unsubscribe();
        super.onDestroy();
    }
}
