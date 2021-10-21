package com.yongliang.ciphersdk.base64;


import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;



/**
 * Created by 41113 on 2018/7/25.
 */

public class RSAUtils {

    private static String RSA = "RSA";
    /**
     * 加密算法RSA  获取公钥和私钥用到
     */
    private static final String RSA_KEY_ALGORITHM = "RSA";
    /* Cipher.getInstance 会用到*/
    public static final String ECB_PADDING = "RSA/ECB/PKCS1Padding";//加密填充方式

    public static final String ECB_PADDING_ANDROID = "RSA";//加密填充方式
    public static final String ECB_NO_PADDING = "RSA/ECB/PKCS1Padding";//加密填充方式
    /**
     * 获取公钥的key
     */
    public static final String PUBLIC_KEY = "_RSA_PUBKEY";

    /**
     * 获取私钥的key
     */
    public static final String PRIVATE_KEY = "_RSA_PRIKEY";

    public  static String clientKey="tyutuy";
    /**
     * RSA算法规定：待加密的字节数不能超过密钥的长度值除以8再减去11。
     * 而加密后得到密文的字节数，正好是密钥的长度值除以 8。
     * */
    private static int KEYSIZE = 2048;// 密钥位数
    private static int RESERVE_BYTES = 11;
    private static int DECRYPT_BLOCK = KEYSIZE / 8;
    private static int ENCRYPT_BLOCK = DECRYPT_BLOCK - RESERVE_BYTES;
    /**
     * 随机生成RSA密钥对(默认密钥长度为2048)
     *
     * @return
     */
    public static KeyPair generateRSAKeyPair()
    {
        return generateRSAKeyPair(2048);
    }

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength
     *            密钥长度，范围：512～2048<br>
     *            一般1024
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA_KEY_ALGORITHM);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * <p> 生成密钥对(公钥和私钥) </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, String> genKeyPair()  {
        KeyPair keyPair = generateRSAKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, String> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, Base64Utils.encode(publicKey.getEncoded()));
        keyMap.put(PRIVATE_KEY, Base64Utils.encode(privateKey.getEncoded()));
        return keyMap;
    }



    /**
     * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 通过私钥byte[]将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }



    /**
     * 公钥加密 -- 私钥解密
     * 用公钥对字符串进行加密
     * @param data 待加密数据
     * @param data 公钥
     */
    public static byte[] encryptWithPublicKey(byte[] data, byte[] key)
            throws Exception {
        Cipher cp = Cipher.getInstance(ECB_PADDING);
        cp.init(Cipher.ENCRYPT_MODE, getPublicKey(key));
        return cp.doFinal(data);
    }

    /**
     * 公钥加密 -- 私钥解密
     * 私钥解密
     *
     * @param data 待解密数据
     * @param key 密钥
     */
    public static byte[] decryptWithPrivateKey(byte[] data, byte[] key)
            throws Exception {
        Cipher cp = Cipher.getInstance(ECB_PADDING);
        cp.init(Cipher.DECRYPT_MODE, getPrivateKey(key));
        byte[] arr = cp.doFinal(data);
        return arr;
    }

    /**
     * 私钥加密  -- 公钥解密
     *私钥加密
     * @param data 私钥加密数据
     * @param key 密钥
     */
    public static byte[] encryptWithPrivateKey(byte[] data, byte[] key)
            throws Exception {
        Cipher cipher = Cipher.getInstance(ECB_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(key));
        return cipher.doFinal(data);
    }



    /**
     * 私钥加密  -- 公钥解密
     *公钥解密
     * @param data 私钥加密数据
     * @param key 密钥
     */
    public static byte[] decryptWithPublicKey(byte[] data, byte[] key)
            throws Exception {
        Cipher cipher = Cipher.getInstance(ECB_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, getPublicKey(key));
        return cipher.doFinal(data);
    }

