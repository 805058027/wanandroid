package com.example.administrator.wanandroid.mvp.presenter;

import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.NaviBean;
import com.example.administrator.wanandroid.mvp.contract.NavigationContract;
import com.example.administrator.wanandroid.mvp.mode.NavigationModel;
import com.example.administrator.wanandroid.net.RxScheduler;

import java.util.List;

import io.reactivex.functions.Consumer;


public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {

    private NavigationContract.Model model;

    public NavigationPresenter() {
        model = new NavigationModel();
    }

    @Override
    public void getNavi() {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.getNavi().compose(RxScheduler.<BaseObjectBean<List<NaviBean>>>Obs_io_main())
                .as(mView.<BaseObjectBean<List<NaviBean>>>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean<List<NaviBean>>>() {
                    @Override
                    public void accept(BaseObjectBean<List<NaviBean>> bean) throws Exception {
                        mView.hideLoading();
                        mView.showNavi(bean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        mView.onError(throwable);
                    }
                });
    }
}
