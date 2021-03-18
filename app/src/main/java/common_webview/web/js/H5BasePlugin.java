package common_webview.web.js;


import common_webview.web.WebCommonFragment;
import common_webview.web.onActivityresult.OnH5ActivityResult;

/**
 * created by houyl
 * on  6:52 PM
 */
public class H5BasePlugin implements H5Plugin {
    public OnH5ActivityResult getResult() {
        return mResult;
    }

    public void setResult(OnH5ActivityResult result) {
        mResult = result;
    }

    public OnH5ActivityResult mResult;
    @Override
    public boolean handleEvent(H5Event h5Event, WebCommonFragment fragment) {
        return false;
    }

    @Override
    public boolean handleAsynEvent(H5Event h5Event, WebCommonFragment fragment) {
        return false;
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onReusme() {

    }

    @Override
    public void onEnd() {

    }

    @Override
    public String getCallBackName() {
        return null;
    }
}
