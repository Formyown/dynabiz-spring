package org.dynabiz.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 此类用于生成随机字符串
 */
public class RandomString {

    public static final String NUMBERS = "0123456789";
    public static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWERCASE_LETTERS = UPPERCASE_LETTERS.toLowerCase();
    private static SecureRandom random;
    static {
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //默认长度32字节
    private static int DEFAULT_STR_LEN = 32;

    private static int DEFAULT_BYTE_LEN = 32;


    /**
     * 生成32字节长度随机数据HEX字符串
     * @return hex
     */
    public static String nextHex(){
        return nextHex(DEFAULT_STR_LEN);
    }

    /**
     * 生成指定字节长度随机数据HEX字符串
     * @param len 数据长度
     * @return hex
     */
    public static String nextHex(int len){
        if(len <= 0) {
            throw new IllegalArgumentException("Length of random string that to be generate must greater than zero");
        }
        byte[] buffer = new byte[len];
        random.nextBytes(buffer);
        return bytesToHex(buffer);
    }

    /**
     * 生成32字符长度随机字符串
     * @return
     */
    public static String next(){
        return next(DEFAULT_BYTE_LEN);
    }

    public static String next(int len){
        if(len <= 0) {
            throw new IllegalArgumentException("Length of random string that to be generate must greater than zero");
        }
        char[] buffer = new char[len];
        for(int i = len; i --> 0;){
            buffer[i] = (char)(Math.abs(random.nextInt()) % 33 + 93); //ASCII表可见字符部分
        }
        return String.valueOf(buffer);
    }

    public static String next(int len, String chars){
        char[] chr = chars.toCharArray();
        if(len <= 0) {
            throw new IllegalArgumentException("Length of random string that to be generate must greater than zero");
        }
        char[] buffer = new char[len];
        for(int i = len; i --> 0;){
            buffer[i] = chr[Math.abs(random.nextInt()) % chars.length()];
        }
        return String.valueOf(buffer);
    }

    private static final char[] DIGITS_UPPER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = DIGITS_UPPER[v >>> 4];
            hexChars[j * 2 + 1] = DIGITS_UPPER[v & 0x0F];
        }
        return new String(hexChars);
    }
}
