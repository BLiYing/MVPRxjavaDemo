package com.example.wangchang.fulv.http.bookapi;

import com.example.wangchang.fulv.entity.HttpResultBookDemo;
import com.example.wangchang.fulv.http.ProgressSubscriber;
import com.example.wangchang.fulv.http.RetrofitCache;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.subjects.PublishSubject;
import xyz.windback.basesdk.base.ActivityLifeCycleEvent;

/**
 * Describe:订阅者工具类
 * Created by liying on 2018/2/7.
 */
public class HttpUtilBook {

    /**
     * 构造方法私有
     */
    private HttpUtilBook() {
    }

    /**
     * 在访问HttpMethods时创建单例
     */
    private static class SingletonHolder {
        private static final HttpUtilBook INSTANCE = new HttpUtilBook();
    }

    /**
     * 获取单例
     */
    public static HttpUtilBook getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 添加线程管理并订阅
     * @param ob
     * @param subscriber
     * @param cacheKey 缓存kay
     * @param event Activity 生命周期
     * @param lifecycleSubject
     * @param isSave 是否缓存
     * @param forceRefresh 是否强制刷新
     */
    public void toSubscribe(Observable ob,
                            final ProgressSubscriber subscriber,
                            String cacheKey,
                            final ActivityLifeCycleEvent event,
                            final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject,
                            boolean isSave,
                            boolean forceRefresh,
                            final boolean isShowDialog) {
        //数据预处理
        Observable.Transformer<HttpResultBookDemo<Object>, Object> result = RxHelperBook.handleResult(event,lifecycleSubject);
        Observable observable = ob.compose(result)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //显示Dialog和一些其他操作
                        if(isShowDialog)
                            subscriber.showProgressDialog();
                    }
                });
        RetrofitCache.load(cacheKey,observable,isSave,forceRefresh).subscribe(subscriber);

    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
//    private class HttpResultFunc<T> implements Func1<MoveHttpResult<T>, T> {
//
//        @Override
//        public T call(MoveHttpResult<T> httpResult) {
////            if (httpResult.getResultCode() != 0) {
////                throw new ApiException(httpResult.getResultCode());
////            }
////            return httpResult.getData();
//
//            if (httpResult.getCount() == 0) {
//                throw new ApiException(100);
//            }
//            return httpResult.getSubjects();
//
//        }
//    }
}
