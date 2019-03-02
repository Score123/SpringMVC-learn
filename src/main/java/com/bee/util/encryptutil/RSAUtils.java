package com.bee.util.encryptutil;

import com.bee.util.PrintUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * Created by AndroidXJ on 2018/5/29.
 */

public class RSAUtils {
    private static byte [] encryptData;
    private static String encryptStr;
    private static byte [] decryptData;
    private static String deccryptStr;
    private static String msg="中华人民公共和国";
    private static String instance="RSA/ECB/PKCS1Padding\"";
    //生成秘钥对
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    //获取公钥(Base64编码)
    public static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    //获取私钥(Base64编码)
    public static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    //将Base64编码后的公钥转换成PublicKey对象
    public static PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    public static PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //公钥加密
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }
    //私钥解密
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //私钥加密
    public static byte[] privateEncrypt(byte[] content, PrivateKey privateKey)throws Exception{
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }
    //公钥解密
    public static byte[] publicDecrypt(byte[] content, PublicKey publicKey)throws Exception{
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }



    //字节数组转Base64编码
    public static String byte2Base64(byte[] bytes){
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    //Base64编码转字节数组
    public static byte[] base642Byte(String base64Key) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Key);
    }
    //使用公钥加密
    public static String encryptPublicData(String content){
        try {
            encryptData= publicEncrypt(string2BYte(content),string2PublicKey(KeyUtil.SERVER_PUBLIC_KEY));
            encryptStr=byte2String(encryptData);
            PrintUtil.println("公钥加密后的数组----"+ Arrays.toString(encryptData));
            PrintUtil.println("公钥加密后的数据----"+ encryptStr);

        } catch (Exception e) {
            PrintUtil.println("公钥加密出错---"+e.getMessage());
            e.printStackTrace();
        }
        return encryptStr;
    }
    //使用私钥解密
    public static String decryptPrivateData(String content){
        try {
            decryptData= privateDecrypt(string2BYte(content),string2PrivateKey(KeyUtil.SERVER_PRIVATE_KEY));
            deccryptStr=byte2String(decryptData);
            PrintUtil.println("解密后的数组----"+ Arrays.toString(decryptData));
            PrintUtil.println("解密后的数据----"+ deccryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deccryptStr;
    }

    //使用私钥加密
    public static String encryptPrivateData(String data){
        try {
            encryptData= privateEncrypt(string2BYte(data),string2PrivateKey(KeyUtil.SERVER_PRIVATE_KEY));
            encryptStr=byte2String(encryptData);
            PrintUtil.println("加密后的数组----"+ Arrays.toString(encryptData));
            PrintUtil.println("加密后的数据----"+ encryptStr);
        } catch (Exception e) {
            PrintUtil.println("公钥加密出错---"+e.getMessage());
            e.printStackTrace();
        }
        return encryptStr;
    }

    //使用公钥解密
    public static String decryptPublicData(String data){
        try {
            decryptData= RSAUtils.publicDecrypt(string2BYte(data), RSAUtils.string2PublicKey(KeyUtil.SERVER_PUBLIC_KEY));
            deccryptStr=byte2String(decryptData);
            PrintUtil.println("解密后的数组----"+ Arrays.toString(decryptData));
            PrintUtil.println("解密后的数据----"+ deccryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deccryptStr;
    }
    /**
     * 将byte数组经过BASE64转成String
     * @param data
     * @return
     */
    public static String byte2String(byte[] data){
        String sData=null;
        BASE64Encoder enc=new BASE64Encoder();
        sData=enc.encodeBuffer(data); //使用BASE64编码
        return sData;
    }

    /**
     * 将String转成byte数组
     * @param data
     * @return
     */
    public static byte[] string2BYte(String data){
        byte [] bData=null;
        BASE64Decoder dec=new BASE64Decoder();
        try {
            bData =dec.decodeBuffer(data);//使用BASE64解码
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bData;
    }
}
