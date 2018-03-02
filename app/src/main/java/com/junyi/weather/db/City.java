package com.junyi.weather.db;/**
 * Created by sunxiulei on 2018/3/2.
 */

import org.litepal.crud.DataSupport;

/**
 * 城市信息实体类
 *
 * @author sunxiulei
 * @date 2018/3/2
 */
public class City extends DataSupport{
    private int id;
    private String cityName;
    private int cityCode;
    private int ProvinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(int provinceId) {
        ProvinceId = provinceId;
    }
}
