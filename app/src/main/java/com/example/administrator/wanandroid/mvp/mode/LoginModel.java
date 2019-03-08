package com.example.administrator.wanandroid.mvp.mode;

import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.LoginBean;
import com.example.administrator.wanandroid.mvp.contract.LoginContract;
import com.example.administrator.wanandroid.net.RetrofitClient;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2019/3/1.
 */

public class LoginModel implements LoginContract.Model {

    @Override
    public Observable<BaseObjectBean<LoginBean>> login(String username, String password) {
        return RetrofitClient.getInstance().getApi().Login(username, password);
    }
}
