
![image](https://github.com/BLiYing/MVPRxjavaDemo/blob/master/app%2Fsreenshots%2Ffulv1.gif)

rxjava用的是1.x版本，由于2.x版本语法变化较大，暂时不考虑升级。

## 一 页面继承约定

### 1.	Activity必须继承子工程basesdk包中的BaseAppCompatActivity（搜索门票和景点详情页面除外）

### 2.	Fragment必须继承子工程basesdk包中的BaseFragment

### 3.	全局静态常量全部放在主工程的base包中

## 二 风格约定

### 1.	红色按钮background统一用下图该布局文件(已经适配所有android版本)（drawable-v21为android5.0及以上机型水波纹效果）
见selector_same_btn.xml。

### 2.	风格统一为（除开MainActivity）
android:theme="@style/AppTheme"

### 3.	权限判断库统一使用
com.yanzhenjie:permission
用法参照WelcomeActivity.class中用法
github地址：http://www.yanzhenjie.com/AndPermission/cn/usage.html

### 4.	数据库使用的是
com.orhanobut:hawk

### 5.	页面跳转动画配置：
Res的Anim中。

### 6.	图片只用一套最高分辨率的xxxhdpi即可

### 7.	这个checkupdatelibrary是更新库

### 8.	Mvp的内存泄漏管理。必须实现
![image](https://github.com/BLiYing/MVPRxjavaDemo/blob/master/app%2Fsreenshots%2Fpic1.png)

## 三 关于引用第三方库配置文件及共用工具的说明

### 1.	全部放在config.gradle文件中，统一管理版本号。引用方式参考项目中主工程.gradle写法

### 2.	相关工具类存放在子工程basesdk的基类框架库中，包名是utils。先去这里寻找自己需要的工具类，如果没有再单独添加到主工程的util包中






