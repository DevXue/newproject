package com.basicproject.demo.main.ui.homeactivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.basicproject.demo.R;
import com.basicproject.demo.common.BaseActivity;
import com.basicproject.demo.common.view.BottomBarItem;
import com.basicproject.demo.common.view.BottomBarLayout;
import com.basicproject.demo.main.constants.Const;
import com.basicproject.demo.main.fragment.homefragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {




        @Bind(R.id.bbl)
        BottomBarLayout mBottomBarLayout;
        @Bind(R.id.vp_content)
        ViewPager mVpContent;

        private List<Fragment> mFragmentList = new ArrayList<>();
        private RotateAnimation mRotateAnimation;
        private Handler mHandler = new Handler();

        @Override
        protected int setLayout() {
            return R.layout.activity_main;
        }

        @Override
        protected void initUI() {
            ButterKnife.bind(this);
        }

        @Override
        protected void initData() {
            HomeFragment homeFragment = new HomeFragment();
            Bundle bundle1 = new Bundle();
            bundle1.putString(Const.DATA, "首页");
            homeFragment.setArguments(bundle1);
            mFragmentList.add(homeFragment);


            HomeFragment discoverFragment = new HomeFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString(Const.DATA, "发现");
            discoverFragment.setArguments(bundle2);
            mFragmentList.add(discoverFragment);

            HomeFragment mineFragment = new HomeFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putString(Const.DATA, "我的");
            mineFragment.setArguments(bundle3);
            mFragmentList.add(mineFragment);


            //  changeFragment(0); //默认显示第一页

            initListener();
        }

        @Override
        protected boolean isShowToolBar() {
            return false;
        }




   /* private void changeFragment(int currentPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, mFragmentList.get(currentPosition));
        transaction.commit();
    }*/


        private void initListener() {
            mVpContent.setAdapter(new MyAdapter(getSupportFragmentManager()));
            mVpContent.setOffscreenPageLimit(3);
            mBottomBarLayout.setViewPager(mVpContent);
            mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
                @Override
                public void onItemSelected(final BottomBarItem bottomBarItem, int previousPosition, final int currentPosition) {
                    Log.i("MainActivity","position: " + currentPosition);
                    if (currentPosition == 0){
                        //如果是第一个，即首页
                        if (previousPosition == currentPosition){
                            //如果是在原来位置上点击,更换首页图标并播放旋转动画
                            bottomBarItem.setIconSelectedResourceId(R.mipmap.tab_loading);//更换成加载图标
                            bottomBarItem.setStatus(true);

                            //播放旋转动画
                            if (mRotateAnimation == null) {
                                mRotateAnimation = new RotateAnimation(0, 360,
                                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                                        0.5f);
                                mRotateAnimation.setDuration(800);
                                mRotateAnimation.setRepeatCount(-1);
                            }
                            ImageView bottomImageView = bottomBarItem.getImageView();
                            bottomImageView.setAnimation(mRotateAnimation);
                            bottomImageView.startAnimation(mRotateAnimation);//播放旋转动画

                            //模拟数据刷新完毕
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    boolean tabNotChanged = mBottomBarLayout.getCurrentItem() == currentPosition; //是否还停留在当前页签
                                    bottomBarItem.setIconSelectedResourceId(R.mipmap.tab_home_selected);//更换成首页原来选中图标
                                    bottomBarItem.setStatus(tabNotChanged);//刷新图标
                                    cancelTabLoading(bottomBarItem);
                                }
                            },3000);
                            return;
                        }
                    }

                    //如果点击了其他条目
                    BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
                    bottomItem.setIconSelectedResourceId(R.mipmap.tab_home_selected);//更换为原来的图标

                    cancelTabLoading(bottomItem);//停止旋转动画
                }
            });

            mBottomBarLayout.setUnread(0,20);//设置第一个页签的未读数为20
            mBottomBarLayout.setUnread(1,1001);//设置第二个页签的未读数
            mBottomBarLayout.showNotify(2);//设置第三个页签显示提示的小红点
            mBottomBarLayout.setMsg(3,"NEW");//设置第四个页签显示NEW提示文字
        }


        class MyAdapter extends FragmentStatePagerAdapter {

            public MyAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        }

        /**
         * 停止首页页签的旋转动画
         */
        private void cancelTabLoading(BottomBarItem bottomItem) {
            Animation animation = bottomItem.getImageView().getAnimation();
            if (animation != null) {
                animation.cancel();
            }
        }
    }