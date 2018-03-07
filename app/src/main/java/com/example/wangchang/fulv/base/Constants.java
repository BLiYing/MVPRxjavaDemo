package com.example.wangchang.fulv.base;


import com.example.wangchang.fulv.application.BaseApplication;

import xyz.windback.basesdk.utils.FileUtil;

/**
 * Describe:保存项目中用到的常量
 * Created by liying on 2018/2/7.
 *
 */
public class Constants {
    public static final String dir = FileUtil.getDiskCacheDir(BaseApplication.getInstance()) + "/girls";
}
