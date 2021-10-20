package com.yongliang.houylbase.webview

import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.utils.log.MyToast

/**
 *
 * created by houyl
 * on  10:33 AM
 */
class MyWebChromeClient  : WebChromeClient(){

    override fun onJsAlert(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean {
        message?.let {
            MyToast.showShort(it)
        }
        return super.onJsAlert(view, url, message, result)
    }
}