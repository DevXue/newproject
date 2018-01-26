package com.basicproject.demo.main.home.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.basicproject.demo.R;
import com.basicproject.demo.common.BaseActivity;
import com.basicproject.demo.common.utils.BottomNavigationViewHelper;
import com.basicproject.demo.common.view.NoScrollViewPager;
import com.basicproject.demo.main.home.adapter.MyFragmentPagerAdapter;
import com.basicproject.demo.main.home.fragment.HomeFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Bind(R.id.viewpager)
    NoScrollViewPager viewPager;
    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.bottomNavigation)
    BottomNavigationViewEx bottomNavigation;
    private MenuItem menuItem;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        initViewPager();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isShowToolBar() {
        return false;
    }


    @OnClick({R.id.viewpager, R.id.content, R.id.bottomNavigation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.viewpager:
                break;
            case R.id.content:
                break;
            case R.id.bottomNavigation:
                break;
        }
    }


    private void initViewPager() {
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.tab2:
                        viewPager.setCurrentItem(1);
                        return true;
                   /* case R.id.navigation_notifications:
                        viewPager.setCurrentItem(2);
                        return true;*/
                    case R.id.tab3:
                        viewPager.setCurrentItem(2);
                        break;

                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        initContentFragment();

    }

    private void initContentFragment() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(HomeFragment.newInstance("首页"));
        mFragmentList.add(HomeFragment.newInstance("首页"));
        mFragmentList.add(HomeFragment.newInstance("首页"));
        //注意使用的是：getSupportFragmentManager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(adapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(0);
    }
}