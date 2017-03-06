package com.example.yajun.dragtoplayout.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yajun.dragtoplayout.R;
import com.example.yajun.dragtoplayout.base.BaseFragment;
import com.example.yajun.dragtoplayout.bean.NewsBanner;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by 亚军 on 2016/4/27.
 */
public class BannerFragment extends BaseFragment {

    @Bind(R.id.iv_pager)
    ImageView photoView;
    @Bind(R.id.view_pager_tv)
    TextView tvTitle;

    private String Tag = BannerFragment.class.getSimpleName();

    private NewsBanner banner;

    public static BannerFragment getInstance(NewsBanner banner) {
        BannerFragment bannerFragment = new BannerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("banner", banner);
        bannerFragment.setArguments(bundle);
        return bannerFragment;
    }

    @Override
    protected void initBundle(Bundle savedInstanceState, Bundle arguments) {
        Bundle bundle = savedInstanceState == null ? getArguments() : savedInstanceState;
        banner = (NewsBanner) bundle.getSerializable("banner");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("banner", banner);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_fragment_pager;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTitle.setText(banner.title);
        Glide.with(getActivity())
                .load(banner.image)
                .centerCrop()
                .crossFade()
                .into(photoView);
    }

    @Override
    protected void onUserVisible() {

    }
}
