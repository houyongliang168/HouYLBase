package com.yongliang.lib_web.web.function;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import com.yongliang.lib_web.web.WebCommonFragment;
import com.yongliang.lib_web.web.bean.H5EncrptBean;
import com.yongliang.lib_web.web.function.encryption.H5EncryptUtils;
import com.yongliang.lib_web.web.js.H5BasePlugin;
import com.yongliang.lib_web.web.js.H5Event;


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
