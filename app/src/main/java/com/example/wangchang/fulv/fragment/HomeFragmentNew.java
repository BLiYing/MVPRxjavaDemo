package com.example.wangchang.fulv.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangchang.fulv.R;
import com.example.wangchang.fulv.activity.TestActivity;
import com.example.wangchang.fulv.adapter.HomeListViewAdapter;
import com.example.wangchang.fulv.base.CacheKey;
import com.example.wangchang.fulv.entity.HomeGirlsEntityParcelable;
import com.example.wangchang.fulv.listen.AppBarStateChangeListener;
import com.example.wangchang.fulv.mvpdemo.contract.HomeContract;
import com.example.wangchang.fulv.mvpdemo.presenter.HomePresent;
import com.example.wangchang.fulv.util.NetUtil;
import com.example.wangchang.fulv.view.MyListView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import xyz.windback.basesdk.base.BaseFragment;
import xyz.windback.basesdk.utils.LogUtil;
import xyz.windback.basesdk.utils.ToastUtils;

/**
 * Describe:首页fragment
 * Created by liying on 2018/3/6
 */
public class HomeFragmentNew extends BaseFragment implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {
    @BindView(R.id.imageOfCollapsingIv)
    ImageView imageOfCollapsingIv;
    @BindView(R.id.historyIv)
    ImageView historyIv;
    @BindView(R.id.searchTv)
    TextView searchTv;
    @BindView(R.id.callIv)
    ImageView callIv;
    @BindView(R.id.main_toorbarLy)
    LinearLayout mainToorbarLy;
    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R.id.cardbagTv)
    TextView cardbagTv;
    @BindView(R.id.rechargeTv)
    TextView rechargeTv;
    @BindView(R.id.ticketTv)
    TextView ticketTv;
    @BindView(R.id.whentoorbarisshow_ly)
    LinearLayout whentoorbarisshowLy;
    @BindView(R.id.listview)
    MyListView listview;
    @BindView(R.id.home_network_error_layout)
    ViewStub homeNetworkErrorLayout;
    @BindView(R.id.nestSv)
    NestedScrollView nestSv;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    private AppBarStateChangeListener.State stateOfAppbarLayout = AppBarStateChangeListener.State.EXPANDED;
    private Context mContext;
    private View rootView;
    private Unbinder mUnbinder;
    private HomeListViewAdapter homeListViewAdapter;

    private int page = 1;
    private int size = 10;
    private ArrayList<HomeGirlsEntityParcelable> data;
    private View networkErrorView;
    //    TextView tryagain_a_tv;
    private HomePresent homePresent;

    public static HomeFragmentNew newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        HomeFragmentNew fragment = new HomeFragmentNew();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        homePresent = new HomePresent(mContext, this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView != null) {
            mUnbinder = ButterKnife.bind(this, rootView);
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_home_new, container, false);
            mUnbinder = ButterKnife.bind(this, rootView);
            initView(rootView);
        }
        //状态栏透明
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ((Activity) mContext).getWindow().setStatusBarColor(ContextCompat.getColor(mContext, R.color.transparent));
        }
        return rootView;
    }


    private void initView(View view) {
        data = new ArrayList<>();
        //设置搜索栏Textview图片
        Drawable drawableLeft = ContextCompat.getDrawable(mContext,
                R.drawable.main_search_icon);
        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        searchTv.setCompoundDrawables(drawableLeft, null, null, null);
        searchTv.setCompoundDrawablePadding(8);//设置图片和text之间的间距

        //设定滑动监听
        appbarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {

                if (state == State.EXPANDED) {
                    //展开状态
                    stateOfAppbarLayout = State.EXPANDED;
                    toolbarMain.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    setBoardWhite();
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    stateOfAppbarLayout = State.COLLAPSED;
                    toolbarMain.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                    setBoardBlue();
                    // TODO: 2018-3-2 改变图标颜色：历史，搜索框，电话
                } else {
                    //中间状态
                    stateOfAppbarLayout = State.IDLE;
                    toolbarMain.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    setBoardWhite();
                }
            }
        });
        if(NetUtil.isConnected(mContext)) {
            homePresent.getGirlsOfPresent(size, page, true, CacheKey.FIRSTGETGIRLS, true, true, true);
        }else{
            ToastUtils.show(mContext,R.string.no_network);
        }
        cardbagTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity((Activity) mContext, TestActivity.class);
            }
        });
    }

    private void setBoardBlue() {
        callIv.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.main_call_blue));
        historyIv.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.main_history_blue));
        searchTv.setBackgroundResource(R.drawable.main_input_blue);

    }

    private void setBoardWhite() {
        callIv.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.main_call));
        historyIv.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.main_history));
        searchTv.setBackgroundResource(R.drawable.main_input);

    }

    private View noDataView;

    public void showEmptyView() {
        listview.setVisibility(View.GONE);
        if (noDataView == null) {

            ViewStub noDataViewStub = homeNetworkErrorLayout;
            noDataView = noDataViewStub.inflate();
        } else {
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    public void showListView() {
        listview.setVisibility(View.VISIBLE);
        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
        homeListViewAdapter = new HomeListViewAdapter(mContext, data);
        listview.setAdapter(homeListViewAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity((Activity) mContext, TestActivity.class);
            }
        });

    }

    private void showError() {

        listview.setVisibility(View.GONE);
        if (noDataView == null) {

            ViewStub noDataViewStub = homeNetworkErrorLayout;
            noDataView = noDataViewStub.inflate();
        } else {
            noDataView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onRefresh() {
        if (!NetUtil.isConnected(mContext)) {
            ToastUtils.show(mContext, R.string.no_network);
            return;
        }
        // TODO: 2018-3-5 添加下拉刷新
//        swipeRefreshLayout.setRefreshing(true);
        homePresent.getGirlsOfPresent(size, 1, true, CacheKey.FIRSTGETGIRLS, true, true, true);
        page = 1;

    }

    @Override
    public void onLoadMore() {
        if (!NetUtil.isConnected(mContext)) {
            ToastUtils.show(mContext, R.string.no_network);
            return;
        }
        if (data.size() % 10 == 0) {
            LogUtil.e("oldmore");
            page++;
            homePresent.getGirlsOfPresent(size, page, false, CacheKey.LOADMOREGIRLS + String.valueOf(page), true, false, false);
        }
    }

    /**
     * 下拉刷新
     *
     * @param datas
     */
    private void refresh(List<HomeGirlsEntityParcelable> datas) {

        data.clear();
        data.addAll(datas);
       /* if ((datas != null && datas.size() <= 5) || datas == null) {
            mHomeAdapter.stopMore();
        }*/
        showListView();

    }

    /**
     * 上拉加载更多
     *
     * @param datas
     */
    public void load(List<HomeGirlsEntityParcelable> datas) {
        data.addAll(datas);
//        mHomeAdapter.addAll(datas);
//        if ((datas != null && datas.size() == 0) || datas == null) {
//            mHomeAdapter.stopMore();
//        }
        homeListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(boolean isfresh) {
        if (isfresh) {
            // TODO: 2018-3-5 添加下拉刷新
//            swipeRefreshLayout.setRefreshing(false);
        }
        showError();

        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.VISIBLE);
            return;
        }
        networkErrorView = homeNetworkErrorLayout.inflate();

        TextView textView = (TextView) networkErrorView.findViewById(R.id.tryagain_tv);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2018-3-5 添加下拉刷新
//                    swipeRefreshLayout.setRefreshing(true);
                    homePresent.getGirlsOfPresent(size, page, true, CacheKey.FIRSTGETGIRLS, true, false, false);
                }
            });
        }


    }

    @Override
    public void showNormal() {

        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {

        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        if (homePresent != null) {
            homePresent.destroy();
            homePresent = null;
        }
        super.onDestroyView();
    }


    @Override
    public void showGirls(boolean isfresh, List<HomeGirlsEntityParcelable> list) {
        if (isfresh) {
            refresh(list);
            // TODO: 2018-3-5 添加下拉刷新
//            swipeRefreshLayout.setRefreshing(false);

        } else {
            load(list);
        }
    }


}
