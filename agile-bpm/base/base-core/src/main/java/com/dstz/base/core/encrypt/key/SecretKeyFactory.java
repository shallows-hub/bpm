package com.dstz.base.core.encrypt.key;

/**
 * @author hj
 * @Description TODO
 * @date 2020/9/2-14:32
 */
public class SecretKeyFactory {
    /**
     * getSecretKey
     * @author hj
    SecretKeyType key类型，现默认返回 PythonPasslibPbkdf2Sha512Key
     * @return com.local.mavenProject.SecretKey
     */
    public static SecretKey getSecretKey(String SecretKeyType){
        return new PythonPasslibPbkdf2Sha512Key();
    }
}
