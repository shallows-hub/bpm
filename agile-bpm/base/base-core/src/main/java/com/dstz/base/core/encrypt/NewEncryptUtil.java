package com.dstz.base.core.encrypt;

import com.dstz.base.core.encrypt.key.SecretKey;
import com.dstz.base.core.encrypt.key.SecretKeyFactory;

/**
 * @author hj
 * @Description TODO
 * @date 2020/9/2-14:21
 */

public class NewEncryptUtil {
    private static final String SecretKeyType = "PythonPasslibPbkdf2Sha512Key";
    /**
     * authenticate
     * @author hj
     * @param attemptedPassword 明文
    encryptedPassword 密文
     * @return boolean
     */
    public static boolean authenticate(String attemptedPassword, String encryptedPassword) throws Exception {
        SecretKey secretKey  = SecretKeyFactory.getSecretKey(SecretKeyType);
        secretKey.setPassword(attemptedPassword);
        return secretKey.authenticate(encryptedPassword);
    };
    /**
     * encrypt
     * @author hj
     * @param attemptedPassword 明文
     * @return java.lang.String 密文
     */
    public static String encrypt(String attemptedPassword) throws Exception {
        SecretKey secretKey  = SecretKeyFactory.getSecretKey(SecretKeyType);
        secretKey.setPassword(attemptedPassword);
        return secretKey.encrypt();
    };
}
