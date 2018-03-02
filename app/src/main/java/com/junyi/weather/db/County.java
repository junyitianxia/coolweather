package com.junyi.weather.db;/**
 * Created by sunxiulei on 2018/3/2.
 */

import org.litepal.crud.DataSupport;

/**
 * 县信息实体类
 *
 * @author sunxiulei
 * @date 2018/3/2
 */
public class County extends DataSupport {
    private int id;
    private String countyName;
    private String weatherId;//天气id
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
