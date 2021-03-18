package common_webview.web.interfaces;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.FrameLayout;

/**
 * created by houyl
 * on  4:51 PM
 */
public interface IWebPageView {

    void showWebView();

    void hindWebView();

    void startProgress(int var1);

    void fullViewAddView(View var1);

    void showVideoFullView();

    void hindVideoFullView();

    void setRequestedOrientation(int var1);

    FrameLayout getVideoFullView();

    View getVideoLoadingProgressView();

     void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error);

    boolean onJsAlert(WebView view, String url, String message, JsResult result);

    void onReceivedTitle(WebView view, String title);
    void onProgressChanged(WebView view, int newProgress);

    void onPageFinished(WebView view, String url);

    void onPageStart(WebView view, String url, Bitmap favicon);

    boolean onShouldOverrideUrlLoading(WebView view, String url);

    WebResourceResponse shouldInterceptRequest(WebView view, String url);
}
