package com.example.wangchang.fulv.http;


import com.example.wangchang.fulv.application.BaseApplication;
import com.example.wangchang.fulv.http.bookapi.ApiBookDemo;
import com.example.wangchang.fulv.http.homeapi.ApiHomeGirlsDemo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.windback.basesdk.http.FastJsonConvertFactory;
import xyz.windback.basesdk.http.Url;

/**
 * Describe:配置api
 * Created by liying on 2018/3/5
 */
public class Api {
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static FastJsonConvertFactory fastJsonConverterFactory = FastJsonConvertFactory.create();
//    private static RxJava2CallAdapterFactory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static RxJavaCallAdapterFactory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static ApiBookDemo SERVICE;
    private static ApiHomeGirlsDemo sApiGankGirls;
    /**
     * 请求超时时间
     */
    public static final int DEFAULT_TIMEOUT = 10000;
    public static ApiBookDemo getDoubanService() {


//            httpClientBuilder.addNetworkInterceptor(new MockInterceptor());
            /**
             *  拦截器
             */
           /* httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl.Builder authorizedUrlBuilder = request.url()
                            .newBuilder()
                            //添加统一参数 如手机唯一标识符,token等
                            .addQueryParameter("key1","value1")
                            .addQueryParameter("key2", "value2");

                    Request newRequest = request.newBuilder()
                            //对所有请求添加请求头
                            .header("mobileFlag", "adfsaeefe").addHeader("type", "4")
                            .method(request.method(), request.body())
                            .url(authorizedUrlBuilder.build())
                            .build();
                    return  chain.proceed(newRequest);
                }
            });*/

            SERVICE = new Retrofit.Builder()
                    .client(BaseApplication.defaultOkHttpClient())//默认设置
                    .baseUrl(Url.BASE_URL_BOOK)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build().create(ApiBookDemo.class);

        return SERVICE;
    }

    public static ApiHomeGirlsDemo getGankService() {
        if (sApiGankGirls == null) {
            //或者手动创建一个OkHttpClient并设置超时时间
           /* OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);*/
//            OkHttpClient okHttpClient = new OkHttpClient();
            sApiGankGirls = new Retrofit.Builder()
                    .client(BaseApplication.defaultOkHttpClient())//默认设置
                    .baseUrl(Url.BASE_URL_HOME)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build().create(ApiHomeGirlsDemo.class);
        }
        return sApiGankGirls;

    }

}
