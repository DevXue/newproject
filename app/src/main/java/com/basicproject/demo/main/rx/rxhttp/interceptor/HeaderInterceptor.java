package com.basicproject.demo.main.rx.rxhttp.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求拦截器  统一添加请求头使用
 */

public class HeaderInterceptor implements Interceptor {

    private Map<String, Object> headerMaps = new TreeMap<>();
    private String headerKey;
    private String heandValue;


    public HeaderInterceptor(Map<String, Object> headerMaps) {
        this.headerMaps = headerMaps;
    }

    public HeaderInterceptor(String headerKey, String heandValue){
        this.headerKey=headerKey;
        this.heandValue=heandValue;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        if (headerMaps != null && headerMaps.size() > 0) {
            for (Map.Entry<String, Object> entry : headerMaps.entrySet()) {
                request.addHeader(entry.getKey(), (String) entry.getValue());
            }
        }else {
            request.addHeader(headerKey,heandValue);
        }
        return chain.proceed(request.build());
    }

}
