package com.example.administrator.wanandroid.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseMvpFragment;
import com.example.administrator.wanandroid.bean.TreeListBean;
import com.example.administrator.wanandroid.mvp.contract.KHDetailContract;
import com.example.administrator.wanandroid.mvp.presenter.KHDetailPresenter;
import com.example.administrator.wanandroid.ui.activity.WebActivity;
import com.example.administrator.wanandroid.ui.adapter.KHDetailAdapter;
import com.example.administrator.wanandroid.util.CommonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;


public class KnowledgeHierarchyDetailFragment extends BaseMvpFragment<KHDetailPresenter> implements KHDetailContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private int id;
    private KHDetailAdapter mAdapter;

    public static KnowledgeHierarchyDetailFragment getInstance(int id) {
        KnowledgeHierarchyDetailFragment mFragment = new KnowledgeHierarchyDetailFragment();
        mFragment.id = id;
        return mFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_know_ledge_hierarchy_detail;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new KHDetailPresenter();
        mPresenter.attachView(this);
        setRefresh();
        initRecyclerView();
        mPresenter.getTreeList(id);
    }

    private void setRefresh() {
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getTreeList(id);
                mRefreshLayout.finishRefresh(1000);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore(id);
                mRefreshLayout.finishLoadMore(1000);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new KHDetailAdapter(R.layout.item_kh_detail, null);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("link", mAdapter.getData().get(position).link);
                intent.putExtra("title", mAdapter.getData().get(position).title);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showTreeList(TreeListBean bean, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.replaceData(bean.datas);
        } else {
            if (bean.datas.size() > 0) {
                mAdapter.addData(bean.datas);
            } else {
                CommonUtils.showMessage(getActivity(), "暂无更多数据");
            }
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onError(Throwable throwable) {

    }


}
