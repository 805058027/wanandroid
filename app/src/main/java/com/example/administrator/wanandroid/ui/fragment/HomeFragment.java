package com.example.administrator.wanandroid.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseMvpFragment;
import com.example.administrator.wanandroid.bean.BannerBean;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.FeedArticleListBean;
import com.example.administrator.wanandroid.mvp.contract.HomeContract;
import com.example.administrator.wanandroid.mvp.presenter.HomePresenter;
import com.example.administrator.wanandroid.ui.activity.LoginActivity;
import com.example.administrator.wanandroid.ui.activity.WebActivity;
import com.example.administrator.wanandroid.ui.adapter.ArticleListAdapter;
import com.example.administrator.wanandroid.util.GifDialogShow;
import com.example.administrator.wanandroid.util.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 */

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.main_pager_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshLayout;
    private String vcType;

    private Banner mBanner;
    private ArticleListAdapter mAdapter;
    private List<FeedArticleListBean.DatasBean> mFeedArticleDataList;
    private List<String> mBannerTitleList;
    private List<String> mBannerUrlList;

    public static HomeFragment getInstance(String vcType) {
        HomeFragment mFragment = new HomeFragment();
        mFragment.vcType = vcType;
        return mFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        setRefresh();
        initRecyclerView();
        mPresenter.getHomeData();
    }

    private void setRefresh() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.Refresh();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mPresenter.loadMore();
            }
        });
    }

    private void initRecyclerView() {
        mFeedArticleDataList = new ArrayList<>();
        mAdapter = new ArticleListAdapter(R.layout.item_search_pager, mFeedArticleDataList);
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mAdapter.setOnItemChildClickListener(mOnItemChildClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        //add head banner
        LinearLayout mHeaderGroup = ((LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.head_banner, null));
        mBanner = mHeaderGroup.findViewById(R.id.head_banner);
        mHeaderGroup.removeView(mBanner);
        mAdapter.addHeaderView(mBanner);
        mRecyclerView.setAdapter(mAdapter);
    }

    BaseQuickAdapter.OnItemClickListener mOnItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("link", mAdapter.getData().get(position).link);
            intent.putExtra("title", mAdapter.getData().get(position).title);
            startActivity(intent);
        }
    };

    BaseQuickAdapter.OnItemChildClickListener mOnItemChildClickListener = new BaseQuickAdapter.OnItemChildClickListener() {
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            switch (view.getId()) {
                case R.id.item_search_pager_like_iv:
                    //收藏监听
                    if (mFeedArticleDataList.get(position).collect) {
                        mPresenter.cancelCollectArticle(mFeedArticleDataList.get(position).id, position);
                    } else {
                        mPresenter.addCollectArticle(mFeedArticleDataList.get(position).id, position);
                    }
                    break;
            }
        }
    };

    @Override
    public void showBannerData(List<BannerBean> bannerDataList) {
        mBannerTitleList = new ArrayList<>();
        List<String> bannerImageList = new ArrayList<>();
        mBannerUrlList = new ArrayList<>();
        for (BannerBean bannerData : bannerDataList) {
            mBannerTitleList.add(bannerData.title);
            bannerImageList.add(bannerData.imagePath);
            mBannerUrlList.add(bannerData.url);
        }
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(bannerImageList);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(mBannerTitleList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(bannerDataList.size() * 400);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @Override
    public void showArticleList(FeedArticleListBean feedArticleListData, boolean isRefresh) {
        if (isRefresh) {//下拉刷新
            mFeedArticleDataList = feedArticleListData.datas;
            mAdapter.replaceData(feedArticleListData.datas);
        } else {//加载更多
            mFeedArticleDataList.addAll(feedArticleListData.datas);
            mAdapter.addData(feedArticleListData.datas);
        }
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }

    @Override
    public void addCollectArticle(BaseObjectBean bean, int position) {
        if (bean.getErrorMsg().equals("请先登录！")) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        } else {
            mFeedArticleDataList.get(position).collect = true;
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void cancelAddCollectArticle(BaseObjectBean bean, int position) {
        if (bean.getErrorMsg().equals("请先登录！")) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        } else {
            mFeedArticleDataList.get(position).collect = false;
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading() {
        GifDialogShow.showRoundProcessDialog(getActivity());
    }

    @Override
    public void hideLoading() {
        GifDialogShow.closeDialog();
    }

    @Override
    public void onError(Throwable throwable) {
        GifDialogShow.closeDialog();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
    }
}
