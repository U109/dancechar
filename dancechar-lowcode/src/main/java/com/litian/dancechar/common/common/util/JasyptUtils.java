package com.litian.dancechar.common.common.util;

import com.alibaba.druid.filter.config.ConfigTools;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * 类描述：mybatis多数据源加密工具类
 *
 * @author terryhl
 */
public class JasyptUtils {

    /**
     * Jasypt生成加密结果
     *
     * @param password 配置文件中设定的加密密码 jasypt.encryptor.password
     * @param value    待加密值
     * @return
     */
    public static String encryptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptOr = new PooledPBEStringEncryptor();
        encryptOr.setConfig(cryptOr(password));
        String result = encryptOr.encrypt(value);
        return result;
    }

    /**
     * 解密
     *
     * @param password 配置文件中设定的加密密码 jasypt.encryptor.password
     * @param value    待解密密文
     * @return
     */
    public static String decyptPwd(String password, String value) {
        PooledPBEStringEncryptor encryptOr = new PooledPBEStringEncryptor();
        encryptOr.setConfig(cryptOr(password));
        String result = encryptOr.decrypt(value);
        return result;
    }

    public static SimpleStringPBEConfig cryptOr(String password) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm(StandardPBEByteEncryptor.DEFAULT_ALGORITHM);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;
    }

    /**
     * 老DB解密
     *
     * @param password
     * @return
     */
    public static String oldDBDecrypt(String password) {
        try {
            return ConfigTools.decrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 老DB加密
     *
     * @param password
     * @return
     */
    public static String oldDBEncrypt(String password) {
        try {
            return ConfigTools.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String JASYPT_KEY = "PWDSalt";

    public static void main(String[] args) {
        System.out.println(oldDBDecrypt("dBbhagesr0E7QL+LqUr7wirtuIvHO6wAytluO6nLrRuClLI+N7uhvWKZxAkUQBbK7NI+rSN2YtB6L9LbchI+HQ=="));
        System.out.println(oldDBDecrypt("bKKSf1yNvA5hVd4ufrHJVkCxlOPt+pXaXpxnXZc1FbPGrvHB1gcA6sdH523fdqTzYznXdtNWXd4UYir+XZfYaA=="));
        System.out.println(oldDBEncrypt("sstuinfo"));
        // 加密
        System.out.println(encryptPwd("PWDSalt", "cempbase"));
        // 解密
        System.out.println(decyptPwd("PWDSalt", "mNPU7OOlQx5+NUM4TrUDHbZVpidgyIA3"));
        System.out.println(decyptPwd("PWDSalt", "VgKce9UwNjqfL34MJgaVyw=="));
    }
}
