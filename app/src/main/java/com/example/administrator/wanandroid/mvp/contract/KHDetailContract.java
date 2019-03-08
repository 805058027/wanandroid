package com.example.administrator.wanandroid.mvp.contract;

import com.example.administrator.wanandroid.base.BaseView;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.TreeListBean;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2019/3/8.
 */

public interface KHDetailContract {

    interface Model {
        Observable<BaseObjectBean<TreeListBean>> getTreeList(int page, int cid);
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void showTreeList(TreeListBean bean, boolean isRefresh);
    }

    interface Presenter {
        void getTreeList(int cid);

        void loadMore(int cid);
    }

}
