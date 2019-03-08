package com.example.administrator.wanandroid.ui.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseMvpFragment;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.NaviBean;
import com.example.administrator.wanandroid.mvp.contract.NavigationContract;
import com.example.administrator.wanandroid.mvp.presenter.NavigationPresenter;
import com.example.administrator.wanandroid.ui.activity.WebActivity;
import com.example.administrator.wanandroid.ui.adapter.NavigationAdapter;
import com.example.administrator.wanandroid.util.GifDialogShow;

import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * 导航
 */

public class NavigationFragment extends BaseMvpFragment<NavigationPresenter> implements NavigationContract.View {

    @BindView(R.id.navigation_tab_layout)
    VerticalTabLayout mNavigationTabLayout;
    @BindView(R.id.navigation_divider)
    View mNavigationDivider;
    @BindView(R.id.navigation_RecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)
    LinearLayout mNormalView;

    private LinearLayoutManager mManager;
    private boolean needScroll;
    private int index;
    private boolean isClickTab;

    public static NavigationFragment getInstance() {
        NavigationFragment mFragment = new NavigationFragment();
        return mFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new NavigationPresenter();
        mPresenter.attachView(this);
        mPresenter.getNavi();
    }

    @Override
    public void showNavi(final BaseObjectBean<List<NaviBean>> navigationDataList) {
        mNavigationTabLayout.setTabAdapter(new TabAdapter() {
            @Override
            public int getCount() {
                return navigationDataList.getData() == null ? 0 : navigationDataList.getData().size();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new TabView.TabTitle.Builder()
                        .setContent(navigationDataList.getData().get(position).name)
                        .setTextColor(ContextCompat.getColor(getActivity(), R.color.shallow_green),
                                ContextCompat.getColor(getActivity(), R.color.shallow_grey))
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return -1;
            }
        });
        initRecyclerView(navigationDataList.getData());
        leftRightLinkage();
    }

    private void initRecyclerView(List<NaviBean> navigationDataList) {
        NavigationAdapter adapter = new NavigationAdapter(R.layout.item_navigation, navigationDataList);
        mRecyclerView.setAdapter(adapter);
        mManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setHasFixedSize(true);
    }

    private void leftRightLinkage() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (needScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    scrollRecyclerView();
                }
                rightLinkageLeft(newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (needScroll) {
                    scrollRecyclerView();
                }
            }
        });

        mNavigationTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tabView, int i) {
                isClickTab = true;
                selectTag(i);
            }

            @Override
            public void onTabReselected(TabView tabView, int i) {
            }
        });
    }

    private void scrollRecyclerView() {
        needScroll = false;
        int indexDistance = index - mManager.findFirstVisibleItemPosition();
        if (indexDistance >= 0 && indexDistance < mRecyclerView.getChildCount()) {
            int top = mRecyclerView.getChildAt(indexDistance).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        }
    }

    private void rightLinkageLeft(int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (isClickTab) {
                isClickTab = false;
                return;
            }
            int firstPosition = mManager.findFirstVisibleItemPosition();
            if (index != firstPosition) {
                index = firstPosition;
                setChecked(index);
            }
        }
    }

    private void selectTag(int i) {
        index = i;
        mRecyclerView.stopScroll();
        smoothScrollToPosition(i);
    }

    private void setChecked(int position) {
        if (isClickTab) {
            isClickTab = false;
        } else {
            if (mNavigationTabLayout == null) {
                return;
            }
            mNavigationTabLayout.setTabSelected(index);
        }
        index = position;
    }

    private void smoothScrollToPosition(int currentPosition) {
        int firstPosition = mManager.findFirstVisibleItemPosition();
        int lastPosition = mManager.findLastVisibleItemPosition();
        if (currentPosition <= firstPosition) {
            mRecyclerView.smoothScrollToPosition(currentPosition);
        } else if (currentPosition <= lastPosition) {
            int top = mRecyclerView.getChildAt(currentPosition - firstPosition).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        } else {
            mRecyclerView.smoothScrollToPosition(currentPosition);
            needScroll = true;
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
    }

}
