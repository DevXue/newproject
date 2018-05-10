package com.basicproject.demo.common;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.basicproject.demo.R;
import com.basicproject.demo.common.interfaces.PermissionLintener;
import com.basicproject.demo.common.interfaces.ResponseLintener;
import com.basicproject.demo.common.utils.DialogUtil;
import com.basicproject.demo.common.utils.ToolBarUtil;
import com.basicproject.demo.main.APP;
import com.basicproject.demo.main.ui.homeactivity.SplashActivity;
import com.yanzhenjie.sofia.Sofia;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * 作者 薛
 * 时间 2018/1/24 0024
 * 功能描述:通用父类
 */

public abstract class BaseActivity extends AppCompatActivity implements ResponseLintener {

    private PermissionLintener lintener;
    private ToolBarUtil mToolBarHelper ;
    private Toolbar toolbar ;
    //  protected ProgressDialog pd;
    protected int page=1;

    protected Dialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState){
            Intent i = new Intent(this, SplashActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        setContentView(this.setLayout());
        this.getAPP().getAppManager().addActivity(this);
         pd = DialogUtil.getRoundProgressBar(this,R.style.alert_dialog,R.layout.loading_layout,R.id.loading_tv,"正在加载……");
         pd=new ProgressDialog(this);
         pd.setTitle("请稍后···");
         pd.setCanceledOnTouchOutside(false);
        initUI();
        initData();
    }

    @Override
    public void setContentView(int layoutResID) {
        if (isShowToolBar())
        {
            mToolBarHelper = new ToolBarUtil(this,layoutResID) ;
            toolbar = mToolBarHelper.getToolBar() ;
            setContentView(mToolBarHelper.getContentView());
            //*把 toolbar 设置到Activity 中*//*
            setSupportActionBar(toolbar);
            //*自定义的一些操作*//*
            onCreateCustomToolBar(toolbar) ;
        }else {
            super.setContentView(layoutResID);
        }
     //   setStatusBar();
    }


    //设置toolbar关闭界面
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    protected abstract int setLayout();//设置布局
    protected abstract void initUI();//初始化
    protected abstract void initData();//初始化数据

    /**
     *向子类提供一个可调用的方法，来控制是否显示ToolBar
     * true:代表子类用的就是父类的ToolBar
     * false:意思就是子类不需要父类的ToolBar,子类自己去实现吧
     */
    protected boolean isShowToolBar(){
        return true;
    }

    public void onCreateCustomToolBar(Toolbar toolbar){
        toolbar.setContentInsetsRelative(0,0);
    }

    //设置沉浸式状态栏
    protected void setStatusBar() {
        Sofia.with(this)
                .statusBarDarkFont()
                .statusBarBackground(ContextCompat.getColor(this, R.color.white))
                .navigationBarBackground(ContextCompat.getColor(this, R.color.colorNavigation));
    }


    /**
     *因为Android6.0之后加入了运行时权限机制（高危权限大致分为9组24个），这里封装了运行时权限操作
     * 参数1：一次请求多少个权限
     * 参数2：请求后利用接口回调执行其他操作
     */
    public void requestRuntimepermission(String[] permissions,PermissionLintener permissionLintener){
        this.lintener=permissionLintener;
        List<String> permissionList=new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }

        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,permissionList.toArray(new String[permissionList.size()]),1);
        }else {
            //做某事吧
            lintener.onGranted();
        }
    }

    //设置中间的文字
    public void setCenterText(String text){
        setTitle("");
        mToolBarHelper.setCenterText(text);
    }

    //设置右边的文字
    public void setRightText(String text){
        mToolBarHelper.setRightText(text);
    }

    //设置右边的图片
    public void setRightImg(int resId){
        mToolBarHelper.setRightImg(resId);
    }

    //设置右侧文字单击事件
    public void setRightTextClick(View.OnClickListener rightTextClick){
        mToolBarHelper.setRightTextClick(rightTextClick);
    }

    //设置图片右侧单击事件
    public void setRightImgClick(View.OnClickListener rightImgClick){
        mToolBarHelper.setRightImgClick(rightImgClick);
    }


    protected void setRefreshing(final boolean refreshing, final SwipeRefreshLayout refreshLayout) {
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this,R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimary));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(refreshing);
            }
        },100);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0) {
                    List<String> deniedPermissions=new ArrayList<>();
                    for (int i = 0; i <grantResults.length ; i++) {
                        int grantResult=grantResults[i];
                        String permission=permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {//权限被拒绝，告诉用户那些权限被拒绝
                            deniedPermissions.add(permission);
                        }
                    }


                    if (deniedPermissions.isEmpty()) {//如果被拒绝权限集合为空，说明用户接受了所有权限
                        lintener.onGranted();
                    }else {
                        lintener.onDenied(deniedPermissions);
                    }

                }
                break;
        }
    }

    public APP getAPP() {
        return (APP) this.getApplication();
    }

    public void finish() {
        this.getAPP().getAppManager().removeActivity(this);
        super.finish();
    }


    public void exitApp(boolean isBackground) {
        this.getAPP().exitApp(isBackground);
    }

    protected void exitApp() {
        this.getAPP().exitApp(false);
    }

    public void exitAppToBackground() {
        this.getAPP().exitApp(true);
    }

    public final void startActivity(Class<?> descActivity) {
        this.startActivity(descActivity, (Bundle)null);
    }

    public final void startActivity(Class<?> descActivity, Bundle bundle) {
        Intent intent = new Intent();
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        intent.setClass(this, descActivity);
        this.startActivity(intent);
    }

    protected void addFragment(Fragment fragment, int container) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(container, fragment);
        fragmentTransaction.commit();
    }

    protected void clearBackStack() {
        for(int i = 0; i <= this.getSupportFragmentManager().getBackStackEntryCount(); ++i) {
            this.getSupportFragmentManager().popBackStack();
        }

    }

    protected void popStack() {
        this.getSupportFragmentManager().popBackStack();
    }


    @Override
    public void onResponseSuccess(String code, Object result) {
    }

    @Override
    public void onResponseFailed(Response response, Exception e) {
        pd.dismiss();
    }

}
