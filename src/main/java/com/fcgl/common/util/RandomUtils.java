package com.fcgl.common.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 随机工具类
 *
 * @author furg@senthink.com
 * @date 2017/11/21
 */
public final class RandomUtils {

    private static final String ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz";

    private static final String ALPHABET_UPPER = ALPHABET_LOWER.toUpperCase();

    private static final String DIGITS = "0123456789";

    private static final String ALPHA_NUM = ALPHABET_LOWER + ALPHABET_UPPER + DIGITS;

    private static final Random random = new SecureRandom();

    private static Integer CODE_LENGTH = 6;

    private RandomUtils() {

    }

    /**
     * 随机字母数字字符串
     * @param length 字符串长度
     * @return string
     */
    public static String randomString(int length) {
        char[] buf = new char[length];
        char[] symbols = ALPHA_NUM.toCharArray();
        for (int i = 0; i < length; i++) {
            buf[i] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }

    /**
     * 随机六位数字字符串
     * @return string
     */
    public static String randomCode() {
        char[] buf = new char[CODE_LENGTH];
        char[] symbols = DIGITS.toCharArray();
        for (int i = 0; i < CODE_LENGTH; i++) {
            buf[i] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }

    /**
     * 500内的随机整数
     * @return int
     */
    public static int randomInt() {
        return random.nextInt(500);
    }

}
