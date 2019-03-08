package com.example.administrator.wanandroid.mvp.presenter;

import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.ProjectTreeBean;
import com.example.administrator.wanandroid.mvp.contract.ProjectContract;
import com.example.administrator.wanandroid.mvp.mode.ProjectModel;
import com.example.administrator.wanandroid.net.RxScheduler;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2019/3/5.
 */

public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    ProjectContract.Model mModel;

    public ProjectPresenter() {
        mModel = new ProjectModel();
    }

    @Override
    public void getProjectTree() {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        mModel.getProjectTree().compose(RxScheduler.<BaseObjectBean<List<ProjectTreeBean>>>Obs_io_main())
                .as(mView.<BaseObjectBean<List<ProjectTreeBean>>>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean<List<ProjectTreeBean>>>() {
                    @Override
                    public void accept(BaseObjectBean<List<ProjectTreeBean>> listBaseObjectBean) throws Exception {
                        mView.hideLoading();
                        mView.showProjectTree(listBaseObjectBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        mView.hideLoading();
                    }
                });
    }

}
