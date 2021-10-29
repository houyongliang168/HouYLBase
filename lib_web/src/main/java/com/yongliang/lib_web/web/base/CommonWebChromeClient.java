package com.yongliang.lib_web.web.base;

import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.yongliang.lib_web.web.interfaces.IWebPageView;


/**
 * @author ren shi qian
 */
public class CommonWebChromeClient extends WebChromeClient {
    private String LOG_TAG = getClass ().getSimpleName ();

    //将内容回调给fragment
    View customView;
    private IWebPageView mIWebPageView;
    private CustomViewCallback mCustomViewCallback;

    public CommonWebChromeClient(IWebPageView mIWebPageView) {
        this.mIWebPageView = mIWebPageView;
    }


    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView (view, callback);
        this.mIWebPageView.setRequestedOrientation (0);
        this.mIWebPageView.hindWebView ();
        if (this.customView != null) {
            callback.onCustomViewHidden ();
        } else {
            this.mIWebPageView.fullViewAddView (view);
            this.customView = view;
            this.mCustomViewCallback = callback;
            this.mIWebPageView.showVideoFullView ();
        }

    }

    @Override
    public void onHideCustomView() {

        if (this.customView != null) {
            this.mIWebPageView.setRequestedOrientation (1);
            this.customView.setVisibility (View.GONE);
            if (this.mIWebPageView.getVideoFullView () != null) {
                this.mIWebPageView.getVideoFullView ().removeView (this.customView);
            }

            this.customView = null;
            this.mIWebPageView.hindVideoFullView ();
            this.mCustomViewCallback.onCustomViewHidden ();
            this.mIWebPageView.showWebView ();
        }

    }


    /**
     * / 接收文档标题
     *
     * @param view
     * @param title
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle (view, title);
        this.mIWebPageView.onReceivedTitle (view, title);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged (view, newProgress);
        this.mIWebPageView.onProgressChanged (view, newProgress);
    }


    /**
     * （WebView视图，字符串url，字符串消息，JsResult结果）：Js中调用alert（）函数，产生的这种
     * boolean客户端是否处理警报对话框。
     *
     * @param view
     * @param url
     * @param message
     * @param result
     * @return
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        if (this.mIWebPageView.onJsAlert (view, url, message, result)) {
            return true;
        }
        return super.onJsAlert (view, url, message, result);
    }
}
