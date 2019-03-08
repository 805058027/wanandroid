package com.example.administrator.wanandroid.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseMvpFragment;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.ProjectListBean;
import com.example.administrator.wanandroid.mvp.contract.ProjectListContract;
import com.example.administrator.wanandroid.mvp.presenter.ProjectListPresenter;
import com.example.administrator.wanandroid.ui.activity.WebActivity;
import com.example.administrator.wanandroid.ui.adapter.ProjectTreeAdapter;
import com.example.administrator.wanandroid.util.CommonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目列表
 */

public class ProjectListFragment extends BaseMvpFragment<ProjectListPresenter> implements ProjectListContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    private int cid;
    private ProjectTreeAdapter mAdapter;
    private List<ProjectListBean.DatasBean> mBean = new ArrayList<>();

    public static ProjectListFragment getInstance(int cid) {
        ProjectListFragment mFragment = new ProjectListFragment();
        mFragment.cid = cid;
        return mFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_tree;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new ProjectListPresenter();
        mPresenter.attachView(this);
        setRefresh();
        InitRecycleView();
        mPresenter.getProjectList(cid);
    }


    private void setRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh(cid);
                mRefreshLayout.finishRefresh(1000);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore(cid);
                mRefreshLayout.finishLoadMore(1000);
            }
        });
    }

    private void InitRecycleView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ProjectTreeAdapter(mBean);
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
    public void showProjectList(BaseObjectBean<ProjectListBean> mListBean, boolean isRefresh) {
        if (isRefresh) {
            mBean = mListBean.getData().datas;
            mAdapter.replaceData(mBean);
        } else {
            if (mListBean.getData().datas.size() > 0) {
                mAdapter.addData(mListBean.getData().datas);
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
        System.out.println("w---" + throwable.getLocalizedMessage());
    }

}
