package com.example.administrator.wanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.bean.QueryDataBean;

import java.util.List;


public class SearchListAdapter extends BaseQuickAdapter<QueryDataBean.DatasBean, BaseViewHolder> {

    public SearchListAdapter(int layoutResId, @Nullable List<QueryDataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QueryDataBean.DatasBean item) {
        if (!TextUtils.isEmpty(item.title)) {
            helper.setText(R.id.item_search_pager_title, Html.fromHtml(item.title));
        }
        if (item.collect) {
            helper.setImageResource(R.id.item_search_pager_like_iv, R.drawable.icon_like);
        } else {
            helper.setImageResource(R.id.item_search_pager_like_iv, R.drawable.icon_like_article_not_selected);
        }
        if (!TextUtils.isEmpty(item.author)) {
            helper.setText(R.id.item_search_pager_author, item.author);
        }
        if (!TextUtils.isEmpty(item.niceDate)) {
            helper.setText(R.id.item_search_pager_niceDate, item.niceDate);
        }
        helper.addOnClickListener(R.id.item_search_pager_like_iv);
    }
}
