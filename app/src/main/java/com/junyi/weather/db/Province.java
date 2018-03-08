package com.junyi.weather.db;/**
 * Created by sunxiulei on 2018/3/1.
 */

import org.litepal.crud.DataSupport;

/**
 * 省份信息实体类
 *
 * @author sunxiulei
 * @date 2018/3/1
 */
public class Province extends DataSupport {
    private int id;
    private String provincename;
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provincename;
    }

    public void setProvinceName(String provinceName) {
        this.provincename = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
