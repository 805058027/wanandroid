package com.example.administrator.wanandroid.net;


import com.example.administrator.wanandroid.bean.BannerBean;
import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.FeedArticleListBean;
import com.example.administrator.wanandroid.bean.HotKeyBean;
import com.example.administrator.wanandroid.bean.LoginBean;
import com.example.administrator.wanandroid.bean.NaviBean;
import com.example.administrator.wanandroid.bean.ProjectListBean;
import com.example.administrator.wanandroid.bean.ProjectTreeBean;
import com.example.administrator.wanandroid.bean.QueryDataBean;
import com.example.administrator.wanandroid.bean.TreeBean;
import com.example.administrator.wanandroid.bean.TreeListBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    /**
     * 获取轮播图
     */
    @GET("banner/json")
    Observable<BaseObjectBean<List<BannerBean>>> getBanner();

    /**
     * 首页文章列表
     */
    @GET("article/list/{num}/json")
    Observable<BaseObjectBean<FeedArticleListBean>> getFeedArticleList(@Path("num") int num);

    /**
     * 登录接口
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseObjectBean<LoginBean>> Login(@Field("username") String username,
                                                @Field("password") String password);

    /**
     * 收藏站内文章
     */
    @POST("lg/collect/{id}/json")
    Observable<BaseObjectBean> addCollectArticle(@Path("id") int id);

    /**
     * 取消收藏文章
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseObjectBean> cancelCollectArticle(@Path("id") int id);

    /**
     * 知识体系数据
     */
    @GET("tree/json")
    Observable<BaseObjectBean<List<TreeBean>>> getTree();

    /**
     * 知识体系下的文章
     */
    @GET("article/list/{page}/json")
    Observable<BaseObjectBean<TreeListBean>> getTreeList(@Path("page") int page,
                                                         @Query("cid ") int cid);

    /**
     * 导航数据
     */
    @GET("navi/json")
    Observable<BaseObjectBean<List<NaviBean>>> getNavi();

    /**
     * 项目分类
     */
    @GET("project/tree/json")
    Observable<BaseObjectBean<List<ProjectTreeBean>>> getProjectTree();

    /**
     * 项目列表数据
     */
    @GET("project/list/{page}/json")
    Observable<BaseObjectBean<ProjectListBean>> getProjectList(@Path("page") int page,
                                                               @Query("cid") int cid);

    /**
     * 搜索热词
     */
    @GET("hotkey/json")
    Observable<BaseObjectBean<List<HotKeyBean>>> getHotKey();

    /**
     * 搜索
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    Observable<BaseObjectBean<QueryDataBean>> getQueryData(@Path("page") int page,
                                                           @Field("k") String k);
}
