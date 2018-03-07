package com.example.wangchang.fulv.http;


import android.content.Context;

import com.example.wangchang.fulv.view.loadview.SimpleLoadDialog;

import rx.Subscriber;
import xyz.windback.basesdk.http.ApiException;
import xyz.windback.basesdk.utils.LogUtil;

import com.example.wangchang.fulv.util.NetUtil;

/**
 * Describe:进度加载条订阅者
 * Created by liying on 2018/2/7
 */
public  abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{


    private SimpleLoadDialog simpleLoadDialog;
    private Context mContext;

    public ProgressSubscriber(Context context) {
        simpleLoadDialog = new SimpleLoadDialog(context,this,true);
        mContext = context;
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }


    /**
     * 显示Dialog
     */
    public void showProgressDialog(){
        if (simpleLoadDialog != null) {
//            simpleLoadDialog.obtainMessage(SimpleLoadDialog.SHOW_PROGRESS_DIALOG).sendToTarget();
            simpleLoadDialog.show();
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog(){
        if (simpleLoadDialog != null) {
//            simpleLoadDialog.obtainMessage(SimpleLoadDialog.DISMISS_PROGRESS_DIALOG).sendToTarget();
            simpleLoadDialog.dismiss();
            simpleLoadDialog = null;
        }
    }
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (!NetUtil.isConnected(mContext)) { //这里自行替换判断网络的代码
            _onError("网络不可用");
        } else if (e instanceof ApiException) {
            _onError(e.getMessage());
        } else {
           // _onError("请求失败，请稍后再试..."+e.getMessage());
            _onError("请求失败，请稍后再试...");
        }
        LogUtil.e("ProgressSubscriber",">>>>>>>>>>>>>>>>"+e.getMessage());
        dismissProgressDialog();
    }


    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
    protected abstract void _onNext(T t);
    protected abstract void _onError(String message);
}
