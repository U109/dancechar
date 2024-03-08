package com.litian.dancechar.framework.common.util;import java.security.Key;import java.security.NoSuchAlgorithmException;import javax.crypto.Cipher;import javax.crypto.KeyGenerator;import javax.crypto.NoSuchPaddingException;import javax.crypto.SecretKey;import javax.crypto.spec.SecretKeySpec;import org.apache.commons.codec.binary.Base64;public final class AESUtils {    public static final String CIPHER_ALGORITHM = "AES/ECB/NoPadding";    private static final String KEY_ALGORITHM = "AES";    private static final int KEY_SIZE = 128;    public AESUtils() {    }    public static Key getKey() throws NoSuchAlgorithmException {        KeyGenerator kg = KeyGenerator.getInstance("AES");        kg.init(128);        SecretKey secretKey = kg.generateKey();        return secretKey;    }    public static Key codeToKey(String key) {        byte[] keyBytes = Base64.decodeBase64(key);        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");        return secretKey;    }    private static String decrypt(byte[] data, byte[] key) throws Exception {        Key k = new SecretKeySpec(key, "AES");        Cipher cipher = Cipher.getInstance("AES");        cipher.init(2, k);        return new String(cipher.doFinal(data), "UTF-8");    }    public static String decrypt(String data, String key) throws Exception {        byte[] decodeBase64 = Base64.decodeBase64(data);        if (decodeBase64 != null && decodeBase64.length != 0) {            return decrypt(decodeBase64, Base64.decodeBase64(key));        } else {            throw new NoSuchPaddingException("decrypting with padded cipher");        }    }    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {        Key k = new SecretKeySpec(key, "AES");        Cipher cipher = Cipher.getInstance("AES");        cipher.init(1, k);        return cipher.doFinal(data);    }    public static String encrypt(String data, String key) throws Exception {        byte[] dataBytes = data.getBytes("UTF-8");        byte[] keyBytes = Base64.decodeBase64(key);        return Base64.encodeBase64String(encrypt(dataBytes, keyBytes));    }    public static String getKeyStr() throws Exception {        return Base64.encodeBase64String(getKey().getEncoded());    }}