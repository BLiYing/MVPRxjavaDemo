// (c)2016 Flipboard Inc, All Rights Reserved.

package com.example.wangchang.fulv.http.homeapi;

import com.example.wangchang.fulv.entity.HttpResultHomeDemo;
import com.example.wangchang.fulv.entity.HomeGirlsEntityParcelable;

import java.util.List;


import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
/**
 * Describe:girls Api接口
 * Created by liying on 2018/2/7.
 */
public interface ApiHomeGirlsDemo {
    @GET("data/福利/{number}/{page}")
    Observable<HttpResultHomeDemo<List<HomeGirlsEntityParcelable>>> getBeauties(@Path("number") int number, @Path("page") int page);
}
