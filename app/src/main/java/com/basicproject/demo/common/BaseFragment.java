package com.basicproject.demo.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.basicproject.demo.R;
import com.basicproject.demo.common.interfaces.ResponseLintener;
import com.basicproject.demo.common.utils.DialogUtil;
import com.basicproject.demo.main.APP;
import com.basicproject.demo.main.constants.Const;
import com.basicproject.demo.main.home.activity.LoginActivity;

import okhttp3.Response;

/**
 * 作者：薛
 * 时间：2017/5/5 15:10
 */

/**
 * 若把初始化内容放到initData实现,就是采用Lazy方式加载的Fragment
 * 若不需要Lazy加载则initData方法内留空,初始化内容放到initViews即可
 * -
 * -注1: 如果是与ViewPager一起使用，调用的是setUserVisibleHint。
 * ------可以调用mViewPager.setOffscreenPageLimit(size),若设置了该属性 则viewpager会缓存指定数量的Fragment
 * -注2: 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
 * -注3: 针对初始就show的Fragment 为了触发onHiddenChanged事件 达到lazy效果 需要先hide再show
 */
public abstract class BaseFragment extends Fragment implements ResponseLintener {

    protected String fragmentTitle;             //fragment标题
    private boolean isVisible;                  //是否可见状态
    private boolean isPrepared;                 //标志位，View已经初始化完成。
    private boolean isFirstLoad = true;         //是否第一次加载
    protected LayoutInflater inflater;
    //protected ProgressDialog pd;
    protected int page=1;

    protected Dialog pd;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        isFirstLoad = true;
        View view = initView(inflater, container, savedInstanceState);
        isPrepared = true;

      //  pd=new ProgressDialog(getActivity());
      //  pd.setMessage("请稍后···");
      //  pd.setCanceledOnTouchOutside(false);
        pd = DialogUtil.getRoundProgressBar(getActivity(),R.style.alert_dialog, R.layout.loading_layout,R.id.loading_tv,"正在加载……");
        lazyLoad();
        return view;
    }

    /** 如果是与ViewPager一起使用，调用的是setUserVisibleHint */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        initData();
    }


    protected String getUserId(){
        return APP.getInstance().getAppConfig().getString(Const.USERID,"");
    }

    protected String getToken(){
        return APP.getInstance().getAppConfig().getString(Const.TOKEN,"");
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initData();

    public String getTitle() {
        return TextUtils.isEmpty(fragmentTitle) ? "" : fragmentTitle;
    }

    public void setTitle(String title) {
        fragmentTitle = title;
    }

    protected void setRefreshing(final boolean refreshing, final SwipeRefreshLayout refreshLayout) {
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(APP.getContext(),R.color.colorAccent),
                ContextCompat.getColor(APP.getContext(), R.color.colorPrimary));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(refreshing);
            }
        },200);
    }


    protected boolean isLogin(Activity activity){
        if (getToken().equals("") ) {
            Intent intent1=new Intent(activity, LoginActivity.class);
            startActivity(intent1);
            activity.overridePendingTransition(R.anim.bottom_open, 0);
            return false;
        }else {
            return true;
        }
    }


    @Override
    public void onResponseSuccess(String code, Object result) {

    }

    @Override
    public void onResponseFailed(Response response, Exception e) {
        pd.dismiss();
    }
}
