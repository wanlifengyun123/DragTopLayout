package com.example.yajun.dragtoplayout.common.switchpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yajun.dragtoplayout.R;
import com.example.yajun.dragtoplayout.weight.LockedViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 亚军 on 2016/5/6.
 *
 */
public class SwitchPagerActivity extends AppCompatActivity {

    public static final String BK_PIC_PATH = "bk_pic_path";

    @Bind(R.id.switch_pager_vp)
    LockedViewPager viewPager;
    @Bind(R.id.switch_pager_title_count_tv)
    TextView tvBackTitle;
    @Bind(R.id.switch_pager_title_delete_tv)
    TextView tvDettele;
    @Bind(R.id.switch_pager_title_rl)
    RelativeLayout rlTitle;

    private String picPath;

    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.layout_common_switch_pager);
        ButterKnife.bind(this);
        picPath = getIntent().getStringExtra(BK_PIC_PATH);
        data.add(picPath);

        initView();
    }

    public void initView(){

        tvDettele.setText("");

        PagerDetailAdapter pageAdapter = new PagerDetailAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        pageAdapter.setData(data);
    }

    @OnClick(R.id.switch_pager_title_count_tv)
    public void onClickBack(View v){
        super.onBackPressed();
    }
}
