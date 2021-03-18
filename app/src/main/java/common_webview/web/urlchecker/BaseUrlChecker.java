package common_webview.web.urlchecker;

import android.graphics.Bitmap;
import android.webkit.WebView;

import androidx.annotation.NonNull;

/**
 * created by houyl
 * on  4:53 PM
 */
public  class BaseUrlChecker implements IUrlChecker, Comparable<BaseUrlChecker> {
    public BaseUrlChecker() {

    }

    public  boolean shouldInterruptPageStart(WebView webView, String url, Bitmap favicon) {
        return false;
    }

    public  boolean shouldInterruptUrlOverride(WebView webView, String url) {

//        try {
//            Uri var4 = Uri.parse (var2);
//            if (!TextUtils.equals ("http", var4.getScheme ()) && !TextUtils.equals ("https", var4.getScheme ())) {
//                a (var4);
//                return true;
//            } else if (var2.endsWith (".apk")) {
//                a (var4);
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Throwable var3) {
//            return false;
//        }
        return false;
    }

    //    优先级的处理
    public int priority = 10;

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void onRelease() {

    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    @Override
    public int compareTo(@NonNull BaseUrlChecker o) {
        if (this.priority < o.priority) return -1;
        else if (this.priority > o.priority) return 1;
        else return 0;
    }
}
