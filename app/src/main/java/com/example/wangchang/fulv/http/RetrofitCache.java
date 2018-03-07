package com.example.wangchang.fulv.http;


import com.orhanobut.hawk.Hawk;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Describe:RetrofitCache
 * Created by liying on 2018/3/5
 */
public class RetrofitCache {
    /**
     * @param cacheKey 缓存的Key
     * @param fromNetwork
     * @param isSave       是否缓存
     * @param forceRefresh 是否强制刷新
     * @param <T>
     * @return
     */
    public static <T> Observable<T> load(final String cacheKey,
                                         Observable<T> fromNetwork,
                                         boolean isSave, boolean forceRefresh) {
        Observable<T> fromCache = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                @SuppressWarnings("unchecked")
                T cache = (T) Hawk.get(cacheKey);
                if (cache != null) {
                    subscriber.onNext(cache);
                } else {
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        //是否缓存
        if (isSave) {
            /**
             * 这里的fromNetwork 不需要指定Schedule,在handleRequest中已经变换了
             */
            fromNetwork = fromNetwork.map(new Func1<T, T>() {
                @Override
                public T call(T result) {
                    Hawk.put(cacheKey, result);
                    return result;
                }
            });
        }
        //强制刷新
        if (forceRefresh) {
            return fromNetwork;
        } else {
//            return Observable.concat(fromCache, fromNetwork).first();
            //若不强制刷新，则先读取缓存数据（即数据库中数据），当缓存不存在时则读取网络数据
            return Observable.concat(fromCache, fromNetwork).takeFirst(new Func1<T, Boolean>() {
                @Override
                public Boolean call(T t) {
                    return t != null;
                }
            });
        }
    }

    /**
     * 清除数据库缓存
     */
    public static void clearRetrofitCache(){
        Hawk.deleteAll();
    }

}
