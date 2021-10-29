package com.yongliang.lib_web.web.function.encryption;


import com.yongliang.lib_web.BuildConfig;

/* 加密信息的参数信息*/
public class EncryptConstont {
    /* 对信息进行加签*/
    public static final String GET_SIGN_FUNCTION = "GET_SIGN_FUNCTION";
    /* 对信息进行解签*/
    public static final String CHECK_SIGN_FUNCTION = "CHECK_SIGN_FUNCTION";

    public static final String ENCRYPTION_FUNCTION_SECRET_KEY = "xxxx";

    /* 对信息进行加密*/
    public static final String ENCRYPT_FUNCTION = "ENCRYPT_FUNCTION";
    /* 对信息进行解密*/
    public static final String DECRYPT_FUNCTION = "DECRYPT_FUNCTION";

    /* RSA 公钥*/
    public static final String RSA_PUNLICK_KEY;
    /* RSA 私钥钥*/
    public static final String RSA_PRIVATE_KEY;

    static {
        if (BuildConfig.isRelease) {//测试
            RSA_PUNLICK_KEY = "";
            RSA_PRIVATE_KEY = "";
        } else {//生产
            RSA_PUNLICK_KEY = "";
            RSA_PRIVATE_KEY = "";
        }
    }

}
