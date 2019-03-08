package com.example.administrator.wanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.bean.ProjectListBean;
import com.example.administrator.wanandroid.bean.ProjectTreeBean;
import com.youth.banner.loader.ImageLoader;

import java.util.List;


public class ProjectTreeAdapter extends BaseQuickAdapter<ProjectListBean.DatasBean, BaseViewHolder> {


    public ProjectTreeAdapter(@Nullable List<ProjectListBean.DatasBean> data) {
        super(R.layout.item_project_tree, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectListBean.DatasBean item) {
        if (!TextUtils.isEmpty(item.envelopePic)) {
            Glide.with(mContext).load(item.envelopePic).into((ImageView) helper.getView(R.id.item_project_list_iv));
        }
        if (!TextUtils.isEmpty(item.title)) {
            helper.setText(R.id.item_project_list_title_tv, item.title);
        }
        if (!TextUtils.isEmpty(item.desc)) {
            helper.setText(R.id.item_project_list_content_tv, item.desc);
        }
        if (!TextUtils.isEmpty(item.niceDate)) {
            helper.setText(R.id.item_project_list_time_tv, item.niceDate);
        }
        if (!TextUtils.isEmpty(item.author)) {
            helper.setText(R.id.item_project_list_author_tv, item.author);
        }
        if (!TextUtils.isEmpty(item.apkLink)) {
            helper.getView(R.id.item_project_list_install_tv).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.item_project_list_install_tv).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.item_project_list_install_tv);
    }
}
