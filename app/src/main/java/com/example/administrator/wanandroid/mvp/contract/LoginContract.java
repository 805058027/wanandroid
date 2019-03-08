package com.example.administrator.wanandroid.mvp.contract;

import com.example.administrator.wanandroid.base.BaseView;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.LoginBean;

import io.reactivex.Observable;


public interface LoginContract {


    interface Model{
        Observable<BaseObjectBean<LoginBean>> login(String username, String password);
    }

    interface View extends BaseView{
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void loginInfo(BaseObjectBean<LoginBean> loginBean);
    }

    interface Presenter{
        void login(String username,String password);
    }

}
