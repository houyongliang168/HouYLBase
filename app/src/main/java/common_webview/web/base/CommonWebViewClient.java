package common_webview.web.base;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import common_webview.web.interfaces.IWebPageView;


/**
 * @author ren shi qian
 */
public class CommonWebViewClient extends WebViewClient {

    public String LOG_TAG = getClass ().getSimpleName ();
    // 使用者提供的Context
    private IWebPageView mIWebPageView;


    public CommonWebViewClient(IWebPageView mIWebPageView) {
        this.mIWebPageView = mIWebPageView;
    }



    public  void onReceivedError(WebView webView, int errorCode,
                                 String description, String failingUrl) {
        super.onReceivedError (webView, errorCode, description, failingUrl);
//        if (errorCode == 404) {
//            String var5 = "Page NO FOUND！";
//            webView.loadUrl ("javascript:document.body.innerHTML=\"" + var5 + "\"");
//        }

    }

//
//    @Override
//    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//
//        return super.shouldInterceptRequest (view, url);
//    }
//
//
//    // API21以上用shouldInterceptRequest(WebView view, WebResourceRequest request)
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//        return super.shouldInterceptRequest (view, request);
//    }
//
//
//    @Override
//    public void onLoadResource(WebView view, String url) {
//        super.onLoadResource (view, url);
//    }
//
//    @Override
//    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
//        super.doUpdateVisitedHistory (view, url, isReload);
//
//
//    }
//
//    @Override
//    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//
//        super.onReceivedError (view, request, error);
//    }

    /**
     * 是否在WebView内加载页面。
     * 给主机应用一个机会接管控制当一个新的url将要被加载在当前WebView。
     * 如果没有提供WebViewClient ，默认的WebView将要求Activity Manager 去选择一个合适的handle为这个url。
     * 如果有WebViewClient ，返回 ‘true’,意思是说主应用程序处理这个url,否则返回‘false’意思是当前的WebView要处理这个url。这个方法不允许使用‘post’请求方法调用。
     *
     * @param view 发起回调的WebView
     * @param url  加载资源的url
     * @return 如果主机应用程序想要离开当前的WebView并且它自己处理url，那么返回 ‘true’,否则，返回‘false’。
     * <p>
     * // 拦截页面加载，返回true表示宿主app拦截并处理了该url，否则返回false由当前WebView处理
     * // 此方法在API24被废弃，不处理POST请求
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (TextUtils.isEmpty (url)) {
            return false;
        } else {
            return this.mIWebPageView != null ? this.mIWebPageView.onShouldOverrideUrlLoading (view, url) : super.shouldOverrideUrlLoading (view, url);
        }
    }

    // 拦截页面加载，返回true表示宿主app拦截并处理了该url，否则返回false由当前WebView处理
// 此方法添加于API24，不处理POST请求，可拦截处理子frame的非http请求
    @TargetApi(Build.VERSION_CODES.N)
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return shouldOverrideUrlLoading (view, request.getUrl ().toString ());
    }

    // 通知应用页面缩放系数变化
    @Override
    public void onScaleChanged(WebView webView, float oldScale, float newScale) {
        super.onScaleChanged (webView, oldScale, newScale);
        if (newScale - oldScale > 7.0F) {
            webView.setInitialScale ((int) (oldScale / newScale * 100.0F));
        }
    }

    /**
     * // 加载资源时发生了一个SSL错误，应用必需响应(继续请求或取消请求)
     * // 处理决策可能被缓存用于后续的请求，默认行为是取消请求
     * 在认证证书不被Android所接受的情况下，我们可以通过设置重写WebViewClient的onReceivedSslError方法在其中设置接受所有网站的证书来解决，具体代码如下
     *
     * @param view
     * @param handler
     * @param error
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        try {
            handler.proceed ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }


    @Override
    public final void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (this.mIWebPageView != null) {
            this.mIWebPageView.onPageStart (view, url, favicon);
        }
        super.onPageStarted (view, url, favicon);
    }

    @Override
    public final void onPageFinished(WebView view, String url) {
        if (this.mIWebPageView != null) {
            this.mIWebPageView.onPageFinished (view, url);
        }
        super.onPageFinished (view, url);
    }
}
