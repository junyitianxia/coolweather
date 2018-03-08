package com.junyi.weather.util;/**
 * Created by sunxiulei on 2018/3/2.
 */
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.junyi.weather.db.City;
import com.junyi.weather.db.County;
import com.junyi.weather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

/**
 * 省市县json解析并保存数据库
 *
 * @author sunxiulei
 * @date 2018/3/2
 */
public class Utility {
    private static final String TAG = "Utility";
    /**
     * 省份数据解析并保存
     * @param reponse
     * @return
     */
    public static boolean handleProvinceResponse(String reponse) {
        if (!TextUtils.isEmpty(reponse)) {
            try {
                JSONArray provinceJson = new JSONArray(reponse);
                for (int i = 0; i < provinceJson.length(); i++) {
                    JSONObject provinceObject = provinceJson.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setId(provinceObject.getInt("id"));
                    province.save();//存储数据库
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }


           /* Gson gson = new Gson();
            Province province = new Province();
            gson.fromJson(reponse, Province.class);
            province.save();//存储数据库
            return true;*/
        }
            return false;
    }

    /**
     * 城市数据解析并保存
     * @param reponse
     * @return
     */
    public static boolean handleCityResponse(String reponse, int provinceId) {
        if (!TextUtils.isEmpty(reponse)) {
            try {

                JSONArray cityJson = new JSONArray(reponse);
                Log.i(TAG, "handleCityResponse: "+reponse);

                for (int i = 0; i < cityJson.length(); i++) {

                    JSONObject cityObject = cityJson.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    Log.i(TAG, "handleCityResponse: "+cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                   city.save();//存储数据库
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            return false;

    }

    /**
     * 城市数据解析并保存
     * @param reponse
     * @return
     */
    public static boolean handleCountyResponse(String reponse,int cityid) {
        if (!TextUtils.isEmpty(reponse)) {
            try {
                JSONArray countyJson = new JSONArray(reponse);
                for (int i = 0; i < countyJson.length(); i++) {
                    JSONObject countyObject = countyJson.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setId(countyObject.getInt("id"));
                    county.setCityId(cityid);
                    county.save();//存储数据库
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            return false;

    }
}
