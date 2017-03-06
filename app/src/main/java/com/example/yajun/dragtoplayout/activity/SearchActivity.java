package com.example.yajun.dragtoplayout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yajun.dragtoplayout.R;
import com.example.yajun.dragtoplayout.base.BaseActivity;
import com.example.yajun.dragtoplayout.weight.ScrimInsetsScrollView;

import butterknife.Bind;

/**
 * Created by yajun on 2016/5/24.
 */
public class SearchActivity extends BaseActivity {

    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.navdrawer)
    ScrimInsetsScrollView navdrawer;

    @Override
    protected int getLayoutId() {
        return R.layout.navdrawer;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    public void handleAction(Message msg) {

    }
}
