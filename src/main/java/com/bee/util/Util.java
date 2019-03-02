package com.bee.util;


import com.bee.util.encryptutil.EncryptUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * @author chenlilin
 * @Title: Util
 * @ProjectName sync
 * @Description: TODO
 * @date 2018/9/13 11:21
 */
public class Util {
    /**
     * 判断是否为空
     * @param obj
     * @return false不为空
     */
    public static boolean isEmpty(Object obj)
    {
        if (obj instanceof Integer && (Integer)obj == 0) {
            return true;
        }
        if (obj == null) {
            return true;
        }
        if (obj instanceof String && obj.toString().length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        return false;
    }
    /**
     * 获得一个无分隔符UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String str = UUID.randomUUID().toString();
        return str.replaceAll("-", "");
    }

    /**
     * 加密的key
     */
    public static final String AESENCYRKEY = "0123456789123456";
    /**
     * 解密的key
     */
    public static final String AESDECYRKEY = "0123456789123456";

    /**
     * 对字符串进行加密
     * @param str
     * @return
     */
    public static String strEncrypt(String str){
        return EncryptUtils.aesSm2DegistEncrypt(AESENCYRKEY,EncryptUtils.pubkS,EncryptUtils.prikS,str);
    }

    /**
     * 对字符串进行解密
     * @param str
     * @return
     */
    public static String strDecrypt(String str){
        return EncryptUtils.aesSm2DegistDecrypt(AESDECYRKEY,EncryptUtils.pubkS,EncryptUtils.prikS,str);
    }
}
