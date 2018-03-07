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
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wangchang.fulv.R;
import com.example.wangchang.fulv.adapter.PlanListViewAdapter;
import com.example.wangchang.fulv.entity.PlanEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import xyz.windback.basesdk.base.BaseFragment;

/**
 * Describe:日程fragment
 * Created by liying on 2018/3/5
 */
public class PlanFragment extends BaseFragment {


    @BindView(R.id.home_network_error_layout)
    ViewStub homeNetworkErrorLayout;
    Unbinder unbinder;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    //    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.planListview)
    ListView planListview;

    private Context mContext;
    private View rootView;
    private PlanListViewAdapter planListViewAdapter;
    private List<PlanEntity> planEntityList;

    public static PlanFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        PlanFragment fragment = new PlanFragment();
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
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        unbinder = ButterKnife.bind(this, view);
        init();
        //去除状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ((Activity) mContext).getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        }
        return view;
    }

    private void init() {

        toolbarTitle.setText(R.string.main_date);
        planEntityList = new ArrayList<>();
        PlanEntity planEntity = new PlanEntity();
        planEntity.setTitle("1");
        for (int i = 0; i < 10; i++) {
            planEntityList.add(planEntity);
        }
        planListViewAdapter = new PlanListViewAdapter(mContext, planEntityList);
        planListview.setAdapter(planListViewAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
