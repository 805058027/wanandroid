package com.example.administrator.wanandroid.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseMvpFragment;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.ProjectTreeBean;
import com.example.administrator.wanandroid.mvp.contract.ProjectContract;
import com.example.administrator.wanandroid.mvp.presenter.ProjectPresenter;
import com.example.administrator.wanandroid.ui.adapter.MyPagerAdapter;
import com.example.administrator.wanandroid.util.GifDialogShow;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 工程
 */

public class ProjectFragment extends BaseMvpFragment<ProjectPresenter> implements ProjectContract.View {


    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.vp)
    ViewPager mVp;
    private MyPagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public static ProjectFragment getInstance() {
        ProjectFragment mFragment = new ProjectFragment();
        return mFragment;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new ProjectPresenter();
        mPresenter.attachView(this);
        mPresenter.getProjectTree();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public void showProjectTree(BaseObjectBean<List<ProjectTreeBean>> mTreeBean) {
        InitTabLayout(mTreeBean.getData());
    }

    private void InitTabLayout(final List<ProjectTreeBean> mBean) {
        for (int i = 0; i < mBean.size(); i++) {
            mFragments.add(ProjectListFragment.getInstance(mBean.get(i).id));
        }
        mVp.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mBean == null ? 0 : mBean.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mBean.get(position).name;
            }
        });
        mTabLayout.setViewPager(mVp);
        mVp.setCurrentItem(0);
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
