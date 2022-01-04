package com.yongliang.ciphersdk.AES;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * created by houyl
 * on  5:27 PM
 */
public class AESEncryptUtil {

    /**
     * 加密
     * @param encryptString  待加密内容
     * @param password 解密密钥
     * @return
     */
    public static String encrypt(String encryptString,String password){
        String result =null;
        try {
            // 生成Key
            KeyGenerator keyGenerator;
            keyGenerator = KeyGenerator.getInstance("AES");
            // 使用这种初始化方法可以特定种子来生成密钥，这样加密后的密文是唯一固定的。
            //keyGenerator.init(128, new SecureRandom(passWord.getBytes()));
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(password.getBytes());
            keyGenerator.init(128,secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();
            // Key转换
            Key key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encodeResult = cipher.doFinal(encryptString.getBytes());
            result = parseByte2HexStr(encodeResult); ;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 解密
     * @param decryptString  待解密内容
     * @param passWord 解密密钥
     * @return
     */
    public static String decrypt(String decryptString,String passWord){
        String result = null;
        try {
            byte[] decryptBytes = parseHexStr2Byte(decryptString);
            // 生成Key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            // 使用这种初始化方法可以特定种子来生成密钥，这样加密后的密文是唯一固定的。
            //keyGenerator.init(128, new SecureRandom(passWord.getBytes()));
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(passWord.getBytes());
            keyGenerator.init(128,secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();
            // Key转换
            Key key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodeResult = cipher.doFinal(decryptBytes);
            result = new String(decodeResult);
        }catch(NumberFormatException e){
            e.printStackTrace();
            System.out.println("AES解密参数非法！");
            return "";
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
