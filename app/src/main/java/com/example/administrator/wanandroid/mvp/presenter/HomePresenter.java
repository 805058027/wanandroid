package com.example.administrator.wanandroid.mvp.presenter;

import com.example.administrator.wanandroid.app.Constants;
import com.example.administrator.wanandroid.base.BasePresenter;
import com.example.administrator.wanandroid.bean.BannerBean;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.FeedArticleListBean;
import com.example.administrator.wanandroid.mvp.contract.HomeContract;
import com.example.administrator.wanandroid.mvp.mode.HomeModel;
import com.example.administrator.wanandroid.net.RxScheduler;
import com.example.administrator.wanandroid.util.CommonUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2019/2/28.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private boolean isRefresh = true;
    private boolean isFirst = true;
    private int PAGE = 1;

    private HomeContract.Model model;

    public HomePresenter() {
        model = new HomeModel();
    }


    @Override
    public void getHomeData() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        if (isFirst) {
            mView.showLoading();
        }
        Observable<BaseObjectBean<List<BannerBean>>> mBanner = model.getBanner();
        Observable<BaseObjectBean<FeedArticleListBean>> mFeedArtice = model.getFeedArticleList(PAGE);
        Observable.zip(mBanner, mFeedArtice, new BiFunction<BaseObjectBean<List<BannerBean>>, BaseObjectBean<FeedArticleListBean>, HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> apply(BaseObjectBean<List<BannerBean>> listBaseObjectBean, BaseObjectBean<FeedArticleListBean> feedArticleListBeanBaseObjectBean) throws Exception {
                return createResponseMap(listBaseObjectBean, feedArticleListBeanBaseObjectBean);
            }
        }).compose(RxScheduler.<HashMap<String, Object>>Obs_io_main())
                .as(mView.<HashMap<String, Object>>bindAutoDispose())
                .subscribe(new Consumer<HashMap<String, Object>>() {
                    @Override
                    public void accept(HashMap<String, Object> stringObjectHashMap) throws Exception {
                        BaseObjectBean<List<BannerBean>> bannerResponse = CommonUtils.cast(stringObjectHashMap.get(Constants.BANNER_DATA));
                        if (bannerResponse != null) {
                            mView.showBannerData(bannerResponse.getData());
                        }
                        BaseObjectBean<FeedArticleListBean> feedArticleListResponse = CommonUtils.cast(stringObjectHashMap.get(Constants.ARTICLE_DATA));
                        if (feedArticleListResponse != null) {
                            mView.showArticleList(feedArticleListResponse.getData(), isRefresh);
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });

    }

    @Override
    public void loadMore() {
        if (!isViewAttached()) {
            return;
        }
        PAGE++;
        isFirst = false;
        isRefresh = false;
        getFeedArticleList();
    }

    @Override
    public void Refresh() {
        if (!isViewAttached()) {
            return;
        }
        PAGE = 1;
        isFirst = false;
        isRefresh = true;
        getHomeData();
    }

    private void getFeedArticleList() {
        model.getFeedArticleList(PAGE).compose(RxScheduler.<BaseObjectBean<FeedArticleListBean>>Obs_io_main())
                .as(mView.<BaseObjectBean<FeedArticleListBean>>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean<FeedArticleListBean>>() {
                    @Override
                    public void accept(BaseObjectBean<FeedArticleListBean> feedArticleListBeanBaseObjectBean) throws Exception {
                        if (feedArticleListBeanBaseObjectBean != null) {
                            mView.showArticleList(feedArticleListBeanBaseObjectBean.getData(), isRefresh);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                    }
                });
    }

    private HashMap<String, Object> createResponseMap(BaseObjectBean<List<BannerBean>> bannerResponse,
                                                      BaseObjectBean<FeedArticleListBean> feedArticleListResponse) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constants.BANNER_DATA, bannerResponse);
        map.put(Constants.ARTICLE_DATA, feedArticleListResponse);
        return map;
    }


    @Override
    public void addCollectArticle(int id, final int position) {
        mView.showLoading();
        model.addCollectArticle(id).compose(RxScheduler.<BaseObjectBean>Obs_io_main())
                .as(mView.<BaseObjectBean>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean>() {
                    @Override
                    public void accept(BaseObjectBean baseObjectBean) throws Exception {
                        mView.addCollectArticle(baseObjectBean, position);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void cancelCollectArticle(int id, final int position) {
        mView.showLoading();
        model.cancelCollectArticle(id).compose(RxScheduler.<BaseObjectBean>Obs_io_main())
                .as(mView.<BaseObjectBean>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean>() {
                    @Override
                    public void accept(BaseObjectBean bean) throws Exception {
                        mView.hideLoading();
                        mView.cancelAddCollectArticle(bean, position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                    }
                });
    }
}
