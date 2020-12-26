package com.yongliang.houylbase.mobile_adptation.toast_change;

import android.content.Context;
import android.view.Gravity;

import com.dovar.dtoast.DToast;
import com.yongliang.houylbase.R;

/**
 * created by houyl
 * on  3:59 PM
 */
public class ToastUtils {
    public void show(Context mContext,String msg){
        //使用默认布局
        DToast.make(mContext)
                .setText(R.id.tv_content_default, msg)
                .setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 30)
                .show();
    }


}
