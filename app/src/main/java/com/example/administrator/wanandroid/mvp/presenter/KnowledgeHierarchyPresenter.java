package com.example.administrator.wanandroid.mvp.presenter;

import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.TreeBean;
import com.example.administrator.wanandroid.mvp.contract.KnowledgeHierarchyContract;
import com.example.administrator.wanandroid.mvp.mode.KnowledgeHierarchyModel;
import com.example.administrator.wanandroid.net.RxScheduler;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2019/3/4.
 */

public class KnowledgeHierarchyPresenter extends BasePresenter<KnowledgeHierarchyContract.View> implements KnowledgeHierarchyContract.presenter {

    private KnowledgeHierarchyContract.Model model;
    private boolean isFirst = true;

    public KnowledgeHierarchyPresenter() {
        model = new KnowledgeHierarchyModel();
    }

    @Override
    public void getTreeDate() {
        if (!isViewAttached()) {
            return;
        }
        if (isFirst) {
            mView.showLoading();
        }
        isFirst = false;
        model.getTree().compose(RxScheduler.<BaseObjectBean<List<TreeBean>>>Obs_io_main())
                .as(mView.<BaseObjectBean<List<TreeBean>>>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean<List<TreeBean>>>() {
                    @Override
                    public void accept(BaseObjectBean<List<TreeBean>> listBaseObjectBean) throws Exception {
                        mView.hideLoading();
                        mView.showTree(listBaseObjectBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                    }
                });
    }


}
