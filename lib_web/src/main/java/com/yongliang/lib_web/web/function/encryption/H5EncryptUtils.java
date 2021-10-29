package com.yongliang.lib_web.web.function.encryption;

import android.text.TextUtils;

import org.json.JSONObject;

import com.yongliang.lib_web.web.bean.H5EncrptBean;


/**
 * 加密的sdk
 */
public class H5EncryptUtils  {

    private String TAG= H5EncryptUtils.class.getSimpleName();

    /**
     * 加密数据处理
     * @param json
     */
    public static H5EncrptBean encryptFunction(String json) throws Exception {
        H5EncrptBean encrytBean=new H5EncrptBean();
        encrytBean.setTag("0");
        JSONObject jsonObject = new JSONObject(json);
        String params="";
        try {
            params= jsonObject.optString("data");
        }catch (Exception e){
            e.printStackTrace();

        }
         if(TextUtils.isEmpty(params)){
            encrytBean.setCode("-100");
            encrytBean.setDesc("解析数据失败,数据为空");
            return encrytBean;
        }
        String param= RSAUtils.encodePublicKey(params,EncryptConstont.RSA_PUNLICK_KEY);
        encrytBean.setCode("0");
        encrytBean.setMsg(param);
        return  encrytBean;
    }

    /**
     * 解密数据处理 只是针对 考试处理
     * @param json
     */
    public static H5EncrptBean decryptFunction(String json) throws Exception {
        H5EncrptBean decrytBean=new H5EncrptBean();
        decrytBean.setTag("1");
        JSONObject jsonObject = new JSONObject(json);
        String params="";
        try {
            params= jsonObject.optString("data");
        }catch (Exception e){
            e.printStackTrace();

        }
        if(TextUtils.isEmpty(params)){
            decrytBean.setCode("-100");
            decrytBean.setDesc("解密数据失败,数据为空");
            return decrytBean;
        }
       String param= RSAUtils.decodePrivateKey(params,EncryptConstont.RSA_PRIVATE_KEY);
        if(TextUtils.isEmpty(param)){
            decrytBean.setCode("-100");
            decrytBean.setDesc("解密数据失败,数据为空");
            return decrytBean;
        }
        decrytBean.setCode("0");
        decrytBean.setMsg(param);
        return  decrytBean;
    }





}
