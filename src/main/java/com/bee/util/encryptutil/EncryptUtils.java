package com.bee.util.encryptutil;


import com.bee.util.PrintUtil;
import com.bee.util.smutil.SM2Utils;
import com.bee.util.smutil.Util;
import org.bouncycastle.util.encoders.Base64;

import java.io.IOException;

/**
 * Created by AndroidXJ on 2018/7/11.
 * 国密+国际标准算法加解密专用类
 */

public class EncryptUtils {
    //公钥: 04D8E7D66AB973795D9F2E1C322D313CC4FED03EE5D78AF4D58403CF5955A49EC209C6276567221E41A4D869E565E61D39AAC824E12509517601DC80F032C351B7
    // 私钥: 00B0DE816901FECF9A65597C67FA1475DB14811215AC78A8476D62565450470752

//    public static String pubKey= "040AE4C7798AA0F119471BEE11825BE46202BB79E2A5844495E97C04FF4DF2548A7C0240F88F1CD4E16352A73C17B7F16F07353E53A176D684A9FE0C6BB798E857";
//    public static String priKey="128B2FA8BD433C6C068C8D803DFF79792A519A55171B1B650C23661D15897263";
//    private static String pubkS = new String(Base64.encode(Util.hexToByte(pubKey)));
//    private static String prikS = new String(Base64.encode(Util.hexToByte(priKey)));
//    private static byte[] cipherText = new byte[0];
//    private static String plainText = "";

    // 国密公钥规范测试公钥
    private static String pubk = "040AE4C7798AA0F119471BEE11825BE46202BB79E2A5844495E97C04FF4DF2548A7C0240F88F1CD4E16352A73C17B7F16F07353E53A176D684A9FE0C6BB798E857";
    public  static String pubkS = new String(Base64.encode(Util.hexToByte(pubk)));

    // 国密私钥规范测试私钥
    private static String prik = "128B2FA8BD433C6C068C8D803DFF79792A519A55171B1B650C23661D15897263";
    public static String prikS = new String(Base64.encode(Util.hexToByte(prik)));
//    private static String plainText = "";

    // 国密规范测试用户ID
    private static String userId = "ALICE123@YAHOO.COM";
//    private static byte[] AESEncryByte = new byte[0];//使用AES方式生成的加密数据
//    private static byte[] AESDecryStr = new byte[0];//使用AES方式生成的解密数据
//    private static byte[] RSAEncryByte = new byte[0];//使用RSA方式生成的加密数据
//    private static byte[] RSADecryStr = new byte[0];//使用RSA方式生成的解密数据
//    private static byte[] digest = new byte[0];//使用SM2方式生成数字证书
//
//    private static String encryData = "";
//    private static String aesSm2EncryStr = "";
//    private static String digestStr = "";
//    private static String  aesEncryStr="";
//    private static String  aesDecryStr="";

