package com.example.administrator.wanandroid.ui.activity;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseMvpActivity;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.OnClick;


public class WebActivity extends BaseMvpActivity {

    @BindView(R.id.web_back)
    ImageView mWebBack;
    @BindView(R.id.web_frame_layout)
    FrameLayout mWebFrameLayout;
    @BindView(R.id.web_title)
    TextView mWebTitle;
    private AgentWeb mAgentWeb;
    private String articleLink;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @OnClick({R.id.web_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.web_back:
                finish();
                break;
        }
    }

    @Override
    public void initView() {
        articleLink = getIntent().getStringExtra("link");
        mWebTitle.setText(getIntent().getStringExtra("title"));
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebFrameLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(R.layout.webview_error_view, -1)
                .createAgentWeb()
                .ready()
                .go(articleLink);
        WebView mWebView = mAgentWeb.getWebCreator().getWebView();
        WebSettings mSettings = mWebView.getSettings();
        mSettings.setAppCacheEnabled(true);
        mSettings.setDomStorageEnabled(true);
        mSettings.setDatabaseEnabled(true);
        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);
        //不显示缩放按钮
        mSettings.setDisplayZoomControls(false);
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        mSettings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        mSettings.setLoadWithOverviewMode(true);
        //自适应屏幕
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
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
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
