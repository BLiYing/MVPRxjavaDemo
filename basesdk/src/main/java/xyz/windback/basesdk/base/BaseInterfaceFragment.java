package xyz.windback.basesdk.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Class description
 *
 *
 * @author LY
 * @version 1.0, 2018-3-5
 */

public interface BaseInterfaceFragment extends View.OnClickListener{


    /**
     * 初始化 view前
     */
    void beforeInitView(final Bundle savedInstanceState);

    /**
     * 绑定布局
     *
     * @return 布局 Id
     */
    @LayoutRes
    int getLayoutId();



    /**
     * 初始化 view
     */
    void initView(final Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param bundle 传递过来的 bundle
     */
    void initData(final Bundle bundle);


    /**
     * 视图点击事件
     *
     * @param view 视图
     */
    void onWidgetClick(final View view);
}
