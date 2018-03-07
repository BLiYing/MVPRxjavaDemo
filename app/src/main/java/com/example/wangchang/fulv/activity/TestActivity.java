package com.example.wangchang.fulv.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wangchang.fulv.R;

import xyz.windback.basesdk.base.BaseAppCompatActivity;
/**
 * Describe:测试页面
 * Created by liying on 2018/3
 */
public class TestActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test);
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    protected boolean isShowBacking() {
        return true;
    }
}
