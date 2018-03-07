
![image](https://github.com/BLiYing/MVPRxjavaDemo/blob/master/app%2Fsreenshots%2Ffulv1.gif)

# 一 页面继承约定

### 1.	Activity必须继承子工程basesdk包中的BaseAppCompatActivity（搜索门票和景点详情页面除外）

### 2.	Fragment必须继承子工程basesdk包中的BaseFragment

### 3.	全局静态常量全部放在主工程的base包中

# 二 风格约定

### 1.	红色按钮background统一用下图该布局文件(已经适配所有android版本)（drawable-v21为android5.0及以上机型水波纹效果）
见selector_same_btn.xml。

### 2.	风格统一为（除开MainActivity）
android:theme="@style/AppTheme"



