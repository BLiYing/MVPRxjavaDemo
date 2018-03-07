package com.example.wangchang.fulv.mvpdemo.contract;

import com.example.wangchang.fulv.entity.HomeGirlsEntityParcelable;

import java.util.List;

/**
 * Describe:HomeContract
 * Created by liying on 2018/3/5
 */
public interface HomeContract {
    interface View{
        void showGirls(boolean isfresh,List<HomeGirlsEntityParcelable> list);
        void showError(boolean isfresh);
        void showNormal();

    }

    interface Prerenter{
        void getGirlsOfPresent(int count, int page,
                               final boolean isfresh,
                               final String cacheKey,
                               final boolean issave,
                               final boolean forceRefresh,
                               final boolean isShowDialog);
        void showGirlsOfPresent(boolean isfresh,List<HomeGirlsEntityParcelable> list);
        void showErrorOfPresent(boolean isfresh);
        void showNormalOfPresent();
        void destroy(); // 释放当前Activity。由于presenter中经常进行一些耗时操作，例如网络请求，但是presenter持有了Activity的强引用，
                        // 如果在请求结束之前，Activity被销毁，那么会导致presenter一直持有Activity的引用，
                        // 使得Activity无法被回收，而发生内存泄漏。
    }

    interface Model {
        void getGirlsOfModel(int count, int page,
                             final boolean isfresh,
                             final String cacheKey,
                             final boolean issave,
                             final boolean forceRefresh,
                             final boolean isShowDialog);

    }
}
