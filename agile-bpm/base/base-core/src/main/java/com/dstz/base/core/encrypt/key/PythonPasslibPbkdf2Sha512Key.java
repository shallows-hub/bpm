package com.dstz.base.core.encrypt.key;


import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * @author hj
 * @Description TODO
 * @date 2020/9/2-14:32
 */
public class PythonPasslibPbkdf2Sha512Key extends SecretKey{
    private String checksum;
    private String salt;
    private static final int SALT_BYTE_SIZE = 16;
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final int HASH_BIT_SIZE = 64 * 8;
    private static final int splitLen = 5;
    private static final String ident = "pbkdf2-sha512";
    private final static Base64.Encoder encoder = Base64.getEncoder();
    private final static Base64.Decoder decoder = Base64.getDecoder();

    /**
     * 迭代次数，默认25000
     */
    private int rounds = 25000;
    private String password;

    private void fromString(String encryptedPassword) throws ParserException{
        String[] encryptedPasswords = encryptedPassword.split("\\$");
        if (splitLen != encryptedPasswords.length || !encryptedPasswords[1].equals(ident)){
            throw new ParserException("必须符合 $pbkdf2-sha512${rounds}${salt}${encryptedPassword}格式");
        }
        try{this.rounds = Integer.parseInt(encryptedPasswords[2]); } catch (NumberFormatException e){
            throw new ParserException("{rounds} must is integer format");
        }
        this.salt = encryptedPasswords[3];
        this.checksum = encryptedPasswords[4];
    }

    public String toString(){
        if ("".equals(this.salt) || "".equals(this.checksum)){
            throw new IllegalStateException("必须先加密");
        }
        return "$" + ident + "$" +  this.rounds + "$"+ this.salt + "$" + this.checksum;
    }

    /**
     * setPassword
     * @author hj
     * @param attemptedPassword 明文
     */
    @Override
    public void setPassword(String attemptedPassword) {
        this.password = attemptedPassword;
    }

    private void checkPassword()throws IllegalStateException{
        if (null == this.password){
            throw new IllegalStateException("必须先设置密码");
        }
    }

    @Override
    public Boolean authenticate(String encryptedPassword) throws InvalidKeySpecException, NoSuchAlgorithmException {
        this.checkPassword();
        this.fromString(encryptedPassword);
        byte[] bytesSalt = PythonDecoder(this.salt);
        byte[] byteChecksum = this.encrypt(this.password, bytesSalt);
        return this.checksum.equals(PythonEncoder(byteChecksum));
    }

    @Override
    public String encrypt() throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.checkPassword();
        byte[] byteSalt = this.generateSalt();
        this.salt = PythonEncoder(byteSalt);
        byte[] byteChecksum = this.encrypt(this.password, byteSalt);
        this.checksum = PythonEncoder(byteChecksum);
        return this.toString();
    }
    private byte[] encrypt(String password, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        javax.crypto.SecretKeyFactory f = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, this.rounds, HASH_BIT_SIZE);
        return f.generateSecret(spec).getEncoded();
    }
    public byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        return salt;
    }

    /*
     * python Passlib Pbkdf2 的base64加密将 + 替换成了 . ,为了正常解码python的base64密码，需要通过此函数转换
     * */
    private static byte[] PythonDecoder(String string){

        switch (string.length() & 3){
            case 0 :break;
            case 2 : string = string+ "==";break;
            case 3 : string = string+ "=";break;
        }
        return decoder.decode(string.replace(".","+"));

    }

    /*
     *将java的base64密文转换为python一致的密文
     * */
    private static String PythonEncoder(byte[] bytes){
        StringBuilder str1 = new StringBuilder ();
        str1.append(encoder.encodeToString(bytes));
        while(0 < str1.length() && '=' == str1.charAt(str1.length()-1)){
            str1.deleteCharAt(str1.length() - 1);
        }
        return str1.toString().replace("+", ".");
    }
}