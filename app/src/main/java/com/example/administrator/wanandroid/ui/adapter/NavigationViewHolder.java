package com.example.administrator.wanandroid.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.wanandroid.R;
import com.zhy.view.flowlayout.FlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/3/4.
 */

public class NavigationViewHolder extends BaseViewHolder {

    @BindView(R.id.item_navigation_tv)
    TextView mTitle;
    @BindView(R.id.item_navigation_flow_layout)
    FlowLayout mFlowLayout;

    public NavigationViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
