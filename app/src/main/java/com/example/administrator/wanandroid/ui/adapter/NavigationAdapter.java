package com.example.administrator.wanandroid.ui.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.bean.NaviBean;
import com.example.administrator.wanandroid.ui.activity.WebActivity;
import com.example.administrator.wanandroid.util.CommonUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

public class NavigationAdapter extends BaseQuickAdapter<NaviBean, BaseViewHolder> {

    public NavigationAdapter(int layoutResId, @Nullable List<NaviBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final NaviBean item) {
        if (!TextUtils.isEmpty(item.name)) {
            helper.setText(R.id.item_navigation_tv, item.name);
        }
        final TagFlowLayout mTagFlowLayout = helper.getView(R.id.item_navigation_flow_layout);
        List<NaviBean.ArticlesBean> mArticles = item.articles;
        mTagFlowLayout.setAdapter(new TagAdapter<NaviBean.ArticlesBean>(mArticles) {
            @Override
            public View getView(FlowLayout parent, int position, NaviBean.ArticlesBean feedArticleData) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.flow_layout_tv,
                        mTagFlowLayout, false);
                if (feedArticleData == null) {
                    return null;
                }
                String name = feedArticleData.title;
                tv.setPadding(CommonUtils.dp2px(10), CommonUtils.dp2px(10),
                        CommonUtils.dp2px(10), CommonUtils.dp2px(10));
                tv.setText(name);
                tv.setTextColor(CommonUtils.randomColor());
                return tv;
            }
        });
        mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("link", item.articles.get(position).link);
                intent.putExtra("title", item.articles.get(position).title);
                mContext.startActivity(intent);
                return true;
            }
        });
    }
}
