package com.example.administrator.wanandroid.mvp.presenter;

import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.TreeListBean;
import com.example.administrator.wanandroid.mvp.contract.KHDetailContract;
import com.example.administrator.wanandroid.mvp.mode.KHDetailModel;
import com.example.administrator.wanandroid.net.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2019/3/8.
 */

public class KHDetailPresenter extends BasePresenter<KHDetailContract.View> implements KHDetailContract.Presenter {

    private int PAGE = 0;
    private KHDetailContract.Model model;

    public KHDetailPresenter() {
        model = new KHDetailModel();
    }

    @Override
    public void getTreeList(int cid) {
        PAGE = 0;
        TreeList(cid, true);
    }


    @Override
    public void loadMore(int cid) {
        PAGE++;
        TreeList(cid, false);
    }

    private void TreeList(int cid, final boolean isRefresh) {
        if (!isViewAttached()) {
            return;
        }
        model.getTreeList(PAGE, cid).compose(RxScheduler.<BaseObjectBean<TreeListBean>>Obs_io_main())
                .as(mView.<BaseObjectBean<TreeListBean>>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean<TreeListBean>>() {
                    @Override
                    public void accept(BaseObjectBean<TreeListBean> treeListBeanBaseObjectBean) throws Exception {
                        mView.showTreeList(treeListBeanBaseObjectBean.getData(), isRefresh);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

}
