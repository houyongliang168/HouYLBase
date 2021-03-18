package common_webview.web.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;


import com.utils.log.MyLog;

import common_webview.web.js.IJsInterface;

import static android.webkit.WebView.setWebContentsDebuggingEnabled;

/**
 * @author ren shi qian
 */
public class H5WebPage extends RelativeLayout {

    private String LOG_TAG = "H5WebPage";
    // 使用者提供的Context
    private Context mUserContext;
    private SimpleWebView mWebView;
    // 辅助处理各种通知、请求事件，如果不设置WebViewClient，请求会跳转系统浏览器
    private WebViewClient h5webViewClient;
    //    辅助处理JavaScript、页喧解析渲染、页面标题、加载进度等等
    private WebChromeClient h5webChromeClient;

    private H5WebListener listener;
    public String mTag = getClass ().getSimpleName ();

    public H5WebPage(Context context, String tag) {
        super (context.getApplicationContext ());
        mUserContext = context;
        mTag = tag;
        init ();
    }

    public H5WebPage(Context context, AttributeSet attrs) {
        super (context.getApplicationContext (), attrs);
        mUserContext = context;
        init ();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void init() {
        if (Build.VERSION.SDK_INT >= 19) {
            if (Env.DEBUG) {
                setWebContentsDebuggingEnabled (true);
            }
        }
        LayoutParams rootView = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // 将WebView加到布局中
        mWebView = new SimpleWebView (mUserContext);
        // Android 7.0以上的webview不设置背景（默认背景应该是透明的），渲染有问题，有明显的卡顿
        mWebView.setBackgroundDrawable (new ColorDrawable(Color.WHITE));
        addView (mWebView, rootView);

        // 移除默认接口防止恶意注入（安卓3.0以上，4.2以下才有此安全漏洞）
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion > 10) {
            mWebView.removeJavascriptInterface ("searchBoxJavaBridge_");
            mWebView.removeJavascriptInterface ("accessibility");
            mWebView.removeJavascriptInterface ("accessibilityTraversal");
        }
        // 水平滚动条不显示
        mWebView.setHorizontalScrollBarEnabled (false);

        WebSettings ws = mWebView.getSettings ();
        //定义泰行销app的UserAgent
        ws.setUserAgentString (mWebView.getUserAgentEx ());
        // 防止个人敏感数据泄漏
        ws.setSavePassword (false);
        ws.setSaveFormData (false);
        // 设置是否允许WebView使用JavaScript脚本
        ws.setJavaScriptEnabled (true);
        // 不允许使用内置的缩放机制
        ws.setBuiltInZoomControls (false);
        //默认不显示ZoomButton，否则会有windowLeaked警告
        if (Build.VERSION.SDK_INT > 10) {
            ws.setDisplayZoomControls (false);
        }
        // 不允许缩放
        ws.setSupportZoom (false);
        ws.setUseWideViewPort (true);
        ws.setLoadWithOverviewMode (true);
        //DOM存储API可用
        ws.setDomStorageEnabled (true);
        //不使用缓存
        ws.setCacheMode (WebSettings.LOAD_NO_CACHE);
        //WebView不可以使用用户的手势进行媒体播放
        ws.setMediaPlaybackRequiresUserGesture (false);
        ws.setAllowContentAccess (true);//允许在WebView中访问内容URL
        // 防止WebView跨源攻击
        // 设置是否允许WebView使用File协议，默认值是允许
        // 注意：不允许使用File协议，则不会存在通过file协议的跨源安全威胁，但同时也限制了WebView的功能，使其不能加载本地的HTML文件
        ws.setAllowFileAccess (true);//允许访问文件
        // 设置是否允许通过file url加载的Javascript读取其他的本地文件
        ws.setAllowFileAccessFromFileURLs (false);//不允许运行在一个URL环境
        // 设置是否允许通过file url加载的Javascript可以访问其他的源，包括其他的文件和http,https等其他的源
        ws.setAllowUniversalAccessFromFileURLs (false);//不允许运行在一个file schema URL环境下的JavaScript访问来自其他任何来源的内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode (WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //播放网络视频
        ws.setPluginState (WebSettings.PluginState.ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode (WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance ().acceptThirdPartyCookies (mWebView);
        }
        // 启动地理定位
        ws.setGeolocationEnabled (true);
        ws.setJavaScriptCanOpenWindowsAutomatically (true);
        ws.setAppCacheEnabled (false);


        mWebView.setOnLongClickListener (new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView) v).getHitTestResult ();
                String urlpictures = result.getExtra ();//获取额外的信息   url 的信息
                Log.e (LOG_TAG, "long:" + urlpictures);

                if (!TextUtils.isEmpty (urlpictures)) {
                    if (listener != null) {
                        return listener.onLongClick (urlpictures);
                    }

                    return false;
                }
                return true;
            }
        });
    }


    public WebView getWebView() {
        return mWebView;
    }


    // 对外传递信息
    public void setListener(H5WebListener listener) {
        this.listener = listener;
    }

    public void loadUrl(String url) {
        if (mWebView != null) {
            mWebView.loadUrl (url);
        }
    }


    /**
     * 添加交互方法
     *
     * @param jsInterface
     */
    @SuppressLint("JavascriptInterface")
    public void addJavascriptInterface(IJsInterface jsInterface) {
        if (this.mWebView != null) {
            this.mWebView.addJavascriptInterface (jsInterface, jsInterface.getName ());
        }
    }

    public void removeJavascriptInterface(IJsInterface jsInterface) {
        if (this.mWebView != null) {
            this.mWebView.removeJavascriptInterface (jsInterface.getName ());
        }

    }

    public void setWebViewClient(WebViewClient webViewClient) {
        if (webViewClient == null || mWebView == null) {
            return;
        }
        // 辅助处理各种通知、请求事件，如果不设置WebViewClient，请求会跳转系统浏览器
        mWebView.setWebViewClient (webViewClient);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            h5webViewClient = webViewClient;
        }
    }

    // 辅助处理各种通知、请求事件，如果不设置WebViewClient，请求会跳转系统浏览器
    public WebViewClient getWebViewClient() {
        if (mWebView == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return mWebView.getWebViewClient ();
        } else {
            return h5webViewClient;
        }
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        if (webChromeClient == null || mWebView == null) {
            return;
        }
        // 辅助处理各种通知、请求事件，如果不设置WebViewClient，请求会跳转系统浏览器
        mWebView.setWebChromeClient (webChromeClient);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            h5webChromeClient = webChromeClient;
        }
    }

    //   辅助处理JavaScript、页喧解析渲染、页面标题、加载进度等等
    public WebChromeClient getWebChromeClient() {
        if (mWebView == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return mWebView.getWebChromeClient ();
        } else {
            return h5webChromeClient;
        }
    }

    /**
     * js获取用户信息的回调
     */
    public void getH5CallBackCommon(String callBackName, String json) {
        String script = "javascript:" + callBackName + "('" + json + "')";
        getH5CallBackCommonBase(script);
    }

    /**
     * js获取用户信息的回调
     */
    public void getH5CallBackCommonBase(String script) {
        if (mWebView == null) {
            return;
        }
        MyLog.wtf (LOG_TAG, "H5获取用户信息回调,script=" + script);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //做一些处理
            MyLog.wtf (LOG_TAG, "H5获取用户信息回调,高版本");
            mWebView.evaluateJavascript (script, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    MyLog.wtf (LOG_TAG, "H5获取用户信息回调_onReceiveValue,value=" + value);

                }
            });
        } else {
            //在版本低于此的时候，做一些处理
            MyLog.wtf (LOG_TAG, "H5获取用户信息回调,低版本");
            mWebView.loadUrl (script);
        }
    }

    public void onRelease() {
        this.removeAllViews ();
        androidClearCache();
        if (mWebView != null) {
            mWebView.removeAllViews ();
            mWebView.loadDataWithBaseURL ((String) null, "", "text/html", "utf-8", (String) null);
            mWebView.clearHistory();
            mWebView.stopLoading ();
            mWebView.setWebChromeClient ((WebChromeClient) null);
            mWebView.setWebViewClient ((WebViewClient) null);
            mWebView.destroy ();
            mWebView=null;
        }
        if(h5webViewClient!=null){
            h5webViewClient=null;
        }
        if(h5webChromeClient!=null){
            h5webChromeClient=null;
        }

        if(listener!=null){
            listener=null;
        }
        if(mUserContext!=null){
            mUserContext=null;
        }
    }


    /**
     * androidClearCache
     */
    private void androidClearCache(){
        if (mWebView != null) {
            getH5CallBackCommonBase("javascript:ISALES.appClearCache()");
        }
    }
}
