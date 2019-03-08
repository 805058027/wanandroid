package com.example.administrator.wanandroid.mvp.presenter;

import android.text.TextUtils;

import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.LoginBean;
import com.example.administrator.wanandroid.mvp.contract.LoginContract;
import com.example.administrator.wanandroid.mvp.mode.LoginModel;
import com.example.administrator.wanandroid.net.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2019/3/1.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private LoginContract.Model model;

    public LoginPresenter() {
        model = new LoginModel();
    }

    @Override
    public void login(String username, String password) {
        if (!isViewAttached()) {
            return;
        }
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            mView.showSnackBar("密码或用户名输入错误");
            return;
        }
        mView.showLoading();
        model.login(username, password)
                .compose(RxScheduler.<BaseObjectBean<LoginBean>>Obs_io_main())
                .as(mView.<BaseObjectBean<LoginBean>>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean<LoginBean>>() {
                    @Override
                    public void accept(BaseObjectBean<LoginBean> loginBeanBaseObjectBean) throws Exception {
                        if (loginBeanBaseObjectBean.getErrorCode() == 0) {
                            mView.loginInfo(loginBeanBaseObjectBean);
                        } else {
                            mView.showSnackBar(loginBeanBaseObjectBean.getErrorMsg());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }
}
