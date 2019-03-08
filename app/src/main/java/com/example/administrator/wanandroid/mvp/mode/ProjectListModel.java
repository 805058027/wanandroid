package com.example.administrator.wanandroid.mvp.mode;

import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.ProjectListBean;
import com.example.administrator.wanandroid.mvp.contract.ProjectListContract;
import com.example.administrator.wanandroid.net.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2019/3/6.
 */

public class ProjectListModel implements ProjectListContract.Model {

    @Override
    public Observable<BaseObjectBean<ProjectListBean>> getProjectList(int page, int cid) {
        return RetrofitClient.getInstance().getApi().getProjectList(page, cid);
    }
}
