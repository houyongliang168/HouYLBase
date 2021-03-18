package common_webview.web.function.encryption;


import com.yongliang.houylbase.BuildConfig;

/* 加密信息的参数信息*/
public class EncryptConstont {
    /* 对信息进行加签*/
    public static final String GET_SIGN_FUNCTION= "GET_SIGN_FUNCTION";
    /* 对信息进行解签*/
    public static final String CHECK_SIGN_FUNCTION= "CHECK_SIGN_FUNCTION";

    public static final String ENCRYPTION_FUNCTION_SECRET_KEY= "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDe4S6a6XIiMc2J8aVzPSctjrhAGchx6dble3I4VXjT6lWqqNSDk8X5wLmN06FUG24wJRKUK4xiQZGF13WddFi6WicHMcLwrizUWC59w/PEGwSJkH3xjYibLwhruMvKagvY70F29l6Vzm52bDDYLn2DC5fhmOHoGFI8wDAQAB";

    /* 对信息进行加密*/
    public static final String ENCRYPT_FUNCTION= "ENCRYPT_FUNCTION";
    /* 对信息进行解密*/
    public static final String DECRYPT_FUNCTION= "DECRYPT_FUNCTION";

    /* RSA 公钥*/
    public static final String RSA_PUNLICK_KEY;
    /* RSA 私钥钥*/
    public static final String RSA_PRIVATE_KEY;

   static {
          if( BuildConfig.DEBUG){//测试
          RSA_PUNLICK_KEY="" ;
          RSA_PRIVATE_KEY="";
      } else{//生产
          RSA_PUNLICK_KEY="" ;
          RSA_PRIVATE_KEY="";
      }
   }

}
