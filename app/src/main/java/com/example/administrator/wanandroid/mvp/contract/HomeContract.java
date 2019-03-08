package com.example.administrator.wanandroid.mvp.contract;

import com.example.administrator.wanandroid.base.BaseView;
import com.example.administrator.wanandroid.bean.BannerBean;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.FeedArticleListBean;

import java.util.List;

import io.reactivex.Observable;


public interface HomeContract {

    interface Model {
        Observable<BaseObjectBean<List<BannerBean>>> getBanner();

        Observable<BaseObjectBean<FeedArticleListBean>> getFeedArticleList(int num);

        Observable<BaseObjectBean> addCollectArticle(int id);

        Observable<BaseObjectBean> cancelCollectArticle(int id);
    }

    interface View extends BaseView {

        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void showBannerData(List<BannerBean> bean);

        void showArticleList(FeedArticleListBean feedArticleListData, boolean isRefresh);

        void addCollectArticle(BaseObjectBean bean,int position);

        void cancelAddCollectArticle(BaseObjectBean bean,int position);
    }

    interface Presenter {
        void getHomeData();

        void loadMore();

        void Refresh();

        void addCollectArticle(int id,int position);

        void cancelCollectArticle(int id,int position);

    }

}
