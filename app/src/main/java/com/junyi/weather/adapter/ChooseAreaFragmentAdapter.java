package com.junyi.weather.adapter;/**
 * Created by sunxiulei on 2018/3/5.
 */

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.junyi.weather.R;

import java.util.List;

/**
 * 城市recycleviewApapter
 *
 * @author sunxiulei
 * @date 2018/3/5
 */
public class ChooseAreaFragmentAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public ChooseAreaFragmentAdapter(@Nullable List<String> data) {
        super(R.layout.choose_area_recycle_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_cari_address_name,item);
    }




}
