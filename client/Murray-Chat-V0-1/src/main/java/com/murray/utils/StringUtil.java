package com.murray.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Murray Law
 * @describe 字符串工具类
 * @createTime 2020/11/28
 */
public class StringUtil {
    public static final String INSERT="insert",DELETE="delete",UPDATE="update";


    public static final String QQMail_REGEX = "=\\?utf-8\\?B\\?(.*?)==\\?=";

    /**
     * @param str   要解析的字符串
     * @param regex 正则表达式
     * @return java.lang.String
     * @description 正则表达式匹配两个指定字符串中间的内容, 返回单个字符串，若匹配到多个的话就返回第一个
     * @author Murray Law
     * @date 2020/11/28 4:13
     */
    public static String getSubUtilSimple(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);// 匹配的模式
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }

    /**
     * @param str       要匹配的字符
     * @param startChar 正则第一个字符
     * @param endChar   正则最后一个字符
     * @return java.lang.String
     * @description 获取两个指定字符串之间的字符
     * @author Murray Law
     * @date 2020/12/20 17:06
     */
    public static String getCharBetweenTwoChar(String str, String startChar, String endChar) {
        String regex = startChar + "(.*)" + endChar;
        return getSubUtilSimple(str, regex);
    }

    /**
     * 获取指定字符串出现的次数
     *
     * @param srcText  源字符串
     * @param findText 要查找的字符串
     * @return
     */
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }
}
