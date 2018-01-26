package com.basicproject.demo.main.rx.rxhttp.download;



import com.basicproject.demo.main.constants.APIConst;
import com.basicproject.demo.main.rx.rxhttp.http.RetrofitClient;
import com.basicproject.demo.main.rx.rxhttp.interceptor.Transformer;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 为下载单独建一个retrofit
 */

public class DownloadRetrofit {

    private static DownloadRetrofit instance;
    private Retrofit mRetrofit;



    public DownloadRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(APIConst.BASEURL)
                .build();
    }

    public static DownloadRetrofit getInstance() {

        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new DownloadRetrofit();
                }
            }

        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public static Observable<ResponseBody> downloadFile(String fileUrl) {
        return DownloadRetrofit
                .getInstance()
                .getRetrofit()
                .create(DownloadApi.class)
                .downloadFile(fileUrl)
                .compose(Transformer.<ResponseBody>switchSchedulers());
    }
}
