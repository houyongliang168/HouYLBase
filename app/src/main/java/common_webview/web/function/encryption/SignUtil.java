package common_webview.web.function.encryption;

import android.text.TextUtils;


import com.utils.log.MyLog;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;



public class SignUtil {

    private static String TAG="SignUtil";
    /**
     * 签名算法 SHA256 再MD5
     *
     * @param paramMap
     * @return
     */
    public static String encodeToken(Map<String, String> paramMap) {
        String str = getPreSignStr(paramMap);
        String sha256 = sha256Encrypt(str);
        if(TextUtils.isEmpty(sha256)){
            return "";
        }
        MyLog.wtf("H5_sha256","-----------sha256:[" + sha256 + "]-----------------");
        String md5 = encryptMD5(sha256);
        MyLog.wtf("H5_md5","-----------md5:[" + md5 + "]-----------------");
        if(TextUtils.isEmpty(md5)){
            return "";
        }
        return md5;
    }

    /**
     * 过滤为空的参数,返回排序后拼接好的加密用的字符串
     * secret 不排序,拼在最后
     */
    private static String getPreSignStr(Map<String, String> paramMap) {
        //滤空
        Map<String, String> map = new HashMap<String, String>();

        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (!TextUtils.isEmpty(entry.getValue())) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        //用=和&拼接每个key/value,ASCII排序,再拼接成整串
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            list.add(k + "=" + v + "&");
        }
//        int size = list.size();
//        String[] arrayToSort = list.toArray(new String[size]);
//        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
//        StringBuilder sb = new StringBuilder();
//        for (int j = 0; j < size; j++) {
//            sb.append(arrayToSort[j]);
//        }
        ArrayList<String> listOrder= orderByASCII(list);//ASCII 排序
        if(listOrder==null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < listOrder.size(); j++) {
            sb.append(listOrder.get(j));
        }
        sb.append(String.format("%s=%s", "secret", "akjdkajhsdfkajdsh"));
        String preSignStr = sb.toString();
        MyLog.wtf(TAG,preSignStr);
        return preSignStr;
    }



    /**
     * 过滤为空的参数,返回排序后拼接好的加密用的字符串
     * 所有的都排序
     */
    private static String getAllOrderPreSignStr(Map<String, String> paramMap) {
        //滤空
        Map<String, String> map = new HashMap<String, String>();

        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (!TextUtils.isEmpty(entry.getValue())) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        //用=和&拼接每个key/value,ASCII排序,再拼接成整串
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            list.add(k + "=" + v + "&");
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < size; j++) {
            sb.append(arrayToSort[j]);
        }
        String preSignStr = sb.deleteCharAt(sb.lastIndexOf("&")).toString();
        MyLog.wtf(TAG,preSignStr);
        return preSignStr;
    }


    /**
     * 根据参数排序
     * @param paramaterses
     * @return
     */
    public static ArrayList<String> orderByASCII(ArrayList<String> paramaterses)
    {
        if (paramaterses != null && paramaterses.size() != 0){
            Collections.sort(paramaterses,new Comparator(){
                @Override
                public int compare(Object o1, Object o2) {
                    if (o1.toString().compareTo(o2.toString()) > 0) {
                        return 1;
                    }else if (o1.toString().compareTo(o2.toString()) < 0){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            });
            return paramaterses;
        }
        return null;
    }



    /**
     * SHA256加密
     *
     * @param strSrc
     *            明文
     * @return 加密之后的密文
     */
    public static String sha256Encrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-256");// 将此换成SHA-1、SHA-512、SHA-384等参数
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * byte数组转换为16进制字符串
     *
     * @param bts
     *            数据源
     * @return 16进制字符串
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     * 加密
     * @param plaintext 明文
     * @return ciphertext 密文
     */
    public final static String encryptMD5(String plaintext) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = plaintext.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
    }
}
