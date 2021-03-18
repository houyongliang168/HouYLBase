package common_webview.web.function.encryption;

import android.text.TextUtils;


import com.utils.log.MyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import common_webview.web.bean.H5SignBean;


/**
 * 加密的sdk
 */
public class H5SignUtils {
    private String TAG= H5SignUtils.class.getSimpleName();

    /**
     * 加签数据处理
     * @param json
     */
    public static H5SignBean getSignFunction(String json) throws Exception {
            MyLog.wtf("getsign",json+"");
            H5SignBean signBean=new H5SignBean();
            signBean.setTag("0");
            JSONObject jsonObject = new JSONObject(json);

            Object value;
            try {
                value = jsonObject.get("params");
            }catch (Exception e){
                e.printStackTrace();
                value=null;
            }
             Map<String, String> resultMap =new HashMap<String, String>();
            if(value==null){

                String sign=SignUtil.encodeToken(resultMap);
                signBean.setCode("0");
                signBean.setMsg(new H5SignBean.MsgBean(sign));
                return  signBean;
            }

            Stack<JSONObject> stObj = new Stack<JSONObject>();
            if (value instanceof JSONObject) {//如果对象是 JSONObject
                JSONObject params= jsonObject.getJSONObject("params");
                stObj.push(params);
                JsonToStringMap(stObj,resultMap);
            }else if(value instanceof JSONArray){//如果返回数组
                signBean.setCode("-100");
                signBean.setDesc("解析数据失败,数据为数组,非jsonObject对象");
                return signBean;
            }else{//如果value 为 字符串 ,int ..
                signBean.setCode("-100");
                signBean.setDesc("解析数据失败,数据为字符串或其他,非对象");
                return signBean;
            }

            if(resultMap.size()==0){//传入空对象{} 直接token 加密
                String sign=SignUtil.encodeToken(resultMap);
                signBean.setCode("0");
                signBean.setMsg(new H5SignBean.MsgBean(sign));
                return signBean;
            }

            String hasToken="";
            try {
                hasToken= jsonObject.optString("hasToken");
            }catch (Exception e){
                e.printStackTrace();
            }

            if(!"n".equals(hasToken)){//如果传入 n ,则不带token
            }
            //resultMap.put("secret",ENCRYPTION_FUNCTION_SECRET_KEY);
            String sign=SignUtil.encodeToken(resultMap);
            signBean.setCode("0");
            signBean.setMsg(new H5SignBean.MsgBean(sign));
            return  signBean;
    }


    /**
     * 解签数据处理  params 为空 默认 使用token 加密
     * @param json
     */
    public static H5SignBean checkSignFunction(String json) throws JSONException {
        H5SignBean signBean=new H5SignBean();
        signBean.setTag("1");
        JSONObject jsonObject = new JSONObject(json);
        /* 接受sign 的数据*/
        String sign="";// 接受sign数据
        try {
            sign=jsonObject.optString("txxSign");
        }catch (Exception e){
            e.printStackTrace();
        }

       if(TextUtils.isEmpty(sign)){
           signBean.setCode("-100");
           signBean.setDesc("解析数据失败,数据为空");
           return signBean;
       }

        /* 接受 params 的数据*/
        Object value;
        try {
            value = jsonObject.get("params");
        }catch (Exception e){
            e.printStackTrace();
            value=null;
        }
        Map<String, String> resultMap =new HashMap<String, String>();
        if(value==null){
            String signNew=SignUtil.encodeToken(resultMap);
            if(TextUtils.isEmpty(signNew)){
                signBean.setCode("-100");
                signBean.setDesc("解密失败");
                return signBean;
            }
            if(sign.equals(signNew)){
                signBean.setCode("0");
                signBean.setDesc("成功");
            }else{
                signBean.setCode("-100");
                signBean.setDesc("解密失败");
            }
            return signBean;
        }



        Stack<JSONObject> stObj = new Stack<JSONObject>();

        if (value instanceof JSONObject) {//如果对象是 JSONObject
            JSONObject params= jsonObject.getJSONObject("params");
            stObj.push(params);
            JsonToStringMap(stObj,resultMap);
        }else if(value instanceof JSONArray){//如果返回数组
            signBean.setCode("-100");
            signBean.setDesc("解析数据失败,数据为数组,非jsonObject对象");
            return signBean;
        }else{//如果value 为 字符串 ,int ..
            signBean.setCode("-100");
            signBean.setDesc("解析数据失败,数据为字符串或其他,非对象");
            return signBean;
        }

        if(resultMap.size()==0){
            String signNew=SignUtil.encodeToken(resultMap);
            if(TextUtils.isEmpty(signNew)){
                signBean.setCode("-100");
                signBean.setDesc("解密失败,数据为空");
                return signBean;
            }
            if(sign.equals(signNew)){
                signBean.setCode("0");
                signBean.setDesc("成功");
            }else{
                signBean.setCode("-100");
                signBean.setDesc("解密失败,两者不相同");
            }
            return signBean;
        }

        /* 接受 hasToken 的数据*/
        String hasToken="";
        try {
            hasToken= jsonObject.optString("hasToken");
        }catch (Exception e){
            e.printStackTrace();
        }

        if(!"n".equals(hasToken)){//如果传入 n ,则不带token
        }
        //resultMap.put("secret",ENCRYPTION_FUNCTION_SECRET_KEY);
        String signNew=SignUtil.encodeToken(resultMap);
        if(TextUtils.isEmpty(signNew)){
            signBean.setCode("-100");
            signBean.setDesc("解密失败,数据为空");
            return signBean;
        }
        if(sign.equals(signNew)){
            signBean.setCode("0");
            signBean.setDesc("成功");
        }else{
            signBean.setCode("-100");
            signBean.setDesc("解密失败");
        }
        return  signBean;
    }





    public static void JsonToMap(Stack<JSONObject> stObj, Map<String, Object> resultMap) throws JSONException {
        if (stObj == null && stObj.pop() == null) {
            return;
        }
        JSONObject json = stObj.pop();
        Iterator it = json.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            //得到value的值
            Object value = json.get(key);
            if (value instanceof JSONObject) {
                stObj.push((JSONObject) value);
            //递归遍历
                JsonToMap(stObj, resultMap);
            } else {
                resultMap.put(key, value);
            }
        }
    }

    public static void JsonToStringMap(Stack<JSONObject> stObj, Map<String, String> resultMap) throws JSONException {
        if (stObj == null && stObj.pop() == null) {
            return;
        }
        JSONObject json = stObj.pop();
        Iterator it = json.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            if(json.isNull(key)){
              continue;
            }
            String value= json.optString(key);
            /* 如果 object 取null 则退回*/
            /* 不为空的时候 判断 是否是空串*/
            if(TextUtils.isEmpty(value)){
                continue;
            }
            if(TextUtils.isEmpty(value.trim())){
                continue;
            }
            resultMap.put(key,json.optString(key));
            //得到value的值
            // Object value = json.get(key);
//            if (value instanceof JSONObject) {
//                stObj.push((JSONObject) value);
//            //递归遍历
//                JsonToStringMap(stObj, resultMap);
//            } else {
//                resultMap.put(key,json.optString(key));
//            }
        }
    }


}
