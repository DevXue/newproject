package com.basicproject.demo.main;

import com.basicproject.demo.main.rx.rxhttp.bean.Rtn;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者 薛
 * 时间 2018/1/24 0024
 * 功能描述: 接口请求地址
 */

public interface API {


    String BASEURL="https://www.baidu.com";


    //获取数据
    @GET("feature/list")
    Observable<Rtn> getData(@Query("pn") int pn, @Query("ps") int ps);


    //post请求
    @FormUrlEncoded  //如果是post请求必须加
    @POST("feedback")
    Observable<Rtn> postRequst(@Field("name")String name ,@Field("opinion") String content);


}
