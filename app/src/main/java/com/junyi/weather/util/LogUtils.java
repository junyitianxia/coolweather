package com.junyi.weather.util;/**
 * Created by sunxiulei on 2018/3/8.
 */

import android.util.Log;

import com.junyi.weather.BuildConfig;

/**
 * log工具类
 *
 * @author sunxiulei
 * @date 2018/3/8
 */
public class LogUtils {
    private static final boolean DEBUG = BuildConfig.LOG;

    public static void debug(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, "debug: " + msg);
        }
    }

    public static void info(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, "info: " + msg);
        }
    }

    public static void error(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, "error: " + msg);
        }
    }

    public static void warn(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, "error: " + msg);

        }
    }
}
