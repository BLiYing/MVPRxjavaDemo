package xyz.windback.basesdk.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

//import rx.subjects.PublishSubject;
import rx.subjects.PublishSubject;
import xyz.windback.basesdk.R;

/**
 * Class description
 *
 * @author WJ
 * @version 1.0, 2018-1-10
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity implements BaseInterface {

    private static final String TAG = BaseAppCompatActivity.class.getSimpleName();
    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();
    private TextView mToolbarTitle;
    private Toolbar mToolbar;
    protected boolean isTransAnim;
    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE);
        getWindow().setBackgroundDrawable(null);
        beforeInitView(savedInstanceState);
        setContentView(getLayoutId());
        isTransAnim = true;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        if (mToolbar != null) {
            //将Toolbar显示到界面
            setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null) {
            //getTitle()的值是activity的android:lable属性值
            mToolbarTitle.setText(getTitle());
            //设置默认的标题不显示
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
//        initViewBase();
        initView(savedInstanceState);
        initData(savedInstanceState);
        if (null != getToolbar() && isShowBacking()) {
            showBack();
        }

    }

    /**
     * this Activity of tool bar.
     * 获取头部.
     *
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }


    /**
     * 设置头部标题
     *
     * @param title
     */
    public void setToolBarTitle(CharSequence title) {
        if (mToolbarTitle != null) {
            getToolbar().setVisibility(View.VISIBLE);
            mToolbarTitle.setText(title);
            mToolbarTitle.setCompoundDrawables(null, null, null, null); //设置无图标
        } else {
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }


    /**
     * 版本号小于21的后退按钮图片
     */
    protected void showBack() {
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        getToolbar().setNavigationIcon(R.drawable.back_selector);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                if(!isFastClick()){
                    finish();
                }
            }
        });
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    protected boolean isShowBacking() {
        return true;
    }



    @Override
    public void onClick(final View view) {
        if (!isFastClick()) onWidgetClick(view);
    }

    @Override
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
        super.onDestroy();

    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 500) {
            lastClick = now;
            return false;
        }
        return true;
    }

    /**
     * [页面跳转]
     *
     * @param clz 要跳转的Activity
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
        if (isTransAnim)
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out);
    }

    /**
     * [页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    public void startActivity(Class<?> clz, Intent intent) {
        intent.setClass(this, clz);
        startActivity(intent);
        if (isTransAnim)
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz    要跳转的Activity
     * @param bundle bundel数据
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (isTransAnim)
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundel数据
     * @param requestCode requestCode
     */
    public void startActivityForResult(Class<?> clz, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        if (isTransAnim)
            overridePendingTransition(R.anim.activity_start_zoom_in, R.anim
                    .activity_start_zoom_out);
    }


    @Override
    public void finish() {
        super.finish();
        if (isTransAnim)
            overridePendingTransition(R.anim.activity_finish_trans_in, R.anim
                    .activity_finish_trans_out);
    }

    /**
     * 是否使用overridePendingTransition过度动画
     * @return 是否使用overridePendingTransition过度动画，默认使用
     */
    protected boolean isTransAnim() {
        return isTransAnim;
    }

    /**
     * 设置是否使用overridePendingTransition过度动画
     */
    protected void setIsTransAnim(boolean b){
        isTransAnim = b;
    }


    /**
     * 重新加载
     */
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v     视图
     * @param event 视图事件
     * @return 返回
     */
    public boolean isShouldHideKeyboard(View v, MotionEvent event) {
        try {
            v.getParent().requestDisallowInterceptTouchEvent(true);//通知父控件勿拦截本控件touch事件
            if (v instanceof EditText) {
                int[] l = {0, 0};
                v.getLocationInWindow(l);
                int left = l[0],
                        top = l[1],
                        bottom = top + v.getHeight(),
                        right = left + v.getWidth();
                // 点击EditText的事件，忽略它。
                return event.getX() <= left || event.getX() >= right
                        || event.getY() <= top || event.getY() >= bottom;
            }
        } catch (Exception e) {
            return false;
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }


    /**
     * 隐藏键盘
     *
     * @return 隐藏键盘结果
     * <p>
     * true:隐藏成功
     * <p>
     * false:隐藏失败
     */
    protected boolean hiddenKeyboard() {
        //点击空白位置 隐藏软键盘
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService
                (INPUT_METHOD_SERVICE);
        return mInputMethodManager.hideSoftInputFromWindow(this
                .getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.RESUME);
        super.onResume();
    }

    @Override
    protected void onPause() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStart() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.START);
        super.onStart();
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP);
        super.onStop();
    }


}
