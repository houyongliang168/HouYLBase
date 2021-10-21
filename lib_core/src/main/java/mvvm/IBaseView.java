package mvvm;

import android.os.Bundle;

import androidx.annotation.NonNull;

/**
 * Created by goldze on 2017/6/15.
 */

public interface IBaseView {
    /* 获取布局的id */
    int getLayoutId();
    /**
     *  初始化参数的方法  在获取 视图之前处理
     */
     void onBeforeCreate();
    /**
     * 页面已被启用，但因内存不足页面被系统销毁过 处理逻辑
     * @param savedInstanceState
     */
     void onBundleHandle(@NonNull Bundle savedInstanceState) ;

    /**
     * 初始化界面观察者的监听
     */
     void initViewObservable();
    /* 获取vm 的VR  值*/
     int initVariableId();
}
