package com.yongliang.houylbase.webview

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.webkit.*
import com.utils.log.MyToast
import com.yongliang.houylbase.utils.JSUtils
import kotlinx.android.synthetic.main.content_webview.*
import java.io.IOException

/**
 *
 * created by houyl
 * on  10:33 AM
 */
class MyWebviewClient  : WebViewClient(){

    override fun onPageFinished(view: WebView?, url: String?) {

        var  jsStr = "console.log(\"输入成功了 onPageFinished\")"

        if (Build.VERSION.SDK_INT >= 19) {
            view?.evaluateJavascript(jsStr, ValueCallback<String?>() {
                Log.d("houyl", "value=" + it);
            });
        }
        super.onPageFinished(view, url)


    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        view?.let {
//            var jsStr = JSUtils.getJSContentFromAsset(view.context, "aa.js")
//方式一
            var  jsStr = "console.log(\"输入成功了 onPageStarted\")"

            if (Build.VERSION.SDK_INT >= 19) {
                view.evaluateJavascript(jsStr, ValueCallback<String?>() {
                    Log.d("houyl", "value=" + it);
                });
            }

        }

        super.onPageStarted(view, url, favicon)
    }

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {

//        try {
//            var response =  WebResourceResponse("application/x-javascript", "UTF-8",view?.context?. getAssets()?.open("comm_files/angular.min.js"));
//            return response;
//        } catch ( e : IOException) {
//            e.printStackTrace();
//        }
//        var  jsStr = "console.log(\"输入成功了 shouldInterceptRequest\")"
//
//        if (Build.VERSION.SDK_INT >= 19) {
//            view?.evaluateJavascript(jsStr, ValueCallback<String?>() {
//                Log.d("houyl", "value=" + it);
//            });
//        }
        return super.shouldInterceptRequest(view, request)
    }


}