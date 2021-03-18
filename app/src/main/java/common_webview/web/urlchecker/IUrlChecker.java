package common_webview.web.urlchecker;

import android.graphics.Bitmap;
import android.webkit.WebView;


/**
 * created by houyl
 * on  4:51 PM
 */
public interface IUrlChecker {

    boolean shouldInterruptPageStart(WebView webView, String url, Bitmap favicon);

    boolean shouldInterruptUrlOverride(WebView webView, String url);
    //    对外提供优先级 排序 基础优先级为10  优先级越高 值越小
    public  int getPriority();
    //    释放内容
    void onRelease();
}
