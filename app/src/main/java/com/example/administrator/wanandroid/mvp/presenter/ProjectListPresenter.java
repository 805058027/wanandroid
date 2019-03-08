package com.example.administrator.wanandroid.mvp.presenter;

import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.ProjectListBean;
import com.example.administrator.wanandroid.mvp.contract.ProjectListContract;
import com.example.administrator.wanandroid.mvp.mode.ProjectListModel;
import com.example.administrator.wanandroid.net.RxScheduler;

import io.reactivex.functions.Consumer;


public class ProjectListPresenter extends BasePresenter<ProjectListContract.View> implements ProjectListContract.Presenter {

    private ProjectListContract.Model model;
    private int PAGE = 1;


    public ProjectListPresenter() {
        model = new ProjectListModel();
    }

    @Override
    public void getProjectList(int cid) {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        getProjectListRequest(cid);
    }


    @Override
    public void loadMore(int cid) {
        if (!isViewAttached()) {
            return;
        }
        PAGE++;
        getProjectListRequest(cid);
    }

    @Override
    public void refresh(int cid) {
        if (!isViewAttached()) {
            return;
        }
        PAGE = 1;
        getProjectListRequest(cid);
    }

    private void getProjectListRequest(int cid) {
        model.getProjectList(PAGE, cid).compose(RxScheduler.<BaseObjectBean<ProjectListBean>>Obs_io_main())
                .as(mView.<BaseObjectBean<ProjectListBean>>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean<ProjectListBean>>() {
                    @Override
                    public void accept(BaseObjectBean<ProjectListBean> listBaseObjectBean) throws Exception {
                        mView.hideLoading();
                        if (PAGE == 1) {
                            mView.showProjectList(listBaseObjectBean, true);
                        } else {
                            mView.showProjectList(listBaseObjectBean, false);
                        }
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
