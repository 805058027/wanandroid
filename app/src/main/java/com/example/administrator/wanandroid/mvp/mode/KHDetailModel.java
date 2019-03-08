package com.example.administrator.wanandroid.mvp.mode;

import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.TreeListBean;
import com.example.administrator.wanandroid.mvp.contract.KHDetailContract;
import com.example.administrator.wanandroid.net.RetrofitClient;

import io.reactivex.Observable;


public class KHDetailModel implements KHDetailContract.Model {
    @Override
    public Observable<BaseObjectBean<TreeListBean>> getTreeList(int page, int cid) {
        return RetrofitClient.getInstance().getApi().getTreeList(page, cid);
    }
}
