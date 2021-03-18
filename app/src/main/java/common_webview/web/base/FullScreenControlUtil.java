package common_webview.web.base;

import android.app.Activity;
import android.os.Build;
import android.view.View;

/**
 * Created by REN SHI QIAN on 2018/11/15.
 */

public class FullScreenControlUtil {
    /**
     * 隐藏虚拟按键，并且全屏
     */
    public static void hideNavigation(Activity activity) {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  // hide nav bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;//唤出的是透明的系统栏和虚拟按钮，短暂的时间后系统栏和虚拟按钮会自动隐藏
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 打开虚拟按键，并且取消全屏
     */
    public static void showNavigation(Activity activity) {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.VISIBLE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
