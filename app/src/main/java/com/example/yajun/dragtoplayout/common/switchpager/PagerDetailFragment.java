package com.example.yajun.dragtoplayout.common.switchpager;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.yajun.dragtoplayout.R;
import com.example.yajun.dragtoplayout.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2015/10/30.
 */
public class PagerDetailFragment extends BaseFragment {

    private static final String BK_LOCAL_FILE = "bundle_local_file";

    @Bind(R.id.item_pager_detail_iv)
    PhotoView photoView;

    private String localFile;

    public static PagerDetailFragment getInstance(String localFile) {
        PagerDetailFragment pagerDetailFragment = new PagerDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BK_LOCAL_FILE, localFile);
        pagerDetailFragment.setArguments(bundle);
        return pagerDetailFragment;
    }

    @Override
    protected void initBundle(Bundle savedInstanceState, Bundle arguments) {
        Bundle bundle = savedInstanceState != null ? savedInstanceState : getArguments();
        localFile = bundle.getString(BK_LOCAL_FILE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(BK_LOCAL_FILE,localFile);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_switch_pager_detail;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
    }

    public void initView() {
        Glide.with(this)
                   .load("file://" + localFile)
                   .centerCrop()
                   .crossFade()
                   .into(photoView);

        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onUserVisible() {

    }
}
