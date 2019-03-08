package com.example.administrator.wanandroid.mvp.contract;

import com.example.administrator.wanandroid.base.BaseView;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.QueryDataBean;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2019/3/7.
 */

public interface SearchListContract {

    interface Model {
        Observable<BaseObjectBean<QueryDataBean>> queryData(int page, String key);
    }

    interface View extends BaseView {

        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void showQueryData(QueryDataBean queryDataBean,boolean isLoadMore);
    }

    interface Presenter {
        void queryData(String key);

        void loadMore(String key);
    }

}
