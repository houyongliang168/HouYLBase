package com.yongliang.houylPage.section.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * created by houyl
 *  封装基本的流程
 */
public abstract class BaseSectionModule <T extends IViewSection,V extends IDataSection > implements IViewSection, IDataSection {
    private T mT;
    private V mV;
    public String TAG;
    protected Context mContext;
    //    获取传递的数据 方便接口请求数据
    protected Bundle mBundle;
    private View view;



    /**
     * 考虑 相关方法的替代方案
     * 如果 赋值给  IViewSection ，IDataSection 相关内容就会先执行
     * @return
     */
    public T getIViewSection(){
        return mT;
    }
    public void setIViewSection(){
        this.mT=mT;
    }

    public V getIDataSection(){
        return mV;
    }
    public void setIDataSection(){
        this.mV=mV;
    }


    //销毁相关数据
    @Override
    public void onDetach() {
        mContext = null;
        mBundle = null;
        view = null;
    }


    /**
     * 初始化控件
     */
    @Override
    public void initUI() {
        view = LayoutInflater.from (mContext).inflate (getlayoutId (), null);
    }
    @Override
    public abstract int getlayoutId();


    //    添加view 展示数据
    @Override
    public View addView(ViewGroup container) {
        if (container == null) {
            return null;
        }
        if (view == null) {
            initUI ();
        }
        setAdapter (view);
        loadData (mBundle);
        container.addView (view);
        return view;
    }

    //    添加view 展示数据
    @Override
    public void showView() {
        if (mContext != null && mContext instanceof Activity) {
            if (((Activity) mContext).isFinishing ()) {
                return;
            }
        }
        if (view != null && view.getVisibility () == View.GONE) {
            view.setVisibility (View.VISIBLE);
        }
    }

    //    添加view 展示数据
    @Override
    public void goneView() {
        if (mContext != null && mContext instanceof Activity) {
            if (((Activity) mContext).isFinishing ()) {
                return;
            }
        }
        if (view != null && view.getVisibility () == View.VISIBLE) {
            view.setVisibility (View.GONE);
        }
    }


    //    添加view 展示数据
    @Override
    public void remove(ViewGroup container) {
        if (container == null || view == null) {
            return;
        }
        container.removeView (view);
    }

    @Override
    public View getView() {
        return view;
    }


    //    设置view 相关 宽高
    @Override
    public void viewPropertySettings(View  view,ViewAttributes viewAtts) {

    }

    ;


    //    沉浸式处理
    @Override
    public void immersiveView() {

    }
    //    添加view 展示数据


    //    布置视图
    public abstract void setAdapter(View view);
    @Override
    public void onReflesh() {
        loadData (mBundle);
    }


    /*加载数据 网络请求数据 mBundle 传递的数据可能为空 */
    @Override
    public abstract void loadData(Bundle mBundle);
}
