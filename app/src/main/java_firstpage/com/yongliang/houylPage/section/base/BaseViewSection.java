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
public abstract class BaseViewSection implements IViewSection {

    public String TAG;
    protected Context mContext;
    //    获取传递的数据 方便接口请求数据
    protected Bundle mBundle;
    private View view;

    public BaseViewSection(Context context, Bundle mBundle) {
        TAG = getClass ().getSimpleName ();
        this.mContext = context;
        this.mBundle = mBundle;
        initUI ();
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
    public void viewPropertySettings(View view,ViewAttributes viewAtts) {

    }

    ;


    //    沉浸式处理
    @Override
    public void immersiveView() {

    }
    //    添加view 展示数据


    //    布置视图
    public abstract void setAdapter(View view);

}
