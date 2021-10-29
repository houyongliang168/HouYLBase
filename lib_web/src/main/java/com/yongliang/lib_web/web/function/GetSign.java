package com.yongliang.lib_web.web.function;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.yongliang.lib_web.web.WebCommonFragment;
import com.yongliang.lib_web.web.bean.H5SignBean;
import com.yongliang.lib_web.web.function.encryption.H5SignUtils;
import com.yongliang.lib_web.web.js.H5BasePlugin;
import com.yongliang.lib_web.web.js.H5Event;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * 加密的sdk
 */
public class GetSign extends H5BasePlugin {
    private String TAG= GetSign.class.getSimpleName();

    public GetSign() {
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
            H5SignBean signBean=  H5SignUtils.getSignFunction (json);
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
