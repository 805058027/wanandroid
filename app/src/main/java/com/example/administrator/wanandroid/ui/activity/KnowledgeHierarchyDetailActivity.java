package com.example.administrator.wanandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseMvpActivity;
import com.example.administrator.wanandroid.bean.TreeBean;
import com.example.administrator.wanandroid.ui.fragment.KnowledgeHierarchyDetailFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class KnowledgeHierarchyDetailActivity extends BaseMvpActivity {


    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title)
    TextView mTitle;
    private List<TreeBean.ChildrenBean> mBean;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_know_ledge_hierarchy_detail;
    }

    @Override
    public void initView() {
        mBean = (List<TreeBean.ChildrenBean>) getIntent().getSerializableExtra("tree");
        mTitle.setText(getIntent().getStringExtra("title"));
        InitTabLayout();
    }

    private void InitTabLayout() {
        for (int i = 0; i < mBean.size(); i++) {
            System.out.println("---" + mBean.get(i).id + "--" + mBean.get(i).name);
            mFragments.add(KnowledgeHierarchyDetailFragment.getInstance(mBean.get(i).id));
        }
        mVp.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

}
