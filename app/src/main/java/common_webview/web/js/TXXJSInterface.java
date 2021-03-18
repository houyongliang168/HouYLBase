package common_webview.web.js;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;


import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import common_webview.web.WebCommonFragment;
import common_webview.web.function.CheckSign;
import common_webview.web.function.Decrypt;
import common_webview.web.function.Encrypt;
import common_webview.web.function.GetSign;
import common_webview.web.function.StatusStyle;

/**
 * J和H5交互的具体类
 * Created by REN SHI QIAN on 2017/12/11.
 * <p>
 * JS和H5交互有以下几种：
 * 1. android  计算完，直接传结果  考虑加密
 * 2。 js 直接调android 方法，不传结果  考虑返回键处理
 * 3. js 调android 方法，android 调用其他页面 ，然后返回结果 考虑扫一扫  考虑所有方法放在一个bean 中，方便存取，每个方法 用一个bean 来获取，通过 wp 来获取
 */
public class TXXJSInterface implements IJsInterface {

    private final String LOG_TAG = this.getClass ().getSimpleName ();
    //
    private WebCommonFragment mWebCommonFragment;
    private Handler mHandler;
    private ConcurrentHashMap<String, H5BasePlugin> mPluginMap = new ConcurrentHashMap<>();

//  数据处理


    public TXXJSInterface(WebCommonFragment mWebCommonFragment) {
        this.mWebCommonFragment = mWebCommonFragment;
        initHandler ();
    }


    private void initHandler() {
        mHandler = new Handler(Looper.getMainLooper ());
    }

