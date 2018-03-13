package com.basicproject.demo.main;

import android.annotation.SuppressLint;
import android.content.Context;

import com.basicproject.demo.BuildConfig;
import com.basicproject.demo.common.BaseApp;
import com.basicproject.demo.common.utils.L;
import com.basicproject.demo.common.utils.Utils;
import com.basicproject.demo.main.constants.APIConst;
import com.basicproject.demo.main.rx.rxhttp.RxGo;

import java.util.Map;
import java.util.TreeMap;

/**
 * 作者 薛
 * 时间 2018/1/24 0024
 * 功能描述:
 */

public class APP extends BaseApp{

    @SuppressLint("StaticFieldLeak")
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        initRxGo();  //初始化RxGo网络请求
        initLog();   //初始化日志工具
    }

    public static Context getContext(){return context;}


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

    public void initLog() {
        Utils.init(this);
        L.Config config = L.getConfig()
                .setLogSwitch(BuildConfig.DEBUG)// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setLogHeadSwitch(true)// 设置 log 头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印 log 时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(L.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(L.V)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(1);// log 栈深度，默认为 1
        L.d(config.toString());
    }



}
