package com.example.administrator.wanandroid.mvp.contract;


import com.example.administrator.wanandroid.base.BaseView;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.ProjectListBean;

import java.util.List;

import io.reactivex.Observable;

public interface ProjectListContract {

    interface Model {
        Observable<BaseObjectBean<ProjectListBean>> getProjectList(int page, int cid);
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void showProjectList(BaseObjectBean<ProjectListBean> mListBean, boolean isRefresh);
    }

    interface Presenter {
        void getProjectList(int cid);

        void loadMore(int cid);

        void refresh(int cid);
    }

}