/*  =================================这几个方法不怎么用 ======================================*/
    /**
     * 使用N、e值还原公钥
     *
     * @param modulus
     * @param publicExponent
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getNEPublicKey(String modulus, String publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }



    /**
     * 使用N、d值还原私钥
     *
     * @param modulus
     * @param privateExponent
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getNEPrivateKey(String modulus, String privateExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 读取密钥信息
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static String readKey(InputStream in) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null)
        {
            if (readLine.charAt(0) == '-')
            {
                continue;
            } else
            {
                sb.append(readLine);
                sb.append('\r');
            }
        }
        return sb.toString();
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr
     *            公钥数据字符串
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception
    {
        try
        {
            byte[] buffer = Base64Utils.decode(publicKeyStr);
            return   getPublicKey(buffer);
        } catch (NoSuchAlgorithmException e)
        {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e)
        {
            throw new Exception("公钥非法");
        } catch (NullPointerException e)
        {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从字符串中加载私钥<br>
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception
    {
        try
        {
            byte[] buffer = Base64Utils.decode(privateKeyStr);
            return getPrivateKey(buffer);
        } catch (NoSuchAlgorithmException e)
        {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e)
        {
            throw new Exception("私钥非法");
        } catch (NullPointerException e)
        {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in
     *            公钥输入流
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(InputStream in) throws Exception
    {
        try
        {
            return loadPublicKey(readKey(in));
        } catch (IOException e)
        {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e)
        {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 从文件中加载私钥
     *

     *            私钥文件名
     * @return 是否成功
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(InputStream in) throws Exception
    {
        try
        {
            return loadPrivateKey(readKey(in));
        } catch (IOException e)
        {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e)
        {
            throw new Exception("私钥输入流为空");
        }
    }


    /**
     * 打印公钥信息
     *
     * @param publicKey
     */
    public static void printPublicKeyInfo(PublicKey publicKey)
    {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        System.out.println("----------RSAPublicKey----------");
        System.out.println("Modulus.length=" + rsaPublicKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPublicKey.getModulus().toString());
        System.out.println("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
        System.out.println("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
    }

    public static void printPrivateKeyInfo(PrivateKey privateKey)
    {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        System.out.println("----------RSAPrivateKey ----------");
        System.out.println("Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPrivateKey.getModulus().toString());
        System.out.println("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
        System.out.println("PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString());

    }

    /*==============================上方为 数据处理基本方法 ====================================================================*/
    /*==============================下面方法可以处理小字段,如果字数多了 会报异常too much data for RSA bloc ====================================================================*/
    /**
     * 公钥加密，私钥解密---加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encodePrivate(String key, String publicRSAKey) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicRSAKey.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(key.getBytes());
        return new String(Base64.encodeBase64(result));
    }

    /**
     * 公钥加密，私钥解密---解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decodePrivate(String key, String privateRSAKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateRSAKey.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(key.getBytes()));
        return new String(result);
    }

    /**
     * 私钥加密，公钥解密---加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encodePublic(String key, String privateRSAKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateRSAKey.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(key.getBytes());
        return new String(Base64.encodeBase64(result));
    }

    /**
     * 私钥加密，公钥解密---解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decodePublic(String key, String publicRSAKey) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicRSAKey.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(key.getBytes()));
        return new String(result);
    }
    /*========================================分块加密解密======================================================*/


    /**
     * 分块加密
     *
     * @param data
     * @param key
     * */
    public static byte[] encryptWithPublicKeyBlock(byte[] data, byte[] key) throws Exception {
        int blockCount = (data.length / ENCRYPT_BLOCK);

        if ((data.length % ENCRYPT_BLOCK) != 0) {
            blockCount += 1;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream(blockCount * ENCRYPT_BLOCK);
        Cipher cipher = Cipher.getInstance(ECB_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(key));

        for (int offset = 0; offset < data.length; offset += ENCRYPT_BLOCK) {
            int inputLen = (data.length - offset);
            if (inputLen > ENCRYPT_BLOCK) {
                inputLen = ENCRYPT_BLOCK;
            }
            byte[] encryptedBlock = cipher.doFinal(data, offset, inputLen);
            bos.write(encryptedBlock);
        }

        bos.close();
        return bos.toByteArray();
    }

    /**
     * 分块加密
     *
     * @param data
     * @param key
     * */
    public static byte[] encryptWithPrivateKeyBlock(byte[] data, byte[] key) throws Exception {
        int blockCount = (data.length / ENCRYPT_BLOCK);

        if ((data.length % ENCRYPT_BLOCK) != 0) {
            blockCount += 1;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream(blockCount * ENCRYPT_BLOCK);
        Cipher cipher = Cipher.getInstance(ECB_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(key));

        for (int offset = 0; offset < data.length; offset += ENCRYPT_BLOCK) {
            int inputLen = (data.length - offset);
            if (inputLen > ENCRYPT_BLOCK) {
                inputLen = ENCRYPT_BLOCK;
            }
            byte[] encryptedBlock = cipher.doFinal(data, offset, inputLen);
            bos.write(encryptedBlock);
        }

        bos.close();
        return bos.toByteArray();
    }

    /**
     * 分块解密
     *
     * @param data
     * @param key
     * */
    public static byte[] decryptWithPublicKeyBlock(byte[] data, byte[] key) throws Exception {
        int blockCount = (data.length / DECRYPT_BLOCK);
        if ((data.length % DECRYPT_BLOCK) != 0) {
            blockCount += 1;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream(blockCount * DECRYPT_BLOCK);
        Cipher cipher = Cipher.getInstance(ECB_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, getPublicKey(key));
        for (int offset = 0; offset < data.length; offset += DECRYPT_BLOCK) {
            int inputLen = (data.length - offset);
            if (inputLen > DECRYPT_BLOCK) {
                inputLen = DECRYPT_BLOCK;
            }
            byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
            bos.write(decryptedBlock);
        }

        bos.close();
        return bos.toByteArray();
    }

    /**
     * 分块解密
     *
     * @param data
     * @param key
     * */
    public static byte[] decryptWithPrivateKeyBlock(byte[] data, byte[] key) throws Exception {
        int blockCount = (data.length / DECRYPT_BLOCK);
        if ((data.length % DECRYPT_BLOCK) != 0) {
            blockCount += 1;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream(blockCount * DECRYPT_BLOCK);
        Cipher cipher = Cipher.getInstance(ECB_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(key));
        for (int offset = 0; offset < data.length; offset += DECRYPT_BLOCK) {
            int inputLen = (data.length - offset);

            if (inputLen > DECRYPT_BLOCK) {
                inputLen = DECRYPT_BLOCK;
            }

            byte[] decryptedBlock = cipher.doFinal(data, offset, inputLen);
            bos.write(decryptedBlock);
        }

        bos.close();
        return bos.toByteArray();
    }



    /*========================================分块加密解密 再次加工======================================================*/



    /**
     * 公钥加密( 需要私钥解密---加密)
     *
     * @param data   需要加密的数据
     * @param publicRSAKey   公钥
     * @return
     * @throws Exception
     */
    public static String encodePublicKey(String data, String publicRSAKey) throws Exception {

       byte[] pubKey= Base64.decodeBase64(publicRSAKey.getBytes());
       byte[] result=   encryptWithPublicKeyBlock(data.getBytes(),pubKey);
         return new String(Base64.encodeBase64(result));
      // return new String(result,"UTF-8");
    }



    /**
     * 私钥解密(需要公钥---加密)
     *
     * @param data 解密数据
     * @param privateRSAKey 私钥
     * @return
     * @throws Exception
     */
    public static String decodePrivateKey(String data, String privateRSAKey) throws Exception {

        byte[] priKey= Base64.decodeBase64(privateRSAKey.getBytes());
        byte[] dataKey= Base64.decodeBase64(data.getBytes());
     // byte[] dataKey= data.getBytes("UTF-8");
        byte[] result=   decryptWithPrivateKeyBlock(dataKey,priKey);
        return new String(result);

    }



}
