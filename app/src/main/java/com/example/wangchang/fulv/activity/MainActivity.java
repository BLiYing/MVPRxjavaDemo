package com.example.wangchang.fulv.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.wangchang.fulv.R;
import com.example.wangchang.fulv.fragment.BookFragment;
import com.example.wangchang.fulv.fragment.HomeFragment;
import com.example.wangchang.fulv.fragment.MineFragment;
import com.example.wangchang.fulv.fragment.PlanFragment;
import com.example.wangchang.fulv.listen.AppBarStateChangeListener;
import com.example.wangchang.fulv.view.loadview.SimpleLoadDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subjects.PublishSubject;
import xyz.windback.basesdk.base.ActivityLifeCycleEvent;
import xyz.windback.basesdk.base.BaseAppCompatActivity;

/**
 * Describe:首页
 * Created by liying on 2018/3
 */
public class MainActivity extends BaseAppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.imageOfCollapsingIv)
    ImageView imageOfCollapsingIv;
    @BindView(R.id.other_titleTv)
    TextView otherTitleTv;
    @BindView(R.id.other_toorbarLy)
    LinearLayout otherToorbarLy;
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
    @BindView(R.id.layFrame)
    FrameLayout layFrame;
    @BindView(R.id.nestSv)
    NestedScrollView nestSv;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    /*@BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.historyIv)
    ImageView historyIv;
    @BindView(R.id.searchTv)
    TextView searchTv;
    @BindView(R.id.callIv)
    ImageView callIv;
    @BindView(R.id.appbarLayout)
    AppBarLayout appbarLayout;

    @BindView(R.id.imageOfCollapsingIv)
    ImageView imageOfCollapsingIv;
    @BindView(R.id.layFrame)
    FrameLayout layFrame;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.other_titleTv)
    TextView otherTitleTv;
    @BindView(R.id.other_toorbarLy)
    LinearLayout otherToorbarLy;
    @BindView(R.id.main_toorbarLy)
    LinearLayout mainToorbarLy;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.nestSv)
    NestedScrollView nestSv;
    *//*@BindView(R.id.toolbar)
    Toolbar toolBarHome;*/

    private ArrayList<Fragment> fragments;
    private Context mContext;

    private HomeFragment homeFragment;
    private BookFragment bookFragment;
    private PlanFragment planFragment;
    private MineFragment settingFragment;

    private BadgeItem numberBadgeItem;
    private SimpleLoadDialog dialogHandler;
    public static MainActivity activity;
    private AppBarStateChangeListener.State stateOfAppbarLayout = AppBarStateChangeListener.State.EXPANDED;

    public static MainActivity getInstance() {
        if (activity != null) {
            return activity;
        }
        return activity;
    }

    public PublishSubject<ActivityLifeCycleEvent> getLifeSubject() {
        return lifecycleSubject;
    }

//    BottomNavigationBar bottomNavigationBar;

    @Override
    public void beforeInitView(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mContext = this;
        activity = this;
        ButterKnife.bind(this);
        initToolBar();
        dialogHandler = new SimpleLoadDialog(MainActivity.this, null, true);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        bottomNavigationBar.setBarBackgroundColor(R.color.navigation_bg);
        bottomNavigationBar.setAutoHideEnabled(false);

        /*numberBadgeItem = new BadgeItem(){}
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                .setText("5")
                .setHideOnSelect(true)
                .setBorderColorResource(R.color.transparent);*/
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.main_home, R.string.main_home).setActiveColorResource(R.color.red))
//                .addItem(new BottomNavigationItem(R.mipmap.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.mipmap.main_date, R.string.main_date).setActiveColorResource(R.color.red))
//                .addItem(new BottomNavigationItem(R.mipmap.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.brown).setTextBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.main_mine, R.string.main_mine).setActiveColorResource(R.color.red))
                .setFirstSelectedPosition(0)
                .initialise();

        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.toggle();
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
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected boolean isShowBacking() {
        return false;
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

    private void initToolBar() {
//        imageOfCollapsingIv.setVisibility(View.VISIBLE);
        nestSv.setNestedScrollingEnabled(true);//设置嵌套滑动可用
        Drawable drawableLeft = ContextCompat.getDrawable(this,
                R.drawable.main_search_icon);
        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());

        searchTv.setCompoundDrawables(drawableLeft, null, null, null);
        searchTv.setCompoundDrawablePadding(8);//设置图片和text之间的间距
        /*toolbarMain.setTitle("");
        toolbarMain.setTitleTextColor(ContextCompat.getColor(mContext, R.color.black));
        toolbarMain.setSubtitle("");
        toolBarHome.setLogo(R.mipmap.ic_launcher);
        toolbarMain.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));*/
        mainToorbarLy.setVisibility(View.VISIBLE);
        otherToorbarLy.setVisibility(View.GONE);
        setSupportActionBar(toolbarMain);
    }

    /**
     * 切换fragment时更改主题
     *
     * @param title
     */
    private void initToolBarOther(String title) {
//        imageOfCollapsingIv.setVisibility(View.GONE);
        nestSv.setNestedScrollingEnabled(false);//设置嵌套滑动不能用
        mainToorbarLy.setVisibility(View.GONE);
        otherToorbarLy.setVisibility(View.VISIBLE);
        otherTitleTv.setText(title);

    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = HomeFragment.newInstance("");
        transaction.replace(R.id.layFrame, homeFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                initToolBar();
                if (homeFragment == null) {
                    homeFragment = (HomeFragment.newInstance(""));
                }

                transaction.replace(R.id.layFrame, homeFragment);
                transaction.commitAllowingStateLoss();
                break;
            /*case 1:
                initToolBarOther();
                if (bookFragment == null) {
                    bookFragment = BookFragment.newInstance("");
                }
                transaction.replace(R.id.layFrame, bookFragment);
                transaction.commitAllowingStateLoss();
                break;*/
            case 1:
//                collapsingToolbar.setA
                initToolBarOther(getString(R.string.main_date));
                if (planFragment == null) {
                    planFragment = PlanFragment.newInstance("");
                }
                transaction.replace(R.id.layFrame, planFragment);
                transaction.commitAllowingStateLoss();
                break;
            /*case 3:
                if (tvFragment == null) {
                    tvFragment = TvFragment.newInstance("");
                }
                transaction.replace(R.id.layFrame, tvFragment);
                transaction.commitAllowingStateLoss();
                break;*/
            case 2:
                initToolBarOther(getString(R.string.main_mine));
                if (settingFragment == null) {
                    settingFragment = MineFragment.newInstance("");
                }
                transaction.replace(R.id.layFrame, settingFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }


    }


    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
