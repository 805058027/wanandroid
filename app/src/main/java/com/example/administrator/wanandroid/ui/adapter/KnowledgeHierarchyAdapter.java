package com.example.administrator.wanandroid.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.bean.TreeBean;
import com.example.administrator.wanandroid.util.CommonUtils;

import java.util.List;

/**
 * Created by Administrator on 2019/3/4.
 */

public class KnowledgeHierarchyAdapter extends BaseQuickAdapter<TreeBean, BaseViewHolder> {

    public KnowledgeHierarchyAdapter(@Nullable List<TreeBean> data) {
        super(R.layout.item_know_ledge_hierarchy, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TreeBean item) {
        if (item.name == null) {
            return;
        }
        helper.setText(R.id.item_knowledge_hierarchy_title, item.name);
        helper.setTextColor(R.id.item_knowledge_hierarchy_title, CommonUtils.randomColor());
        if (item.children == null) {
            return;
        }
        StringBuilder content = new StringBuilder();
        for (TreeBean.ChildrenBean data : item.children) {
            content.append(data.name).append("   ");
        }
        helper.setText(R.id.item_knowledge_hierarchy_content, content.toString());
    }
}
