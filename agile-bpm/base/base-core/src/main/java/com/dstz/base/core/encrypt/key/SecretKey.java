package com.dstz.base.core.encrypt.key;

/**
 * @author hj
 * @Description TODO
 * @date 2020/9/2-14:31
 */

public abstract class SecretKey {
    /**
     * 设置明文，必须设置明文后才能加密、验证
     * @author hj
     * @param attemptedPassword 明文
     * @return void
     */
    public abstract void setPassword(String attemptedPassword);

    /**
     * authenticate 必须设置明文后才能加密、验证
     * @author hj
     * @param encryptedPassword 密文
     * @return java.lang.Boolean
     */
    public abstract Boolean authenticate (String encryptedPassword)throws Exception;

    /**
     * 加密
     * @author hj
     * @return java.lang.String
     */
    public abstract String encrypt()throws Exception;
}