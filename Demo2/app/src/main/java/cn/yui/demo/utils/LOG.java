package cn.yui.demo.utils;

import android.util.Log;

import cn.yui.demo.BuildConfig;


/**
 * @author liaoyuhuan
 * @name ${PROJECT_NAME}
 * @class
 * @time 2018/4/11  11:00
 * @description
 */
public class LOG {
    static final String TAG = "hd/";
    /**
     * false 调试
     * true 不调试
     */
    private static final boolean disableDebug = !BuildConfig.DEBUG;

    public static int v(String tag, String data) {
        if (disableDebug) {
            return 0;
        }
        return Log.v(TAG + tag, data);
    }

    public static int V(String tag, String data) {
        return Log.v(TAG + tag, data);
    }

    public static int i(String tag, String data) {
        if (disableDebug) {
            return 0;
        }

        return Log.i(TAG + tag, data);
    }

    public static int d(String tag, String data) {
        if (disableDebug) {
            return 0;
        }
        return Log.d(TAG + tag, data);
    }

    public static int D(String tag, String data) {
        return Log.d(TAG + tag, data);
    }

    public static int w(String tag, String data) {
        if (disableDebug) {
            return 0;
        }
        return Log.w(TAG + tag, data);
    }

    public static int W(String tag, String data) {
        return Log.w(TAG + tag, data);
    }

    public static int e(String tag, String data) {
        if (disableDebug) {
            return 0;
        }
        return Log.e(TAG + tag, data);
    }

    public static int E(String tag, String data) {
        return Log.e(TAG + tag, data);
    }
}
