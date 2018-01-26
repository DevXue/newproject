package com.basicproject.demo.main.home.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basicproject.demo.R;
import com.basicproject.demo.common.BaseActivity;
import com.basicproject.demo.common.interfaces.PermissionLintener;
import com.basicproject.demo.common.utils.ToastUtil;
import com.basicproject.demo.main.constants.Const;
import com.basicproject.demo.main.home.adapter.GuideAdapter;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

@SuppressLint("InflateParams")
public class GuideActivity extends BaseActivity implements OnPageChangeListener {

    @Bind(R.id.guideViewPager)
    ViewPager guideViewPager;
    @Bind(R.id.guideLinearLayout)
    LinearLayout guideLinearLayout;

    private List<View> viewList;

    private ImageView[] dotsImageView;


    private GuideAdapter adapter;

    private TextView guideStartTextView;

    private int[] ids = {R.id.guideOneImageView, R.id.guideTwoImageView, R.id.guideThreeImageView};

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        initView();
        initPermission();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int setLayout() {
        return R.layout.activity_guide;
    }

    protected void setStatusBar(){
        StatusBarUtil.setTranslucentForCoordinatorLayout(this,0);
    }

    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        viewList = new ArrayList<View>();
        viewList.add(inflater.inflate(R.layout.guide_one, null));
        viewList.add(inflater.inflate(R.layout.guide_two, null));
        viewList.add(inflater.inflate(R.layout.guide_three, null));
        adapter = new GuideAdapter(viewList);
        guideViewPager = (ViewPager) findViewById(R.id.guideViewPager);
        guideViewPager.setAdapter(adapter);
        guideStartTextView = (TextView) viewList.get(2).findViewById(R.id.guideStartTextView);
        guideStartTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置已经引导
                getAPP().getAppConfig().setBoolean(Const.ISFIRST, false);
                String userid = getAPP().getAppConfig().getString(Const.USERID, "");
                if (TextUtils.isEmpty(userid)) {
                   // startActivity(LoginActivity.class);
                    startActivity(MainActivity.class);
                } else {
                    startActivity(MainActivity.class);
                }
                finish();
            }
        });
        guideViewPager.setOnPageChangeListener(this);

        dotsImageView = new ImageView[viewList.size()];
        for (int i = 0; i < viewList.size(); i++) {
            dotsImageView[i] = (ImageView) findViewById(ids[i]);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        for (int i = 0; i < ids.length; i++) {
            if (i == arg0)
                dotsImageView[i].setImageResource(R.drawable.guide_dot_focused);
            else {
                dotsImageView[i].setImageResource(R.drawable.guide_dot_normal);
            }
        }
    }

    @Override
    protected boolean isShowToolBar() {
        return false;
    }

    private void initPermission(){
        requestRuntimepermission(new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE

        }, new PermissionLintener() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied(List<String> deniedPermisson) {
                ToastUtil.showViewToast(R.string.no_permission_storage);
            }
        });

    }
}
