package com.example.wangchang.fulv.http.bookapi;


import com.example.wangchang.fulv.entity.HttpResultBookDemo;
import com.example.wangchang.fulv.entity.parcelabledemo.SubjectEntityParcelable;
import com.example.wangchang.fulv.entity.serializabledemo.SubjectEntitySerializable;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Describe:豆瓣电影 Api接口
 * Created by liying on 2018/2/7.
 */
public interface ApiBookDemo {

    @GET("top250")
    Observable<HttpResultBookDemo<List<SubjectEntityParcelable>>> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<HttpResultBookDemo<SubjectEntitySerializable>> getUser(@Query("touken") String touken);

}
