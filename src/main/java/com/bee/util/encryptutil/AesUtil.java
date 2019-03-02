package com.bee.util.encryptutil;

import org.apache.mina.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author xyuxiao
 * @date 2017/1/4
 */
public class AesUtil {
    /**
     * 格式化key
     *
     * @param token
     * @return
     */
    public static String formatKey(String token) {
        return "dhht" + token.substring(0, 12);
    }

    /**
     * 加密
     *
     * @param key     密钥
     * @param content 明文字符串
     * @return 密文字节数组
     */
    public static String encrypt(String key, String content) {
        try {
            byte[] rawKey = formatKey(key).getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encypted = cipher.doFinal(content.getBytes("UTF-8"));
            String encrypt = objToString(encypted);
            return encrypt.replace("+", " ");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 解密
     *
     * @param content 密文字节数组
     * @param key     密钥
     * @return 解密后的字符串
     */
    public static String decrypt(String key, String content) {
        try {
            byte[] rawKey = formatKey(key).getBytes();
            byte[] encrypted = (byte[]) stringToObj(content.replace(" ", "+"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decrypted = cipher.doFinal(encrypted);
            String string = new String(decrypted, "UTF-8");
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param seed 种子数据
     * @return 密钥数据
     */
    private static byte[] getRawKey(byte[] seed) {
        byte[] rawKey = null;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(seed);
            // AES加密数据块分组长度必须为128比特，密钥长度可以是128比特、192比特、256比特中的任意一个
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            rawKey = secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
        }
        return rawKey;
    }

    /**
     * 将对象转换成base64编码的字符串
     *
     * @return
     */
    public static String objToString(Object obj) {
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(obj);
            // 将字节流编码成base64的字符窜
            String objBase64 = new String(Base64.encodeBase64(baos
                    .toByteArray()));
            return objBase64;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将base64编码的字符串 转换成对象
     */
    public static Object stringToObj(String str) {
        //读取字节
        byte[] base64 = Base64.decodeBase64(str.getBytes());
        Object obj = null;
        //封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            //再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
            try {
                //读取对象
                obj = (Object) bis.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

}

