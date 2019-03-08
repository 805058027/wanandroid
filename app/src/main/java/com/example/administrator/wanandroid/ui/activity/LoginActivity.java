package com.example.administrator.wanandroid.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.base.BaseMvpActivity;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.LoginBean;
import com.example.administrator.wanandroid.mvp.contract.LoginContract;
import com.example.administrator.wanandroid.mvp.presenter.LoginPresenter;
import com.example.administrator.wanandroid.util.GifDialogShow;
import com.example.administrator.wanandroid.util.PreferencesUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/3/1.
 */

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.login_account_edit)
    EditText mLoginAccountEdit;
    @BindView(R.id.login_password_edit)
    EditText mLoginPasswordEdit;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.login_register_btn)
    Button mLoginRegisterBtn;

    private String mUserName, mPassWord;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.login_btn, R.id.login_register_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                mUserName = mLoginAccountEdit.getText().toString();
                mPassWord = mLoginPasswordEdit.getText().toString();
                mPresenter.login(mUserName, mPassWord);
                break;
            case R.id.login_register_btn:

                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void loginInfo(BaseObjectBean<LoginBean> loginBean) {
       /* PreferencesUtil preferencesUtil = new PreferencesUtil("login_info");
        preferencesUtil.put("mUserName", mUserName);
        preferencesUtil.put("mPassWord", mPassWord);*/
        finish();
    }

    @Override
    public void showLoading() {
        GifDialogShow.showRoundProcessDialog(this);
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
