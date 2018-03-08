package com.junyi.weather;/**
 * Created by sunxiulei on 2018/3/2.
 */

import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * 应用使用Application
 *
 * @author sunxiulei
 * @date 2018/3/2
 */
public class MyApplication extends LitePalApplication {
    private static Context context = null;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    private static Context getAppContext() {
        return context;
    }
}
