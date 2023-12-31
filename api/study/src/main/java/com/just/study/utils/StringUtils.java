package com.just.study.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * string 自定义工具类
 */
public class StringUtils {

    /**
     * 数字集合，用于做随机数字符串
     */
    private static final List<Integer> nums = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0).collect(Collectors.toList());

    /**
     * 小写字母集合，用于做随机字符串
     */
    private static final List<Character> lowerCaseLetters = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

    /**
     * 大写字母集合，用于做随机字符串
     */
    private static final List<Character> capitalLetters = Arrays.asList('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');

    /**
     * 判断变量是否为 null
     *
     * @param value 需要判断的变量
     * @return true 不为空 false 为空
     */
    public static Boolean isNotNull(Object value) {
        try {
            Class<?> clazz = value.getClass();
            //字符串做非空和去空格
            if ("java.lang.String".equalsIgnoreCase(clazz.getName())) {
                return !("".equals(value.toString()) || value == null || "null".equalsIgnoreCase(value.toString()));
            } else if ("java.math.BigDecimal".equalsIgnoreCase(clazz.getName())) {
                System.out.println(new BigDecimal(value.toString()));
                return false;
            } else {
                return value != null;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * 生成 9 位 数字，可作为账号，或者唯一id
     *
     * @return
     */
    public static String generateNum9() {
        StringBuilder account = new StringBuilder("79");
        for (int i = 1; i <= 7; i++) {
            account.append((int) (Math.random() * 10));
        }
        return account.toString();
    }

    /**
     * 生成 传入长度 数字，可作为账号，或者唯一id
     *
     * @return
     */
    public static String generateNum(int length) {
        StringBuilder account = new StringBuilder("79");
        for (int i = 1; i <= length; i++) {
            account.append((int) (Math.random() * 10));
        }
        return account.toString();
    }

    /**
     * 生成唯一 ID
     * @return ID
     */
    public static String randomUUID() {
        StringBuilder uid = new StringBuilder();
        for (int i = 1; i <= 16; i++) {
            int random = (int) (Math.random() * nums.size());
            uid.append(nums.get(random));
            uid.append(lowerCaseLetters.get(random));
        }
        return uid.toString();
    }


}
