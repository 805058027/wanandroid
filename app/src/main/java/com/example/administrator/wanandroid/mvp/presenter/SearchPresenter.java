package com.example.administrator.wanandroid.mvp.presenter;

import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.HotKeyBean;
import com.example.administrator.wanandroid.mvp.contract.SearchContract;
import com.example.administrator.wanandroid.mvp.mode.SearchModel;
import com.example.administrator.wanandroid.net.RxScheduler;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2019/3/7.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    SearchContract.Model model;

    public SearchPresenter() {
        model = new SearchModel();
    }

    @Override
    public void getHotKey() {
        if (!isViewAttached()) {
            return;
        }
        model.getHotKey().compose(RxScheduler.<BaseObjectBean<List<HotKeyBean>>>Obs_io_main())
                .as(mView.<BaseObjectBean<List<HotKeyBean>>>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean<List<HotKeyBean>>>() {
                    @Override
                    public void accept(BaseObjectBean<List<HotKeyBean>> bean) throws Exception {
                        mView.showHotKey(bean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                    }
                });
    }
}
