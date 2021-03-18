package common_webview.web.js;

/**
 * created by houyl
 * on  1:52 PM
 */
public interface IJsInterface {
    //    获取js回调名称，比如android
    String getName();
//获取 fragment  Resume  状态
    void onResume();

    //    释放内容
    void onRelease();
}
