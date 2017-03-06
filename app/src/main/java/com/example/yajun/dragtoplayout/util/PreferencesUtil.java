package com.example.yajun.dragtoplayout.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import java.util.Set;

/**
 * SharedPreferences工具类，包含常用的数值获取和存储
 *
 * @author Administrator
 */
public class PreferencesUtil {

    /**
     * 默认的SharePreference名称
     */
    private static final String SHARED_NAME = "SharedPreferences";

    public static SharedPreferences getPre(Context context,String defaultKey) {
        if (TextUtils.isEmpty(defaultKey)) {
            defaultKey = SHARED_NAME;
        }
        SharedPreferences sp = context.getSharedPreferences(defaultKey, Context.MODE_PRIVATE);
        return sp;
    }

    /**
     * 是否包含key
     *
     * @param key key关键字
     * @return 包含返回true；反之返回false
     */
    public static boolean containsKey(Context context,String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.contains(key);
    }

    /**
     * 获取String
     *
     * @param key      key关键字
     * @param defValue 默认值
     * @return 返回获取的String值
     */
    public static String getString(Context context,String key, String defValue) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(key, defValue);
    }

    /**
     * 获取Set<String> 集合
     *
     * @param key       key关键字
     * @param defValues 默认值
     * @return 返回Set<String>值
     */
    public static Set<String> getStringSet(Context context,String key, Set<String> defValues) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getStringSet(key, defValues);
    }

    /**
     * 获取int值
     *
     * @param key      key关键字
     * @param defValue 默认值
     * @return 返回int值，
     */
    public static int getInt(Context context,String key, int defValue) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(key, defValue);
    }

    /**
     * 获取float值
     *
     * @param key      key关键字
     * @param defValue 默认值
     * @return 返回float对应值
     */
    public static float getFloat(Context context,String key, float defValue) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getFloat(key, defValue);
    }

    /**
     * 获取Long类型值
     *
     * @param key      key关键字
     * @param defValue 默认值
     * @return 返回对应的long类型的值
     */
    public static long getLong(Context context,String key, long defValue) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getLong(key, defValue);
    }

    /**
     * 获取boolean类型的值
     *
     * @param key      key关键字
     * @param defValue 默认值
     * @return 返回boolean类型的值
     */
    public static boolean getBoolean(Context context,String key, boolean defValue) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存Stirng类型的值
     *
     * @param key   key关键字
     * @param value 对应的值
     * @return 成功返回true，失败返回false
     */
    public static boolean putString(Context context,String key, String value) {
        return getEditor(context).putString(key, value).commit();
    }

    /**
     * 保存Set<String>集合的值
     *
     * @param key   key关键字
     * @param value 对应值
     * @return 成功返回true，失败返回false
     */
    public static boolean putStringSet(Context context,String key, Set<String> value) {
        return getEditor(context).putStringSet(key, value).commit();
    }

    /**
     * 保存int类型的值
     *
     * @param key   key关键字
     * @param value 对应值
     * @return 成功返回true，失败返回false
     */
    public static boolean putInt(Context context,String key, int value) {
        return getEditor(context).putInt(key, value).commit();
    }

    /**
     * 保存long类型的值
     *
     * @param key   key关键字
     * @param value 对应值
     * @return 成功返回true，失败返回false
     */
    public static boolean putLong(Context context,String key, long value) {
        return getEditor(context).putLong(key, value).commit();
    }

    /**
     * 保存float类型的值
     *
     * @param key   key关键字
     * @param value 对应值
     * @return 成功返回true，失败返回false
     */
    public static boolean putFloat(Context context,String key, float value) {
        return getEditor(context).putFloat(key, value).commit();
    }

    /**
     * 保存boolean类型的值
     *
     * @param key   key关键字
     * @param value 对应值
     * @return 成功返回true，失败返回false
     */
    public static boolean putBoolean(Context context,String key, boolean value) {
        return getEditor(context).putBoolean(key, value).commit();
    }

    /**
     * 删除关键字key
     *
     * @param key 关键字key
     * @return 成功返回true，失败返回false
     */
    public static boolean removeKey(Context context,String key) {
        return getEditor(context).remove(key).commit();
    }

    /**
     * 清除所有的关键字
     *
     * @return 成功返回true，失败返回false
     */
    public static boolean clearValues(Context context) {
        return getEditor(context).clear().commit();
    }


    /**
     * 获取SharedPreferences对象
     *
     * @return 返回SharedPreferences对象
     */
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_NAME, Activity.MODE_PRIVATE);
    }

    /**
     * 获取Editor对象
     *
     * @return 返回Editor对象
     */
    private static Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }
}
