package com.yongliang.houylbase

import android.graphics.Color
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yongliang.houylbase.hilt.Truck
import com.yongliang.houylbase.utils.JSUtils
import com.yongliang.houylbase.webview.MyWebChromeClient
import com.yongliang.houylbase.webview.MyWebviewClient
import common_animation.drawable_animation.DrawableAnimationUtils.getAnimationDrawable
import common_animation.drawable_animation.DrawableAnimationUtils.unzipFileFromAssets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_webview.*
import launch.launchstarter.time.LauncheTimer
import javax.inject.Inject


// 注入点
@AndroidEntryPoint
class WebviewActivity : AppCompatActivity() {
    @Inject
    lateinit var truck: Truck
    val url = "https://isales.taikang.com/static/testSdk/sdk.html"
   var mHasRecorded=false
    val  ss=SecondFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.LaunchTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        truck.deliver()
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            clearHistory()
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()

            var jsStr = JSUtils.getJSContentFromAsset(this, "aa.js")
//方式一
            var ss="alert(\"aaaa\")"
            ss = "console.log(\"输入成功了 aaa\")"

            if (Build.VERSION.SDK_INT >= 19) {
                webview.evaluateJavascript(ss, ValueCallback<String?>() {
                    Log.d("houyl", "value=" + it);
                });
//                webview.evaluateJavascript(jsStr, ValueCallback<String?>() {
//                    Log.d("houyl", "value=" + it);
//                });
            }

//           var src="file:///android_asset/aa.js"
//            var baseUrl = "file:///android_asset";
//            webview.loadDataWithBaseURL(baseUrl, src, "text/html", "utf-8", null);

//            var js = "var newscript = document.createElement(\"script\");";
//            js += "newscript.src=\"file:///android_asset/aa.js\";";
//            js += "document.body.appendChild(newscript);";
//            webview.loadUrl("javascript:" + js);
//
//            if (Build.VERSION.SDK_INT >= 19) {
//                webview.evaluateJavascript(js, ValueCallback<String?>() {
//                    Log.d("houyl", "value=" + it);
//                });
//            }
//            val  s1=ThreeFragment()
//
//            ss.addView(ll_contain,supportFragmentManager);
//            s1.addView(ll_contain,supportFragmentManager);

//            ss.addView(ll_contain);


//            /*帧动画处理 不容易实现*/
//            unzipFileFromAssets(applicationContext, "aaa.zip", "myname/animation")
//           val animation= getAnimationDrawable("myname/animation", applicationContext, 50);
//            img4.setBackgroundDrawable(animation)
//            animation?.start()
//
//            /*矢量动画*/
////            img5
//            val animatedVectorDrawable =img5.getDrawable() as AnimatedVectorDrawable
//            if (animatedVectorDrawable.isRunning) {
//                animatedVectorDrawable.stop()
//            } else {
//                animatedVectorDrawable.start()
//            }

        }
        img4.setOnClickListener {
            ss.remove(ll_contain)
        }
