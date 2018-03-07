package com.example.wangchang.fulv.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.example.wangchang.fulv.R;
import com.example.wangchang.fulv.activity.MainActivity;
import com.example.wangchang.fulv.activity.MainActivityNew;
import com.example.wangchang.fulv.adapter.bookadapter.BookAdapter;
import com.example.wangchang.fulv.base.CacheKey;
import com.example.wangchang.fulv.base.DataKey;
import com.example.wangchang.fulv.entity.parcelabledemo.SubjectEntityParcelable;
import com.example.wangchang.fulv.http.Api;
import com.example.wangchang.fulv.http.ProgressSubscriber;
import com.example.wangchang.fulv.http.bookapi.HttpUtilBook;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import xyz.windback.basesdk.base.ActivityLifeCycleEvent;
import xyz.windback.basesdk.utils.LogUtil;

import static android.content.ContentValues.TAG;

/**
 * Describe:待用测试页
 * Created by liying on 2018/3/5
 */
public class BookFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {


    @BindView(R.id.book_erv)
    EasyRecyclerView bookErv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.network_error_layout)
    ViewStub mNetworkErrorLayout;


    private Context mContext;
    private View rootView;
    private Unbinder mUnbinder;
    private BookAdapter bookAdapter;

    private int start = 0;
    private int count = 5;
//    private int start_count = 5;
    private ArrayList<SubjectEntityParcelable> data;


    private View networkErrorView;

    public static BookFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        BookFragment fragment = new BookFragment();
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
            mUnbinder = ButterKnife.bind(this, rootView);
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_book, container, false);
            mUnbinder = ButterKnife.bind(this, rootView);
            initView(rootView);
            doGetDoubanMovie(start, count, true, CacheKey.MOVIEKEY, true, true, true);
        }

        return rootView;
    }

    private void initView(View view) {
        data = new ArrayList<>();

        bookAdapter = new BookAdapter(mContext);
        bookErv.setLayoutManager(new LinearLayoutManager(mContext));
        bookErv.setAdapterWithProgress(bookAdapter);
        bookAdapter.setMore(R.layout.load_more_layout, this);
        bookAdapter.setNoMore(R.layout.no_more_layout);
        bookAdapter.setError(R.layout.error_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setProgressViewOffset(false, DataKey.swipeStart, DataKey.swipeEnd);
        swipeRefreshLayout.setOnRefreshListener(this);
        bookAdapter.setOnItemClickListener(new BookAdapter.BookItemOnclickListen() {
            @Override
            public void onItemClick(int position, BaseViewHolder viewHold) {


            }


        });
    }

    /**
     * 获取豆瓣电影
     * @param start
     * @param count
     * @param isfresh
     * @param cacheKey
     * @param issave
     * @param forceRefresh
     * @param isShowDialog
     */
    private void doGetDoubanMovie(int start, int count,
                                  final boolean isfresh,
                                  final String cacheKey,
                                  final boolean issave,
                                  final boolean forceRefresh,
                                  final boolean isShowDialog) {
        //获取豆瓣电影TOP 100
        Observable ob = Api.getDoubanService().getTopMovie(start, count);
        HttpUtilBook.getInstance().toSubscribe(ob, new ProgressSubscriber<List<SubjectEntityParcelable>>(mContext) {
                    @Override
                    protected void _onError(String message) {
                        if (isfresh){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        showError();
                    }

                    @Override
                    protected void _onNext(List<SubjectEntityParcelable> list) {

                        if (isfresh) {
                            refresh(list);
                            swipeRefreshLayout.setRefreshing(false);

                        } else {
                            load(list);
                        }
                        showNormal();


                    }
                }, cacheKey, ActivityLifeCycleEvent.CREATE,
                MainActivityNew.getInstance().getLifeSubject(),
                issave,
                forceRefresh,
                isShowDialog);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder != null){
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        doGetDoubanMovie(start, count, true, CacheKey.MOVIEKEY, false, false, false);
        start = 0;
    }

    @Override
    public void onLoadMore() {
        if (data.size() % count == 0) {
            LogUtil.d(TAG, "onloadmore");
            start++;
            doGetDoubanMovie(start, count, false, CacheKey.MOREMOVIEKEY+ String.valueOf(start), true, true, false);
        }
    }

    private void refresh(List<SubjectEntityParcelable> datas) {
        data.clear();
        data.addAll(datas);
        bookAdapter.clear();
        bookAdapter.addAll(datas);
        if ((datas != null && datas.size() <= 5) || datas == null) {
            bookAdapter.stopMore();
        }

    }

    public void load(List<SubjectEntityParcelable> datas) {
        data.addAll(datas);
        bookAdapter.addAll(datas);
        if ((datas != null && datas.size() == 0) || datas == null) {
            bookAdapter.stopMore();
        }
    }

    public void showError() {
        bookErv.showError();
        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.VISIBLE);
            return;
        }
        networkErrorView = mNetworkErrorLayout.inflate();
        TextView textView = (TextView) networkErrorView.findViewById(R.id.tryagain_tv);
        if(textView != null)
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swipeRefreshLayout.setRefreshing(true);
                    doGetDoubanMovie(start, count, true, CacheKey.MOVIEKEY, true, true, false);
                }
            });
    }

    public void showNormal() {
        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.GONE);
        }

    }
}
