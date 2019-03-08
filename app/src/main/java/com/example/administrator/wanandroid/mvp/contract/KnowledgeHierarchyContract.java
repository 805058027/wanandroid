package com.example.administrator.wanandroid.mvp.contract;


import com.example.administrator.wanandroid.base.BaseView;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.TreeBean;

import java.util.List;

import io.reactivex.Observable;


public interface KnowledgeHierarchyContract {

    interface Model {
        Observable<BaseObjectBean<List<TreeBean>>> getTree();
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void showTree(BaseObjectBean<List<TreeBean>> tree);
    }

    interface presenter {
        void getTreeDate();
    }

}