    @Override
    public void onRelease() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages (null);
        }
        cleanAllData ();
        mWebCommonFragment = null;
    }

    //释放js 相关资源
    private void cleanAllData() {
        Iterator it = mPluginMap.entrySet ().iterator ();
        while (it.hasNext ()) {
            Map.Entry entry = (Map.Entry) it.next ();
            H5BasePlugin h5BasePlugin = (H5BasePlugin) entry.getValue ();
            h5BasePlugin.onRelease ();
        }
        mPluginMap.clear ();
    }




    /**
     * 获取加签信息的方法
     *
     * @param info
     */
    @JavascriptInterface
    public void getSign(String info) {

        if (TextUtils.isEmpty (info)) {
            return;
        }
//        默认是异步
        String mothedName = "getSign";
        GetSign getSign;
        H5Event event = new H5Event (mothedName, info);
        if (mPluginMap.get (mothedName) != null) {
            H5BasePlugin basePlugin = mPluginMap.get (mothedName);
            if (basePlugin instanceof GetSign) {
                getSign = (GetSign) basePlugin;
            } else {
                getSign = new GetSign ();
                mPluginMap.put (mothedName, getSign);
            }
        } else {
            getSign = new GetSign ();
            mPluginMap.put (mothedName, getSign);
        }
        getSign.handleEvent (event, mWebCommonFragment);


    }

    /**
     * 获取解签信息的方法
     *
     * @param info
     */
    @JavascriptInterface
    public void checkSign(String info) {
        if (TextUtils.isEmpty (info)) {
            return;
        }
//        默认是异步
        String mothedName = "checkSign";
        CheckSign checkSign;
        H5Event event = new H5Event (mothedName, info);
        if (mPluginMap.get (mothedName) != null) {
            H5BasePlugin basePlugin = mPluginMap.get (mothedName);
            if (basePlugin instanceof CheckSign) {
                checkSign = (CheckSign) basePlugin;
            } else {
                checkSign = new CheckSign ();
                mPluginMap.put (mothedName, checkSign);
            }
        } else {
            checkSign = new CheckSign ();
            mPluginMap.put (mothedName, checkSign);
        }
        checkSign.handleEvent (event, mWebCommonFragment);
    }


    /**
     * 获取加密信息的方法
     *
     * @param info
     */
    @JavascriptInterface
    public void encrypt(String info) {
        if (TextUtils.isEmpty (info)) {
            return;
        }
//        默认是异步
        String mothedName = "encrypt";
        Encrypt ownPlugin;
        H5Event event = new H5Event (mothedName, info);
        if (mPluginMap.get (mothedName) != null) {
            H5BasePlugin basePlugin = mPluginMap.get (mothedName);
            if (basePlugin instanceof Encrypt) {
                ownPlugin = (Encrypt) basePlugin;
            } else {
                ownPlugin = new Encrypt ();
                mPluginMap.put (mothedName, ownPlugin);
            }
        } else {
            ownPlugin = new Encrypt ();
            mPluginMap.put (mothedName, ownPlugin);
        }
        ownPlugin.handleEvent (event, mWebCommonFragment);


    }

    /**
     * 获取解密信息的方法
     *
     * @param info
     */
    @JavascriptInterface
    public void decrypt(String info) {
        if (TextUtils.isEmpty (info)) {
            return;
        }
//        默认是异步
        String mothedName = "decrypt";
        Decrypt ownPlugin;
        H5Event event = new H5Event (mothedName, info);
        if (mPluginMap.get (mothedName) != null) {
            H5BasePlugin basePlugin = mPluginMap.get (mothedName);
            if (basePlugin instanceof Decrypt) {
                ownPlugin = (Decrypt) basePlugin;
            } else {
                ownPlugin = new Decrypt ();
                mPluginMap.put (mothedName, ownPlugin);
            }
        } else {
            ownPlugin = new Decrypt ();
            mPluginMap.put (mothedName, ownPlugin);
        }
        ownPlugin.handleEvent (event, mWebCommonFragment);

    }


    /**
     * 由于状态栏的字体颜色需要设置，为此拓展了一个原生的交互方法
     * 方法名：setStatusStyle
     * 入参：style 值：0 黑色，1 白色
     */
    @JavascriptInterface
    public void setStatusStyle(String info) {
        if (TextUtils.isEmpty (info)) {
            return;
        }
//        默认是异步
        String mothedName = "setStatusStyle";
        StatusStyle ownPlugin;
        H5Event event = new H5Event (mothedName, info);
        if (mPluginMap.get (mothedName) != null) {
            H5BasePlugin basePlugin = mPluginMap.get (mothedName);
            if (basePlugin instanceof StatusStyle) {
                ownPlugin = (StatusStyle) basePlugin;
            } else {
                ownPlugin = new StatusStyle ();
                mPluginMap.put (mothedName, ownPlugin);
            }
        } else {
            ownPlugin = new StatusStyle ();
            mPluginMap.put (mothedName, ownPlugin);
        }
        ownPlugin.handleEvent (event, mWebCommonFragment);


    }




    //    案例 startactivityforresult
    @JavascriptInterface
    public void H5ScanByNative(final String info) {
        if (TextUtils.isEmpty (info)) {
            return;
        }
//        默认是异步
//        String mothedName= ThreadJudgeUtils.getMethodName ();
//        H5Event event=new H5Event (mothedName,info);
//        H5ScanByNative h5ScanByNative=new H5ScanByNative ();
//        h5ScanByNative.handleAsynEvent (event,mWebCommonFragment);
//       在主线程 发送信息
//        mHandler.post (new Runnable () {
//            @Override
//            public void run() {
//                String mothedName= ThreadJudgeUtils.getMethodName ();
//                H5Event event=new H5Event (mothedName,info);
//                H5GetUsersInfoByNative h5GetUsersInfoByNative=new H5GetUsersInfoByNative();
//                h5GetUsersInfoByNative.handleAsynEvent (event,mWebCommonFragment);
//            }
//        });

    }


    @Override
    public String getName() {
        return "android";
    }

    @Override
    public void onResume() {
        Iterator it = mPluginMap.entrySet ().iterator ();
        while (it.hasNext ()) {
            Map.Entry entry = (Map.Entry) it.next ();
            H5BasePlugin h5BasePlugin = (H5BasePlugin) entry.getValue ();
            h5BasePlugin.onReusme ();
        }
    }







}