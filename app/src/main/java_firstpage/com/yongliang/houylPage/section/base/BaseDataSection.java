package com.yongliang.houylPage.section.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * created by houyl
 *  封装基本的流程
 */
public abstract class BaseDataSection implements IDataSection {

    public String TAG;
    protected Context mContext;
    //    获取传递的数据 方便接口请求数据
    protected Bundle mBundle;
    private View view;

    public BaseDataSection(Context context, Bundle mBundle) {
        TAG = getClass ().getSimpleName ();
        this.mContext = context;
        this.mBundle = mBundle;
    }

    //销毁相关数据
    public void onDetach() {
        mContext = null;
        mBundle = null;
        view = null;
    }




    @Override
    public void onReflesh() {
        loadData (mBundle);
    }


    /*加载数据 网络请求数据 mBundle 传递的数据可能为空 */
    @Override
    public abstract void loadData(Bundle mBundle);
}
