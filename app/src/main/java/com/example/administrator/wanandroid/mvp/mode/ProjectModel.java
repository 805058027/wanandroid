package com.example.administrator.wanandroid.mvp.mode;

import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.ProjectListBean;
import com.example.administrator.wanandroid.bean.ProjectTreeBean;
import com.example.administrator.wanandroid.mvp.contract.ProjectContract;
import com.example.administrator.wanandroid.net.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2019/3/5.
 */

public class ProjectModel implements ProjectContract.Model {

    @Override
    public Observable<BaseObjectBean<List<ProjectTreeBean>>> getProjectTree() {
        return RetrofitClient.getInstance().getApi().getProjectTree();
    }
}
