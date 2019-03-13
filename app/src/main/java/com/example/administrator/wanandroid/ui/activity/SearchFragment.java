package com.example.administrator.wanandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseMvpDialogFragment;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.HotKeyBean;
import com.example.administrator.wanandroid.mvp.contract.SearchContract;
import com.example.administrator.wanandroid.mvp.presenter.SearchPresenter;
import com.example.administrator.wanandroid.ui.adapter.SearchHistoryAdapter;
import com.example.administrator.wanandroid.util.PreferencesUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchFragment extends BaseMvpDialogFragment<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.search_back)
    ImageView mSearchBack;
    @BindView(R.id.search_ensure)
    TextView mSearchEnsure;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.top_search_flow_layout)
    TagFlowLayout mTopSearchFlowLayout;
    @BindView(R.id.search_history_clear_all_tv)
    TextView mSearchHistoryClearAllTv;
    @BindView(R.id.search_history_null_tint_tv)
    TextView mSearchHistoryNullTintTv;
    @BindView(R.id.search_history_rv)
    RecyclerView mSearchHistoryRv;
    //本地存储搜索历史
    private PreferencesUtil preferencesUtil = new PreferencesUtil("search_history");
    private Set<String> searchHistorySet;
    private List<String> searchHistoryList;
    private SearchHistoryAdapter mAdapter;
    private String key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
    }

    @OnClick({R.id.search_back, R.id.search_ensure, R.id.search_history_clear_all_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back:
                dismiss();
                break;
            case R.id.search_ensure:
                key = mSearchEdit.getText().toString().trim();
                if (TextUtils.isEmpty(key)) {
                    Toast.makeText(getActivity(), "请输入检索字段", Toast.LENGTH_SHORT).show();
                    return;
                }
                searchHistorySet.add(key);
                preferencesUtil.put("history", searchHistorySet);
                mSearchEdit.setText("");
                //todo:实时刷新历史列表失败
                if (isRepeat()) {
                    mAdapter.addData(key);
                }
                jumpSearchHistoryList(key);
                break;
            case R.id.search_history_clear_all_tv:
                preferencesUtil.remove("history");
                mSearchHistoryNullTintTv.setVisibility(View.VISIBLE);
                mAdapter.replaceData(new ArrayList<String>());
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new SearchPresenter();
        mPresenter.attachView(this);
        mPresenter.getHotKey();
        initHistoryList();
    }

    @Override
    public void showHotKey(BaseObjectBean<List<HotKeyBean>> bean) {
        final List<HotKeyBean> hotKeyBeans = bean.getData();
        mTopSearchFlowLayout.setAdapter(new TagAdapter<HotKeyBean>(hotKeyBeans) {
            @Override
            public View getView(FlowLayout parent, int position, HotKeyBean bean) {
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv,
                        parent, false);
                String name = bean.name;
                tv.setText(name);
                mTopSearchFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        jumpSearchHistoryList(hotKeyBeans.get(position).name);
                        return true;
                    }
                });
                return tv;
            }
        });
    }

    private void initHistoryList() {
        searchHistorySet = preferencesUtil.getStringSet("history");
        if (searchHistorySet == null) {
            searchHistorySet = new HashSet<>();
            mSearchHistoryNullTintTv.setVisibility(View.VISIBLE);
        }
        mSearchHistoryRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchHistoryList = new ArrayList(searchHistorySet);
        mAdapter = new SearchHistoryAdapter(R.layout.item_search_history, searchHistoryList);
        mSearchHistoryRv.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_search_history_tv:
                        jumpSearchHistoryList(mAdapter.getData().get(position));
                        break;
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }

    private void initDialog() {
        Window window = getDialog().getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        //DialogSearch的宽
        int width = (int) (metrics.widthPixels * 0.98);
        assert window != null;
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        //取消过渡动画 , 使DialogSearch的出现更加平滑
        window.setWindowAnimations(R.style.DialogEmptyAnimation);
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

    private void jumpSearchHistoryList(String mKey) {
        Intent intent = new Intent(getActivity(), SearchListActivity.class);
        intent.putExtra("key", mKey);
        startActivity(intent);
    }

    private boolean isRepeat() {
        int index = 0;
        for (String str : searchHistorySet) {
            if (str.equals(key)) {
                index++;
            }
        }
        if (index == 0) {
            return true;
        }
        return false;
    }

}
