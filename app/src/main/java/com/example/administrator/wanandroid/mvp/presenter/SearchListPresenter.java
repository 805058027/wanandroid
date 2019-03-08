package com.example.administrator.wanandroid.mvp.presenter;

import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.QueryDataBean;
import com.example.administrator.wanandroid.mvp.contract.SearchListContract;
import com.example.administrator.wanandroid.mvp.mode.SearchListModel;
import com.example.administrator.wanandroid.net.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2019/3/7.
 */

public class SearchListPresenter extends BasePresenter<SearchListContract.View> implements SearchListContract.Presenter {

    private int PAGE = 1;

    private SearchListContract.Model model;

    public SearchListPresenter() {
        model = new SearchListModel();
    }

    @Override
    public void queryData(String key) {
        if (!isViewAttached()) {
            return;
        }
        QueryData(key, false);
    }


    @Override
    public void loadMore(String key) {
        if (!isViewAttached()) {
            return;
        }
        PAGE++;
        QueryData(key, true);
    }

    private void QueryData(String key, final boolean isLoadMore) {
        if (!isLoadMore) {
            mView.showLoading();
        }
        model.queryData(PAGE, key).compose(RxScheduler.<BaseObjectBean<QueryDataBean>>Obs_io_main())
                .as(mView.<BaseObjectBean<QueryDataBean>>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean<QueryDataBean>>() {
                    @Override
                    public void accept(BaseObjectBean<QueryDataBean> queryDataBeanBaseObjectBean) throws Exception {
                        mView.showQueryData(queryDataBeanBaseObjectBean.getData(), isLoadMore);
                        if (!isLoadMore) {
                            mView.hideLoading();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        if (!isLoadMore) {
                            mView.hideLoading();
                        }
                    }
                });
    }

}
