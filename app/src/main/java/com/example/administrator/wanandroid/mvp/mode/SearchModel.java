package com.example.administrator.wanandroid.mvp.mode;

import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.HotKeyBean;
import com.example.administrator.wanandroid.mvp.contract.SearchContract;
import com.example.administrator.wanandroid.net.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2019/3/7.
 */

public class SearchModel implements SearchContract.Model {
    @Override
    public Observable<BaseObjectBean<List<HotKeyBean>>> getHotKey() {
        return RetrofitClient.getInstance().getApi().getHotKey();
    }
}
