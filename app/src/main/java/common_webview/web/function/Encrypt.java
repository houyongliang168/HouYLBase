package common_webview.web.function;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import common_webview.web.WebCommonFragment;
import common_webview.web.bean.H5EncrptBean;
import common_webview.web.function.encryption.H5EncryptUtils;
import common_webview.web.js.H5BasePlugin;
import common_webview.web.js.H5Event;


/**
 * 加密的sdk
 */
public class Encrypt extends H5BasePlugin {
    private String TAG= Encrypt.class.getSimpleName();

    public Encrypt() {
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
            H5EncrptBean signBean=  H5EncryptUtils.encryptFunction (json);
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
