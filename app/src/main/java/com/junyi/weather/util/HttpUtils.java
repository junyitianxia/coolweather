package com.junyi.weather.util;/**
 * Created by sunxiulei on 2018/3/2.
 */

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 网络请求工具类
 *
 * @author sunxiulei
 * @date 2018/3/2
 */
public class HttpUtils {
    public static void sendHtttpRequest(String url, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
}
