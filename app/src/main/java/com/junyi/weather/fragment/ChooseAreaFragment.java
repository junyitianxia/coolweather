package com.junyi.weather.fragment;/**
 * Created by sunxiulei on 2018/3/2.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.junyi.weather.R;
import com.junyi.weather.adapter.ChooseAreaFragmentAdapter;
import com.junyi.weather.db.City;
import com.junyi.weather.db.County;
import com.junyi.weather.db.Province;
import com.junyi.weather.util.HttpUtils;
import com.junyi.weather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 城市列表选择
 *
 * @author sunxiulei
 * @date 2018/3/2
 */
public class ChooseAreaFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ChooseAreaFragment";
    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;
    private ProgressDialog progressDialog;
    private ImageButton mIbCaBack;
    private TextView mTvCaTitle;
    private RecyclerView mRvCaList;
    private ChooseAreaFragmentAdapter mAdapter;
    /**
     * 省份列表数据
     */
    private ArrayList<Province> provinceList;
    /**
     * 城市列表数据
     */
    private ArrayList<City> cityList;
    /**
     * 县列表数据
     */
    private ArrayList<County> countyList;
    /**
     * 展示数据内容
     */
    private ArrayList<String> dataList = new ArrayList<>();
    /**
     * 选中的级别
     */
    private int currentLevel;
    /**
     * 选中的省份
     */
    private Province selectedProvince;
    /**
     * 选中的城市
     */
    private City selectedCity;
    /**
     * 选中的县
     */
    private County selectedConunty;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Log.i(TAG, "onSimpleItemClick: " + LEVEL_PROVINCE);
        mIbCaBack = (ImageButton) view.findViewById(R.id.ib_ca_back);
        mTvCaTitle = (TextView) view.findViewById(R.id.tv_ca_title);
        mRvCaList = (RecyclerView) view.findViewById(R.id.rv_ca_list);
        mTvCaTitle.setText("选择地区");
        LinearLayoutManager linearlayout = new LinearLayoutManager(getActivity());
        linearlayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRvCaList.setLayoutManager(linearlayout);
        mRvCaList.addOnItemTouchListener(onItemClickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_ca_back:

                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIbCaBack.setOnClickListener(this);
        queryProvince();
    }

    /**
     * 查询省份数据
     */
    private void queryProvince() {
        provinceList = (ArrayList<Province>) DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            mAdapter = new ChooseAreaFragmentAdapter(dataList);
            mRvCaList.setAdapter(mAdapter);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }
    }

    /**
     * 网络请求
     *
     * @param address  地址
     * @param citytype 标识
     */
    private void queryFromServer(String address, final String citytype) {
        showProgressDialog();
        Log.i(TAG, "queryFromServer: address" + address);
        HttpUtils.sendHtttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String reponse = response.body().string();
                Log.i(TAG, "onResponse: " + citytype + reponse);
                boolean result = false;
                if (citytype.equals("province")) {
                    result = Utility.handleProvinceResponse(reponse);
                } else if (citytype.equals("city")) {
                    result = Utility.handleCityResponse(reponse, selectedProvince.getId());
                } else if (citytype.equals("county")) {
                    result = Utility.handleCountyResponse(reponse, selectedCity.getCityCode());
                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if (citytype.equals("province")) {
                                queryProvince();
                            } else if (citytype.equals("city")) {
                                queryCity();
                            } else if (citytype.equals("county")) {
                                queryCounty();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                closeProgressDialog();
                Toast.makeText(getContext(), "加载失败稍后重试", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 设置县数据
     */
    private void queryCounty() {
        countyList = (ArrayList<County>) DataSupport.where("cityid = ?", String.valueOf(selectedCity.getCityCode())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());

            }
            mAdapter.setNewData(dataList);
            currentLevel = LEVEL_COUNTY;
        } else {
            int provinceCode = selectedProvince.getId();
            int cityCode = selectedCity.getCityCode();
            Log.i(TAG, "queryCounty: " + cityCode);
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }
    }

    /**
     * 设置城市数据
     */
    private void queryCity() {
        Log.i(TAG, "queryCity: " + selectedProvince.getId());
        cityList = (ArrayList<City>) DataSupport.where("ProvinceId = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
                Log.i(TAG, "queryCity: " + city.getCityName());
                Log.i(TAG, "queryCity: " + city.getCityCode());
            }
            mAdapter.setNewData(dataList);
            currentLevel = LEVEL_CITY;
        } else {
            int proviceId = selectedProvince.getId();
            String address = "http://guolin.tech/api/china/" + proviceId;
            queryFromServer(address, "city");
        }
    }

    /**
     * 关闭dialog
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 显示加载dialog
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            Log.i(TAG, "onSimpleItemClick: " + LEVEL_PROVINCE);
            if (currentLevel == LEVEL_PROVINCE) {
                Log.i(TAG, "onSimpleItemClick: " + LEVEL_PROVINCE);
                selectedProvince = provinceList.get(position);
                queryCity();
            } else if (currentLevel == LEVEL_CITY) {
                selectedCity = cityList.get(position);
                Log.i(TAG, "onSimpleItemClick: " + selectedCity.getId());
                queryCounty();
            } else if (currentLevel == LEVEL_COUNTY) {

            }
        }
    };
}
