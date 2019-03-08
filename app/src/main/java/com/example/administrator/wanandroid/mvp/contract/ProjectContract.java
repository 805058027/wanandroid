package com.example.administrator.wanandroid.mvp.contract;

import com.example.administrator.wanandroid.base.BaseView;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.ProjectListBean;
import com.example.administrator.wanandroid.bean.ProjectTreeBean;

import java.util.List;

import io.reactivex.Observable;


public interface ProjectContract {

    interface Model {
        Observable<BaseObjectBean<List<ProjectTreeBean>>> getProjectTree();
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void showProjectTree(BaseObjectBean<List<ProjectTreeBean>> mTreeBean);

    }

    interface Presenter {

        void getProjectTree();

    }

}
