package com.example.administrator.wanandroid.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseMvpFragment;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.TreeBean;
import com.example.administrator.wanandroid.mvp.contract.KnowledgeHierarchyContract;
import com.example.administrator.wanandroid.mvp.presenter.KnowledgeHierarchyPresenter;
import com.example.administrator.wanandroid.ui.activity.KnowledgeHierarchyDetailActivity;
import com.example.administrator.wanandroid.ui.adapter.KnowledgeHierarchyAdapter;
import com.example.administrator.wanandroid.util.GifDialogShow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * 知识体系
 */

public class KnowledgeHierarchyFragment extends BaseMvpFragment<KnowledgeHierarchyPresenter> implements KnowledgeHierarchyContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    private KnowledgeHierarchyAdapter mAdapter;

    public static KnowledgeHierarchyFragment getInstance() {
        KnowledgeHierarchyFragment mFragment = new KnowledgeHierarchyFragment();
        return mFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_know_ledge_hierarchy;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new KnowledgeHierarchyPresenter();
        mPresenter.attachView(this);
        setRefresh();
        mPresenter.getTreeDate();
    }

    private void setRefresh() {
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getTreeDate();
            }
        });
    }

    @Override
    public void showTree(final BaseObjectBean<List<TreeBean>> tree) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new KnowledgeHierarchyAdapter(tree.getData());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), KnowledgeHierarchyDetailActivity.class);
                intent.putExtra("tree", (Serializable) tree.getData().get(position).children);
                intent.putExtra("title", tree.getData().get(position).name);
                startActivity(intent);
            }
        });
    }


    @Override
    public void showLoading() {
        GifDialogShow.showRoundProcessDialog(getActivity());
    }

    @Override
    public void hideLoading() {
        GifDialogShow.closeDialog();
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onError(Throwable throwable) {
        GifDialogShow.closeDialog();
        mRefreshLayout.finishRefresh();
    }

}
