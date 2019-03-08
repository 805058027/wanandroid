package com.example.administrator.wanandroid.mvp.mode;

import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.NaviBean;
import com.example.administrator.wanandroid.mvp.contract.NavigationContract;
import com.example.administrator.wanandroid.net.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;


public class NavigationModel implements NavigationContract.Model {

    @Override
    public Observable<BaseObjectBean<List<NaviBean>>> getNavi() {
        return RetrofitClient.getInstance().getApi().getNavi();
    }
}
