package com.basicproject.demo.main;

import android.annotation.SuppressLint;
import android.content.Context;

import com.basicproject.demo.BuildConfig;
import com.basicproject.demo.common.BaseApp;
import com.basicproject.demo.common.utils.Utils;
import com.basicproject.demo.main.rx.rxhttp.RxGo;

/**
 * 作者 薛
 * 时间 2018/1/24 0024
 * 功能描述:
 */

public class APP extends BaseApp{

    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static Context getContext(){return context;}


    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        initRxGo();  //初始化RxGo网络请求
    }




    /**
     * 初始化RxGo网络请求
     */
    private void initRxGo(){
        //Map<String, Object> headerMaps = new TreeMap<>();
        //headerMaps.put("access-token", "5103E894201342A9AF8E6239F0322769");
        RxGo.init(this);
        RxGo.getInstance()
                //开启全局配置
                .config()
                //全局的BaseUrl
                .setBaseUrl(API.BASEURL)
                //开启缓存策略
                //.setCache()
                //全局的请求头信息
                .setHeaders("access-token","")
                //全局持久话cookie,保存本地每次都会携带在header中
                .setCookie(false)
                //全局ssl证书认证
                //信任所有证书,不安全有风险
                .setSslSocketFactory()
                //使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(getAssets().open("your.cer"))
                //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(getAssets().open("your.bks"), "123456", getAssets().open("your.cer"))
                //全局超时配置
                .setReadTimeout(10)
                //全局超时配置
                .setWriteTimeout(10)
                //全局超时配置
                .setConnectTimeout(10)
                //全局是否打开请求log日志
                .setLog(true);

    }




}
