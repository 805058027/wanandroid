package com.example.administrator.wanandroid.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseMvpActivity;
import com.example.administrator.wanandroid.bean.QueryDataBean;
import com.example.administrator.wanandroid.mvp.contract.SearchListContract;
import com.example.administrator.wanandroid.mvp.presenter.SearchListPresenter;
import com.example.administrator.wanandroid.ui.adapter.SearchListAdapter;
import com.example.administrator.wanandroid.util.CommonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import butterknife.BindView;

public class SearchListActivity extends BaseMvpActivity<SearchListPresenter> implements SearchListContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    private String mKey;
    private SearchListAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_list;
    }

    @Override
    public void initView() {
        mPresenter = new SearchListPresenter();
        mPresenter.attachView(this);
        mKey = getIntent().getStringExtra("key");
        setRefresh();
        setRecycleView();
        mPresenter.queryData(mKey);
    }


    private void setRefresh() {
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mPresenter.loadMore(mKey);
                mRefreshLayout.finishLoadMore(1000);
            }
        });
    }

    private void setRecycleView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchListAdapter(R.layout.item_search_pager, null);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SearchListActivity.this, WebActivity.class);
                intent.putExtra("link", mAdapter.getData().get(position).link);
                intent.putExtra("title", mAdapter.getData().get(position).title);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showQueryData(QueryDataBean queryDataBean, boolean isLoadMore) {
        if (!isLoadMore) {
            mAdapter.replaceData(queryDataBean.datas);
        } else {
            if (queryDataBean.datas.size() > 0) {
                mAdapter.addData(queryDataBean.datas);
            } else {
                CommonUtils.showMessage(this, "暂无更多数据");
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
