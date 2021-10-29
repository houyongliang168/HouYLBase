package com.yongliang.lib_web.web.view;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.yongliang.lib_web.R;


/**
 * created by houyl
 * 给视频一个view 入口
 * on  6:56 PM
 */
public class BaseFramelayout extends FrameLayout {
    public BaseFramelayout(Context context) {
        super (context);
        this.setBackgroundColor (context.getResources ().getColor (R.color.colorffffff));
    }

    public final boolean onTouchEvent(MotionEvent var1) {
        return true;
    }

}