package com.example.administrator.wanandroid.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseMvpActivity;
import com.example.administrator.wanandroid.ui.fragment.HomeFragment;
import com.example.administrator.wanandroid.ui.fragment.KnowledgeHierarchyFragment;
import com.example.administrator.wanandroid.ui.fragment.NavigationFragment;
import com.example.administrator.wanandroid.ui.fragment.ProjectFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseMvpActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;
    @BindView(R.id.fragment_group)
    FrameLayout mFragmentGroup;
    @BindView(R.id.drawable)
    ImageView mDrawable;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.drawer_menu)
    DrawerLayout mDrawerMenu;
    private ArrayList<Fragment> mFragments;
    private int mLastFgIndex;
    private long mPressedTime = 0;
    private SearchFragment mSearchFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.drawable, R.id.search})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.drawable:
                mDrawerMenu.openDrawer(Gravity.LEFT);
                break;
            case R.id.search:
                if (mSearchFragment == null) {
                    mSearchFragment = new SearchFragment();
                }
                if (mSearchFragment.isAdded()) {
                    mSearchFragment.dismiss();
                }
                mSearchFragment.show(getSupportFragmentManager(), "SearchDialogFragment");
                break;
        }
    }

    @Override
    public void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.getInstance(""));
        mFragments.add(KnowledgeHierarchyFragment.getInstance());
        mFragments.add(NavigationFragment.getInstance());
        mFragments.add(ProjectFragment.getInstance());
        initBottomNavigationView();
        mDrawerMenu.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.setClickable(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void initBottomNavigationView() {
        switchFragment(0);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_tab1:
                        mTitle.setText("首页");
                        switchFragment(0);
                        return true;
                    case R.id.item_tab2:
                        mTitle.setText("知识体系");
                        switchFragment(1);
                        return true;
                    case R.id.item_tab3:
                        mTitle.setText("导航");
                        switchFragment(2);
                        return true;
                    case R.id.item_tab4:
                        mTitle.setText("项目");
                        switchFragment(3);
                        return true;
                }
                return false;
            }
        });
    }


    /**
     * 切换fragment
     *
     * @param position 要显示的fragment的下标
     */
    private void switchFragment(int position) {
        if (position >= mFragments.size()) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment targetFg = mFragments.get(position);
        Fragment lastFg = mFragments.get(mLastFgIndex);
        mLastFgIndex = position;
        ft.hide(lastFg);
        if (!targetFg.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(targetFg).commit();
            ft.add(R.id.fragment_group, targetFg);
        }
        ft.show(targetFg);
        ft.commitAllowingStateLoss();
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

    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {
            this.finish();
            System.exit(0);
        }
    }
}
