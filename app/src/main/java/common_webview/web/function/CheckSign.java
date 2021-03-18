package common_webview.web.function;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import common_webview.web.WebCommonFragment;
import common_webview.web.bean.H5SignBean;
import common_webview.web.function.encryption.H5SignUtils;
import common_webview.web.js.H5BasePlugin;
import common_webview.web.js.H5Event;


/**
 * 加密的sdk
 */
public class CheckSign extends H5BasePlugin {
    private String TAG= CheckSign.class.getSimpleName();

    public CheckSign() {
    }

    @Override
    public boolean handleEvent(H5Event h5Event, WebCommonFragment fragment) {
        if (h5Event == null) {
            return false;
        }
        try {

            String json = h5Event.getJsonSource ();
            JSONObject jsonObject = new JSONObject(json);
            String callBackName = jsonObject.getString ("callBackName");
            if (TextUtils.isEmpty (callBackName)) {
                return false;
            }
            H5SignBean signBean=  H5SignUtils.checkSignFunction (json);
            //        处理后调用
            if (fragment != null) {
                fragment.getH5CallBackCommon (callBackName, new Gson ().toJson (signBean));
            }
        } catch (JSONException e) {
            e.printStackTrace ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return true;
    }

    @Override
    public void onRelease() {

    }

    @Override
    public String getCallBackName() {
        return null;
    }



}
