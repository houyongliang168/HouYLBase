package com.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by liuyang249 on 2018/7/17.
 * 解决listview嵌套在scrollview的问题
 */

public class CustomeListView extends ListView {
    public CustomeListView(Context context) {
        super(context);
    }

    public CustomeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
