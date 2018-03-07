package com.example.wangchang.fulv.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.wangchang.fulv.R;
import com.example.wangchang.fulv.activity.MyOrderActivity;
import com.example.wangchang.fulv.view.MySettingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import xyz.windback.basesdk.base.BaseFragment;


/**
 * Describe:我的fragment
 * Created by liying on 2018/3/5
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.cardMv)
    MySettingView cardMv;
    @BindView(R.id.orderMv)
    MySettingView orderMv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.etcMv)
    MySettingView etcMv;
    @BindView(R.id.pointMv)
    MySettingView pointMv;
    @BindView(R.id.messageMv)
    MySettingView messageMv;
    @BindView(R.id.questionMv)
    MySettingView questionMv;
    @BindView(R.id.aboutMv)
    MySettingView aboutMv;

    private Unbinder unbinder;
    private Context mContext;
    private View rootView;

    public static MineFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView != null) {
            unbinder = ButterKnife.bind(this, rootView);
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_mine
                    , container, false);
            unbinder = ButterKnife.bind(this, rootView);
            init();
        }
        //去除状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ((Activity) mContext).getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        }
        // TODO: 2018-3-6 记得删除
//        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 初始化布局
     */
    private void init() {
        toolbarTitle.setText(R.string.main_mine);
        cardMv.setIvLeft(R.drawable.mine_card);
        cardMv.setTitle(getString(R.string.minecard));
        orderMv.setIvLeft(R.drawable.mine_order);
        orderMv.setTitle(getString(R.string.mineorder));
        etcMv.setIvLeft(R.drawable.mine_etc);
        etcMv.setTitle(getString(R.string.mineetc));
        pointMv.setIvLeft(R.drawable.mine_point);
        pointMv.setTitle(getString(R.string.minepoint));
        messageMv.setIvLeft(R.drawable.mine_message);
        messageMv.setTitle(getString(R.string.minemessage));
        questionMv.setIvLeft(R.drawable.mine_question);
        questionMv.setTitle(getString(R.string.minequestion));
        aboutMv.setIvLeft(R.drawable.mine_about);
        aboutMv.setTitle(getString(R.string.mineaboutas));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.cardMv, R.id.orderMv, R.id.etcMv, R.id.pointMv, R.id.messageMv, R.id.questionMv, R.id.aboutMv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cardMv:
                break;
            case R.id.orderMv:
                startActivity((Activity) mContext, MyOrderActivity.class);
                break;
            case R.id.etcMv:
                break;
            case R.id.pointMv:
                break;
            case R.id.messageMv:
                break;
            case R.id.questionMv:
                break;
            case R.id.aboutMv:
                break;
        }
    }
}
