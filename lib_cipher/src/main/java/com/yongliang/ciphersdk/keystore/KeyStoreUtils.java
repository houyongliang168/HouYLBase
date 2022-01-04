package com.yongliang.ciphersdk.keystore;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/**
 * created by houyl
 * on  9:46 上午
 * 完整参考
 * https://www.jianshu.com/p/dc5a9f906eb8
 *
 * <p>
 * 利用 Android Keystore 系统，您可以在容器中存储加密密钥，从而提高从设备中提取密钥的难度。
 * 在密钥进入 Keystore 后，可以将它们用于加密操作，而密钥材料仍不可导出。
 * 此外，它提供了密钥使用的时间和方式限制措施，例如要求进行用户身份验证才能使用密钥，或者限制为只能在某些加密模式中使用。
 * <p>
 * <p>
 * Keystore 系统由KeyChain API 以及在 Android 4.3(API 级别 18)中引入的 Android Keystore 提供程序功能使用。
 * <p>
 * 完成一些我们应用中的一些比较私密敏感的信息的加密存储，以及解密，防止APP的一些关键信息泄露。
 * 我们在实际开发中应该如何使用呢？这里我简单做个介绍，主要分为三步
 * ：第一步，密钥的创建。
 * 第二步，对我们的数据进行加密，
 * 然后最后当然就是我们要使用到数据的时候再对加密后的数据进行解密了
 */
public class KeyStoreUtils {

    final String ANDROID_KEY_STORE = "AndroidKeyStore";
    byte[] encryptedIv;

    /**
     * 可以看到，我们首先创建得到一个密钥生成器KeyGenerator 的实例，其中两个参数的相关说明参看下图：
     * https://www.sohu.com/a/359895259_671494
     * <p>
     * <p>
     * 然后使用 KeyGenerator 的实例初始化一些相关的配置，
     * 如上我们配置了密钥的别名，以及使用到的相关属性加密解密，
     * setBlockModes 使我们确信仅指定可用于加密和解密的数据块模式中，如果使用的任何其他类型的块模式，它将被拒绝。
     * 由于我们使用 “AES / GCM / NoPadding” 转换算法，我们还告诉KeyGenParameterSpec应该使用的填充类型为NoPadding。
     * 至此，我们便完成了通过密钥生成器生成密钥的全部过程了。
     *
     * @param alias
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidAlgorithmParameterException
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private SecretKey getSecretKey(final String alias) throws NoSuchAlgorithmException,

            NoSuchProviderException, InvalidAlgorithmParameterException {

        final KeyGenerator keyGenerator = KeyGenerator

                .getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE);

        keyGenerator.init(new KeyGenParameterSpec.Builder(alias,

                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)

                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)

                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)

                .build());

        return keyGenerator.generateKey();
    }

    /**
     * 可以看到我们首先得到了 Cipher 的实例，并指定了加密的算法为 "AES/GCM/NoPadding" ,
     * 然后直接初始化 Cipher 的实例指定为加密模式，并传入我们的第一步创建的密钥，这
     * 里我们存储 Cipher 初始化向量（IV），
     * 因为我们在解密的时候会用到。最后一步我们便直接调用 Cipher 的 doFinal 完成对数据的加密，doFinal方法返回一个字节数组，它是实际的加密文本，我们直接 Base64 编码一次就好：
     *
     * @param alias
     * @param textToEncrypt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IOException
     * @throws InvalidAlgorithmParameterException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    byte[] encryptText(final String alias, final String textToEncrypt)

            throws NoSuchAlgorithmException,

            NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IOException,

            InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {

        final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(alias));
//        解密需要
        encryptedIv = cipher.getIV();
        byte[] encryption = cipher.doFinal(textToEncrypt.getBytes("UTF-8"));

        return encryption;
    }

    //    数据加密 转 base64
    String encryptTextBase64(byte[] encryption) {
        return Base64.encodeToString(encryption, Base64.DEFAULT);
    }

    //    数据加密 转 base64
    byte[] decryptTextBase64(String decryption) {
        byte[] data = Base64.decode(decryption, Base64.DEFAULT);
        return data;
    }

    /**
     * 其实加密与解密是一组对称的操作，解密其实就是加密的一个反向操作。
     * 同样的我们还是需要先获得 Cipher 的实例，然后我们通过GCMParameterSpec 类来赋予 Cipher 初始化向量的参数，
     * 这里简单的对 GCMParameterSpec 的两个参数进行说明，
     * 简单的块密码模式（例如CBC）通常只需要初始化向量（例如IvParameterSpec），
     * 但GCM需要以下参数：
     * <p>
     * IV：初始化向量（IV）
     * tLen：认证标签T的长度（以位为单位）
     *
     * @param alias
     * @param encryptedData
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    String decrypt(String alias, byte[] encryptedData) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException, CertificateException, UnrecoverableEntryException, KeyStoreException {
        final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        final GCMParameterSpec spec = new GCMParameterSpec(128, encryptedIv);

        cipher.init(Cipher.DECRYPT_MODE, getSecretKeyWithDecrypt(alias), spec);

        return new String(cipher.doFinal(encryptedData), "UTF-8");
    }



    private SecretKey getSecretKeyWithDecrypt(final String alias) throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, KeyStoreException, CertificateException, IOException {
       KeyStore keyStore= KeyStore.getInstance(ANDROID_KEY_STORE);
        keyStore.load(null);
        KeyStore.SecretKeyEntry secretKeyEntry= (KeyStore.SecretKeyEntry) keyStore.getEntry(alias, null);
        return secretKeyEntry.getSecretKey();
    }


}
