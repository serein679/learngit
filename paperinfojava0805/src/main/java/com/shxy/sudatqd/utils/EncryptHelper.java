package com.shxy.sudatqd.utils;

import cn.hutool.crypto.SmUtil;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

/**
 * @author Lenl
 * @version v1.0
 * @create 2022-03-28 12:29
 * @description
 */
public class EncryptHelper {
    private static Logger logger = LoggerFactory.getLogger(EncryptHelper.class);

    private static final String ENCODING = "UTF-8";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * sm3算法加密
     *
     * @param paramStr 待加密字符串
     * @return 返回加密后，固定长度=32的16进制字符串
     * @explain
     */
    public static String encrypt(String paramStr) {
        // 将返回的hash值转换成16进制字符串
        String resultHexString = "";
        try {
            // 将字符串转换成byte数组
            byte[] srcData = paramStr.getBytes(ENCODING);
            // 调用hash()
            byte[] resultHash = hash(srcData);
            // 将返回的hash值转换成16进制字符串
            resultHexString = ByteUtils.toHexString(resultHash);
        } catch (UnsupportedEncodingException e) {
            logger.error("计算字符串sm3哈希值时出错,字符串为'" + paramStr + "'：" + e.getMessage());
        }
        return resultHexString;
    }

    /**
     * 返回长度=32的byte数组
     *
     * @param srcData
     * @return
     * @explain 生成对应的hash值
     */
    public static byte[] hash(byte[] srcData) {
        SM3Digest digest = new SM3Digest();
        //update the message digest with a single byte.
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        //close the digest, producing the final digest value.
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
     * sm3算法加密
     *
     * @param paramStr 待加密字符串
     * @param key      密钥
     * @return 返回加密后，固定长度=32的16进制字符串
     * @explain
     */
    public static String encryptPlus(String paramStr, String key) {
        // 将返回的hash值转换成16进制字符串
        String resultHexString = "";
        try {
            // 将字符串转换成byte数组
            byte[] srcData = paramStr.getBytes(ENCODING);
            // 调用hash()
            byte[] resultHash = hmac(srcData, key.getBytes(ENCODING));
            // 将返回的hash值转换成16进制字符串
            resultHexString = ByteUtils.toHexString(resultHash);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultHexString;
    }

    /**
     * 通过密钥进行加密
     *
     * @param key     密钥
     * @param srcData 被加密的byte数组
     * @return
     * @explain 指定密钥进行加密
     */
    public static byte[] hmac(byte[] key, byte[] srcData) {
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(srcData, 0, srcData.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }

    /**
     * 判断源数据与加密数据是否一致
     *
     * @param srcStr       原字符串
     * @param sm3HexString 16进制字符串
     * @return 校验结果
     * @explain 通过验证原数组和生成的hash数组是否为同一数组，验证2者是否为同一数据
     */
    public static boolean verify(String srcStr, String sm3HexString) {
        boolean flag = false;
        try {
            //使用指定的字符集将字符串编码为 byte 序列，并将结果存储到一个新的 byte 数组中
            byte[] srcData = srcStr.getBytes(ENCODING);
            //16进制 --> byte[]
            byte[] sm3Hash = ByteUtils.fromHexString(sm3HexString);
            byte[] newHash = hash(srcData);
            //判断数组是否相等
            if (Arrays.equals(newHash, sm3Hash)) {
                flag = true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return flag;
    }
/***********************使用Hutool
 * 工具类*****************************************************************************************/
    /**
     * sm3算法加密
     *
     * @param paramStr 待加密字符串
     * @return 返回加密后，固定长度=32的16进制字符串
     * @explain
     */
    public static String encryptSm3ByHutool(String paramStr) {
        return SmUtil.sm3(paramStr);
    }

    /**
     * 判断源数据与加密数据是否一致(Hutool)
     *
     * @param srcStr       原字符串
     * @param sm3HexString 16进制字符串
     * @return 校验结果
     */
    public static boolean verifySm3ByHutool(String srcStr, String sm3HexString) {
        boolean flag = false;
        if (sm3HexString.equals(encryptSm3ByHutool(srcStr))) {
            flag = true;
        }
        return flag;
    }
/***********************使用Hutool
 * 工具类*****************************************************************************************/

    /**
     * 计算文件 MD5
     *
     * @param path 文件路径
     * @return 返回文件的md5字符串，如果计算过程中任务的状态变为取消或暂停，返回null， 如果有其他异常，返回空字符串
     */
    public static String fileToMD5(String path) {
        FileInputStream fis = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(path);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                md5.update(buffer, 0, len);
            }
            fis.close();

            byte[] byteArray = md5.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : byteArray) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            logger.error("计算文件串md5哈希值时出错,文件为'" + path + "'：" + e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("计算文件串md5哈希值时出错,且关闭文件时出错,文件为'" + path + "'：" + e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * 求一个字符串的md5值
     *
     * @param input 字符串
     * @return md5 value
     */

    public static String getStringMD5(String input) {
        if (input == null || input.length() == 0) {
            return null;
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            byte[] byteArray = md5.digest();

            BigInteger bigInt = new BigInteger(1, byteArray);
            // 参数16表示16进制
            String result = bigInt.toString(16);
            // 不足32位高位补零
            while (result.length() < 32) {
                result = "0" + result;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            logger.error("计算字符串串md5哈希值时出错,字符串为'" + input + "'：" + e.getMessage());
        }
        return null;
    }


}
