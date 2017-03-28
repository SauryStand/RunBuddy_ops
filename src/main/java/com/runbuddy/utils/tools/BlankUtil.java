package com.runbuddy.utils.tools;

import java.io.Serializable;
import java.util.*;

/**
 * 逻辑、功能相关描述:验证对象空值，字符串零长，集合无元素
 * all is override method
 * Created by Administrator on 2017/3/25.
 */
public class BlankUtil {

    /**
     * 断字符串是否为空或零长
     * 字符串为空或零长 false 字符串不为空且不是零长字符串
     * @param str
     * @return
     */
    public static boolean isBlank(final String str){
        return (str == null) || (str.trim().length() <= 0);//trim,去掉字符序列左边和右边的空格
    }

    /**
     * 判断字符是否为空或空格
     * @param character
     * @return
     */
    public static boolean isBlank(final Character character){
        return (character == null) || character.equals(' ');//为空返回false
    }

    /**
     * 判断对象对否为空
     * @param object
     * @return
     */
    public static boolean isBlank(final Object object){
        return (object == null);
    }

    /**
     * 判断集合对象是否为空或者无元素
     * @param objects
     * @return
     */
    public static boolean isBlank(final Object[] objects){
        return (objects == null) || (objects.length <= 0);
    }

    /**
     * 判断集合对象是否为空或无元素
     * @param obj
     * @return
     */
    public static boolean isBlank(final Collection<?> obj){
        return (obj == null) || (obj.size() <= 0);
    }

    /**
     * 判断set对象是否为空
     * @param obj
     * @return
     */
    public static boolean isBlank(final Set<?> obj) {
        return (obj == null) || (obj.size() <= 0);
    }
    /**
     * 判断vector对象是否为空或无元素
     * @param obj
     * @return
     */
    public static boolean isBlank(final Vector<?> obj) {
        return (obj == null) || (obj.size() <= 0);
    }
    /**
     * Function:判断HashTable对象是否为空或无元素
     * @param obj
     *            待检查的HashTable对象变量
     * @return true HashTable对象为空或无元素 false HashTable对象不为空且有元素
     */
    public static boolean isBlank(final Hashtable<?, ?> obj) {
        return (obj == null) || (obj.size() <= 0);
    }

    /**
     * Function:判断Serializable对象是否为空
     * @param obj
     *            待检查的Serializable对象变量
     * @return true Serializable对象为空 false Serializable对象不为空
     */
    public static boolean isBlank(final Serializable obj) {
        return obj == null;
    }

    /**
     * Function:判断Map对象是否为空或无元
     * @param obj
     *            待检查的Map对象变量
     * @return true Map对象为空或无元素 false Map对象不为空且有元素
     */
    public static boolean isBlank(final Map<?, ?> obj) {
        return (obj == null) || (obj.size() <= 0);
    }

    /**
     * Function:判断Long对象是否为空
     * @param obj
     *            待检查的Long对象变量
     * @return true Long对象为空 false Long对象不为空且有元素
     */
    public static boolean isBlank(final Long obj) {
        return obj == null;
    }

}
