package com.example.wangchang.fulv.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.wangchang.fulv.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.windback.basesdk.base.BaseAppCompatActivity;

public class MyOrderActivity extends BaseAppCompatActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void beforeInitView(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        toolbarTitle.setText(R.string.order_title);
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
