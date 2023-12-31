package com.just.study.utils;

import javafx.scene.input.DataFormat;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义日志
 */
public class Log {

    /**
     * java 8 日期格式转换
     */
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    /**
     * 用于获取类的名称
     */
    private static String targetName = null;

    /**
     * 实例
     */
    private static Log log;

    /**
     * 日志打印 黄色
     */
    private static final String ANSI_YELLOW = "\u001B[34m";


    /**
     * 日志打印 绿色
     */
    private static final String ANSI_GREEN = "\u001B[32m";

    /**
     * 日志打印 黑色
     */
    private static final String ANSI_BLACK = "\u001B[30m";

    /**
     * 日志打印 蓝色
     */
    private static final String ANSI_BLUE = "\u001B[34m";

    /**
     * 日志打印 红色
     */
    private static final String ANSI_RED = "\u001B[31m";

    /**
     * 创建日志实例
     * @param clazz 操作哪个类
     * @return
     */
    public static Log getLogger(Class clazz) {
        targetName = clazz.getName();
        if (log == null) {
            log = new Log();
        }
        return log;
    }

    /**
     * 日志打印 info级别
     * @param message
     */
    public void info(String message) {
        LocalDateTime now = LocalDateTime.now();
        System.out.print("\n" + formatter.format(now));
        System.out.print(ANSI_GREEN + "  [ INFO ] ");
        System.out.print(ANSI_BLUE + targetName + " : ");
        System.out.print(ANSI_BLACK + message + "\t");

    }

    /**
     * 日志打印 error级别
     * @param message
     */
    public void error(String message) {
        LocalDateTime now = LocalDateTime.now();
        System.out.print("\n" + formatter.format(now));
        System.out.print(ANSI_RED + "  [ ERROR ] ");
        System.out.print(ANSI_BLUE + targetName + " : ");
        System.out.print(ANSI_BLACK + message + "\t");

    }

    /**
     * 日志打印 warn级别
     * @param message
     */
    public void warn(String message) {
        LocalDateTime now = LocalDateTime.now();
        System.out.print("\n" + formatter.format(now));
        System.out.print(ANSI_YELLOW + "  [ WARN ] ");
        System.out.print(ANSI_BLUE + targetName + " : ");
        System.out.print(ANSI_BLACK + message + "\t");

    }

}
