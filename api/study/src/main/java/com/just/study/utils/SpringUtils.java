package com.just.study.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

/**
 * spring 工具类
 */
public class SpringUtils extends BeanUtils {

    /**
     * 对象拷贝
     * @param source 需要拷贝的对象
     * @return 返回一个新的对象
     */
    public static Object copyObject(Object source) {
        Object target = new Object();
        copyProperties(source, target);
        return target;
    }



}
