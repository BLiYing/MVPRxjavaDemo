package com.example.wangchang.fulv.application;

import android.app.Application;

import com.example.wangchang.fulv.http.Api;
import com.example.wangchang.fulv.util.Utils;
import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import xyz.windback.basesdk.BuildConfig;
import xyz.windback.basesdk.http.HttpLoggingInterceptor;
import xyz.windback.basesdk.utils.LogUtil;

/**
 * Created by helin on 2016/11/11 11:15.
 */

public class BaseApplication extends Application implements Thread.UncaughtExceptionHandler {
    private static BaseApplication mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Utils.init(this);//初始化贯穿整个生命周期的context
        Hawk.init(this).build();//初始化加密数据库
        //配置是否显示log
        LogUtil.isDebug = true;

        //配置程序异常退出处理
//        Thread.setDefaultUncaughtExceptionHandler(new LocalFileHandler(this));

    }

    public static OkHttpClient defaultOkHttpClient() {
        OkHttpClient client;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /**
         * HttpLoggingInterceptor1 是一个拦截器，用于输出网络请求和结果的 Log，
         * 可以配置 level 为 BASIC / HEADERS / BODY，都很好理解，
         * 对应的是原来 retrofit 的 set log level 方法
         * DEBUG模式下打印
         */
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BODY));
        }
        /*builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return false;
            }
        });*/
        client = builder.connectTimeout(Api.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Api.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Api.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)//错误重连
                .build();
        return client;
    }

    public static BaseApplication getInstance(){ return mApplication;}

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
