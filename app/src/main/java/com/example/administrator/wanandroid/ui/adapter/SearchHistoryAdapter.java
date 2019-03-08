package com.example.administrator.wanandroid.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.util.CommonUtils;

import java.util.List;

public class SearchHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SearchHistoryAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_search_history_tv, item);
        helper.setTextColor(R.id.item_search_history_tv, CommonUtils.randomColor());
        helper.addOnClickListener(R.id.item_search_history_tv);
    }
}
