package com.yongliang.houylPage.section.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * created by houyl
 * on  1:18 PM
 * 视图 封装接口
 */
public interface IViewSection {

    /**
     * 初始化控件
     */
    public void initUI();

//    获取布局视图id
    public  int getlayoutId();

    //    添加view
    public View addView(ViewGroup container);

    //设置view 相关 宽高 属性设置
    public void viewPropertySettings(View view,ViewAttributes viewAtts);

    //    添加view 展示数据
    public void showView();

    //添加view 展示数据
    public void goneView();

    //    添加view 展示数据
    public void remove(ViewGroup container);

    //   获取相关view
    public View getView();

    //    沉浸式处理
    public  void immersiveView();

    //销毁相关数据
    public void onDetach();

    /* 获取vm 的VR  值*/
    int initVariableId();

    Context getContext();

    //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
    void initViewObservable();
}
