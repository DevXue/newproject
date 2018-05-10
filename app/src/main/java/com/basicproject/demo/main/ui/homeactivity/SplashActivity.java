package com.basicproject.demo.main.ui.homeactivity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import com.basicproject.demo.R;
import com.basicproject.demo.common.BaseActivity;
import com.basicproject.demo.main.constants.Const;
import com.dvp.base.util.MobileUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @Bind(R.id.versionTv)
    TextView versionTv;

    private static final int ANIMATION_DURATION = 3000;
    private boolean isFirstIn = false;//是否首次打开APP


    @Override
    protected int setLayout() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
    }

    @Override
    protected boolean isShowToolBar() {
        return false;
    }

    @Override
    protected void initData() {
        versionTv.setText(MobileUtil.getVersionName(SplashActivity.this));
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
               /* startActivity(LoginActivity.class);
                finish();*/
                initSplash();
            }
        },3000);
    }


    private void initSplash(){
        //这里开始逻辑判断 该跳转何方···
            isFirstIn=getAPP().getAppConfig().getBoolean(Const.ISFIRST,true);
        if (!isFirstIn) {
            String userid = getAPP().getAppConfig().getString(Const.USERID,"");//获取用户id
            if (TextUtils.isEmpty(userid)) {
                //如果用户ID为空，说明没有登录过，那么进入登录页
                // goLogin();
                goHome();
            } else {
                // 登录过就进入页面
                goHome();
            }
        } else {
            //说明是首次启动进入引导页
            goGuide();
        }
    }


    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        if(getIntent().getBundleExtra(Const.DATA) != null){
            intent.putExtra(Const.DATA, getIntent().getBundleExtra(Const.DATA));
        }
        startActivity(intent);
        finish();
    }

    private void goLogin() {
        startActivity(LoginActivity.class);
        finish();
    }

    private void goGuide() {
        startActivity(GuideActivity.class);
        finish();
    }
}
