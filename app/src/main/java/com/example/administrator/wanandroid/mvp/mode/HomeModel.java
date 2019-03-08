package com.example.administrator.wanandroid.mvp.mode;

import com.example.administrator.wanandroid.bean.BannerBean;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.FeedArticleListBean;
import com.example.administrator.wanandroid.net.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;

import com.example.administrator.wanandroid.mvp.contract.HomeContract;

/**
 * 负责网络请求
 */

public class HomeModel implements HomeContract.Model {

    @Override
    public Observable<BaseObjectBean<List<BannerBean>>> getBanner() {
        return RetrofitClient.getInstance().getApi().getBanner();
    }

    @Override
    public Observable<BaseObjectBean<FeedArticleListBean>> getFeedArticleList(int num) {
        return RetrofitClient.getInstance().getApi().getFeedArticleList(num);
    }

    @Override
    public Observable<BaseObjectBean> addCollectArticle(int id) {
        return RetrofitClient.getInstance().getApi().addCollectArticle(id);
    }

    @Override
    public Observable<BaseObjectBean> cancelCollectArticle(int id) {
        return RetrofitClient.getInstance().getApi().cancelCollectArticle(id);
    }
}