    /**
     * 使用RSA+SM2+数字签名对数据进行加密加数字签名   因为RSA加解密后数据的hashCode改变，无法进行数字证书验签，所以不能采用这种方式
     *
     * @param rsaPubKey RSA公钥
     * @param sm2pubKey SM2公钥
     * @param aesData   需要加密的数据
     */
    public static void rsaSm2DegistEncrypt(String rsaPubKey, String sm2pubKey, String aesData) {
        PrintUtil.println("RSA加密签的数据----" + aesData.hashCode());
        byte[] RSAEncryByte = new byte[0];
        byte[] digest = new byte[0];
        try {
            String rsaEncryStr = RSAUtils.encryptPublicData(aesData);
            PrintUtil.println("RSA加密后的数据----" + rsaEncryStr);
            RSAEncryByte = SM2Utils.encrypt(Base64.decode(pubkS.getBytes()), rsaEncryStr.getBytes());
            PrintUtil.println("SM2加密后的数据----" + new String(Base64.encode(RSAEncryByte)));
            digest = SM2Utils.sign(userId.getBytes(), Base64.decode(prikS.getBytes()), aesData.getBytes());
            PrintUtil.println("生成的签名证书----" + Util.getHexString(digest));
            PrintUtil.println("生成的签名证书的长度----" + Util.getHexString(digest).length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用RSA+SM2+数字签名对数据进行解密验签,暂时不用
     *
     * @param aesDecyrKey RSA私钥
     * @param sm2PriKey   SM2私钥
     * @param data        需要解密验签的数据
     */
    public static void rsaSm2DegistDecrypt(String aesDecyrKey, String sm2PriKey, String data) {
        byte[] RSAEncryByte = new byte[0];
        byte[] digest = new byte[0];
        String plainText="";
        try {
            plainText = new String(SM2Utils.decrypt(Base64.decode(prikS.getBytes()), RSAEncryByte));
            PrintUtil.println("SM2解密后的数据----" + plainText);
            String rsaDecryStr = RSAUtils.decryptPrivateData(plainText);
            boolean isSame = "asdfasdfasdfasdf".equals(rsaDecryStr);
            PrintUtil.println("RSA解密后的数据----" + rsaDecryStr.hashCode());
            PrintUtil.println("解密后的数据是否一致----" + isSame);
            boolean vs = SM2Utils.verifySign(userId.getBytes(), Base64.decode(pubkS.getBytes()), rsaDecryStr.getBytes(), digest);
            PrintUtil.println("验签是否通过-----" + vs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用AES+SM2+数字签名实现数据加密
     *
     * @param aesEncyrKey AES秘钥
     * @param sm2PubKey   SM2公钥
     * @param data        需要加密加签的数据
     */
    public static String aesSm2DegistEncrypt(String aesEncyrKey, String sm2PubKey,String sm2PriKey, String data) {
        String result="";
        byte[] AESEncryByte = new byte[0];
        String aesSm2EncryStr="";
        byte[] digest = new byte[0];
        String digestStr="";
        try {
//            Log.i(MainActivity.TAG, "加密前的数据----" + data);
            String aesEnStr = AesUtil.encrypt(aesEncyrKey, data);
//            Log.i(MainActivity.TAG, "AES加密后的数据----" + aesEnStr);
            AESEncryByte = SM2Utils.encrypt(Base64.decode(sm2PubKey.getBytes()), aesEnStr.getBytes());
            aesSm2EncryStr = new String(Base64.encode(AESEncryByte));
//            Log.i(MainActivity.TAG, "SM2加密后的数据----" + aesSm2EncryStr);
            digest = SM2Utils.sign(userId.getBytes(), Base64.decode(sm2PriKey.getBytes()), data.getBytes());
            digestStr = new String(Base64.encode(digest));
//            Log.i(MainActivity.TAG, "生成的签名证书----" + Util.getHexString(digest));
//            Log.i(MainActivity.TAG, "生成的签名证书的长度----" + Util.getHexString(digest).length());
//            Log.i(MainActivity.TAG, "证书转换成Sting后的长度---" + digestStr.length());
            result= digestStr + new String(Base64.encode(AESEncryByte));
//            result=new String(Base64.encode(AESEncryByte));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 使用AES+SM2+数字签名对数据进行解密
     * @param aesDecyrKey AES秘钥
     * @param sm2PriKey   SM2私钥
     * @param data        需要解密验签的数据
     */
    public static String aesSm2DegistDecrypt(String aesDecyrKey, String sm2PubKey, String  sm2PriKey,String data) {
        PrintUtil.println("开始解密-----");
        String result="";
        try {
            //需要解密的数据，由于数字签名的长度是一定的，所以这里解密的数据直接从第97为开始
            String decryData = data.substring(96);
            result = new String(SM2Utils.decrypt(Base64.decode(sm2PriKey.getBytes()), Base64.decode(decryData.getBytes())));
//            PrintUtil.println("SM2解密后的数据----" + result);
            result = AesUtil.decrypt(aesDecyrKey, result);
//            PrintUtil.println("AES解密后的数据----" + result);
            PrintUtil.println("结束解密-----");
        } catch (IOException e) {
            PrintUtil.println("揭秘错误-------"+e.getMessage());
            return result;
        }
        return result;
    }

    /**
     * 对已解密的数据进行签名证书验签
     * @param decryData  解密后的数据
     * @param encryData  未解密的数据,用来获取签名摘要
     * @return
     */
    public static boolean isDigestRight(String decryData,String encryData,String sm2PubKey){
        boolean vs=false;
        try {
            String digestData=encryData.substring(0,96);//数字签名的长度保持96位不变
            vs= SM2Utils.verifySign(userId.getBytes(), Base64.decode(sm2PubKey.getBytes()), decryData.getBytes(), Base64.decode(digestData.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vs;
    }


    /**
     * 使用RSA+Sm2对AES秘钥进行加密
     *
     * @param sm2pubKey SN2公钥
     * @param aesData   需要加密的AES秘钥
     */
    public static String rsaSm2Encrypt(String sm2pubKey, String aesData) {
        byte[] RSAEncryByte = new byte[0];
        String aesEncryStr="";
        try {
            String rsaEncryStr = RSAUtils.encryptPublicData(aesData);
            PrintUtil.println("RSA加密后的数据----" + rsaEncryStr);
            RSAEncryByte = SM2Utils.encrypt(Base64.decode(sm2pubKey.getBytes()), rsaEncryStr.getBytes());
            aesEncryStr=new String(Base64.encode(RSAEncryByte));
            PrintUtil.println("SM2加密后的数据----" + aesEncryStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aesEncryStr;
    }

    /**
     * 使用RSA+SM2对数据进行解密
     ** @param sm2Prikey SM2私钥
     * @param data      需要解密的数据
     */
    public static String rsaSm2Decrypt(String sm2Prikey, String data) {
        String aesDecryStr="";
        String plainText="";
        try {
            plainText = new String(SM2Utils.decrypt(Base64.decode(sm2Prikey.getBytes()), Base64.decode(data.getBytes())));
            PrintUtil.println("SM2解密后的数据----" + plainText);
            aesDecryStr = RSAUtils.decryptPrivateData(plainText);
            PrintUtil.println("RSA解密后的数据----" + aesDecryStr);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return aesDecryStr;
    }
    /**
     * 使用AES+SM2对数据进行加密
     *
     * @param aesEncyrKey AES算法秘钥
     * @param sm2PubKey   SM2算法公钥
     * @param data        需要加密的数据
     */
    public static void aeSm2Encrypt(String aesEncyrKey, String sm2PubKey, String data) {
        byte[] AESEncryByte = new byte[0];
        try {
            String aesEnStr = AesUtil.encrypt(aesEncyrKey, data);
            PrintUtil.println("AES加密后的数据----" + aesEnStr);
            AESEncryByte = SM2Utils.encrypt(Base64.decode(pubkS.getBytes()), aesEnStr.getBytes());
            PrintUtil.println("SM2加密后的数据----" + new String(Base64.encode(AESEncryByte)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用AES+SM2对数据进行解密
     *
     * @param aesDecyrKey AES秘钥
     * @param sm2PriKey   SM2私钥
     * @param data        需要解密的数据
     */
    public static void aesSm2Decypt(String aesDecyrKey, String sm2PriKey, String data) {
        byte[] AESEncryByte = new byte[0];
        String plainText="";
        try {
            plainText = new String(SM2Utils.decrypt(Base64.decode(prikS.getBytes()), AESEncryByte));
            PrintUtil.println("SM2解密后的数据----" + plainText);
            plainText = AesUtil.decrypt(aesDecyrKey, plainText);
            PrintUtil.println( "AES解密后的数据----" + plainText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用SM2对数据进行加密
     * @param sm2PubKey
     * @param data
     */
    public static String sm2Encrypt(String sm2PubKey,String data){
        PrintUtil.println("开始加密-------");
        byte[] AESEncryByte = new byte[0];
        try {
            AESEncryByte = SM2Utils.encrypt(Base64.decode(sm2PubKey.getBytes()), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintUtil.println("结束加密-------");
        return new String(Base64.encode(AESEncryByte));
    }

    /**
     * 使用SSM2对数据进行解密
     * @param sm2Prikey
     * @param data
     */
    public static String sm2Decrypt(String sm2Prikey,String data){
        String result="";
        try {
            result = new String(SM2Utils.decrypt(Base64.decode(sm2Prikey.getBytes()), Base64.decode(data.getBytes())),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 数据加密的调用方法
     * @param data
     * @return
     */
    public static String  encryData(String data){
        return sm2Encrypt(EncryptUtils.pubkS,data);
    }
    /**
     * 数据解密的调用方法
     * @param data
     * @return
     */
    public static String  decryData(String data){
//        return sm2Decrypt(EncryptUtils.prikS,data);
        return EncryptUtils.aesSm2DegistDecrypt("1111111111111111", EncryptUtils.pubkS, EncryptUtils.prikS,data);
    }

}
