package com.basicproject.demo.main.rx.rxhttp.observer;

import android.app.Dialog;


import com.basicproject.demo.common.utils.ToastUtil;
import com.basicproject.demo.main.rx.rxhttp.RxGo;
import com.basicproject.demo.main.rx.rxhttp.base.BaseStringObserver;

import io.reactivex.disposables.Disposable;


/**
 *自定义Observer 处理string回调
 */

public abstract class StringObserver extends BaseStringObserver {

    private Dialog mProgressDialog;

    public StringObserver() {
    }

    public StringObserver(Dialog progressDialog) {
        mProgressDialog = progressDialog;
    }

    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract void onError(String errorMsg);

    /**
     * 成功回调
     *
     * @param data 结果
     */
    protected abstract void onSuccess(String data);


    @Override
    public void doOnSubscribe(Disposable d) {
        RxGo.addDisposable(d);
    }

    @Override
    public void doOnError(String errorMsg) {
        dismissLoading();
        ToastUtil.showViewToast(errorMsg);
        onError(errorMsg);
    }

    @Override
    public void doOnNext(String string) {
        onSuccess(string);
    }


    @Override
    public void doOnCompleted() {
        dismissLoading();
    }

    /**
     * 隐藏loading对话框
     */
    private void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
