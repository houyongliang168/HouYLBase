package common_webview.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.utils.density.DensityUtil;
import com.utils.log.MyLog;
import com.yongliang.houylbase.BR;
import com.yongliang.houylbase.R;
import com.yongliang.houylbase.databinding.FragmentCommonWebBinding;
import com.yongliang.houylbase.databinding.ViewstubLayoutWebHeadBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import common_webview.web.base.CommonWebChromeClient;
import common_webview.web.base.CommonWebViewClient;
import common_webview.web.base.H5WebPage;
import common_webview.web.interfaces.IWebPageView;
import common_webview.web.js.IJsInterface;
import common_webview.web.js.TXXJSInterface;
import common_webview.web.onActivityresult.H5ActivityResultManager;
import common_webview.web.urlchecker.BaseUrlChecker;
import common_webview.web.urlchecker.UrlCheckSdkManager;
import common_webview.web.urlchecker.function.TelCheckFunction;
import common_webview.web.util.H5TitleUtil;
import common_webview.web.util.StatusBarUtil;
import mvvm.CoreBaseMVVMFragment;

import static android.os.Looper.getMainLooper;
import static common_webview.web.ConstantUtils.BACK_2_H5_SINGLE;

/**
 * 所有view 逻辑界面都在 Fragment 中处理
 * // * A simple {@link } subclass.
 * Use the {@link WebCommonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebCommonFragment extends CoreBaseMVVMFragment<FragmentCommonWebBinding, WebpageViewModel> implements IWebPageView {

    private static final String LOG_TAG = "WebCommonFragment";
    public H5WebPage mWP;
    public Map<String, IJsInterface> mJsInterfaceMap = new HashMap<>();
    private static Handler mHandler = new Handler(getMainLooper());
    private Thread mainThread = getMainLooper().getThread();

    public String rootUrl = null;//原始Url
    public String Url = null;//显示的URL


    private UrlCheckSdkManager mUrlCheckSdkManager;


    public static WebCommonFragment newInstance(Bundle args) {
        WebCommonFragment fragment = new WebCommonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /* 从 bundle 中获取参数*/
        Bundle bundle = getArguments();
        if (bundle != null && bundle.size() > 0) {
            getArguments(bundle);
        }
    }

    /**
     * 主参数在这里获取
     *
     * @param bundle
     */
    @Override
    public void onArgumentsHandle(Bundle bundle) {
        Url = bundle.getString(ConstantUtils.URL, "");
        rootUrl = Url;
    }

    /**
     * 辅助参数 从这里获取并存入viewmodule
     *
     * @param bundle
     */
    public void getArguments(Bundle bundle) {
        boolean back2Html5 = bundle.getBoolean(ConstantUtils.BACK_2_H5, false);
        boolean back2Html5Single = bundle.getBoolean(BACK_2_H5_SINGLE, false);
        if (back2Html5) {
            viewModel.backHtmlControl.setValue(1);
        } else if (back2Html5Single) {
            viewModel.backHtmlControl.setValue(2);
        }
//        默认不显示 状态栏
        if (!TextUtils.isEmpty(Url)) {
            if (Url.contains("hasTitle=f")) {
                viewModel.statusBarControl.setValue(0);
            } else {
                viewModel.statusBarControl.setValue(1);
            }
        }
    }


    public boolean backControl() {
        if (viewModel.backHtmlControl.getValue() != null) {
            switch (viewModel.backHtmlControl.getValue()) {
                case 1:
                    mWP.loadUrl("javascript:goBack()");
                    return true;
                case 2:
                    mWP.loadUrl("javascript:Hybrid.goBack()");
                    return true;
                default:
                    break;
            }

        }
        if (mWP.getWebView().canGoBack()) {
            mWP.getWebView().goBack();
        } else {
            if (viewModel.backClickControl.getValue() != null && viewModel.backClickControl.getValue()) {
                getActivity().finish();
            }
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            if (backControl()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBinding(FragmentCommonWebBinding b) {
//        oncreate 中初始化web
        initWeb();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWP != null) {
            mWP.getWebView().onResume();
//            mWP.getWebView ().resumeTimers();
        }


//        js 获取resume 状态
        Iterator it = mJsInterfaceMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            IJsInterface jsInterface = (IJsInterface) entry.getValue();
            jsInterface.onResume();
        }


    }

    /**
     * Does a best-effort attempt to pause any processing that can be paused safely, such as animations and geolocation. Note that this call does not pause JavaScript. To pause JavaScript globally, use  pauseTimers(). To resume WebView, call onResume().
     * <p>
     * 【翻译：】通知内核尝试停止所有处理，如动画和地理位置，但是不能停止Js，如果想全局停止Js，可以调用pauseTimers()全局停止Js，调用onResume()恢复。
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mWP != null) {
            mWP.getWebView().onPause();
//            mWP.getWebView ().pauseTimers ();
        }
    }

    // 头部数据是否显示
    private ViewstubLayoutWebHeadBinding viewStubBinding;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_common_web;
    }


    //    初始化web
    public void initWeb() {
//        Webview可见后 就不空了。
        getUrlCheckSdkManager().addUrlChecker(new TelCheckFunction());
        // 添加webview视图
        mWP = new H5WebPage(this.getContext(), "");
        binding.flWeb.addView(mWP);

//       添加交互方法 ，添加到map 方便及时消除
        TXXJSInterface txxjsInterface = new TXXJSInterface(this);
        mWP.addJavascriptInterface(txxjsInterface);
        mJsInterfaceMap.put(txxjsInterface.getName(), txxjsInterface);
//     添加基础方法
        mWP.setWebChromeClient(new CommonWebChromeClient(this));
        mWP.setWebViewClient(new CommonWebViewClient(this));
//      将状态栏高度传递给H5
        WebSettings setting = mWP.getWebView().getSettings();
        String ua = setting.getUserAgentString();
        if (Url.contains("hasTitle=f")) {
            String str = ua + "/statusBarHeight(" + DensityUtil.px2dip(getActivity(), StatusBarUtil.getStatusBarHeight(getActivity())) + ")/safeAreaHeight(0)";
            setting.setUserAgentString(str);
        } else {
            setting.setUserAgentString(ua + "/");
        }
        if (Build.VERSION.SDK_INT >= 19) {
            setting.setLoadsImagesAutomatically(true);
        } else {
            setting.setLoadsImagesAutomatically(false);
        }
        mWP.loadUrl(Url);

    }

    //    Fragment 对外提供一些方法
//    获取H5WebPage
    public H5WebPage getH5WebPage() {
        return this.mWP;
    }


    public UrlCheckSdkManager getUrlCheckSdkManager() {
        if (mUrlCheckSdkManager == null) {
            mUrlCheckSdkManager = new UrlCheckSdkManager();
        }
        return mUrlCheckSdkManager;
    }

    public void addUrlChecker(BaseUrlChecker iUrlChecker) {
        if (mUrlCheckSdkManager != null) {
            mUrlCheckSdkManager.addUrlChecker(iUrlChecker);
        }
    }

    public void removeUrlChecker(BaseUrlChecker iUrlChecker) {
        if (mUrlCheckSdkManager != null) {
            mUrlCheckSdkManager.removeUrlChecker(iUrlChecker);
        }
    }

    //    获取WebView
    public WebView getWebview() {
        if (mWP == null) {
            return null;
        }
        return this.mWP.getWebView();
    }


    //JSInterface 动态修改-- 添加
    public void addJavascriptInterface(IJsInterface jsInterface) {
        if (this.mWP != null) {
            this.mWP.removeJavascriptInterface(jsInterface);
        }
    }

    // 获取统一回调的方法
    public void getH5CallBackCommon(final String callBackName, final String json) {
//        主线程
        if (Thread.currentThread() == mainThread) {
            if (this.mWP != null) {
                this.mWP.getH5CallBackCommon(callBackName, json);
            }
        } else {
//            切换到主线程
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mWP != null) {
                        mWP.getH5CallBackCommon(callBackName, json);
                    }
                }
            });
        }

    }


    @Override
    public void onDestroy() {
        if (binding.flWeb != null) {
            binding.flWeb.removeAllViews();
        }
        cleanJSIterfaceData();
        if (mWP != null) {
            mWP.onRelease();
        }
        mHandler.removeCallbacksAndMessages(null);
        if (mUrlCheckSdkManager != null) {
            mUrlCheckSdkManager = null;
        }
        super.onDestroy();
    }

    //释放js 相关资源
    public void cleanJSIterfaceData() {
        Iterator it = mJsInterfaceMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            IJsInterface jsInterface = (IJsInterface) entry.getValue();
            if (mWP != null) {
                mWP.removeJavascriptInterface(jsInterface);
            }
            jsInterface.onRelease();
        }
        mJsInterfaceMap.clear();
    }

    @Override
    public void showWebView() {

    }

    @Override
    public void hindWebView() {

    }

    @Override
    public void startProgress(int newProgress) {
        if (binding == null) {
            return;
        }
        MyLog.wtf(LOG_TAG, "H5加载进度newProgress变化：" + newProgress);

        if (viewModel != null && viewModel.progressSwitchControl.getValue() != null && viewModel.progressSwitchControl.getValue()) {
            if (newProgress < 11 || newProgress > 80) {

            } else if (newProgress == 100) {
                viewModel.progressControl.setValue(-2);
            } else {
                if (binding.progressBar.getVisibility() == View.GONE) {
                    viewModel.progressControl.setValue(-1);
                }
                viewModel.progressControl.setValue(newProgress);
                binding.progressBar.setProgress(newProgress);//设置进度值

            }
        }


    }

    @Override
    public void fullViewAddView(View var1) {

    }

    @Override
    public void showVideoFullView() {

    }

    @Override
    public void hindVideoFullView() {

    }

    @Override
    public void setRequestedOrientation(int var1) {

    }

    @Override
    public FrameLayout getVideoFullView() {
        return null;
    }

    @Override
    public View getVideoLoadingProgressView() {
        return null;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//        js 弹框
        showErrorDialog(message);
        result.cancel();
        return true;
    }


    @Override
    public void onReceivedTitle(WebView webView, String title) {

        if (viewModel == null || viewModel.titleControl.getValue() == null || 1 == viewModel.titleControl.getValue()) {
            return;
        }
        if (H5TitleUtil.titleIsUrl(webView.getUrl(), title)) {
            viewModel.titleWordsControl.setValue("");
        } else {
            viewModel.titleWordsControl.setValue(title);
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (getActivity().isFinishing()) {
            return;
        }

        try {
            startProgress(newProgress);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        if (getActivity().isFinishing()) {
            return;
        }
        try {
//            图片资源最后加载
            if (!webView.getSettings().getLoadsImagesAutomatically()) {
                webView.getSettings().setLoadsImagesAutomatically(true);
            }
            dismissProgressDialog();
            MyLog.wtf("H5_onPageFinished", "执行了");
//            if (mUserContext.iv_close != null && webView.canGoBack ()) {
//                mUserContext.iv_close.setVisibility (View.VISIBLE);
//            }
            // h5网页成功加载以后先调用h5ready函数
            if (mWP != null) {
                mWP.getH5CallBackCommonBase("javascript:ISALES.ready()");
            }
            if (viewModel != null && viewModel.progressControl.getValue() != null) {
                if (-1 == viewModel.progressControl.getValue()) {
                    viewModel.progressControl.setValue(-2);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onPageStart(WebView webView, String url, Bitmap favicon) {
        if (getActivity().isFinishing()) {
            return;
        }
//        弹出进度框
        showProgressDialog();
        if (this.mUrlCheckSdkManager != null) {
            Iterator iterator = this.mUrlCheckSdkManager.getUrlCheckers().iterator();

            while (iterator.hasNext()) {
                BaseUrlChecker checker;
                if ((checker = (BaseUrlChecker) iterator.next()) != null && checker.shouldInterruptPageStart(webView, url, favicon)) {
                    if (getWebview() != null) {
                        getWebview().stopLoading();
                    }
                    return;
                }
            }
        }

    }

    @Override
    public boolean onShouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }


    public WebCommonFragment() {
    }


    //显示数据空的效果
    private void showTitleView() {
        if (viewStubBinding == null) {

            binding.webHead.getViewStub().setOnInflateListener(new ViewStub.OnInflateListener() {
                @Override
                public void onInflate(ViewStub stub, View inflated) {
                    //如果在 xml 中没有使用 bind:userInfo="@{userInf}" 对 viewStub 进行数据绑定
                    //那么可以在此处进行手动绑定
//                    ViewStubBinding viewStubBinding = DataBindingUtil.bind(inflated);
//                    viewStubBinding.setUserInfo(user);
                    MyLog.wtf("hyl", "onInflate");
                    ViewstubLayoutWebHeadBinding viewStubBinding = DataBindingUtil.bind(inflated);
                    viewStubBinding.setViewModel(viewModel);
                }
            });
            View titleView = binding.webHead.getViewStub().inflate();

        }
        viewStubBinding.llWebview.setVisibility(View.VISIBLE);
    }

    //隐藏数据空的效果
    private void hintTitleView() {
        if (viewStubBinding == null) {
            return;
        }
        viewStubBinding.llWebview.setVisibility(View.GONE);
    }


    @Override
    public void initViewObservable() {
        viewModel.titleControl.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                switch (integer) {
                    case 0:
                        showTitleView();
                        break;
                    case 1:
                        hintTitleView();
                        break;
                }
            }
        });
//        进度条控制器
        viewModel.progressControl.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (binding == null || binding.progressBar == null) {
                    return;
                }
                switch (integer) {
                    case -2:
                        binding.progressBar.setVisibility(View.GONE);
                        break;
                    case -1:
                        binding.progressBar.setVisibility(View.VISIBLE);
                        break;
                    default:
                        binding.progressBar.setProgress(integer);
                        break;
                }
            }
        });
//        状态栏处理
        viewModel.statusBarControl.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (binding == null || binding.vStatusbar == null) {
                    return;
                }
                switch (integer) {
                    case 0:
                        binding.vStatusbar.setVisibility(View.GONE);
                        break;
                    case 1:
                        binding.vStatusbar.setVisibility(View.VISIBLE);
                        binding.vStatusbar.setHeight(StatusBarUtil.getStatusBarHeight(getActivity()));
                        break;
                    default:
                        break;
                }
            }
        });
        //        头部文字
        viewModel.titleWordsControl.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String str) {
                if (viewStubBinding == null) {
                    return;
                }
                if (viewStubBinding.llWebview.getVisibility() == View.VISIBLE) {
                    viewStubBinding.tvTitle.setText(str);
                }

            }
        });
//        返回键处理
        viewModel.backClickControl.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

                if (mWP == null || mWP.getWebView() == null) {
                    return;
                }
                backControl();
            }
        });
        //        进度条开关控制处理
//        viewModel.progressSwitchControl.observe (this, new Observer<Boolean> () {
//            @Override
//            public void onChanged(@Nullable Boolean aBoolean) {
//
//            }
//        });
//     更多点击暂不处理
        viewModel.moreClickControl.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {

                if (mWP == null || mWP.getWebView() == null) {
                    return;
                }
//
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mWP.getWebView().evaluateJavascript("javascript:ISALES.showMenu()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String json) {
                            MyLog.wtf("mhtshowMenu", json);
                            if (TextUtils.isEmpty(json) || "null".equals(json)) {
//                             showNativeMenu();
                            } else {
                                try {
                                    JSONObject jsonObject = new JSONObject(json);
                                    String isShowH5Dialog = jsonObject.optString("h5");
                                    if (!"true".equals(isShowH5Dialog)) {
//                                     showNativeMenu();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public int initVariableId() {
        return BR.webViewModel;
    }


    //JSInterface 动态修改--移除
    public void removeJavascriptInterface(IJsInterface jsInterface) {
        if (this.mWP != null) {
            this.mWP.removeJavascriptInterface(jsInterface);
        }
    }


    //   结果回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        H5ActivityResultManager.getInstance().sendResult(requestCode, resultCode, data);
    }


    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return null;
    }


}