//        measureAATime()
        initwebview()
        webview.loadUrl(url)

    }

    private fun measureAATime() {
        if (!mHasRecorded) {
            mHasRecorded = true
            webview.getViewTreeObserver()
                .addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        webview.getViewTreeObserver().removeOnPreDrawListener(this)
                        LauncheTimer.endRecord("FeedShow")
                        return true
                    }
                })
        }
    }

    private fun initwebview() {
        // Android 7.0以上的webview不设置背景（默认背景应该是透明的），渲染有问题，有明显的卡顿
        // Android 7.0以上的webview不设置背景（默认背景应该是透明的），渲染有问题，有明显的卡顿
        webview.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        // 移除默认接口防止恶意注入（安卓3.0以上，4.2以下才有此安全漏洞）

        // 移除默认接口防止恶意注入（安卓3.0以上，4.2以下才有此安全漏洞）
        val currentapiVersion = Build.VERSION.SDK_INT
        if (currentapiVersion > 10) {
            webview.removeJavascriptInterface("searchBoxJavaBridge_")
            webview.removeJavascriptInterface("accessibility")
            webview.removeJavascriptInterface("accessibilityTraversal")
        }
        // 水平滚动条不显示
        // 水平滚动条不显示
        webview.setHorizontalScrollBarEnabled(false)
        // 辅助处理各种通知、请求事件，如果不设置WebViewClient，请求会跳转系统浏览器
        // 辅助处理各种通知、请求事件，如果不设置WebViewClient，请求会跳转系统浏览器
        webview.setWebViewClient(MyWebviewClient())
        // 辅助处理JavaScript、页喧解析渲染、页面标题、加载进度等等
        // 辅助处理JavaScript、页喧解析渲染、页面标题、加载进度等等
        webview.setWebChromeClient(MyWebChromeClient())

        val ws: WebSettings = webview.getSettings()
        //定义app的UserAgent
        //定义app的UserAgent
        ws.setUserAgentString("android")
        // 防止个人敏感数据泄漏
        // 防止个人敏感数据泄漏
        ws.savePassword = false
        ws.saveFormData = false
        // 设置是否允许WebView使用JavaScript脚本
        // 设置是否允许WebView使用JavaScript脚本
        ws.javaScriptEnabled = true
        //允许使用内置的缩放机制
        //允许使用内置的缩放机制
        ws.builtInZoomControls = true
        //默认不显示ZoomButton，否则会有windowLeaked警告
        //默认不显示ZoomButton，否则会有windowLeaked警告
        if (Build.VERSION.SDK_INT > 10) {
            ws.displayZoomControls = false
        }
        // 允许缩放
        // 允许缩放
        ws.setSupportZoom(true)
        ws.useWideViewPort = true
        ws.loadWithOverviewMode = true
        //DOM存储API可用
        //DOM存储API可用
        ws.domStorageEnabled = true
        //不使用缓存
        //不使用缓存
        ws.cacheMode = WebSettings.LOAD_DEFAULT
//        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //WebView不可以使用用户的手势进行媒体播放
        //        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //WebView不可以使用用户的手势进行媒体播放
        ws.mediaPlaybackRequiresUserGesture = false
        ws.allowContentAccess = true //允许在WebView中访问内容URL

        // 防止WebView跨源攻击
        // 设置是否允许WebView使用File协议，默认值是允许
        // 注意：不允许使用File协议，则不会存在通过file协议的跨源安全威胁，但同时也限制了WebView的功能，使其不能加载本地的HTML文件
        // 防止WebView跨源攻击
        // 设置是否允许WebView使用File协议，默认值是允许
        // 注意：不允许使用File协议，则不会存在通过file协议的跨源安全威胁，但同时也限制了WebView的功能，使其不能加载本地的HTML文件
        ws.allowFileAccess = true //允许访问文件

        // 设置是否允许通过file url加载的Javascript读取其他的本地文件
        // 设置是否允许通过file url加载的Javascript读取其他的本地文件
        ws.allowFileAccessFromFileURLs = false //不允许运行在一个URL环境

        // 设置是否允许通过file url加载的Javascript可以访问其他的源，包括其他的文件和http,https等其他的源
        // 设置是否允许通过file url加载的Javascript可以访问其他的源，包括其他的文件和http,https等其他的源
        ws.allowUniversalAccessFromFileURLs =
            false //不允许运行在一个file schema URL环境下的JavaScript访问来自其他任何来源的内容

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        //播放网络视频
        //播放网络视频
        ws.pluginState = WebSettings.PluginState.ON
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            CookieManager.getInstance().acceptThirdPartyCookies(webview)
        }
        // 启动地理定位
        // 启动地理定位
        ws.setGeolocationEnabled(true)
        ws.setJavaScriptEnabled(true)
        val dir: String = getDir("database", MODE_PRIVATE).getPath()
        ws.setGeolocationDatabasePath(dir)
        ws.databasePath=dir
        ws.javaScriptCanOpenWindowsAutomatically = true
        ws.setAppCacheEnabled(true)
        ws.setAppCacheMaxSize(20 * 1024 * 1024)
        ws.setAppCachePath(getDir("appcach", MODE_PRIVATE).getPath())
        // 此处添加自定义的JS接口,和H5进行交互
        webview.addJavascriptInterface(js, "android")

    }

    private val js = JSInterface()

    private fun clearHistory() {
        /**
         * Android WebView自带的缓存机制有5种：
         * webview 是否使用缓存由setCacheMode 来决定的
        A.浏览器 缓存机制  :
        1. 特点： 其通过控制 控制缓存的有效时间，标识文件在服务器上的最新更新时间
        2. 机制：下次请求时，如果文件缓存过期，浏览器通过 If-Modified-Since 字段带上这个时间，发送给服务器，由服务器比较时间戳来判断文件是否有修改。如果没有修改，服务器返回304告诉浏览器继续使用缓存；如果有修改，则返回200，同时返回最新的文件。
        3. 场景： 静态资源文件的存储，如JS、CSS、字体、图片等。
        4. 位置 ：内存和磁盘。from memory cache代表使用内存中的缓存，from disk cache则代表使用的是硬盘中的缓存，浏览器读取缓存的顺序为memory –> disk。
        5. 清理缓存处理 ： 由H5 和服务器控制，不处理。

       B,  Application Cache 缓存机制  app级别 ，H5可以控制写入
        https://blog.csdn.net/zhoujie_zhoujie/article/details/56682364
        介绍：HTML5 引入了应用程序缓存，简称appcache，是专门为开发离线web应用而设计的。Appcache就是从浏览器的缓存中分出来的一块缓存区。要想在这个缓存中保存数据，可以使用一个描述文件(manifest file)，可以将大部分图片资源、js、css等静态资源放在manifest文件配置中。当页面打开时通过manifest文件来读取本地文件或是请求服务器文件。
       1. 特点： 以文件为单位进行缓存，且文件有一定更新机制（类似于浏览器缓存机制），
       2. 原理说明：
        // AppCache 在首次加载生成后，也有更新机制。被缓存的文件如果要更新，需要更新 manifest 文件
        // 因为浏览器在下次加载时，除了会默认使用缓存外，还会在后台检查 manifest 文件有没有修改（byte by byte)
        发现有修改，就会重新获取 manifest 文件，对 Section：CACHE MANIFEST 下文件列表检查更新
        // manifest 文件与缓存文件的检查更新也遵守浏览器缓存机制
        // 如用户手动清了 AppCache 缓存，下次加载时，浏览器会重新生成缓存，也可算是一种缓存的更新
        // AppCache 的缓存文件，与浏览器的缓存文件分开存储的，因为 AppCache 在本地有 5MB（分 HOST）的空间限制
       3. setAppCachePath 是全局的
        4，可以设置大小
        5.场景： 静态资源文件的存储，存储静态文件（如JS、CSS、字体文件）
        6. 该缓存h5可以设置，可以通过目录删除改文件。


       C.  Dom Storage 缓存机制
        1.   DOM Storage 分为 sessionStorage & localStorage； 二者使用方法基本相同，区别在于作用范围不同：
        a. sessionStorage：具备临时性，即存储与页面相关的数据，它在页面关闭后无法使用
        b. localStorage：具备持久性，即保存的数据在页面关闭后也可以使用。
        2. 特点
        localStorage - 没有时间限制的数据存储
        localStorage是跨多个窗口，且持续范围可超过当前会话；意味着当浏览器关闭再重新打开，数据依然是可用的。数据保留到通过js删除或用户清除浏览器缓存。
        sessionStorage - 针对一个 session 的数据存储
        sessionStorage 是个全局对象，它维护着在页面会话(page session)期间有效的存储空间。只要浏览器开着，页面会话周期就会一直持续。
        当页面重新载入(reload)或者被恢复(restores)时，页面会话也是一直存在。
        存储空间大（ 5MB）
        3. 场景
        代替 **将 不需要让服务器知道的信息 存储到 cookies **的这种传统方法
        Dom Storage 机制类似于 Android 的 SharedPreference机制
        4. 设置存储路径（setDatabasePath）Android中Webkit会为DOMStorage产生两个文件（my_path/localstorage/http_blog.csdn.net_0.localstorage和my_path/Databases.db）
        5. 删除方法  删除该设置路径下的文件  兼容性考虑添加 context.deleteDatabase（"webview.db"）；context.deleteDatabase（"webviewCache.db"）；

        D. Web SQL Database 缓存机制
        1.特点：充分利用数据库的优势，可方便对数据进行增加、删除、修改、查询
        2. 场景：存储适合数据库的结构化数据
        3.    settings.setDatabasePath(cacheDirPath);
        4. 删除缓存 ： 删除该目录

       E  Indexed Database 缓存机制
        1、类似于 Dom Storage 存储机制 的key-value存储方式,Dom Storage 的升级版
        2. 开启 ：   settings.setJavaScriptEnabled(true);

        链接：https://www.jianshu.com/p/5e7075f4875f

         */
        //清空所有Cookie
        CookieSyncManager.createInstance(applicationContext) //Create a singleton CookieSyncManager within a context
        val cookieManager = CookieManager.getInstance() // the singleton CookieManager instance
        cookieManager.removeAllCookies(ValueCallback { }) // Removes all cookies.
        CookieSyncManager.getInstance().sync() // forces sync manager to sync nØow
// 清除网页查找的高亮匹配字符。
        webview.clearMatches()
        // 清除当前 WebView 访问的历史记录
        webview.clearHistory()
//清除ssl信息  出来错误的证书信息
        webview.clearSslPreferences()
//清空网页访问留下的缓存数据。需要注意的时，由于缓存是全局的，所以只要是WebView用到的缓存都会被清空，即便其他地方也会使用到。该方法接受一个参数，从命名即可看出作用。若设为false，则只清空内存里的资源缓存，而不清空磁盘里的。
        webview.clearCache(true)
//        清除自动完成填充的表单数据。需要注意的是，该方法只需清除当前表单域自动完成填充的表单数据，并不会清除WebView存储到本地的数据。
        webview.clearFormData()
        WebStorage.getInstance().deleteAllData()


        deleteDatabase("webview.db")
        deleteDatabase("webviewCache.db")




    }
}