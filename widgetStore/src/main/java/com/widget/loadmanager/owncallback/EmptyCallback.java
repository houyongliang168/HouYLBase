package com.widget.loadmanager.owncallback;


import com.widget.loadmanager.callback.Callback;
import com.yongliang.widgetstore.R;

/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.module_layout_no_data;
    }

}
