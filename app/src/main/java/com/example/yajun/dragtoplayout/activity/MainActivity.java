package com.example.yajun.dragtoplayout.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.yajun.dragtoplayout.fragment.Fragment1;
import com.example.yajun.dragtoplayout.R;
import com.example.yajun.dragtoplayout.base.BaseActivity;
import com.example.yajun.dragtoplayout.util.UrlUtil;
import com.lzy.widget.tab.PagerSlidingTabStrip;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    private String[] fChannelNames = null;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.headerbar)
    LinearLayout headerbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.tab)
    PagerSlidingTabStrip pagerSlidingTabStrip;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(toolbar != null){
            toolbar.setTitle("Google I/O Test");
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fChannelNames = UrlUtil.CHANNEL_NAME;

        mViewPager.setOffscreenPageLimit(fChannelNames.length);
        OurViewPagerAdapter pagerAdapter = new OurViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        pagerSlidingTabStrip.setViewPager(mViewPager);

    }

    private class OurViewPagerAdapter extends FragmentPagerAdapter {

        public OurViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fChannelNames.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fChannelNames[position];
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment1.getInstance(position);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        toolbar.inflateMenu(R.menu.menu_search);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_search:
//                        startActivity(new Intent(MainActivity.this, SearchActivity.class));
                        return true;
                }
                return false;
            }
        });
        return true;
    }
}
