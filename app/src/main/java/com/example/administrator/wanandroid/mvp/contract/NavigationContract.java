package com.example.administrator.wanandroid.mvp.contract;

import com.example.administrator.wanandroid.base.BaseView;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.NaviBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2019/3/4.
 */

public interface NavigationContract {

    interface Model {
        Observable<BaseObjectBean<List<NaviBean>>> getNavi();
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void showNavi(BaseObjectBean<List<NaviBean>> bean);
    }

    interface Presenter {
        void getNavi();
    }
}
