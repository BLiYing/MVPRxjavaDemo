package com.example.wangchang.fulv.mvpdemo.model;

import android.content.Context;

import com.example.wangchang.fulv.activity.MainActivity;
import com.example.wangchang.fulv.activity.MainActivityNew;
import com.example.wangchang.fulv.base.CacheKey;
import com.example.wangchang.fulv.entity.HomeGirlsEntityParcelable;
import com.example.wangchang.fulv.http.Api;
import com.example.wangchang.fulv.http.ProgressSubscriber;
import com.example.wangchang.fulv.http.homeapi.HttpUtilHome;
import com.example.wangchang.fulv.mvpdemo.contract.HomeContract;
import com.example.wangchang.fulv.mvpdemo.presenter.HomePresent;

import java.util.List;

import rx.Observable;
import xyz.windback.basesdk.base.ActivityLifeCycleEvent;

/**
 * Created by liying on 2018-1-22.
 */
public class HomeModel implements HomeContract.Model {

    private HomePresent prerenter;
    private Context context;

    public HomeModel(Context context,HomePresent prerenter){
        this.prerenter = prerenter;
        this.context = context;
    }

    @Override
    public void getGirlsOfModel(int count, int page,
                                boolean isfresh,
                                String cacheKey,
                                boolean issave,
                                boolean forceRefresh,
                                boolean isShowDialog) {
            getGirls(count, page, isfresh, cacheKey, issave, forceRefresh, isShowDialog);


    }

    private void getGirls(int count, int page,
                          final boolean isfresh,
                          final String cacheKey,
                          final boolean issave,
                          final boolean forceRefresh,
                          final boolean isShowDialog) {
        Observable ob = Api.getGankService().getBeauties(count, page);
        HttpUtilHome.getInstance().toSubscribe(ob, new ProgressSubscriber<List<HomeGirlsEntityParcelable>>(context) {
                    @Override
                    protected void _onError(String message) {
                        /*if (isfresh) {
                            swipeRefreshLayout.setRefreshing(false);

                        }
                        showError();*/
                        prerenter.showErrorOfPresent(isfresh);
                    }

                    @Override
                    protected void _onNext(List<HomeGirlsEntityParcelable> list) {

                       /* if (isfresh) {

                            refresh(list);
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            load(list);

                        }*/
                       prerenter.showGirlsOfPresent(isfresh,list);
                       /* showNormal();*/
                       prerenter.showNormalOfPresent();

                    }

                },
                cacheKey,
                ActivityLifeCycleEvent.CREATE,
                MainActivityNew.getInstance().getLifeSubject(),
                issave,
                forceRefresh,
                isShowDialog);
    }
}
