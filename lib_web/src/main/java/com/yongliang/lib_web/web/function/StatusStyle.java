package com.yongliang.lib_web.web.function;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yongliang.lib_web.web.WebCommonFragment;
import com.yongliang.lib_web.web.js.H5BasePlugin;
import com.yongliang.lib_web.web.js.H5Event;


/**
 * 加密的sdk
 */
public class StatusStyle extends H5BasePlugin {
    private String TAG = StatusStyle.class.getSimpleName ();

    public StatusStyle() {
    }

    @Override
    public boolean handleEvent(H5Event h5Event, WebCommonFragment fragment) {
        if (h5Event == null) {
            return false;
        }
        try {

            String style = h5Event.getJsonSource ();
            Window window = fragment.getActivity ().getWindow ();
            if ("0".equals (style)) {
                //hei
                window.addFlags (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.getDecorView ().setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else if ("1".equals (style)) {
                //bai
                window.addFlags (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.getDecorView ().setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return true;
    }

    @Override
    public void onRelease() {

    }

    @Override
    public String getCallBackName() {
        return null;
    }


}
