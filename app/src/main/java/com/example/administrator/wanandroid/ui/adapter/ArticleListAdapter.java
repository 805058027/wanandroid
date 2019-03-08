package com.example.administrator.wanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.bean.FeedArticleListBean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/1.
 */

public class ArticleListAdapter extends BaseQuickAdapter<FeedArticleListBean.DatasBean, BaseViewHolder> {

    public ArticleListAdapter(int layoutResId, @Nullable List<FeedArticleListBean.DatasBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, FeedArticleListBean.DatasBean article) {
        if (!TextUtils.isEmpty(article.title)) {
            helper.setText(R.id.item_search_pager_title, Html.fromHtml(article.title));
        }
        if (article.collect) {
            helper.setImageResource(R.id.item_search_pager_like_iv, R.drawable.icon_like);
        } else {
            helper.setImageResource(R.id.item_search_pager_like_iv, R.drawable.icon_like_article_not_selected);
        }
        if (!TextUtils.isEmpty(article.author)) {
            helper.setText(R.id.item_search_pager_author, article.author);
        }
        if (!TextUtils.isEmpty(article.niceDate)) {
            helper.setText(R.id.item_search_pager_niceDate, article.niceDate);
        }
        helper.addOnClickListener(R.id.item_search_pager_like_iv);
    }
}
