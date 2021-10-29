package com.yongliang.lib_web.web.bean;


/**
 *  接受信息的bean
 */

public class H5EncrptBean {
    private String desc;
    private String code;
    private String msg;
    private String tag;//0  加密 1 解密

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "H5EncrptBean{" +
                "desc='" + desc + '\'' +
                ", code='" + code + '\'' +
                ", msg=" + msg +
                ", tag='" + tag + '\'' +
                '}';
    }
}

