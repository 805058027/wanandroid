package com.example.administrator.wanandroid.mvp.mode;

import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.QueryDataBean;
import com.example.administrator.wanandroid.mvp.contract.SearchListContract;
import com.example.administrator.wanandroid.net.RetrofitClient;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2019/3/7.
 */

public class SearchListModel implements SearchListContract.Model {
    @Override
    public Observable<BaseObjectBean<QueryDataBean>> queryData(int page, String key) {
        return RetrofitClient.getInstance().getApi().getQueryData(page, key);
    }
}
