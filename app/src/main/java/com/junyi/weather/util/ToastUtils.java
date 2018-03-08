package com.junyi.weather.util;/**
 * Created by sunxiulei on 2018/3/8.
 */

import android.widget.Toast;

import com.junyi.weather.MyApplication;

/**
 * toast工具类
 *
 * @author sunxiulei
 * @date 2018/3/8
 */
public class ToastUtils {
    private static Toast toast = null;

    /**
     * 创建短时toast
     *
     * @param msg
     */
    public static void showShortToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }

    /**
     * 创建长时间toast
     * @param msg
     */
    public static void showLongToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), "", Toast.LENGTH_LONG);
        }
        toast.setText("msg");
        toast.show();
    }
}
