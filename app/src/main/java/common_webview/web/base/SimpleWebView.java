package common_webview.web.base;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;



/**
 * @author ren shi qian
 */
public class SimpleWebView extends WebView {

    private static String sUserAgent;

    public SimpleWebView(Context context) {
        this(context, null);
    }

    public SimpleWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // webview 插件化后对资源的统一处理
        WebViewResourceHelper.addChromeResourceIfNeeded(context);
    }

    public SimpleWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // webview 插件化后对资源的统一处理
        WebViewResourceHelper.addChromeResourceIfNeeded(context);
    }

    public String getUserAgentEx() {
        if (sUserAgent == null) {
            // 泰行销所有的webview必须添加下列两行代码
            WebSettings ws = getSettings();
            String ua = ws.getUserAgentString();
            sUserAgent = ua ;
        }
        return sUserAgent;
    }

    @Override
    public void loadUrl(String url) {
        // 此处可“种植”Cookie（Q&T）
        super.loadUrl(url);
    }
}
