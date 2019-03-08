package com.example.administrator.wanandroid.mvp.mode;

import com.example.administrator.wanandroid.bean.BaseObjectBean;
import com.example.administrator.wanandroid.bean.TreeBean;
import com.example.administrator.wanandroid.mvp.contract.KnowledgeHierarchyContract;
import com.example.administrator.wanandroid.net.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;

public class KnowledgeHierarchyModel implements KnowledgeHierarchyContract.Model {

    @Override
    public Observable<BaseObjectBean<List<TreeBean>>> getTree() {
        return RetrofitClient.getInstance().getApi().getTree();
    }
}
