package com.example.administrator.wanandroid.base;

import android.arch.lifecycle.Lifecycle;

import com.example.administrator.wanandroid.util.CommonUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

/**
 * Created by Administrator on 2019/3/7.
 */

public abstract class BaseMvpDialogFragment<T extends BasePresenter> extends BaseDialogFragment implements BaseView {

    protected T mPresenter;

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(getActivity(), Lifecycle.Event.ON_DESTROY));
    }

    @Override
    public void showSnackBar(String message) {
        CommonUtils.showSnackMessage(getActivity(), message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }
}