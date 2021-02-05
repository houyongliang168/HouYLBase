package com.yongliang.houylbase.utils;


import com.yongliang.houylbase.serretutils.Base64Utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by liuyang249 on 2019/8/8.
 */

/**
 * @author Administrator
 */
public class AESUtils {

    // /** 算法/模式/填充 **/
//	private static final String CipherMode = "AES/ECB/NoPadding";

    private static final String CipherMode = "AES";

    /**
     * 生成一个AES密钥对象
     *
     * @return
     */
    public static SecretKeySpec generateKey() {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom());
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            return key;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成一个AES密钥字符串
     *
     * @return
     */
    public static String generateKeyString() {
        return byte2hex(generateKey().getEncoded());
    }

    /**
     * 加密字节数据
     *
     * @param content
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] content, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过byte[]类型的密钥加密String
     *
     * @param content
     * @param key
     * @return 16进制密文字符串
     */
    public static String encrypt(String content, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
            byte[] data = cipher.doFinal(content.getBytes("UTF-8"));
            String result = byte2hex(data);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过String类型的密钥加密String
     *
     * @param content
     * @param key
     * @return 16进制密文字符串
     */
    public static String encrypt(String content, String key) {
        byte[] data = null;
        try {
            data = content.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SecretKeySpec SS = new SecretKeySpec(hex2byte(key), "AES");
        byte[] bytes =
                SS.getEncoded();
        data = encrypt(data, bytes);
        String result = byte2hex(data);
        return result;
    }

    /**
     * 通过byte[]类型的密钥解密byte[]
     *
     * @param content
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] content, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过String类型的密钥 解密String类型的密文
     *
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content, String key) {
        byte[] data = null;
        try {
            data = hex2byte(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = decrypt(data, hex2byte(key));
        if (data == null)
            return null;
        String result = null;
        try {
            result = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过byte[]类型的密钥 解密String类型的密文
     *
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
            byte[] data = cipher.doFinal(hex2byte(content));
            return new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字节数组转成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) { // 一个字节的数，
        StringBuffer sb = new StringBuffer(b.length * 2);
        String tmp = "";
        for (int n = 0; n < b.length; n++) {
            // 整数转成十六进制表示
            tmp = (Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase(); // 转成大写
    }

    /**
     * 将hex字符串转换成字节数组
     *
     * @param inputString
     * @return
     */
    public static byte[] hex2byte(String inputString) {
        if (inputString == null || inputString.length() < 2) {
            return new byte[0];
        }
        inputString = inputString.toLowerCase();
        int l = inputString.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = inputString.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }


    //方法：length为产生的位数
    public static String getRandomString(int length) {
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
// 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    /**
     * 字符串转化成为16进制字符串
     *
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }


    public static void main(String[] args) {

//        String result = AESUtils.encrypt ("adfadfjkadhfkajhdfkjahdkjfhakjdhfkajhd", getRandomString(128));
//        String result = AESEncryptUtil.encrypt ("adfadfjkadhfkajhdfkjahdkjfhakjdhfkajhd", "ADADADFAD7867");
//        System.out.println(result);
//       String SS= AESEncryptUtil.decrypt (result, "ADADADFAD7867");
//
//        System.out.println(SS);
//         生成Key
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            // 使用这种初始化方法可以特定种子来生成密钥，这样加密后的密文是唯一固定的。
            //keyGenerator.init(128, new SecureRandom(passWord.getBytes()));
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = Base64.encodeBase64("lNmUhOXMRUGbqZDP".getBytes());
            secureRandom.setSeed(bytes);
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();
            String algorithm = secretKey.getAlgorithm();
            String format = secretKey.getFormat();
            String keyB = byte2hex(keyBytes);


            System.out.println(keyB);
//            System.out.println(keyC);
//            String randomS = getRandomString(16);
            String randomS = "sCi6HNb2ol3r2dQj";
            System.out.println("randomS :" + randomS);
            String random2 = strTo16(randomS);
            System.out.println("random2 :" + random2);
            String result1 = AESUtils.encrypt("123", random2);
            System.out.println("result1 :" + result1);
            String result3 = Base64Utils.encode(result1.getBytes());
            System.out.println("result3 :" + result3);
            String result2 = AESUtils.decrypt(result1, random2);


            System.out.println(result2);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

//      String SS=  AESTest2.generateKey();
//        System.out.println(SS);
//        String result1 = AESTest2.encrypt ( SS,"adfadfjkadhfkajhdfkjahdkjfhakjdhfkajhd");
//        String result2 = AESTest2.decrypt ( SS,result1);
//        System.out.println(result1);
//        System.out.println(result2);
//        System.out.println("C60D8B7F2C4A576937CF58AD65384024".length());
    }

}
